package com.sudeca.services;

import com.sudeca.model.Rol;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * * Author: Francisco Hernandez
 **/

@Component
public interface IRolService {
    List<Rol> getAllRoles();
    List<Rol> getRolesCaja();
    List<Rol> getRolesMaster();
    Optional<Rol> getRolById(long id);
    Rol saveRol(Rol role);
    Rol updateRol(long id, Rol role);
    void deleteRol(long id);
}
