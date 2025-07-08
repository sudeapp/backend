package com.sudeca.controller;

import com.google.gson.Gson;
import com.sudeca.dto.Message;
//import com.sudeca.model.UserReturn;
import com.sudeca.model.UsuarioRol;
import com.sudeca.repository.UserRoleRepository;
import com.sudeca.services.UserRole.IUserRoleService;
import com.sudeca.services.UserRole.UserRoleServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.sudeca.security.Constans.*;

@RestController
@RequestMapping("/api")
public class UserRoleController {
	
	private static final Logger logger = LogManager.getLogger();

      @Autowired
      UserRoleRepository usuarioRolRepository;

      @Autowired
      IUserRoleService userRoleService;

     @GetMapping("/userRol")
      public ResponseEntity<List<UsuarioRol>> getAllUserRol() {
        try {
            userRoleService = new UserRoleServiceImpl(usuarioRolRepository);
            List<UsuarioRol> usuarioRoles = userRoleService.getAllUserRoles();
            logger.info("userRole: "+usuarioRoles);

          if (usuarioRoles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }

          return new ResponseEntity<>(usuarioRoles, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("e.getMessage(): "+e.getMessage());
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
      }

     /* @GetMapping("/empresas/{id}")
      public ResponseEntity<Empresa> getEmpresaById(@PathVariable("id") long id) {
        Optional<Empresa> empresaData = usuarioRolRepository.findById(id);

          return empresaData.map(empresa -> new ResponseEntity<>(empresa, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
      }*/

      @PostMapping("/userRol")
      public String createUserRol(@RequestBody UsuarioRol userRole) {
          Message message;
          Gson gson = new Gson();
            try {
                userRoleService = new UserRoleServiceImpl(usuarioRolRepository);
                UsuarioRol _usuarioRol = userRoleService.saveUserRole(userRole);
                logger.info("_userRol: "+_usuarioRol);

                ResponseEntity<UsuarioRol> responseUsuarioRol = new ResponseEntity<>(_usuarioRol, HttpStatus.CREATED);

                /*if(responseUsuarioRol.getStatusCodeValue()==HttpStatus.CREATED.value()) {
                    UserReturn userReturn = new UserReturn(_usuarioRol.getIdUserRol(),_usuarioRol.getDateCreate(),_usuarioRol.getDateCreate(),true,INSERT_STATUS);
                    return gson.toJson(userReturn);
                }
                else {
                    message = new Message(ERROR_INSERT_STATUS+responseUsuarioRol.getStatusCode(),false);
                    return gson.toJson(message);
                }*/
                return gson.toJson(null);
            } catch (Exception e) {
                message = new Message(ERROR_EXCEPTION_THROWN+" "+e.getMessage(),false);
                return gson.toJson(message);
            }
      }

  @PutMapping("/userRol/{id}")
  public ResponseEntity<UsuarioRol> updateUserRole(@PathVariable("id") long id, @RequestBody UsuarioRol usuarioRol) {
      userRoleService = new UserRoleServiceImpl(usuarioRolRepository);
      Optional<UsuarioRol> userRoleData = usuarioRolRepository.findById(id);

    if (userRoleData.isPresent()) {
    //    String jwt = new CreateJWTImpl().createJWT(empresa.getCodempresa(), empresa.getRif(),  empresa.getRazonSocial(),JW_TIME_TO_LIVE);
        UsuarioRol _userRoleData = userRoleData.get();
        _userRoleData.setUsuario(usuarioRol.getUsuario());
        _userRoleData.setRol(usuarioRol.getRol());
        _userRoleData.setFechaReg(userRoleData.get().getFechaReg());
        return new ResponseEntity<>(usuarioRolRepository.save(_userRoleData), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/usuarioRol/{id}")
  public String deleteUsuarioRol(@PathVariable("id") long id) {
      Message message;
      Gson gson = new Gson();
    try {
    	usuarioRolRepository.deleteById(id);
        message = new Message("Relación eliminada",true);
       //message = new Message("Relación eliminada",true);
        return gson.toJson(message);
      //return new ResponseEntity<>(HttpStatus.valueOf(1));
    } catch (Exception e) {
        message = new Message(ERROR_EXCEPTION_THROWN+" "+e.getMessage(),false);
        return gson.toJson(message);
      //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

    /*public UserRole findByUsuario(User user) {
        try {
            return usuarioRolRepository.findByUseraContainingIgnoreCase(user);
        } catch (Exception e) {
            return null;
        }
    }*/
    /*public Empresa findByRifEmpresa(String rif) {
        try {
            return usuarioRolRepository.findByRifContainingIgnoreCase(rif);
        } catch (Exception e) {
            return null;
        }
    }
    public Empresa findByRazonSocialEmpresa(String razon) {
        try {
            return usuarioRolRepository.findByRazonSocialContainingIgnoreCase(razon);
        } catch (Exception e) {
            return null;
        }
    }*/
}