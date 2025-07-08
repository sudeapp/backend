package com.sudeca.services;

import com.sudeca.model.PlanCatalogo;
import org.springframework.stereotype.Service;


/**
 * * Author: Luis López
 **/

@Service
public interface IPlanCatalogoService {
    PlanCatalogo findByCuenta(long idPlanContable,String cuenta);
}
