package com.sudeca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.io.Serializable;

@Data
@Entity
@Table(name = "plantilla_plan_catalogo", schema = "contabilidad",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id_pplan_contable", "cuenta"}, name = "pplanc_uk1"),
                @UniqueConstraint(columnNames = {"id_pplan_catalogo", "id_pplan_contable", "nivel", "longitud"}, name = "pplanc_uk2"),
                @UniqueConstraint(columnNames = {"id_pplan_contable", "id_pplan_catalogo", "id_taux"}, name = "pplanc_uk3")
        })
public class PlantillaPlanCatalogo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pplan_catalogo", nullable = false)
    private Long idPPlanCatalogo;

    @NotNull
    @Column(name = "id_pplan_contable", nullable = false)
    private Long idPPlanContable;

    @NotNull
    @Digits(integer = 2, fraction = 0)
    @Column(name = "nivel", nullable = false, precision = 2)
    private Integer nivel;

    @NotNull
    @Digits(integer = 2, fraction = 0)
    @Column(name = "longitud", nullable = false, precision = 2)
    private Integer longitud;

    @NotBlank
    @Size(max = 20)
    @Column(name = "cuenta", nullable = false, length = 20)
    private String cuenta;

    @NotBlank
    @Size(max = 120)
    @Column(name = "descripcion", nullable = false, length = 120)
    private String descripcion;

    @Column(name = "id_cuenta_asc")
    private Long idCuentaAsc;

    @ManyToOne
    @JoinColumn(name = "id_taux", referencedColumnName = "id_taux", nullable = true)
    private AuxiliarTipo auxiliarTipo;

    @NotNull
    @Column(name = "movimiento", nullable = false)
    private Boolean movimiento = false;

    @NotNull
    @Column(name = "monetaria", nullable = false)
    private Boolean monetaria = false;

    @NotNull
    @Column(name = "estatus_cuenta", nullable = false, columnDefinition = "boolean default true")
    private Boolean estatusCuenta = true;

    @NotNull
    @Digits(integer = 1, fraction = 0)
    @Column(name = "tplan", nullable = false, columnDefinition = "numeric(1) default 1")
    private Integer tplan = 1;

}
