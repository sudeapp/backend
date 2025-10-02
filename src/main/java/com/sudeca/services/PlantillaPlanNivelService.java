package com.sudeca.services;
import com.sudeca.model.PlantillaPlanNivel;

import java.util.List;
import java.util.Optional;

public interface PlantillaPlanNivelService {

    List<PlantillaPlanNivel> findAll();

    Optional<PlantillaPlanNivel> findById(Long id);

    PlantillaPlanNivel save(PlantillaPlanNivel plantillaPlanNivel);

    void deleteById(Long id);

    List<PlantillaPlanNivel> findByIdPplanContable(Long idPplanContable);

    List<PlantillaPlanNivel> findByNivel(Integer nivel);

    Optional<PlantillaPlanNivel> findByIdPplanContableAndNivel(Long idPplanContable, Integer nivel);

    boolean existsByIdPplanContableAndNivelAndLongitud(Long idPplanContable, Integer nivel, Integer longitud);

    boolean existsByIdPplanContableAndNivel(Long idPplanContable, Integer nivel);

    List<PlantillaPlanNivel> findByDescripcionContaining(String descripcion);

    PlantillaPlanNivel update(Long id, PlantillaPlanNivel plantillaPlanNivel);
}
