package com.sudeca.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuario",schema = "acceso")
public class Usuario implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_usuario")
  private long idUsuario;

  @Column(name = "usuario")
  private String usuario;

  @Column(name = "pass")
  private String pass;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "apellido")
  private String apellido;

  @Column(name = "cedula")
  private String cedula;

  @Column(name = "direccion")
  private String direccion;

  @Column(name = "telefono")
  private String telefono;

  @Column(name = "celular")
  private String celular;

  @Column(name = "email")
  private String email;

  @Column(name = "fecha_nac")
  private LocalDate fechaNac;

  @Column(name = "fecha_reg")
  private LocalDate dateCreate;

  @Column(name = "fecha_up")
  private LocalDate dateModified;

  @Column(name = "cod_reinicio")
  private String codReset;

  @Column(name = "fecha_exp_cod")
  private String fechaExpCod;

  @Column(name = "estatus")
  private Integer estatus;

  @ManyToOne
  @JoinColumn(name = "id_caho")
  @JsonBackReference // Indica que este es el lado "secundario"
  private CajaAhorro cajaAhorro;

  @OneToMany(mappedBy = "usuario",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH},
            orphanRemoval = true)
  @JsonManagedReference // Indica que este es el lado "principal" de la relación (evita la redundancia)
  @ToString.Exclude
  private List<UsuarioRol> rol = new ArrayList<>();

  private String token;

  public Usuario(){

  }

}
