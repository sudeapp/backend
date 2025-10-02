package com.sudeca.services;

import com.sudeca.model.PlantillaPlanContable;

import java.util.List;
import java.util.Optional;

public interface PlantillaPlanContableService {
    List<PlantillaPlanContable> findAll();
    Optional<PlantillaPlanContable> findById(Long id);
    Optional<PlantillaPlanContable> findByCodigo(String codPPlan);
    List<PlantillaPlanContable> findByDescripcion(String descripcion);
    List<PlantillaPlanContable> findByValido(boolean valido);
    PlantillaPlanContable save(PlantillaPlanContable plantilla);
    PlantillaPlanContable update(Long id, PlantillaPlanContable plantilla);
    void deleteById(Long id);
    boolean existsById(Long id);
}