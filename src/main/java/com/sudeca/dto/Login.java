package com.sudeca.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Entity
//@Table(name = "usuario")
@Data
public class Login {
    private String usuario;
    private String pass;

    public Login(String usuario, String pass) {
        this.usuario = usuario;
        this.pass = pass;
    }
}
