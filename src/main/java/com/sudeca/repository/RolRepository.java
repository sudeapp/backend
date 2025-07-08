package com.sudeca.repository;

import com.sudeca.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolRepository extends JpaRepository<Rol, Long> {

  Rol findByRolContainingIgnoreCase(String rol);

  List<Rol> findByCodigo(int i);
  List<Rol> findByUsuarioCaja(int i);
}
