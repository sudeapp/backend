package com.sudeca.impl;

import com.sudeca.dto.ComprobanteDTO;
import com.sudeca.dto.ComprobanteDetDTO;
import com.sudeca.model.Comprobante;
import com.sudeca.model.ComprobanteDet;
import com.sudeca.model.Usuario;
import com.sudeca.repository.*;
import com.sudeca.services.IComprobanteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ComprobanteServiceImpl implements IComprobanteService {

    @Autowired
    private ComprobanteRepository comprobanteRepository;
    @Autowired
    private ComprobanteDetRepository detalleRepository;
    @Autowired
    private PlanCatalogoRepository planCatalogoRepository;
    @Autowired
    private AuxiliarRepository auxiliarRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    public ComprobanteServiceImpl() {
    }
    private static final Logger logger = LogManager.getLogger();
    private BigDecimal totalDebe = BigDecimal.ZERO;
    private BigDecimal totalHaber = BigDecimal.ZERO;
    private BigDecimal totalDiferencia = BigDecimal.ZERO;

    @Override
    public Comprobante saveComprobanteWithDetails(ComprobanteDTO comprobanteDTO) {
        // carga los totales (Débitos = Créditos = diferencia)
        Comprobante comprobante = new Comprobante();

        comprobante.setFechaCbte(comprobanteDTO.getFechaCbte());
        comprobante.setPeriodo(comprobanteDTO.getPeriodo());
        comprobante.setEstatusCbte(comprobanteDTO.getEstatusCbte());
        comprobante.setFechaVerificacion(comprobanteDTO.getFechaVerificacion());
        comprobante.setIdTcbte(comprobanteDTO.getIdTcbte());
        comprobante.setIdCaho(comprobanteDTO.getIdCaho());
        comprobante.setFechaCreacion(LocalDate.now());
        comprobante.setNroCbte(comprobanteDTO.getNroCbte());
        // Campos de auditoría
        comprobante.setUsuarioCreacion(usuarioRepository.findById(comprobanteDTO.getIdUsuarioCreacion())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado")));

        List<ComprobanteDet> detalleCompro = new ArrayList<>();

        // Procesar detalles actualizados
        for (ComprobanteDetDTO detalleDTO : comprobanteDTO.getComprobanteDet()) {
            detalleCompro.add(convertirRequestADetalle(detalleDTO));
        }
        comprobante.setComprobanteDet(detalleCompro);
        cargarDebCre(comprobante.getComprobanteDet());
        comprobante.setBsMontoDebito(totalDebe);
        comprobante.setBsMontoCredito(totalHaber);
        comprobante.setBsMontoDiferencia(totalDiferencia);

        // Establecer la relación bidireccional
        for (ComprobanteDet detalle : comprobante.getComprobanteDet()) {
            detalle.setComprobante(comprobante);
        }

        // Guardar el comprobante (la cascada guardará los detalles)
        return comprobanteRepository.save(comprobante);
    }

    private void cargarDebCre(List<ComprobanteDet> detalles) {
        totalDebe = BigDecimal.ZERO;
        totalHaber = BigDecimal.ZERO;

        for (ComprobanteDet detalle : detalles) {
            if ("D".equals(detalle.getDbcr())) {
                totalDebe = totalDebe.add(detalle.getBsMonto());
            } else if ("C".equals(detalle.getDbcr())) {
                totalHaber = totalHaber.add(detalle.getBsMonto());
            }
        }

        totalDiferencia = totalDebe.subtract(totalHaber);
    }

    @Override
    public List<Comprobante> ultimosComprobantes(Long idCaho,Long idUsuario){
        Pageable pageable = PageRequest.of(0, 10);
        return comprobanteRepository.ultimosComprobantes(idCaho,idUsuario,pageable);
    }
    @Override
    public List<Comprobante> buscarComprobantes(Long idCaho,
                                                LocalDate fechaInicio,
                                                LocalDate fechaFin,
                                                Long nroComprobante,
                                                String nombreUsuario,
                                                Integer idEstatus) {

        // Crear la especificación dinámica
        StringBuilder logMessage = new StringBuilder("Búsqueda de comprobantes con filtros: ");

        if (fechaInicio != null) {
            logMessage.append("[Fecha Inicio: ").append(fechaInicio).append("] ");
        }
        if (fechaFin != null) {
            logMessage.append("[Fecha Fin: ").append(fechaFin).append("] ");
        }
        if (nroComprobante != null) {
            logMessage.append("[Nro Comprobante: ").append(nroComprobante).append("] ");
        }
        if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
            logMessage.append("[Usuario: ").append(nombreUsuario).append("] ");
        }

        logger.info(logMessage.toString());

        // Construir la especificación
        Specification<Comprobante> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (idCaho != null) {
                predicates.add(cb.equal(root.get("idCaho"), idCaho));
            }

            if (fechaInicio != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("fechaCbte"), fechaInicio));
            }
            if (fechaFin != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("fechaCbte"), fechaFin));
            }
            if (nroComprobante != null) {
                predicates.add(cb.equal(root.get("nroCbte"), nroComprobante));
            }
            if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
                Join<Comprobante, Usuario> usuarioJoin = root.join("usuarioCreacion", JoinType.INNER);
                predicates.add(cb.like(
                        cb.lower(usuarioJoin.get("nombre")),
                        "%" + nombreUsuario.toLowerCase() + "%"
                ));
            }

            if (idEstatus != null) {
                predicates.add(cb.equal(root.get("estatusCbte"), idEstatus));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return comprobanteRepository.findAll(spec);
    }
    @Override
    public Comprobante actualizarComprobante(Long id, ComprobanteDTO request) {
        Comprobante comprobante = comprobanteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comprobante no encontrado"));

        // Actualizar campos principales
        actualizarCamposComprobante(comprobante, request, request.getIdUsuarioActualizacion());

        // Procesar detalles
        List<ComprobanteDet> nuevosDetalles = new ArrayList<>();

        for (ComprobanteDetDTO detRequest : request.getComprobanteDet()) {
            ComprobanteDet nuevoDetalle = convertirRequestADetalle(detRequest);
            nuevoDetalle.setComprobante(comprobante);
            nuevosDetalles.add(nuevoDetalle);
        }
        procesarDetalles(comprobante,nuevosDetalles);

        // Recalcular montos
        recalcularMontosComprobante(comprobante);

        return comprobanteRepository.save(comprobante);
    }

    private void actualizarCamposComprobante(Comprobante comprobante,
                                             ComprobanteDTO request,
                                             Long usuarioActualId) {
        comprobante.setFechaCbte(request.getFechaCbte());
        comprobante.setPeriodo(request.getPeriodo());
        comprobante.setEstatusCbte(request.getEstatusCbte());
        //comprobante.setFechaVerificacion(request.getFechaVerificacion());
        //comprobante.setIdUsuarioVerificacion(request.getIdUsuarioVerificacion());
        comprobante.setFechaVerificacion(null);
        comprobante.setIdUsuarioVerificacion(null);
        comprobante.setIdTcbte(request.getIdTcbte());

        // Campos de auditoría
        //comprobante.setFechaActualizacion(LocalDate.now());
        //comprobante.setIdUsuarioActualizacion(usuarioActualId);
        comprobante.setFechaModificacion(LocalDate.now());
        comprobante.setIdUsuarioModificacion(usuarioActualId);
    }

    private ComprobanteDet convertirRequestADetalle(ComprobanteDetDTO request) {
        ComprobanteDet detalle = new ComprobanteDet();
        detalle.setIdPlanContable(request.getIdPlanContable());
        detalle.setPlanCatalogo(planCatalogoRepository.findById(request.getIdPlanCatalogo())
                .orElseThrow(() -> new EntityNotFoundException("Plan Catalogo no encontrado")));

        detalle.setIdTaux(request.getIdTaux());

        if (request.getIdAuxi() != null) {
            detalle.setAuxiliar(auxiliarRepository.findById(request.getIdAuxi())
                    .orElseThrow(() -> new EntityNotFoundException("Auxiliar no encontrado")));
        }

        detalle.setIdOpec(request.getIdOpec());
        detalle.setNroDoc(request.getNroDoc());
        detalle.setFechaDoc(request.getFechaDoc());
        detalle.setDbcr(request.getDbcr());
        detalle.setBsMonto(request.getBsMonto());
        detalle.setDescripcion(request.getDescripcion());
        detalle.setLinea(request.getLinea());
        detalle.setTplan(request.getTplan());

        return detalle;
    }

    private void recalcularMontosComprobante(Comprobante comprobante) {
        BigDecimal totalDebito = BigDecimal.ZERO;
        BigDecimal totalCredito = BigDecimal.ZERO;

        for (ComprobanteDet detalle : comprobante.getComprobanteDet()) {
            if ("D".equals(detalle.getDbcr())) {
                totalDebito = totalDebito.add(detalle.getBsMonto());
            } else if ("C".equals(detalle.getDbcr())) {
                totalCredito = totalCredito.add(detalle.getBsMonto());
            }
        }

        comprobante.setBsMontoDebito(totalDebito);
        comprobante.setBsMontoCredito(totalCredito);
        comprobante.setBsMontoDiferencia(totalDebito.subtract(totalCredito).abs());
    }

    private void procesarDetalles(Comprobante comprobanteExistente, List<ComprobanteDet> detallesActualizados) {
        // Crear copia de la colección original
        List<ComprobanteDet> detallesOriginales = new ArrayList<>(comprobanteExistente.getComprobanteDet());
        Map<Long, ComprobanteDet> mapDetallesExistentes = detallesOriginales.stream()
                .collect(Collectors.toMap(ComprobanteDet::getId, d -> d));

        // Procesar detalles actualizados
        for (ComprobanteDet detalleActualizado : detallesActualizados) {
            if (detalleActualizado.getId() != null) {
                // Actualizar detalle existente
                ComprobanteDet detalleExistente = mapDetallesExistentes.get(detalleActualizado.getId());
                if (detalleExistente != null) {
                    actualizarCamposDetalle(detalleExistente, detalleActualizado);
                    mapDetallesExistentes.remove(detalleActualizado.getId());
                }
            } else {
                // Nuevo detalle
                comprobanteExistente.agregarDetalle(detalleActualizado);
            }
        }

        // Eliminar detalles no incluidos
        mapDetallesExistentes.values().forEach(comprobanteExistente::eliminarDetalle);
    }
    private void actualizarCamposDetalle(ComprobanteDet detalleExistente, ComprobanteDet detalleActualizado) {
        detalleExistente.setIdPlanContable(detalleActualizado.getIdPlanContable());
        detalleExistente.setPlanCatalogo(detalleActualizado.getPlanCatalogo());
        detalleExistente.setIdTaux(detalleActualizado.getIdTaux());
        detalleExistente.setAuxiliar(detalleActualizado.getAuxiliar());
        detalleExistente.setIdOpec(detalleActualizado.getIdOpec());
        detalleExistente.setNroDoc(detalleActualizado.getNroDoc());
        detalleExistente.setFechaDoc(detalleActualizado.getFechaDoc());
        detalleExistente.setDbcr(detalleActualizado.getDbcr());
        detalleExistente.setBsMonto(detalleActualizado.getBsMonto());
        detalleExistente.setDescripcion(detalleActualizado.getDescripcion());
        detalleExistente.setLinea(detalleActualizado.getLinea());
        detalleExistente.setTplan(detalleActualizado.getTplan());
    }
}
