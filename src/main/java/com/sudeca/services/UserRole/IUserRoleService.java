package com.sudeca.services.UserRole;

import com.sudeca.model.UsuarioRol;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * * Author: Francisco Hernandez
 **/
@Component
public interface IUserRoleService {
    UsuarioRol saveUserRole(UsuarioRol UserRole);
    List<UsuarioRol> getAllUserRoles();
    Optional<UsuarioRol> getUUserRoleById(long id);
    UsuarioRol updateUserRole(long id, UsuarioRol userRole);
    void deleteUserRole(long id);
}
