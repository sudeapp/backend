package com.sudeca.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(
        name = "plantilla_plan_nivel",
        schema = "contabilidad",
        uniqueConstraints = {
                @UniqueConstraint(name = "pplann_uk1", columnNames = {"id_pplan_contable", "nivel", "longitud"}),
                @UniqueConstraint(name = "pplann_uk2", columnNames = {"id_pplan_contable", "nivel"})
        }
)
public class PlantillaPlanNivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pplan_nivel", nullable = false)
    private Long idPplanNivel;

    @Column(name = "id_pplan_contable", nullable = false)
    private Long idPplanContable;

    @Column(name = "nivel", nullable = false, precision = 2)
    private Integer nivel;

    @Column(name = "longitud", nullable = false, precision = 2)
    private Integer longitud;

    @Column(name = "descripcion", nullable = false, length = 60)
    private String descripcion;

    @Column(name = "longitud_nivel", nullable = false, precision = 2)
    private Integer longitudNivel;

}
