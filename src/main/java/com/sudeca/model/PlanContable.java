package com.sudeca.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

/**
 * * Author: Francisco Hernandez
 **/
@Data
@Entity
@Table(name = "plan_contable", schema = "contabilidad")
public class PlanContable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_plan_contable")
    private Long idPlanContable;

    @Column(name = "cod_plan")
    private String codPlan;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "niveles")
    private String levels;

    @Column(name = "longitud")
    private String lengths;

    @Column(name = "st_plan")
    private String stPlan;

    @Column(name = "valido")
    private String valid;

    public PlanContable() {
    }

    public PlanContable(String codPlan, String description, String levels, String lengths, String stPlan, String valid) {
        this.codPlan = codPlan;
        this.description = description;
        this.levels = levels;
        this.lengths = lengths;
        this.stPlan = stPlan;
        this.valid = valid;
    }
}
