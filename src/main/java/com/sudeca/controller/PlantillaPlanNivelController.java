package com.sudeca.controller;

import com.sudeca.dto.ResponseDTO;
import com.sudeca.model.PlantillaPlanNivel;
import com.sudeca.services.IAuxiliarService;
import com.sudeca.services.IFuncionesService;
import com.sudeca.services.IPlanCatalogoService;
import com.sudeca.services.PlantillaPlanNivelService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plantilla-plan-nivel")
public class PlantillaPlanNivelController {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private IFuncionesService funcionesService;
    @Autowired
    private PlantillaPlanNivelService service;

    @GetMapping
    public ResponseEntity<List<PlantillaPlanNivel>> getAll() {
        try {
            List<PlantillaPlanNivel> plantillas = service.findAll();
            return new ResponseEntity<>(plantillas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantillaPlanNivel> getById(@PathVariable("id") Long id) {
        Optional<PlantillaPlanNivel> plantilla = service.findById(id);
        return plantilla.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<PlantillaPlanNivel> create(@RequestBody PlantillaPlanNivel plantillaPlanNivel) {
        try {
            PlantillaPlanNivel nuevaPlantilla = service.save(plantillaPlanNivel);
            return new ResponseEntity<>(nuevaPlantilla, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlantillaPlanNivel> update(@PathVariable("id") Long id, @RequestBody PlantillaPlanNivel plantillaPlanNivel) {
        try {
            PlantillaPlanNivel updatedPlantilla = service.update(id, plantillaPlanNivel);
            return new ResponseEntity<>(updatedPlantilla, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            service.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/contable/{idPplanContable}")
    public ResponseEntity<List<PlantillaPlanNivel>> getByIdPplanContable(@PathVariable("idPplanContable") Long idPplanContable) {
        try {
            List<PlantillaPlanNivel> plantillas = service.findByIdPplanContable(idPplanContable);
            return new ResponseEntity<>(plantillas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<PlantillaPlanNivel>> getByNivel(@PathVariable("nivel") Integer nivel) {
        try {
            List<PlantillaPlanNivel> plantillas = service.findByNivel(nivel);
            return new ResponseEntity<>(plantillas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/contable/{idPplanContable}/nivel/{nivel}")
    public ResponseEntity<PlantillaPlanNivel> getByIdPplanContableAndNivel(
            @PathVariable("idPplanContable") Long idPplanContable,
            @PathVariable("nivel") Integer nivel) {
        try {
            Optional<PlantillaPlanNivel> plantilla = service.findByIdPplanContableAndNivel(idPplanContable, nivel);
            return plantilla.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<PlantillaPlanNivel>> searchByDescripcion(@RequestParam("descripcion") String descripcion) {
        try {
            List<PlantillaPlanNivel> plantillas = service.findByDescripcionContaining(descripcion);
            return new ResponseEntity<>(plantillas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/validar-nivel")
    public ResponseEntity<ResponseDTO> getValidarNivel(@RequestParam("idPPlanContable") Long idPPlanContable) {
        logger.info("getValidarNivel: "+ idPPlanContable);
        boolean res = funcionesService.getValidaNivelPlantilla(idPPlanContable);
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
