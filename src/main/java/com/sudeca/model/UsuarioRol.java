package com.sudeca.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * * Author: Francisco Hernandez
 **/
@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuario_rol",schema = "acceso")
public class UsuarioRol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_usuario_rol")
    private Long idUsuarioRol;

    @Column(name = "fecha_reg",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp fechaReg;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_usuario")
    @ToString.Exclude
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;

    public UsuarioRol() {
    }

}
