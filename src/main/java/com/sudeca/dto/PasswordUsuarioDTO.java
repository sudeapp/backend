package com.sudeca.dto;
import lombok.Data;

@Data
public class PasswordUsuarioDTO {
    private long idUsuario;
    private String viejo_password;
    private String nuevo_password;
    private String mensaje;
}
