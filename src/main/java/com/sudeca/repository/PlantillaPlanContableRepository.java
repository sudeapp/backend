package com.sudeca.repository;

import com.sudeca.model.PlantillaPlanContable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlantillaPlanContableRepository extends JpaRepository<PlantillaPlanContable, Long> {
    Optional<PlantillaPlanContable> findByCodPPlan(String codPPlan);
    List<PlantillaPlanContable> findByDescripcionContainingIgnoreCase(String descripcion);
    List<PlantillaPlanContable> findByValido(boolean valido);
}