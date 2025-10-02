package com.sudeca.controller;

import java.time.LocalDate;
import java.util.*;

import com.sudeca.dto.*;
import com.sudeca.model.*;

import com.sudeca.utils.EmailService;
import com.sudeca.services.IUsuarioService;
import com.sudeca.utils.GeneratePassword;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.sudeca.security.JwtConstans.*;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
	
	private static final Logger logger = LogManager.getLogger();

    @Autowired
    private EmailService emailService;

    @Autowired
    private IUsuarioService userService;

    @GetMapping("/get-all")
    public ResponseEntity<List<Usuario>> getAllUsers() {
        try {
            List<Usuario> users = new ArrayList<Usuario>();
            users = userService.getAllUsers();
            logger.info("users: "+users);

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
             }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("e.getMessage(): "+e.getMessage());
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-users-by-idcaho")
    public ResponseEntity<List<Usuario>> getAllUsersCaho(@RequestParam("idCaho") long id) {
        try {
            List<Usuario> users = userService.getAllUsersCaho(id);
            logger.info("Número de usuarios encontrados: {}", users.size());

            // Siempre devuelve la lista, incluso si está vacía
            return new ResponseEntity<>(users, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Error al obtener usuarios para idCaho {}: {}", id, e.getMessage(), e);

            // Devuelve una lista vacía con error 500
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-users-sudeca")
    public ResponseEntity<List<Usuario>> getAllUsersSudeca() {
        try {
            List<Usuario> users = userService.getAllUsersSudeca();
            logger.info("Número de usuarios encontrados: {}", users.size());

            // Siempre devuelve la lista, incluso si está vacía
            return new ResponseEntity<>(users, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Error al obtener usuarios sudeca {}: {}", e.getMessage(), e);

            // Devuelve una lista vacía con error 500
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-id/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable("id") long id) {
        Optional<Usuario> userData = userService.getUserById(id);

        return userData.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> createUser(@RequestBody UsuarioDTO user) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Usuario _user = userService.saveUser(user,true);
            logger.info("_user: "+_user);
            if (_user != null){
                responseDTO.setData(_user);
                responseDTO.setStatus("success");
                responseDTO.setMessage("guardar");
            }else{
                responseDTO.setData(null);
                responseDTO.setStatus("notFound");
                responseDTO.setMessage("error al guardar");
            }
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setData(null);
            responseDTO.setStatus("Error");
            responseDTO.setMessage(ERROR_EXCEPTION_THROWN + " " +
                    "Registro no guardado " + e.getMessage());
            return ResponseEntity.ok(responseDTO);
        }
    }

    @PostMapping("/cambiar-password")
    public ResponseEntity<ResponseDTO> cambiarPassword(@RequestBody PasswordUsuarioDTO user) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            PasswordUsuarioDTO _user = userService.savePassword(user);
            logger.info("_user: "+_user);
            if (_user != null){
                responseDTO.setData(_user);
                responseDTO.setStatus("success");
                responseDTO.setMessage("guardar");
            }else{
                responseDTO.setData(_user);
                responseDTO.setStatus("notFound");
                responseDTO.setMessage("error al guardar");
            }
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setData(null);
            responseDTO.setStatus("Error");
            responseDTO.setMessage(ERROR_EXCEPTION_THROWN + " " +
                    "Registro no guardado " + e.getMessage());
            return ResponseEntity.ok(responseDTO);
        }
    }

    @PostMapping("/save-sudeca")
    public ResponseEntity<ResponseDTO> createUserSudeca(@RequestBody UsuarioDTO user) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Usuario _user = userService.saveUser(user,false);
            logger.info("_user: "+_user);
            if (_user != null){
                responseDTO.setData(_user);
                responseDTO.setStatus("success");
                responseDTO.setMessage("guardar");
            }else{
                responseDTO.setData(null);
                responseDTO.setStatus("notFound");
                responseDTO.setMessage("error al guardar");
            }
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setData(null);
            responseDTO.setStatus("Error");
            responseDTO.setMessage(ERROR_EXCEPTION_THROWN + " " +
                    "Registro no guardado " + e.getMessage());
            return ResponseEntity.ok(responseDTO);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateUser(@RequestParam("id") long id, @RequestBody UsuarioDTO user) {
        logger.info("updateUser _user: " + id,user);

        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Usuario userUpdate = userService.updateUserDTO(id,user);
            logger.info("_user: "+user);
            if (userUpdate != null){
                responseDTO.setData(user);
                responseDTO.setStatus("success");
                responseDTO.setMessage("update");
            }else{
                responseDTO.setData(null);
                responseDTO.setStatus("notFound");
                responseDTO.setMessage("error al actualizar");
            }
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setData(null);
            responseDTO.setStatus("Error");
            responseDTO.setMessage(ERROR_EXCEPTION_THROWN + " " +
                    "Registro no guardado " + e.getMessage());
            return ResponseEntity.ok(responseDTO);
        }
    }

    @PutMapping("/update-profile")
    public ResponseEntity<ResponseDTO> updateUserProfile(@RequestParam("id") long id, @RequestBody Usuario user) {
        logger.info("updateUser _user: " + id,user);

        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Usuario userUpdate = userService.updateUser(id,user);
            logger.info("_user: "+user);
            if (userUpdate != null){
                responseDTO.setData(user);
                responseDTO.setStatus("success");
                responseDTO.setMessage("update");
            }else{
                responseDTO.setData(null);
                responseDTO.setStatus("notFound");
                responseDTO.setMessage("error al actualizar");
            }
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            responseDTO.setData(null);
            responseDTO.setStatus("Error");
            responseDTO.setMessage(ERROR_EXCEPTION_THROWN + " " +
                    "Registro no guardado " + e.getMessage());
            return ResponseEntity.ok(responseDTO);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        try {
            userService.deleteUser(id);
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/send-email/{mail}")
    public String sendMail(@PathVariable("mail") String email) {
        String emailValue="";

        logger.info("email: "+email);
        Usuario user = userService.getEmail(email);
        logger.info("user: "+user);
        try {
            if(user!=null) {
                // Generate Código de Validación
                GeneratePassword generatePassword = new GeneratePassword();
                String validateCode = generatePassword.crearPasswordOnlyNumbers();

                //Calcular Fecha de Expiración
                //Date fechExp = emailService.SumarDias(new Date(),1);
                LocalDate fechExp = LocalDate.now().plusDays(1);
                // Actualizar la tabla Usuario
                logger.info("fechExp: "+fechExp);
                user.setFechaExpCod(fechExp);
                user.setCodReset(validateCode);
                Usuario userUpdate = userService.updateUser(user.getIdUsuario(),user);
                return emailService.sendSimpleMail(email,validateCode);
            } else{
                emailValue="El correo no existe en el Sistema";
            }
        } catch (Exception e) {
            logger.info("Exception e: "+e.getMessage());
        }

        return emailValue;
    }

    @GetMapping("/get-email")
    public Usuario getEmail(@RequestParam("email") String email) {
        logger.info("getEmail: "+ email);
        try {
          return userService.getEmail(email);
        } catch (Exception e) {
          return null;
        }
    }

    @GetMapping("/get-cedula")
    public Usuario getCedula(@RequestParam("cedula") String cedula) {
        logger.info("getCedula: "+ cedula);
        try {
            return userService.getCedula(cedula);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/{id}/estatus")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateStatusDTO request) {

        userService.updateUserStatus(id, request.getEstatus());
        return ResponseEntity.ok().body("Estatus actualizado exitosamente");
    }
}