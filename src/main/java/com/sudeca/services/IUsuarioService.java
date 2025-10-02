package com.sudeca.services;

import com.sudeca.dto.Login;
import com.sudeca.dto.PasswordUsuarioDTO;
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
    Usuario getCedula(String cedula);
    Usuario getEmail(String email);
    Usuario getUser(String usuario);
    Usuario saveUser(UsuarioDTO user,boolean isUserCaja);
    Usuario getLogin(Login login);
    List<Usuario> getAllUsers();
    List<Usuario> getAllUsersCaho(long idCaho);
    List<Usuario> getAllUsersSudeca();
    Optional<Usuario> getUserById(long id);
    Usuario updateUser(long id, Usuario user);
    Usuario updateUserDTO(long id, UsuarioDTO user);
    void deleteUser(long id);
    Usuario getCodReset(PaswReset paswReset);
    void updateUserStatus(Long idUsuario, Integer nuevoEstatus);
    PasswordUsuarioDTO savePassword(PasswordUsuarioDTO user);
}
