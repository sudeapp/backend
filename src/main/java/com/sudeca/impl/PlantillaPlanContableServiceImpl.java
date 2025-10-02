package com.sudeca.impl;

import com.sudeca.model.PlantillaPlanContable;
import com.sudeca.repository.PlantillaPlanContableRepository;
import com.sudeca.services.PlantillaPlanContableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlantillaPlanContableServiceImpl implements PlantillaPlanContableService {

    @Autowired
    private PlantillaPlanContableRepository repository;

    @Override
    public List<PlantillaPlanContable> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<PlantillaPlanContable> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<PlantillaPlanContable> findByCodigo(String codPPlan) {
        return repository.findByCodPPlan(codPPlan);
    }

    @Override
    public List<PlantillaPlanContable> findByDescripcion(String descripcion) {
        return repository.findByDescripcionContainingIgnoreCase(descripcion);
    }

    @Override
    public List<PlantillaPlanContable> findByValido(boolean valido) {
        return repository.findByValido(valido);
    }

    @Override
    public PlantillaPlanContable save(PlantillaPlanContable plantilla) {
        return repository.save(plantilla);
    }

    @Override
    public PlantillaPlanContable update(Long id, PlantillaPlanContable plantilla) {
        if (repository.existsById(id)) {
            plantilla.setIdPPlanContable(id);
            return repository.save(plantilla);
        }
        throw new RuntimeException("PlantillaPlanContable con id " + id + " no encontrada");
    }

    @Override
    public void deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("PlantillaPlanContable con id " + id + " no encontrada");
        }
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
