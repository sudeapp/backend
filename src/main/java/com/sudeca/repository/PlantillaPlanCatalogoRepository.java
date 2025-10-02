package com.sudeca.repository;

import com.sudeca.model.PlanCatalogo;
import com.sudeca.model.PlantillaPlanCatalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlantillaPlanCatalogoRepository extends JpaRepository<PlantillaPlanCatalogo, Long> {

    // Buscar por ID de plantilla contable
    List<PlantillaPlanCatalogo> findByIdPPlanContable(Long idPPlanContable);

    List<PlantillaPlanCatalogo> findByIdPPlanContableOrderByCuentaAsc(Long idPPlanContable);

    // Buscar por cuenta exacta
    Optional<PlantillaPlanCatalogo> findByCuenta(String cuenta);

    // Buscar cuentas por descripción (búsqueda parcial)
    List<PlantillaPlanCatalogo> findByDescripcionContainingIgnoreCase(String descripcion);

    // Buscar cuentas por nivel
    List<PlantillaPlanCatalogo> findByNivel(Integer nivel);

    // Buscar cuentas por estado
    List<PlantillaPlanCatalogo> findByEstatusCuenta(Boolean estatusCuenta);

    // Buscar cuentas por tipo de plan
    List<PlantillaPlanCatalogo> findByTplan(Integer tplan);

    // Consulta personalizada para buscar por cuenta y plantilla
    @Query("SELECT p FROM PlantillaPlanCatalogo p WHERE p.idPPlanContable = :idPPlanContable AND p.cuenta = :cuenta")
    Optional<PlantillaPlanCatalogo> findByIdPPlanContableAndCuenta(
            @Param("idPPlanContable") Long idPPlanContable,
            @Param("cuenta") String cuenta
    );
}
