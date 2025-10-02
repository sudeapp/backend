package com.sudeca.services;

import com.sudeca.model.PlanCatalogo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * * Author: Luis López
 **/

@Service
public interface IPlanCatalogoService {
    PlanCatalogo findByCuenta(long idPlanContable,String cuenta);
    Optional<PlanCatalogo> findById(Long id);
    PlanCatalogo save(PlanCatalogo planCatalogo);
    void deleteById(Long id);
    List<PlanCatalogo> findByIdPlanContable(Long idPlanContable);
    PlanCatalogo update(PlanCatalogo planCatalogo);
}
