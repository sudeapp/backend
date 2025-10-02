package com.sudeca.services;

import com.sudeca.model.PlantillaPlanCatalogo;

import java.util.List;
import java.util.Optional;

public interface PlantillaPlanCatalogoService {

    List<PlantillaPlanCatalogo> findAll();
    Optional<PlantillaPlanCatalogo> findById(Long id);
    List<PlantillaPlanCatalogo> findByIdPPlanContable(Long idPPlanContable);
    Optional<PlantillaPlanCatalogo> findByCuenta(String cuenta);
    List<PlantillaPlanCatalogo> findByDescripcion(String descripcion);
    List<PlantillaPlanCatalogo> findByNivel(Integer nivel);
    List<PlantillaPlanCatalogo> findByEstatusCuenta(Boolean estatusCuenta);
    List<PlantillaPlanCatalogo> findByTplan(Integer tplan);
    Optional<PlantillaPlanCatalogo> findByIdPPlanContableAndCuenta(Long idPPlanContable, String cuenta);
    PlantillaPlanCatalogo save(PlantillaPlanCatalogo plantillaPlanCatalogo);
    PlantillaPlanCatalogo update(PlantillaPlanCatalogo plantillaPlanCatalogo);
    void deleteById(Long id);
    boolean existsById(Long id);
}
