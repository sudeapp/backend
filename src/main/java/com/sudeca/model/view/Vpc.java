package com.sudeca.model.view;

import jakarta.persistence.Entity;
import lombok.Data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Immutable;

@Data
@Entity
@Immutable
@Table(name = "vpc",schema = "contabilidad")
public class Vpc {

    @Id
    @Column(name = "id_plan_catalogo")
    private int id_plan_catalogo;

    @Column(name = "formato")
    private String formato;

    @Column(name = "cuenta")
    private String cuenta;

    @Column(name = "nivel")
    private String nivel;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "descripcion")
    private String descripcion;

}