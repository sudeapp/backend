package com.sudeca.repository;

import com.sudeca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

  List<Usuario> findByNombreContainingIgnoreCase(String nombre);

  Usuario findByEmailContainingIgnoreCase(String email);

  Usuario findByUsuarioContainingIgnoreCase(String usuario);

  Usuario findByUsuarioAndPassContainingIgnoreCase(String usuario, String pass);

  @Query("SELECT u FROM Usuario u WHERE u.usuario = :usuario and u.pass = :pass")
  Usuario findByUsuarioAndPass(@Param("usuario") String usuario,String pass);

  Usuario findByCodResetContainingIgnoreCase(String code);

  Usuario findByEmail(String email);
  Usuario findByCedula(String cedula);
  @Query("SELECT u FROM Usuario u WHERE u.cajaAhorro.id = :idCaja")
  List<Usuario> findByCajaAhorroId(@Param("idCaja") long idCaja);

  @Query("SELECT u FROM Usuario u WHERE u.usuarioCaja = :usuarioCaja")
  List<Usuario> findByUsuarioCaja(@Param("usuarioCaja") int usuarioCaja);
/*
  @Modifying
  @Query("DELETE FROM Usuario u WHERE u.cajaAhorro.idCaja = :idCaja AND u.idUsuario NOT IN :ids")
  void deleteUsuariosNotInList(@Param("idCaja") Long idCaja, @Param("ids") List<Long> ids);*/
}
