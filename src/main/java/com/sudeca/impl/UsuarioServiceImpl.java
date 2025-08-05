package com.sudeca.impl;

import com.google.gson.Gson;
import com.sudeca.dto.Login;
import com.sudeca.dto.Message;
import com.sudeca.dto.PaswReset;
import com.sudeca.dto.UsuarioDTO;
import com.sudeca.model.*;
import com.sudeca.repository.CajaAhorroRepository;
import com.sudeca.repository.RolRepository;
import com.sudeca.repository.UsuarioRepository;
import com.sudeca.repository.UsuarioRolRepository;
import com.sudeca.security.ValidateEmail;
import com.sudeca.services.IUsuarioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.sudeca.security.Constans.*;

/**
 * * Author: Francisco Hernandez
 **/
@Service
@Component
public class UsuarioServiceImpl implements IUsuarioService {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    UsuarioRepository userRepository;
    @Autowired
    RolRepository rolRepository;
    @Autowired
    UsuarioRolRepository usuarioRolRepository;
    @Autowired
    private CajaAhorroRepository cajaAhorroRepository;
    Message message;
    Gson gson = new Gson();

    public UsuarioServiceImpl() {

    }

    @Override
    public Usuario saveUser(UsuarioDTO dto,boolean isUserCaja) {
        Usuario usuario = new Usuario();
        usuario.setPass(dto.getPass());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCedula(dto.getCedula());
        usuario.setDireccion(dto.getDireccion());
        usuario.setTelefono(dto.getTelefono());
        usuario.setCelular(dto.getCelular());
        usuario.setEmail(dto.getEmail());
        usuario.setFechaNac(dto.getFechaNac());
        usuario.setFechaExpCod(dto.getFechaExpCod());
        usuario.setEstatus(dto.getEstatus());

        // Campos que requieren lógica especial
        usuario.setUsuario(dto.getEmail()); // username
        usuario.setDateCreate(LocalDate.now()); // Fecha actual
        usuario.setCodReset(""); // Valor por defecto

        if(isUserCaja){
            usuario.setUsuarioCaja(2);
            usuario.setCajaAhorro(cajaAhorroRepository.findById(dto.getId_caho())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado")));
        }else{
            usuario.setUsuarioCaja(1);
        }

        // Campos no presentes en el DTO
        usuario.setToken(null); // Inicializar como null
        usuario.setRol(new ArrayList<>());

        Usuario userExiste = findByUsuario(usuario.getUsuario());
        if (userExiste!=null) {
            message = new Message(USER_EXISTS_BD,false);
            //return gson.toJson(message);
        }

        if(!ValidateEmail.isValidateEmail(usuario.getEmail())) {
            message = new Message(EMAIL_WITH_FORMAT,false);
            //return gson.toJson(message);
        }
        Usuario userCorreExiste = findByEmail(usuario.getEmail());
        if (userCorreExiste != null) {
            message = new Message(EMAIL_EXISTS_BD,false);
            //return gson.toJson(message);
        }

        Rol rol = rolRepository.findById(dto.getIdRol()).orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));
        logger.info("userIn.getIdusuario(): "+usuario.getIdUsuario());
        if (rol != null) {
            UsuarioRol usuarioRol = new UsuarioRol();
            usuarioRol.setRol(rol);
            usuarioRol.setUsuario(usuario);
            usuarioRol.setFechaReg((Timestamp.from(Instant.now())));
            usuario.getRol().add(usuarioRol);
        }
        Usuario _user = userRepository.save(usuario);
        logger.info("_user: "+_user);

        /*
            // Manejar múltiples roles
            if (dto.getRolesIds() != null && !dto.getRolesIds().isEmpty()) {
                for (Long rolId : dto.getRolesIds()) {
                    Rol rol = rolRepository.findById(rolId)
                        .orElseThrow(() -> new EntityNotFoundException("Rol con id " + rolId + " no encontrado"));

                    UsuarioRol usuarioRol = new UsuarioRol();
                    usuarioRol.setRol(rol);
                    usuarioRol.setUsuario(usuario);
                    usuarioRol.setFechaReg(Timestamp.from(Instant.now()));

                    usuario.getRol().add(usuarioRol);
                }
            }
         */
        return _user;
    }

    @Override
    public Usuario getLogin(Login login) {
        Usuario userData = userRepository.findByUsuarioAndPass(login.getUsuario(),login.getPass());
        logger.info("**- userData: "+userData);
        return userData;
    }

    @Override
    public Usuario getEmail(String email) {
        logger.info("**- getEmail: "+email);
        return userRepository.findByEmail(email);
    }

    @Override
    public Usuario getCedula(String cedula) {
        logger.info("**- getCedula: "+cedula);
        return userRepository.findByCedula(cedula);
    }

    @Override
    public Usuario getUser(String usuario) {
        logger.info("**- getUser: "+usuario);
        return userRepository.findByUsuarioContainingIgnoreCase(usuario);
    }

    @Override
    public Usuario getCodReset(PaswReset paswReset) {
        logger.info("**- getCodReset: ", paswReset);
        return userRepository.findByCodResetContainingIgnoreCase(paswReset.getEmail_code());
    }

    @Override
    public List<Usuario> getAllUsers() {
        List<Usuario> users = new ArrayList<Usuario>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public List<Usuario> getAllUsersCaho(long idCaho) {
        return userRepository.findByCajaAhorroId(idCaho);
    }

    @Override
    public List<Usuario> getAllUsersSudeca() {
        return userRepository.findByUsuarioCaja(1);
    }

    @Override
    public Optional<Usuario> getUserById(long id) {
         Optional<Usuario> userData = userRepository.findById(id);
         return userData;
    }

    @Override
    public Usuario updateUser(long id, Usuario user) {
        Optional<Usuario> userData = userRepository.findById(id);
        try {
            if (userData.isPresent()) {
                Usuario _user = userData.get();
                _user.setUsuario(user.getUsuario());
                _user.setPass(user.getPass());
                _user.setNombre(user.getNombre());
                _user.setApellido(user.getApellido());
                _user.setCedula(user.getCedula());
                _user.setDireccion(user.getDireccion());
                _user.setTelefono(user.getTelefono());
                _user.setCelular(user.getCelular());
                _user.setEmail(user.getEmail());
                _user.setFechaNac(user.getFechaNac());
                _user.setDateModified(LocalDate.now());
                _user.setDateCreate(userData.get().getDateCreate());
                return userRepository.save(_user);
            } else {
                logger.info("Usuario no Existe en el Sistema");
                return null;
            }
        } catch (Exception e) {
            logger.info("e.getMessage(): "+e.getMessage());
            return null;
        }
    }

    @Override
    public Usuario updateUserDTO(long id, UsuarioDTO user) {
        Optional<Usuario> userData = userRepository.findById(id);
        try {
            if (userData.isPresent()) {
                Usuario _user = userData.get();
                //_user.setUsuario(user.getUsuario());
                _user.setPass(user.getPass());
                _user.setNombre(user.getNombre());
                _user.setApellido(user.getApellido());
                _user.setCedula(user.getCedula());
                _user.setDireccion(user.getDireccion());
                _user.setTelefono(user.getTelefono());
                _user.setCelular(user.getCelular());
                _user.setEmail(user.getEmail());
                _user.setFechaNac(user.getFechaNac());
                _user.setDateModified(LocalDate.now());
                _user.setDateCreate(userData.get().getDateCreate());
                _user.setUsuarioCaja(userData.get().getUsuarioCaja());

                Rol rol = rolRepository.findById(user.getIdRol()).orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));
                logger.info("userIn.getIdusuario(): "+_user.getIdUsuario());
                if (rol != null) {
                    usuarioRolRepository.deleteByIdUsuario(_user.getIdUsuario());
                    logger.info("ROL**********************: "+ rol.getIdRol());
                    UsuarioRol usuarioRol = new UsuarioRol();
                    usuarioRol.setRol(rol);
                    usuarioRol.setUsuario(_user);
                    usuarioRol.setFechaReg((Timestamp.from(Instant.now())));
                    usuarioRolRepository.save(usuarioRol);
                }

                return userRepository.save(_user);
            } else {
                logger.info("Usuario no Existe en el Sistema");
                return null;
            }
        } catch (Exception e) {
            logger.info("e.getMessage(): "+e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public Usuario findByEmail(String email) {
        try {
            return userRepository.findByEmailContainingIgnoreCase(email);
        } catch (Exception e) {
            return null;
        }
    }

    public Usuario findByUsuario(String usuario) {
        try {
            return userRepository.findByUsuarioContainingIgnoreCase(usuario);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void updateUserStatus(Long idUsuario, Integer nuevoEstatus) {
        Usuario usuario = userRepository.findById(idUsuario)
                .orElseThrow(() -> new OpenApiResourceNotFoundException("Usuario no encontrado con id: " + idUsuario));

        usuario.setEstatus(nuevoEstatus);
        usuario.setDateModified(LocalDate.now());  // Actualizar fecha de modificación

        userRepository.save(usuario);
    }
}

