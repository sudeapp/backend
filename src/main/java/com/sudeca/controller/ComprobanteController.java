package com.sudeca.controller;

import com.sudeca.dto.ComprobanteDTO;
import com.sudeca.dto.ResponseDTO;
import com.sudeca.dto.ValidaComprobanteDTO;
import com.sudeca.model.Auxiliar;
import com.sudeca.model.Comprobante;
import com.sudeca.model.PlanCatalogo;
import com.sudeca.services.IAuxiliarService;
import com.sudeca.services.IComprobanteService;
import com.sudeca.services.IFuncionesService;
import com.sudeca.services.IPlanCatalogoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/api/comprobante")
public class ComprobanteController {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private IPlanCatalogoService planCatalogoService;
    @Autowired
    private IAuxiliarService auxiliarService;
    @Autowired
    private IFuncionesService funcionesService;
    @Autowired
    private IComprobanteService comprobanteService;

    @GetMapping("/buscar-cuenta")
    public ResponseEntity<ResponseDTO> buscarCaja(@RequestParam("idPlanContable") long idPlanContable,@RequestParam String cuenta) {
        logger.info("buscarCaja: " + cuenta);
        PlanCatalogo plan = planCatalogoService.findByCuenta(idPlanContable,cuenta);
        ResponseDTO responseDTO = new ResponseDTO();
        if (plan != null){
            responseDTO.setData(plan);
            responseDTO.setStatus("success");
            responseDTO.setMessage("Búsqueda");
        }else{
            responseDTO.setData(null);
            responseDTO.setStatus("notFound");
            responseDTO.setMessage("Registro no encontrado");
        }

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/buscar-auxiliar")
    public ResponseEntity<ResponseDTO> buscarAuxiliar(@RequestParam String codigo) {
        logger.info("buscarAux: " + codigo);
        Auxiliar aux = auxiliarService.findByCodAuxiliary(codigo);
        ResponseDTO responseDTO = new ResponseDTO();
        if (aux != null){
            responseDTO.setData(aux);
            responseDTO.setStatus("success");
            responseDTO.setMessage("Búsqueda");
        }else{
            responseDTO.setData(null);
            responseDTO.setStatus("notFound");
            responseDTO.setMessage("Registro no encontrado");
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/fecha-valor")
    public ResponseEntity<ResponseDTO> getFechaValor(@RequestParam("idCaho") int idCaho, @RequestParam("fechaValor") String fechaValor) {
        LocalDate fechaValo = LocalDate.parse(fechaValor); // Convertir String a LocalDate
        logger.info("getFechaValor fecha: "+fechaValor);
        boolean res = funcionesService.obtenerFechaValor(idCaho,fechaValo);
        ResponseDTO responseDTO = new ResponseDTO();
        if (res){
            responseDTO.setData(true);
            responseDTO.setStatus("success");
            responseDTO.setMessage("validación");
        }else{
            responseDTO.setData(false);
            responseDTO.setStatus("notFound");
            responseDTO.setMessage("Registro no encontrado");
        }
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> createComprobante(@RequestBody ComprobanteDTO comprobante) {
        logger.info("createComprobante: " + comprobante);
        ResponseDTO responseDTO = new ResponseDTO();
        try {

            Comprobante savedComprobante = comprobanteService.saveComprobanteWithDetails(comprobante);

            if (savedComprobante != null){
                responseDTO.setData(savedComprobante);
                responseDTO.setStatus("success");
                responseDTO.setMessage("Create");
            }else{
                responseDTO.setData(null);
                responseDTO.setStatus("Error");
                responseDTO.setMessage("Registro no guardado");
            }
        } catch (IllegalArgumentException e) {
            responseDTO.setData(false);
            responseDTO.setStatus("Error");
            responseDTO.setMessage("Registro no guardado");
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/lista-por-filtro")
    public ResponseEntity<List<Comprobante>> buscarComprobantes(
            @RequestParam(value = "idCaho", required = true) Long idCaho,
            @RequestParam(value = "nroComprobante", required = false) Long nroComprobante,
            @RequestParam(value = "fechaInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(value = "fechaFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(value = "nombreUsuario", required = false) String nombreUsuario,
            @RequestParam(value = "idEstatus", required = false) Integer idEstatus) {

        logger.info("Búsqueda de comprobantes - Parámetros: nroComprobante={}, fechaInicio={}, fechaFin={}, nombreUsuario={}",
                nroComprobante, fechaInicio, fechaFin, nombreUsuario);

        try {
            List<Comprobante> resultados = comprobanteService.buscarComprobantes(
                    idCaho,
                    fechaInicio,
                    fechaFin,
                    nroComprobante,
                    nombreUsuario,
                    idEstatus
            );

            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            logger.error("Error en búsqueda de comprobantes", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/ultimos-comprobantes")
    public ResponseEntity<List<Comprobante>> ultimosComprobantes(
            @RequestParam(value = "idCaho", required = true) Long idCaho,
            @RequestParam(value = "idUsuario", required = true) Long idUsuario) {


        try {
            List<Comprobante> resultados = comprobanteService.ultimosComprobantes(
                    idCaho,
                    idUsuario
            );

            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            logger.error("Error en búsqueda de comprobantes", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> actualizarComprobante(
            @PathVariable Long id,
            @RequestBody ComprobanteDTO comprobante) {

        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Comprobante comprobanteActualizado = comprobanteService.actualizarComprobante(id, comprobante);

            if (comprobanteActualizado != null){
                responseDTO.setData(comprobanteActualizado);
                responseDTO.setStatus("success");
                responseDTO.setMessage("Update");
            }else{
                responseDTO.setData(null);
                responseDTO.setStatus("Error");
                responseDTO.setMessage("Registro no actualizado");
            }
        } catch (IllegalArgumentException e) {
            responseDTO.setData(false);
            responseDTO.setStatus("Error");
            responseDTO.setMessage("Registro no actalizado");
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/verificar-comprobante")
    public ResponseEntity<ResponseDTO> getVerificarComprobante(@RequestParam("comprobantes") List<Integer> comprobantes,
                                                               @RequestParam("idUsuario") long idUsuario) {
        logger.info("getVerificarComprobante: "+comprobantes.size());
        logger.info("getVerificarComprobante 2: "+comprobantes.get(0));
        boolean res = true;
                //funcionesService.getVerificaComprobante(idComprobante,idUsuario);
        ResponseDTO responseDTO = new ResponseDTO();
        if (res){
            responseDTO.setData(true);
            responseDTO.setStatus("success");
            responseDTO.setMessage("Verificación");
        }else{
            responseDTO.setData(false);
            responseDTO.setStatus("notFound");
            responseDTO.setMessage("Registro no verificado");
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/actualizar-comprobante")
    public ResponseEntity<ResponseDTO> getActualizarComprobante(@RequestParam("idComprobante") long idComprobante,
                                                               @RequestParam("idUsuario") long idUsuario) {
        logger.info("getActualizarComprobante: "+idComprobante);
        boolean res = funcionesService.getVerificaComprobante(idComprobante,idUsuario);
        ResponseDTO responseDTO = new ResponseDTO();
        if (res){
            responseDTO.setData(true);
            responseDTO.setStatus("success");
            responseDTO.setMessage("Verificación");
        }else{
            responseDTO.setData(false);
            responseDTO.setStatus("notFound");
            responseDTO.setMessage("Registro no verificado");
        }
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/verificar-comprobante")
    public ResponseEntity<ResponseDTO> verificarComprobante(@RequestBody ValidaComprobanteDTO comprobantes) {
        logger.info("createComprobante: " + comprobantes);
        logger.info("getVerificarComprobante: "+comprobantes.getComprobantes().size());
        logger.info("getVerificarComprobante 2: "+comprobantes.getComprobantes().get(0));
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            AtomicBoolean res = new AtomicBoolean(true);
            comprobantes.getComprobantes().forEach(comprobante -> {
                System.out.println("Procesando comprobante ID: " + comprobante.getIdComprobante());
                boolean r = funcionesService.getVerificaComprobante(comprobante.getIdComprobante(),comprobantes.getIdUsuario());
                if(r == false){
                    res.set(false);
                }
            });

            if (res.get()){
                responseDTO.setData(res);
                responseDTO.setStatus("success");
                responseDTO.setMessage("Create");
            }else{
                responseDTO.setData(null);
                responseDTO.setStatus("Error");
                responseDTO.setMessage("Registro no guardado");
            }
        } catch (IllegalArgumentException e) {
            responseDTO.setData(false);
            responseDTO.setStatus("Error");
            responseDTO.setMessage("Registro no guardado");
        }
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/actualizar-comprobante")
    public ResponseEntity<ResponseDTO> actualizarComprobante(@RequestBody ValidaComprobanteDTO comprobantes) {
        logger.info("actualizarComprobante 1: " + comprobantes);
        logger.info("actualizarComprobante 2: "+comprobantes.getComprobantes().size());
        logger.info("actualizarComprobante 2: "+comprobantes.getComprobantes().get(0));
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            AtomicBoolean res = new AtomicBoolean(true);
            comprobantes.getComprobantes().forEach(comprobante -> {
                System.out.println("Procesando comprobante ID: " + comprobante.getIdComprobante());
                boolean r = funcionesService.getActualizaComprobante(comprobante.getIdComprobante(),comprobantes.getIdUsuario());
                if(r == false){
                    res.set(false);
                }
            });

            if (res.get()){
                responseDTO.setData(res);
                responseDTO.setStatus("success");
                responseDTO.setMessage("Create");
            }else{
                responseDTO.setData(null);
                responseDTO.setStatus("Error");
                responseDTO.setMessage("Registro no guardado");
            }
        } catch (IllegalArgumentException e) {
            responseDTO.setData(false);
            responseDTO.setStatus("Error");
            responseDTO.setMessage("Registro no guardado");
        }
        return ResponseEntity.ok(responseDTO);
    }

}
