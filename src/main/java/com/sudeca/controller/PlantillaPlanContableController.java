package com.sudeca.controller;

import com.sudeca.model.PlantillaPlanContable;
import com.sudeca.services.PlantillaPlanContableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plantillas-plan-contable")
public class PlantillaPlanContableController {

    @Autowired
    private PlantillaPlanContableService service;

    @GetMapping
    public ResponseEntity<List<PlantillaPlanContable>> getAll() {
        List<PlantillaPlanContable> plantillas = service.findAll();
        return new ResponseEntity<>(plantillas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantillaPlanContable> getById(@PathVariable Long id) {
        Optional<PlantillaPlanContable> plantilla = service.findById(id);
        return plantilla.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<PlantillaPlanContable> getByCodigo(@PathVariable String codigo) {
        Optional<PlantillaPlanContable> plantilla = service.findByCodigo(codigo);
        return plantilla.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/descripcion/{descripcion}")
    public ResponseEntity<List<PlantillaPlanContable>> getByDescripcion(@PathVariable String descripcion) {
        List<PlantillaPlanContable> plantillas = service.findByDescripcion(descripcion);
        return new ResponseEntity<>(plantillas, HttpStatus.OK);
    }

    @GetMapping("/valido/{valido}")
    public ResponseEntity<List<PlantillaPlanContable>> getByValido(@PathVariable boolean valido) {
        List<PlantillaPlanContable> plantillas = service.findByValido(valido);
        return new ResponseEntity<>(plantillas, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PlantillaPlanContable> create(@RequestBody PlantillaPlanContable plantilla) {
        try {
            PlantillaPlanContable nuevaPlantilla = service.save(plantilla);
            return new ResponseEntity<>(nuevaPlantilla, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlantillaPlanContable> update(@PathVariable Long id, @RequestBody PlantillaPlanContable plantilla) {
        try {
            PlantillaPlanContable plantillaActualizada = service.update(id, plantilla);
            return new ResponseEntity<>(plantillaActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
