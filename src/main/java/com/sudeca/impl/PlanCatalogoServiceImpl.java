package com.sudeca.impl;

import com.sudeca.model.AuxiliarTipo;
import com.sudeca.model.PlanCatalogo;
import com.sudeca.model.Rol;
import com.sudeca.repository.AuxiliarTipoRepository;
import com.sudeca.repository.PlanCatalogoRepository;
import com.sudeca.repository.RolRepository;
import com.sudeca.services.IPlanCatalogoService;
import com.sudeca.services.IRolService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springdoc.api.OpenApiResourceNotFoundException;
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
    @Autowired
    AuxiliarTipoRepository auxiliarTipoRepository;
    public PlanCatalogoServiceImpl() {

    }

    @Override
    public PlanCatalogo findByCuenta(long idPlanContable,String cuenta) {
        return planCatalogoRepository.findByCuenta(idPlanContable,cuenta);
    }
    @Override
    public Optional<PlanCatalogo> findById(Long id) {
        return planCatalogoRepository.findById(id);
    }

    @Override
    public PlanCatalogo save(PlanCatalogo planCatalogo) {
        // Validar restricción única UK1
        /*if (planCatalogoRepository.existsByIdPlanContableAndCuenta(
                planCatalogo.getIdPlanContable(), planCatalogo.getCuenta())) {
            throw new RuntimeException("Ya existe una cuenta con el mismo código en este plan contable");
        }*/

        PlanCatalogo _planCatalogo = planCatalogoRepository.save(planCatalogo);

        if(_planCatalogo.getAuxiliarTipo() != null){
            _planCatalogo.setAuxiliarTipo(auxiliarTipoRepository.findById(_planCatalogo.getAuxiliarTipo().getIdTaux()).orElseThrow(() -> new OpenApiResourceNotFoundException("Plan no encontrado")));
        }
        return _planCatalogo;
    }

    @Override
    public PlanCatalogo update(PlanCatalogo planCatalogo) {

        PlanCatalogo _planCatalogo = planCatalogoRepository.findById(planCatalogo.getIdPlanCatalogo()).orElseThrow(() -> new OpenApiResourceNotFoundException("Plan no encontrado"));
        _planCatalogo.setDescripcion(planCatalogo.getDescripcion());

        return planCatalogoRepository.save(_planCatalogo);
    }
    @Override
    public void deleteById(Long id) {
        planCatalogoRepository.deleteById(id);
    }
    @Override
    public List<PlanCatalogo> findByIdPlanContable(Long idPlanContable) {
        /*return planCatalogoRepository.findAll().stream()
                .filter(p -> p.getIdPlanContable().equals(idPlanContable))
                .toList();*/

        return planCatalogoRepository.findByIdPlanContableOrderByCuentaAsc(idPlanContable);
    }

}
