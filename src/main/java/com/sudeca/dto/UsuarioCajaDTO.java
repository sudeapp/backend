package com.sudeca.dto;

import com.sudeca.model.Rol;
import com.sudeca.model.UsuarioRol;

public record UsuarioCajaDTO(
    long idUsuario,
    String nombre,
    String email,
    String pass,
    Integer estatus,

    Rol rol
) {}
