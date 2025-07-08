package com.sudeca.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * * Author: Luis López
 **/
@Data
@Entity
@AllArgsConstructor
@Builder
@Table(
        name = "auxiliar",
        schema = "contabilidad",
        uniqueConstraints = { // restricciones UNIQUE a nivel de tabla
                @UniqueConstraint(columnNames = "cod_auxi", name = "auxi_uk1"),
                @UniqueConstraint(columnNames = "id_fiscal", name = "auxi_uk2")
        }
)
public class Auxiliar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id // Marca el campo como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el valor es generado por la base de datos (IDENTITY para PostgreSQL)
    @Column(name = "id_auxi", nullable = false) // Mapea a la columna id_auxi, no nula
    private Long idAuxi; // Usamos Long para int8

    @Column(name = "cod_auxi", length = 10, nullable = false) // Mapea a cod_auxi, varchar(10), no nulo
    private String codAuxi;

    @Column(name = "desc_auxi", length = 90, nullable = false) // Mapea a desc_auxi, varchar(90), no nulo
    private String descAuxi;

    @Column(name = "direccion", length = 120, nullable = true) // Mapea a direccion, varchar(120), puede ser nulo
    private String direccion;

    @Column(name = "telefono", length = 60, nullable = true) // Mapea a telefono, varchar(60), puede ser nulo
    private String telefono;

    @Column(name = "rif", length = 255, nullable = true) // Mapea a rif, varchar(255), puede ser nulo
    private String rif;
    public Auxiliar() {
    }
}
