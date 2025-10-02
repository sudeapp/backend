package com.sudeca.repository;

import com.sudeca.model.Categoria;
import com.sudeca.model.PlanCatalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlanCatalogoRepository extends JpaRepository<PlanCatalogo, Long> {
    @Query("SELECT c FROM PlanCatalogo c WHERE idPlanContable = :idPlanContable and c.cuenta = :cuenta and c.movimiento = true and c.estatusCuenta = true")
    PlanCatalogo findByCuenta(@Param("idPlanContable") long idPlanContable,@Param("cuenta") String cuenta);
    boolean existsByIdPlanContableAndCuenta(Long idPlanContable, String cuenta);
    List<PlanCatalogo> findByIdPlanContable(Long idPlanContable);
    List<PlanCatalogo> findByIdPlanContableOrderByCuentaAsc(Long idPlanContable);
    @Query("SELECT c FROM PlanCatalogo c WHERE c.idPlanContable = :idPlanContable ORDER BY c.cuenta ASC")
    List<PlanCatalogo> findByIdPlanContableCuentaAsc(@Param("idPlanContable") Long idPlanContable);
}
