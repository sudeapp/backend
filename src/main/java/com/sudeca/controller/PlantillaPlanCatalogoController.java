package com.sudeca.controller;

import com.sudeca.dto.ResponseDTO;
import com.sudeca.dto.ValidaCodigoContableDTO;
import com.sudeca.model.PlanCatalogo;
import com.sudeca.model.PlantillaPlanCatalogo;
import com.sudeca.services.IFuncionesService;
import com.sudeca.services.PlantillaPlanCatalogoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plantillas-plan-catalogo")
public class PlantillaPlanCatalogoController {

    @Autowired
    private PlantillaPlanCatalogoService service;
    @Autowired
    private IFuncionesService funcionesService;
    private static final Logger logger = LogManager.getLogger();

    @GetMapping
    public ResponseEntity<List<PlantillaPlanCatalogo>> getAll() {
        List<PlantillaPlanCatalogo> plantillas = service.findAll();
        return new ResponseEntity<>(plantillas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantillaPlanCatalogo> getById(@PathVariable Long id) {
        Optional<PlantillaPlanCatalogo> plantilla = service.findById(id);
        return plantilla.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/plantilla/{idPPlanContable}")
    public ResponseEntity<List<PlantillaPlanCatalogo>> getByIdPPlanContable(@PathVariable Long idPPlanContable) {
        List<PlantillaPlanCatalogo> plantillas = service.findByIdPPlanContable(idPPlanContable);
        return new ResponseEntity<>(plantillas, HttpStatus.OK);
    }

    @GetMapping("/cuenta/{cuenta}")
    public ResponseEntity<PlantillaPlanCatalogo> getByCuenta(@PathVariable String cuenta) {
        Optional<PlantillaPlanCatalogo> plantilla = service.findByCuenta(cuenta);
        return plantilla.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/descripcion/{descripcion}")
    public ResponseEntity<List<PlantillaPlanCatalogo>> getByDescripcion(@PathVariable String descripcion) {
        List<PlantillaPlanCatalogo> plantillas = service.findByDescripcion(descripcion);
        return new ResponseEntity<>(plantillas, HttpStatus.OK);
    }

    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<PlantillaPlanCatalogo>> getByNivel(@PathVariable Integer nivel) {
        List<PlantillaPlanCatalogo> plantillas = service.findByNivel(nivel);
        return new ResponseEntity<>(plantillas, HttpStatus.OK);
    }

    @GetMapping("/estatus/{estatus}")
    public ResponseEntity<List<PlantillaPlanCatalogo>> getByEstatus(@PathVariable Boolean estatus) {
        List<PlantillaPlanCatalogo> plantillas = service.findByEstatusCuenta(estatus);
        return new ResponseEntity<>(plantillas, HttpStatus.OK);
    }

    @GetMapping("/tplan/{tplan}")
    public ResponseEntity<List<PlantillaPlanCatalogo>> getByTplan(@PathVariable Integer tplan) {
        List<PlantillaPlanCatalogo> plantillas = service.findByTplan(tplan);
        return new ResponseEntity<>(plantillas, HttpStatus.OK);
    }

    @GetMapping("/buscar")
    public ResponseEntity<PlantillaPlanCatalogo> getByIdPPlanContableAndCuenta(
            @RequestParam Long idPPlanContable,
            @RequestParam String cuenta) {
        Optional<PlantillaPlanCatalogo> plantilla = service.findByIdPPlanContableAndCuenta(idPPlanContable, cuenta);
        return plantilla.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public ResponseEntity<PlantillaPlanCatalogo> create(@RequestBody PlantillaPlanCatalogo plantillaPlanCatalogo) {
        try {
            PlantillaPlanCatalogo nuevaPlantilla = service.save(plantillaPlanCatalogo);
            return new ResponseEntity<>(nuevaPlantilla, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<PlantillaPlanCatalogo> update(@RequestBody PlantillaPlanCatalogo plantillaPlanCatalogo,
                                    BindingResult result) {
        try {
            PlantillaPlanCatalogo plantillaActualizada = service.update(plantillaPlanCatalogo);
            return new ResponseEntity<>(plantillaActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> delete(@RequestParam("id") Long id) {
        if (!service.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/validar-cuenta")
    public ResponseEntity<ResponseDTO> getValidaCuentaCaja(@RequestParam("idPPlanContable") Long idPPlanContable, @RequestParam("cuenta") String cuenta) {
        logger.info("getValidaCuentaCaja: "+cuenta);
        ValidaCodigoContableDTO res = funcionesService.validarCodigoContablePlantilla(idPPlanContable,cuenta);
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
}
