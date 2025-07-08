package com.sudeca.controller;

import com.sudeca.dto.CajaAhorroDTO;
import com.sudeca.model.CajaAhorro;
import com.sudeca.services.ICajaAhorroService;
import com.sudeca.services.IFuncionesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/cajas-ahorro")
public class CajaAhorroController {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private ICajaAhorroService cajaAhorroService;

    @Autowired
    private IFuncionesService funcionesService;

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

    @GetMapping("/cierre")
    public LocalDate getFechaCierre(@RequestParam("fechaUmc") String fechaUmcStr,@RequestParam("lapsoCierre") int lapsoCierre) {
        LocalDate fechaUmc = LocalDate.parse(fechaUmcStr);
        logger.info("getFechaCierre fechaUmc: "+fechaUmc);
        return funcionesService.obtenerFechaCierre(fechaUmc,lapsoCierre);
    }
}
