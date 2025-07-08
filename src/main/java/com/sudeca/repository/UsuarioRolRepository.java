package com.sudeca.repository;

import com.sudeca.model.UsuarioRol;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM UsuarioRol c WHERE c.usuario.idUsuario = :idUsuario")
    void deleteByIdUsuario(@Param("idUsuario") Long idCaho);
}
