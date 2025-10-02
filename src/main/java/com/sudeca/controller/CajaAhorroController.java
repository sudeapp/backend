package com.sudeca.controller;

import com.sudeca.dto.*;
import com.sudeca.model.CajaAhorro;
import com.sudeca.model.PlantillaPlanContable;
import com.sudeca.model.view.Vpc;
import com.sudeca.services.ICajaAhorroService;
import com.sudeca.services.IFuncionesService;
import com.sudeca.services.IPPlanContableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cajas-ahorro")
public class CajaAhorroController {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private ICajaAhorroService cajaAhorroService;
    @Autowired
    private IFuncionesService funcionesService;
    @Autowired
    private IPPlanContableService ppContableService;
    @PostMapping
    public ResponseEntity<?> saveCajaAhorro(@RequestBody CajaAhorroDTO request) {
        try {
            CajaAhorro savedCaja = cajaAhorroService.saveCajaAhorro(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCaja);
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error de duplicado: " + ex.getMostSpecificCause().getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al crear la caja de ahorro: " + e.getMessage());
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<CajaAhorro> buscarCaja(
            @RequestParam String tipo,
            @RequestParam String valor) {

        CajaAhorro caja;
        if ("codigo".equals(tipo)) {
            caja = cajaAhorroService.findByCodigoCaho(valor);
        } else if ("rif".equals(tipo)) {
            caja = cajaAhorroService.findByRif(valor);
        } else {
            return ResponseEntity.badRequest().build();
        }

        return caja != null
                ? ResponseEntity.ok(caja)
                : ResponseEntity.ok(null);
    }

    @GetMapping("/validar-caja")
    public ResponseEntity<CajaAhorro> validarCaja(
            @RequestParam String tipo,
            @RequestParam String valor,
            @RequestParam Long idCaho) {

        CajaAhorro caja;
        if ("codigo".equals(tipo)) {
            caja = cajaAhorroService.findByValCodigo(valor,idCaho);
        } else if ("rif".equals(tipo)) {
            caja = cajaAhorroService.findByValRif(valor,idCaho);
        } else {
            return ResponseEntity.badRequest().build();
        }

        return caja != null
                ? ResponseEntity.ok(caja)
                : ResponseEntity.ok(null);
    }

    @PutMapping
    public ResponseEntity<?> updateCajaAhorro(@RequestBody CajaAhorroDTO updateDTO) {
        try {
            CajaAhorro updatedCaja = cajaAhorroService.updateCajaAhorro(updateDTO);
            return ResponseEntity.ok(updatedCaja);

        } catch (Exception ex) {
            return ResponseEntity.internalServerError()
                    .body("Error al actualizar la caja de ahorro: " + ex.getMessage());
        }
    }

    @GetMapping("/lista-pplan-contable")
    public ResponseEntity<ResponseDTO> getListaPPlanContable() {
        logger.info("getListaPPlanContable");
        List<PlantillaPlanContable> res = ppContableService.getAllPPlanContable();
        ResponseDTO responseDTO = new ResponseDTO();
        if (res != null){
            responseDTO.setData(res);
            responseDTO.setStatus("success");
            responseDTO.setMessage("Consulta");
        }else{
            responseDTO.setData(false);
            responseDTO.setStatus("notFound");
            responseDTO.setMessage("Registro no encontrado");
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/cierre")
    public LocalDate getFechaCierre(@RequestParam("fechaUmc") String fechaUmcStr,@RequestParam("lapsoCierre") int lapsoCierre) {
        LocalDate fechaUmc = LocalDate.parse(fechaUmcStr);
        logger.info("getFechaCierre fechaUmc: "+fechaUmc);
        return funcionesService.obtenerFechaCierre(fechaUmc,lapsoCierre);
    }

    @GetMapping("/libro-diario")
    public ResponseEntity<ResponseDTO> getLibroDiario(@RequestParam("idCaho") Long idCaho, @RequestParam("fecha") String fecha) {
        LocalDate fechaValo = LocalDate.parse(fecha); // Convertir String a LocalDate
        logger.info("getLibroDiario fecha: "+fecha);
        List<LibroDiarioDTO> res = funcionesService.obtenerLibroDiario(idCaho,fechaValo);
        ResponseDTO responseDTO = new ResponseDTO();
        if (res != null){
            responseDTO.setData(res);
            responseDTO.setStatus("success");
            responseDTO.setMessage("validación");
        }else{
            responseDTO.setData(false);
            responseDTO.setStatus("notFound");
            responseDTO.setMessage("Registro no encontrado");
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/lista-comprobante")
    public ResponseEntity<ResponseDTO> getListaComprobante(@RequestParam("idCaho") Long idCaho,
                                                           @RequestParam("fechaDesde") String fechaDesde,
                                                           @RequestParam("fechaHasta") String fechaHasta,
                                                           @RequestParam("tipo") Integer tipo) {

        LocalDate fechaDesde1 = LocalDate.parse(fechaDesde); // Convertir String a LocalDate
        LocalDate fechaHata1 = LocalDate.parse(fechaHasta);

        logger.info("getListaComprobante fecha: "+fechaDesde1);
        List<ListaComprobanteDTO> res = funcionesService.obtenerListaComprobante(idCaho,fechaDesde1,fechaHata1,tipo);
        ResponseDTO responseDTO = new ResponseDTO();
        if (res != null){
            responseDTO.setData(res);
            responseDTO.setStatus("success");
            responseDTO.setMessage("Reporte");
        }else{
            responseDTO.setData(false);
            responseDTO.setStatus("notFound");
            responseDTO.setMessage("Registro no encontrado");
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/estado-resultado")
    public ResponseEntity<ResponseDTO> getEstadoResultado(@RequestParam("idCaho") Long idCaho,
                                                                  @RequestParam("fecha") String fecha,
                                                                  @RequestParam("periodo") Integer periodo,
                                                                  @RequestParam("tipo") Boolean tipo) {
        LocalDate fecha1 = LocalDate.parse(fecha);
        logger.info("getEstadoResultado fecha: "+fecha1);
        List<EstadoResultadoDTO> res = funcionesService.obtenerEstadoResultado(idCaho,fecha1,periodo,tipo);
        ResponseDTO responseDTO = new ResponseDTO();
        if (res != null){
            responseDTO.setData(res);
            responseDTO.setStatus("success");
            responseDTO.setMessage("Reporte");
        }else{
            responseDTO.setData(false);
            responseDTO.setStatus("notFound");
            responseDTO.setMessage("Registro no encontrado");
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/balance-comprobacion")
    public ResponseEntity<ResponseDTO> getBalanceComprobacion(@RequestParam("idCaho") Long idCaho,
                                                          @RequestParam("fecha") String fecha,
                                                          @RequestParam("periodo") Integer periodo,
                                                          @RequestParam("tipo") Boolean tipo) {
        LocalDate fecha1 = LocalDate.parse(fecha);
        logger.info("getEstadoResultado fecha: "+fecha1);
        List<BalanceComprobacionDTO> res = funcionesService.obtenerBalanceComprobacion(idCaho,fecha1,periodo,tipo);
        ResponseDTO responseDTO = new ResponseDTO();
        if (res != null){
            responseDTO.setData(res);
            responseDTO.setStatus("success");
            responseDTO.setMessage("Reporte");
        }else{
            responseDTO.setData(false);
            responseDTO.setStatus("notFound");
            responseDTO.setMessage("Registro no encontrado");
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/balance-general")
    public ResponseEntity<ResponseDTO> getBalanceGeneral(@RequestParam("idCaho") Long idCaho,
                                                              @RequestParam("fecha") String fecha,
                                                              @RequestParam("periodo") Integer periodo,
                                                              @RequestParam("tipo") Boolean tipo) {
        LocalDate fecha1 = LocalDate.parse(fecha);
        logger.info("getEstadoResultado fecha: "+fecha1);
        List<BalanceGeneralDTO> res = funcionesService.obtenerBalanceGeneral(idCaho,fecha1,periodo,tipo);
        ResponseDTO responseDTO = new ResponseDTO();
        if (res != null){
            responseDTO.setData(res);
            responseDTO.setStatus("success");
            responseDTO.setMessage("Reporte");
        }else{
            responseDTO.setData(false);
            responseDTO.setStatus("notFound");
            responseDTO.setMessage("Registro no encontrado");
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/val-mes-cierre")
    public ResponseEntity<ResponseDTO> getMesCierre(@RequestParam("idCaho") int idCaho, @RequestParam("fecha") String fecha) {
        LocalDate fechaValo = LocalDate.parse(fecha); // Convertir String a LocalDate
        logger.info("getMesCierre fecha: "+fecha);
        boolean res = funcionesService.obtenerMesCierre(idCaho,fechaValo);
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

    @GetMapping("/ultimos-movimientos")
    public ResponseEntity<ResponseDTO> getUltimosMovimientos(@RequestParam("idCaho") Long idCaho, @RequestParam("dias") int dias) {
        logger.info("getUltimosMovimientos: "+idCaho);
        List<UltimosMovimientosDTO> res = funcionesService.obtenerUltimosMovimientos(idCaho,dias);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(res);
        responseDTO.setStatus("success");
        responseDTO.setMessage("validación");

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/vpc")
    public ResponseEntity<ResponseDTO> getVpc(@RequestParam("idCaho") Long idCaho) {
        logger.info("getVpc: "+idCaho);
        List<Vpc> res = funcionesService.obtenerVpc(idCaho);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(res);
        responseDTO.setStatus("success");
        responseDTO.setMessage("validación");

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/cierre-mensual")
    public ResponseEntity<ResponseDTO> getCierreMensual(@RequestParam("idCaho") Long idCaho,@RequestParam("idUsuario") Long idUsuario) {
        logger.info("getCierreMensual: "+ idCaho);
        boolean res = funcionesService.getCierreMensual(idCaho,idUsuario);
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

    @GetMapping("/cierre-anual")
    public ResponseEntity<ResponseDTO> getCierreAnual(@RequestParam("idCaho") Long idCaho,@RequestParam("idUsuario") Long idUsuario) {
        logger.info("getCierreAnual: "+ idCaho);
        boolean res = funcionesService.getCierreAnual(idCaho,idUsuario);
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
}
