package com.sudeca.repository;

import com.sudeca.model.Categoria;
import com.sudeca.model.PlanCatalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlanCatalogoRepository extends JpaRepository<PlanCatalogo, Long> {
    @Query("SELECT c FROM PlanCatalogo c WHERE idPlanContable = :idPlanContable and c.cuenta = :cuenta and c.movimiento = true and c.estatusCuenta = true")
    PlanCatalogo findByCuenta(@Param("idPlanContable") long idPlanContable,@Param("cuenta") String cuenta);
}
