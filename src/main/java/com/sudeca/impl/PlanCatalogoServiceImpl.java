package com.sudeca.impl;

import com.sudeca.model.PlanCatalogo;
import com.sudeca.model.Rol;
import com.sudeca.repository.PlanCatalogoRepository;
import com.sudeca.repository.RolRepository;
import com.sudeca.services.IPlanCatalogoService;
import com.sudeca.services.IRolService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * * Author: Francisco Hernandez
 **/
@Service
@Component
public class PlanCatalogoServiceImpl implements IPlanCatalogoService {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    PlanCatalogoRepository planCatalogoRepository;

    public PlanCatalogoServiceImpl() {

    }

    @Override
    public PlanCatalogo findByCuenta(long idPlanContable,String cuenta) {
        return planCatalogoRepository.findByCuenta(idPlanContable,cuenta);
    }

}
