package com.sudeca.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * * Author: Francisco Hernandez
 **/
@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rol",schema = "acceso")
public class Rol implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_rol")
    private long idRol;

    @Column(name = "codigo")
    private int codigo;

    @Column(name = "usuario_caja")
    private int usuarioCaja;
    @Column(name = "rol")
    private String rol;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_reg")
    private String fechaReg;

    @Column(name = "fecha_up")
    private String fechaUp;

    @Column(name = "estatus")
    private Integer estatus;

    @OneToMany(mappedBy = "rol",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH},
            orphanRemoval = true)
    @JsonManagedReference // Indica que este es el lado "principal" de la relación (evita la redundancia)
    @ToString.Exclude
    private List<RolPermisologia> rolPermisologia = new ArrayList<>();
    public Rol() {
    }

    public Rol(String rol, String descripcion, Integer status) {
        this.rol = rol;
        this.descripcion = descripcion;
        this.estatus = status;
        this.fechaReg = String.valueOf(new Date());
        this.fechaUp = String.valueOf(new Date());
    }

    @Override
    public String toString() {
        return "Role [idRol=" + idRol + ", rol=" + rol + ", descripcion=" + descripcion+ ", status=" + estatus +  ", FechaCreacion=" + fechaReg + "]";
    }
}
