package com.sudeca.repository;

import com.sudeca.model.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UsuarioRol, Long> {

}