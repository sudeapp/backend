package com.sudeca.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categoria", schema = "contabilidad")
public class Categoria implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id_categoria")
  private Integer idCategoria;

  @Column(name = "cod_categoria")
  private String codCategoria;

  @Column(name = "desc_categoria")
  private String descCategoria;

  @Column(name = "oby")
  private int oby;


  public Categoria() {

  }

}
