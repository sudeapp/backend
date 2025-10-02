package com.sudeca.impl;
import com.sudeca.model.PlantillaPlanNivel;
import com.sudeca.repository.PlantillaPlanNivelRepository;
import com.sudeca.services.PlantillaPlanNivelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlantillaPlanNivelServiceImpl implements PlantillaPlanNivelService {

    @Autowired
    private PlantillaPlanNivelRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<PlantillaPlanNivel> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlantillaPlanNivel> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public PlantillaPlanNivel save(PlantillaPlanNivel plantillaPlanNivel) {
        // Check unique constraints before saving
        if (repository.existsByIdPplanContableAndNivelAndLongitud(
                plantillaPlanNivel.getIdPplanContable(),
                plantillaPlanNivel.getNivel(),
                plantillaPlanNivel.getLongitud())) {
            throw new RuntimeException("Violates unique constraint pplann_uk1");
        }

        if (repository.existsByIdPplanContableAndNivel(
                plantillaPlanNivel.getIdPplanContable(),
                plantillaPlanNivel.getNivel())) {
            throw new RuntimeException("Violates unique constraint pplann_uk2");
        }

        return repository.save(plantillaPlanNivel);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlantillaPlanNivel> findByIdPplanContable(Long idPplanContable) {
        return repository.findByIdPplanContable(idPplanContable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlantillaPlanNivel> findByNivel(Integer nivel) {
        return repository.findByNivel(nivel);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlantillaPlanNivel> findByIdPplanContableAndNivel(Long idPplanContable, Integer nivel) {
        return repository.findByIdPplanContableAndNivel(idPplanContable, nivel);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByIdPplanContableAndNivelAndLongitud(Long idPplanContable, Integer nivel, Integer longitud) {
        return repository.existsByIdPplanContableAndNivelAndLongitud(idPplanContable, nivel, longitud);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByIdPplanContableAndNivel(Long idPplanContable, Integer nivel) {
        return repository.existsByIdPplanContableAndNivel(idPplanContable, nivel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlantillaPlanNivel> findByDescripcionContaining(String descripcion) {
        return repository.findByDescripcionContaining(descripcion);
    }

    @Override
    @Transactional
    public PlantillaPlanNivel update(Long id, PlantillaPlanNivel plantillaPlanNivel) {
        Optional<PlantillaPlanNivel> existing = repository.findById(id);
        if (existing.isPresent()) {
            PlantillaPlanNivel existingEntity = existing.get();

            // Check if the update would violate unique constraints
            if (!existingEntity.getIdPplanContable().equals(plantillaPlanNivel.getIdPplanContable()) ||
                    !existingEntity.getNivel().equals(plantillaPlanNivel.getNivel()) ||
                    !existingEntity.getLongitud().equals(plantillaPlanNivel.getLongitud())) {

                if (repository.existsByIdPplanContableAndNivelAndLongitud(
                        plantillaPlanNivel.getIdPplanContable(),
                        plantillaPlanNivel.getNivel(),
                        plantillaPlanNivel.getLongitud())) {
                    throw new RuntimeException("Violates unique constraint pplann_uk1");
                }
            }

            if (!existingEntity.getIdPplanContable().equals(plantillaPlanNivel.getIdPplanContable()) ||
                    !existingEntity.getNivel().equals(plantillaPlanNivel.getNivel())) {

                if (repository.existsByIdPplanContableAndNivel(
                        plantillaPlanNivel.getIdPplanContable(),
                        plantillaPlanNivel.getNivel())) {
                    throw new RuntimeException("Violates unique constraint pplann_uk2");
                }
            }

            // Update fields
            existingEntity.setIdPplanContable(plantillaPlanNivel.getIdPplanContable());
            existingEntity.setNivel(plantillaPlanNivel.getNivel());
            existingEntity.setLongitud(plantillaPlanNivel.getLongitud());
            existingEntity.setDescripcion(plantillaPlanNivel.getDescripcion());
            existingEntity.setLongitudNivel(plantillaPlanNivel.getLongitudNivel());

            return repository.save(existingEntity);
        } else {
            throw new RuntimeException("PlantillaPlanNivel not found with id: " + id);
        }
    }
}
