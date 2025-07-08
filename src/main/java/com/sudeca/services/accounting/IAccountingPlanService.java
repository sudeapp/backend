package com.sudeca.services.accounting;

import com.sudeca.model.PlanContable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * * Author: Francisco Hernandez
 **/
@Component
public interface IAccountingPlanService {
    PlanContable save(PlanContable planContable);
    List<PlanContable> getAll();
    Optional<PlanContable> getById(long id);
    PlanContable update(long id, PlanContable planContable);
    void delete(long id);
    PlanContable findByCod(String cod);
}
