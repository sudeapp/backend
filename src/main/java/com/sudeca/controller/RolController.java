package com.sudeca.controller;

import com.google.gson.Gson;
import com.sudeca.dto.Message;
import com.sudeca.model.Rol;
//import com.sudeca.model.UserReturn;
import com.sudeca.services.IRolService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.sudeca.security.JwtConstans.*;

@RestController
@RequestMapping("/api/rol")
public class RolController {
     private static final Logger logger = LogManager.getLogger();
     @Autowired
     IRolService rolService;

     @GetMapping("/all")
      public ResponseEntity<List<Rol>> getAllRoles() {
        try {
            List<Rol> roles = rolService.getAllRoles();

            logger.info("roles: "+roles);

            if (roles.isEmpty()) {
               return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(roles, HttpStatus.OK);
            } catch (Exception e) {
                logger.info("e.getMessage(): "+e.getMessage());
              return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
      }

    @GetMapping("/roles-caja")
    public ResponseEntity<List<Rol>> getRolesCaja() {
        try {
            logger.info("getRolesCaja************* ");
            List<Rol> roles = rolService.getRolesCaja();

            logger.info("roles: "+roles);

            if (roles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(roles, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("e.getMessage(): "+e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/roles-master")
    public ResponseEntity<List<Rol>> getRolesMaster() {
        try {
            logger.info("getRolesMaster************* ");
            List<Rol> roles = rolService.getRolesMaster();

            logger.info("roles: "+roles);

            if (roles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(roles, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("e.getMessage(): "+e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

      @GetMapping("/roles/{id}")
      public ResponseEntity<Rol> getRoleById(@PathVariable("id") long id) {
            Optional<Rol> roleData = rolService.getRolById(id);
            return roleData.map(role -> new ResponseEntity<>(role, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
      }

      @PostMapping("/roles")
      public String createRole(@RequestBody Rol role) {
          Message message;
          Gson gson = new Gson();
            try {
                Rol _role = rolService.saveRol(role);
                if (_role==null) {
                    message = new Message(ROL_EXISTS_BD,false);
                    return gson.toJson(message);
                }

                logger.info("_role: "+_role);

                ResponseEntity<Rol> responseRol = new ResponseEntity<>(_role, HttpStatus.CREATED);

                /*if(responseRol.getStatusCodeValue()==HttpStatus.CREATED.value()) {
                    UserReturn userReturn = new UserReturn(_role.getIdRol(),_role.getDateCreate(),_role.getDateModified(),true,ROL_STATUS);
                    return gson.toJson(userReturn);
                }
                else {
                    message = new Message(ERROR_INSERT_STATUS+responseRol.getStatusCode(),false);
                    return gson.toJson(message);
                }*/
                return gson.toJson(null);
            } catch (Exception e) {
                message = new Message(ERROR_EXCEPTION_THROWN+" "+e.getMessage(),false);
                return gson.toJson(message);
            }
      }
    /*
    @PutMapping("/roles/{id}")
    public ResponseEntity<Rol> updateRole(@PathVariable("id") long id, @RequestBody Rol role) {
        Optional<Rol> roleData = roleRepository.findById(id);
        if (roleData.isPresent()) {
            String jwt = new CreateJWTImpl().createJWT(role.getRol(),role.getDescripcion(),  role.getEstatus(),JW_TIME_TO_LIVE);
            Rol _role = roleData.get();
            _role.setRol(role.getRol());
            _role.setDescripcion(role.getDescripcion());
            _role.setEstatus(role.getEstatus());

            _role.setFechaUp(String.valueOf(new Date()));
            _role.setFechaReg(roleData.get().getFechaReg());
            return new ResponseEntity<>(roleRepository.save(_role), HttpStatus.OK);
        } else {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/role/{id}")
    public ResponseEntity<HttpStatus> deleteRole(@PathVariable("id") long id) {
        try {
            roleRepository.deleteById(id);
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Rol findByRole(String role) {
        try {
            return roleRepository.findByRolContainingIgnoreCase(role);
        } catch (Exception e) {
            return null;
        }
    }*/

}