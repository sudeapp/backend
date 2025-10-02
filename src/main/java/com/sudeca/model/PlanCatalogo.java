package com.sudeca.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
@Data
@Entity
@Table(
        name = "plan_catalogo",
        schema = "contabilidad"
)
public class PlanCatalogo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan_catalogo", nullable = false)
    private Long idPlanCatalogo;

    @Column(name = "id_plan_contable", nullable = false)
    private Long idPlanContable;

    @Column(name = "nivel", nullable = false, precision = 2)
    private Integer nivel;

    @Column(name = "longitud", nullable = false, precision = 2)
    private Integer longitud;

    @Column(name = "cuenta", nullable = false, length = 20)
    private String cuenta;

    @Column(name = "descripcion", nullable = false, length = 120)
    private String descripcion;

    @Column(name = "id_cuenta_asc")
    private Long idCuentaAsc;

    /*@Column(name = "id_taux")
    private Long idTaux;*/

    @ManyToOne
    @JoinColumn(name = "id_taux", referencedColumnName = "id_taux", nullable = true)
    private AuxiliarTipo auxiliarTipo;

    @Column(name = "movimiento", nullable = false)
    private Boolean movimiento;

    @Column(name = "monetaria", nullable = false)
    private Boolean monetaria;

    @Column(name = "estatus_cuenta", nullable = false, columnDefinition = "boolean default true")
    private Boolean estatusCuenta;

    @Column(name = "tplan", nullable = false, precision = 1, columnDefinition = "numeric(1) default 1")
    private Integer tplan = 1;

    // Constructores
    public PlanCatalogo() {}

}