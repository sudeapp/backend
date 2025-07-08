package com.sudeca.controller;

import com.google.gson.Gson;
import com.sudeca.config.JWTAuthtenticationConfig;
import com.sudeca.dto.*;
import com.sudeca.model.Usuario;
import com.sudeca.impl.CreateJWTImpl;
import com.sudeca.services.IUsuarioService;
import com.sudeca.utils.EmailService;
import com.sudeca.utils.GeneratePassword;
import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.sudeca.security.Constans.*;

@RestController
@RequestMapping("/api")
public class LoginController {
	
	private static final Logger logger = LogManager.getLogger();

    @Autowired
    private EmailService emailService;

    @Autowired
    private IUsuarioService userService;

    @Autowired
    JWTAuthtenticationConfig jwtAuthtenticationConfig;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> getLogin(@RequestBody Login login) {
        logger.info("getLogin " + login.getPass());
        logger.info("getLogin " + login.getUsuario());
        ResponseDTO responseDTO = new ResponseDTO();
        Usuario userData = userService.getLogin(login);
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        if(userData!=null) {
            logger.info("userData: "+ userData.getUsuario());
            String token = jwtAuthtenticationConfig.getJWTToken(login.getUsuario());
            logger.info("token: "+ token);
            userData.setToken(token);
            usuarioDTO = usuarioDTO.toDTO(userData);
            responseDTO.setData(usuarioDTO);
            responseDTO.setStatus("success");
            responseDTO.setMessage("Autenticación exitosa");
            logger.info("ResponseDTO 1", responseDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            responseDTO.setData(userData);
            responseDTO.setStatus("Error");
            responseDTO.setMessage("Error en la autenticación");
            logger.info("ResponseDTO 2", responseDTO);
            return new ResponseEntity<>(responseDTO,HttpStatus.OK);
        }
    }

      @GetMapping("/users")
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

      @GetMapping("/users/{id}")
      public ResponseEntity<Usuario> getUserById(@PathVariable("id") long id) {
            Optional<Usuario> userData = userService.getUserById(id);

            return userData.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
      }

      @PutMapping("/users/{id}")
    public ResponseEntity<Usuario> updateUser(@PathVariable("id") long id, @RequestBody Usuario user) {
      Usuario userUpdate = userService.updateUser(id,user);
      if(userUpdate!=null){
          return new ResponseEntity<>(userUpdate, HttpStatus.OK);
      } else {
          return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        try {
            userService.deleteUser(id);
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/mail/{mail}")
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
                Date fechExp = emailService.SumarDias(new Date(),1);

                // Actualizar la tabla Usuario
                logger.info("fechExp: "+fechExp);
                user.setFechaExpCod(String.valueOf(fechExp));
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

    @PostMapping("/password-recovery")
    public ResponseEntity<Map<String, String>> passwordRecovery(@RequestBody Map<String, String> request) {
        String email = request.get("username");
        String emailValue = "";
        Map<String, String> response = new HashMap<>();

        logger.info("email: " + email);
        Usuario user = userService.getEmail(email);
        logger.info("user: " + user);

        try {
            if(user != null) {
                // Generate Código de Validación
                GeneratePassword generatePassword = new GeneratePassword();
                String validateCode = generatePassword.crearPasswordOnlyNumbers();

                //Calcular Fecha de Expiración
                Date fechExp = emailService.SumarDias(new Date(), 1);

                // Actualizar la tabla Usuario
                logger.info("fechExp: " + fechExp);
                user.setFechaExpCod(String.valueOf(fechExp));
                user.setCodReset(validateCode);
                Usuario userUpdate = userService.updateUser(user.getIdUsuario(), user);

                // Enviar correo
                String emailResult = emailService.sendSimpleMail(email, validateCode);

                response.put("status", "success");
                response.put("message", emailResult);
                return ResponseEntity.ok(response);
            } else {
                emailValue = "El correo no existe en el Sistema";
                response.put("status", "error");
                response.put("message", emailValue);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage(), e);
            response.put("status", "error");
            response.put("message", "Ocurrió un error al procesar la solicitud");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody PaswReset paswReset) {
        Map<String, String> response = new HashMap<>();
        logger.info("paswReset.getEmail_code(): "+paswReset.getEmail_code());
        Usuario user = findByCodReset(paswReset);
        logger.info("user: "+user);
        try {
            if(user!=null) {
                // Actualizar el nuevo password en la la tabla Usuario
                user.setPass(String.valueOf(paswReset.getPassword()));
                Usuario userUpdate = userService.updateUser(user.getIdUsuario(),user);
                if(userUpdate!=null){
                    response.put("status", "success");
                    response.put("message", "Contraseña reseteada exitosamente!");
                    return ResponseEntity.ok(response);
                } else {
                    response.put("status", "Error");
                    response.put("message", "Error al resetear contraseña!");
                    return ResponseEntity.ok(response);
                }
            } else{
                logger.info("El código: "+paswReset.getEmail_code()+" no existe en el Sistema ");
                response.put("status", "Error");
                response.put("message", "El código que introdujo es incorrecto");
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            logger.info("Exception e: "+e.getMessage());
            response.put("status", "Error");
            response.put("message", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/jwtVerify")
    public ResponseEntity<Object> jwtVerify(@RequestBody Jwt jwt){
        ResponseReturn response = new ResponseReturn();
        try {
            Claims claims = new CreateJWTImpl().decodeJWT(jwt.getJwt());
            return new ResponseEntity<>(claims, HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Exception e: "+e.getMessage());
            response.setResult(false);
            response.setMensaje(e.getMessage());
            response.setStatus("Error");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    public Usuario findByUsuario(String usuario) {
        try {
            return userService.getUser(usuario);
        } catch (Exception e) {
            return null;
        }
    }
    public Usuario findByCodReset(PaswReset paswReset) {
        try {
            return userService.getCodReset(paswReset);
        } catch (Exception e) {
            return null;
        }
    }


}