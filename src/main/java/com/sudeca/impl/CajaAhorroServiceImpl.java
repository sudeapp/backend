package com.sudeca.impl;

import com.sudeca.dto.CajaAhorroDTO;
import com.sudeca.dto.CategoriaDTO;
import com.sudeca.dto.UsuarioCajaDTO;
import com.sudeca.model.*;
import com.sudeca.repository.CajaAhorroRepository;
import com.sudeca.repository.CategoriaCajaAhorroRepository;
import com.sudeca.repository.CategoriaRepository;
import com.sudeca.repository.UsuarioRepository;
import com.sudeca.services.ICajaAhorroService;
import jakarta.validation.ConstraintViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * * Author: Luis Lopez
 **/
@Service
@Component
public class CajaAhorroServiceImpl implements ICajaAhorroService {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private CajaAhorroRepository cajaAhorroRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CategoriaCajaAhorroRepository categoriaCajaAhorroRepository;

    public CajaAhorroServiceImpl() {
    }

    @Override
    public CajaAhorro findByCodigoCaho(String valor) {
        return cajaAhorroRepository.findByCodigoCaho(valor);
    }

    @Override
    public CajaAhorro findByRif(String valor) {
        return cajaAhorroRepository.findByRif(valor);
    }
    @Override
    public CajaAhorro saveCajaAhorro(CajaAhorroDTO request) {
        try {
            // Conversión de DTO a Entity
            CajaAhorro caja = new CajaAhorro();
            caja.setCodigoCaho(request.codigoCaho());
            caja.setRif(request.rif());
            caja.setDescripcion(request.descripcion());
            caja.setNombre(request.nombre());
            caja.setPatrono(request.patrono());
            caja.setSector(request.sector());
            caja.setIdPlanContable(request.idPlan());
            caja.setPeriodosEjercicio(request.periodosEjercicio());
            caja.setUltimoMesCerrado(request.ultimoMesCerrado());
            caja.setUltimoPeriodoCerrado(request.ultimoPeriodoCerrado());
            caja.setUltimoLapsoGenerado(request.ultimoLapsoGenerado());
            caja.setUsuarioCreacion(request.usuarioCreacion());
            // Campos con valores por defecto (se sobreescriben si vienen en el request)
            caja.setMesCierre(request.mesCierre() != null ? request.mesCierre() : (short) 12);
            caja.setLapsoCierreMensual(request.lapsoCierreMensual() != null ? request.lapsoCierreMensual() : (short) 15);

            // Campos opcionales
            caja.setIdMonedaLocal(request.idMonedaLocal());
            caja.setCuentaGananciasPerdidas(request.cuentaGananciasPerdidas());
            caja.setInicioVigencia(request.inicioVigencia());
            caja.setFinVigencia(request.finVigencia());
            caja.setEstatus(request.estatus());

            // Manejo de relaciones
            if (request.categorias() != null && !request.categorias().isEmpty()) {
                request.categorias().forEach(categoriaRequest -> {
                    CategoriaCajaAhorro categoriaCajaAhorro = new CategoriaCajaAhorro();
                    Categoria cat = categoriaRepository.obtenerPorIdCategoria(categoriaRequest.idCategoria());
                    categoriaCajaAhorro.setCategoria(cat);
                    categoriaCajaAhorro.setCajaAhorro(caja); // Establece la relación bidirecciona
                    caja.getCategoriaCajaAhorros().add(categoriaCajaAhorro);
                });
            }

            if (request.usuarios() != null && !request.usuarios().isEmpty()) {
                request.usuarios().forEach(usuariosRequest -> {
                    Usuario usuario = new Usuario();
                    UsuarioRol usuarioRol = new UsuarioRol();
                    usuario.setEmail(usuariosRequest.email());
                    usuario.setUsuario(usuariosRequest.email());
                    usuario.setNombre(usuariosRequest.nombre());
                    usuario.setPass(usuariosRequest.pass());
                    usuario.setEstatus(usuariosRequest.estatus());

                    usuarioRol.setUsuario(usuario);
                    usuarioRol.setRol(usuariosRequest.rol());
                    usuarioRol.setFechaReg((Timestamp.from(Instant.now())));

                    usuario.getRol().add(usuarioRol);
                    usuario.setCajaAhorro(caja);
                    caja.getUsuarios().add(usuario);

                    //CREAR USUARIOROL
                });
            }
            return cajaAhorroRepository.save(caja);
        } catch (DataIntegrityViolationException ex) {
            // Manejar errores de restricciones únicas (UK) y FK
            String errorMessage = "Error de integridad de datos: " + ex.getMostSpecificCause().getMessage();
            if (ex.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cve = (ConstraintViolationException) ex.getCause();
                errorMessage += cve.getConstraintViolations() != null ?
                        "Violación de restricción: " + cve.getConstraintViolations() :
                        ex.getMostSpecificCause().getMessage();
            }
            throw new RuntimeException(errorMessage, ex);

        } catch (Exception ex) {
            // Manejo genérico de otros errores
            throw new RuntimeException("Error al guardar la caja de ahorro: " + ex.getMessage(), ex);
        }
    }
    @Override
    public CajaAhorro updateCajaAhorro(CajaAhorroDTO updateDTO) {

        try {

            CajaAhorro existingCaja = cajaAhorroRepository.findById(updateDTO.idCaho())
                    .orElseThrow(() -> new OpenApiResourceNotFoundException("Caja no encontrada con id: " + updateDTO.idCaho() ));

            // Actualizar campos básicos
            existingCaja.setCodigoCaho(updateDTO.codigoCaho());
            existingCaja.setRif(updateDTO.rif());
            existingCaja.setNombre(updateDTO.nombre());
            existingCaja.setPatrono(updateDTO.patrono());
            existingCaja.setSector(updateDTO.sector());
            existingCaja.setMesCierre(updateDTO.mesCierre());
            existingCaja.setLapsoCierreMensual(updateDTO.lapsoCierreMensual());
            existingCaja.setUltimoMesCerrado(updateDTO.ultimoMesCerrado());
            existingCaja.setInicioVigencia(updateDTO.inicioVigencia());
            existingCaja.setFinVigencia(updateDTO.finVigencia());
            existingCaja.setUltimoLapsoGenerado(updateDTO.ultimoLapsoGenerado());
            //existingCaja.setUsuarioModificacion(usuarioModificacion);
            //existingCaja.setFechaModificacion(new Date());

            // Actualizar categorías
            actualizarCategorias(existingCaja, updateDTO.categorias());

            // Actualizar usuarios
            actualizarUsuarios(existingCaja, updateDTO.usuarios());

            CajaAhorro updatedCaja = cajaAhorroRepository.save(existingCaja);
            return updatedCaja;

        } catch (Exception ex) {
            return null;
        }
    }

    private void actualizarCategorias(CajaAhorro caja, List<CategoriaDTO> categoriasDTO) {
        // Eliminar categorías existentes
        categoriaCajaAhorroRepository.deleteByIdCaho(caja.getIdCaho());

        // Agregar nuevas categorías
        for (CategoriaDTO categoriaDTO : categoriasDTO) {
            Categoria categoria = categoriaRepository.findById(categoriaDTO.idCategoria())
                    .orElseThrow(() -> new OpenApiResourceNotFoundException("Categoría no encontrada"));

            CategoriaCajaAhorro relacion = new CategoriaCajaAhorro();
            relacion.setCajaAhorro(caja);
            relacion.setCategoria(categoria);
            categoriaCajaAhorroRepository.save(relacion);
        }
    }

    private void actualizarUsuarios(CajaAhorro caja, List<UsuarioCajaDTO> usuariosDTO) {
        for (UsuarioCajaDTO usuarioDTO : usuariosDTO) {
            if (usuarioDTO.idUsuario() == 0) {
                // Nuevo usuario
                Usuario nuevoUsuario = new Usuario();
                UsuarioRol usuarioRol = new UsuarioRol();
                nuevoUsuario.setNombre(usuarioDTO.nombre());
                nuevoUsuario.setEmail(usuarioDTO.email());
                nuevoUsuario.setPass(usuarioDTO.pass());
                //nuevoUsuario.setRol(usuarioDTO.getRol());
                nuevoUsuario.setEstatus(usuarioDTO.estatus());
                nuevoUsuario.setCajaAhorro(caja);

                usuarioRol.setUsuario(nuevoUsuario);
                usuarioRol.setRol(usuarioDTO.rol());
                usuarioRol.setFechaReg((Timestamp.from(Instant.now())));

                nuevoUsuario.getRol().add(usuarioRol);
                usuarioRepository.save(nuevoUsuario);
            } else {
                // Usuario existente
                Usuario usuario = usuarioRepository.findById(usuarioDTO.idUsuario())
                        .orElseThrow(() -> new OpenApiResourceNotFoundException("Usuario no encontrado"));
                usuario.setUsuario(usuario.getUsuario());//conserva el usuario
                usuario.setNombre(usuarioDTO.nombre());
                usuario.setEmail(usuarioDTO.email());

                if (usuarioDTO.pass() != null && !usuarioDTO.pass().isEmpty()) {
                    usuario.setPass(usuarioDTO.pass());
                }

                //usuario.setRol(usuarioDTO.getRol());
                usuario.setEstatus(usuarioDTO.estatus());
                usuarioRepository.save(usuario);
            }
        }

        // Eliminar usuarios removidos
        /*List<Long> idsActuales = usuariosDTO.stream()
                .map(UsuarioCajaDTO::idUsuario)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        usuarioRepository.deleteUsuariosNotInList(caja.getIdCaho(), idsActuales);*/
    }
}
