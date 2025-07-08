package com.sudeca.repository;

import com.sudeca.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Categoria findByIdCategoria(Integer id);

    @Query("SELECT c FROM Categoria c WHERE c.idCategoria = :id")
    Categoria obtenerPorIdCategoria(@Param("id") long id);
}
