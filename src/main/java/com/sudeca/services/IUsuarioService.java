package com.sudeca.services;

import com.sudeca.dto.Login;
import com.sudeca.dto.PaswReset;
import com.sudeca.dto.UsuarioDTO;
import com.sudeca.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * * Author: Francisco Hernandez
 **/
@Component
public interface IUsuarioService {
    Usuario getEmail(String email);
    Usuario getUser(String usuario);
    Usuario saveUser(UsuarioDTO user);
    Usuario getLogin(Login login);
    List<Usuario> getAllUsers();
    List<Usuario> getAllUsersCaho(long idCaho);
    Optional<Usuario> getUserById(long id);
    Usuario updateUser(long id, Usuario user);
    Usuario updateUserDTO(long id, UsuarioDTO user);
    void deleteUser(long id);
    Usuario getCodReset(PaswReset paswReset);
}
