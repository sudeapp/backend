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
@Table(name = "permisologia", schema = "acceso")
public class Permisologia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permisologia", nullable = false)
    private Long id;

    @Column(name = "descripcion", nullable = false, length = 90)
    private String descripcion;

    @Column(name = "estatus", columnDefinition = "numeric(1) default 1")
    private Short estatus = 1;  // Usamos Short para coincidir con numeric(1)

    @Column(name = "codigo")
    private Integer codigo;

    // Constructor por defecto (requerido por JPA)
    public Permisologia() {
    }

}
