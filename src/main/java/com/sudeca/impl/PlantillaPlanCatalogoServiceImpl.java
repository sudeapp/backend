package com.sudeca.impl;

import com.sudeca.model.PlanCatalogo;
import com.sudeca.model.PlantillaPlanCatalogo;
import com.sudeca.repository.AuxiliarTipoRepository;
import com.sudeca.repository.PlantillaPlanCatalogoRepository;
import com.sudeca.services.PlantillaPlanCatalogoService;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlantillaPlanCatalogoServiceImpl implements PlantillaPlanCatalogoService {

    @Autowired
    private PlantillaPlanCatalogoRepository repository;
    @Autowired
    AuxiliarTipoRepository auxiliarTipoRepository;

    @Override
    public List<PlantillaPlanCatalogo> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<PlantillaPlanCatalogo> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<PlantillaPlanCatalogo> findByIdPPlanContable(Long idPPlanContable) {
        return repository.findByIdPPlanContableOrderByCuentaAsc(idPPlanContable);
    }

    @Override
    public Optional<PlantillaPlanCatalogo> findByCuenta(String cuenta) {
        return repository.findByCuenta(cuenta);
    }

    @Override
    public List<PlantillaPlanCatalogo> findByDescripcion(String descripcion) {
        return repository.findByDescripcionContainingIgnoreCase(descripcion);
    }

    @Override
    public List<PlantillaPlanCatalogo> findByNivel(Integer nivel) {
        return repository.findByNivel(nivel);
    }

    @Override
    public List<PlantillaPlanCatalogo> findByEstatusCuenta(Boolean estatusCuenta) {
        return repository.findByEstatusCuenta(estatusCuenta);
    }

    @Override
    public List<PlantillaPlanCatalogo> findByTplan(Integer tplan) {
        return repository.findByTplan(tplan);
    }

    @Override
    public Optional<PlantillaPlanCatalogo> findByIdPPlanContableAndCuenta(Long idPPlanContable, String cuenta) {
        return repository.findByIdPPlanContableAndCuenta(idPPlanContable, cuenta);
    }

    @Override
    public PlantillaPlanCatalogo save(PlantillaPlanCatalogo plantillaPlanCatalogo) {
        PlantillaPlanCatalogo _planCatalogo = repository.save(plantillaPlanCatalogo);

        if(_planCatalogo.getAuxiliarTipo() != null){
            _planCatalogo.setAuxiliarTipo(auxiliarTipoRepository.findById(_planCatalogo.getAuxiliarTipo().getIdTaux()).orElseThrow(() -> new OpenApiResourceNotFoundException("Plan no encontrado")));
        }
        return _planCatalogo;
    }

    @Override
    public PlantillaPlanCatalogo update(PlantillaPlanCatalogo plantillaPlanCatalogo) {
        /*if (repository.existsById(id)) {
            plantillaPlanCatalogo.setIdPPlanCatalogo(id);
            return repository.save(plantillaPlanCatalogo);
        }*/
        PlantillaPlanCatalogo _planCatalogo = repository.findById(plantillaPlanCatalogo.getIdPPlanCatalogo()).orElseThrow(() -> new OpenApiResourceNotFoundException("Plan no encontrado"));
        _planCatalogo.setDescripcion(plantillaPlanCatalogo.getDescripcion());
        return repository.save(_planCatalogo);
    }

    @Override
    public void deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("PlantillaPlanCatalogo con id " + id + " no encontrada");
        }
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
