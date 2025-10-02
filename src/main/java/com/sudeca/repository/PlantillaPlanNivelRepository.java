package com.sudeca.repository;
import com.sudeca.model.PlantillaPlanNivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlantillaPlanNivelRepository extends JpaRepository<PlantillaPlanNivel, Long> {
    // Find by id_pplan_contable
    List<PlantillaPlanNivel> findByIdPplanContable(Long idPplanContable);

    // Find by nivel
    List<PlantillaPlanNivel> findByNivel(Integer nivel);

    // Find by id_pplan_contable and nivel
    Optional<PlantillaPlanNivel> findByIdPplanContableAndNivel(Long idPplanContable, Integer nivel);

    // Check if exists by unique constraint fields
    boolean existsByIdPplanContableAndNivelAndLongitud(Long idPplanContable, Integer nivel, Integer longitud);

    boolean existsByIdPplanContableAndNivel(Long idPplanContable, Integer nivel);

    // Custom query to find by description containing text
    @Query("SELECT p FROM PlantillaPlanNivel p WHERE LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :descripcion, '%'))")
    List<PlantillaPlanNivel> findByDescripcionContaining(@Param("descripcion") String descripcion);
}