package com.sudeca.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

/**
 * * Author: Luis López
 **/
@Data
@Entity
@Table(name = "plantilla_plan_contable", schema = "contabilidad")
public class PlantillaPlanContable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pplan_contable")
    private Long idPPlanContable;

    @Column(name = "cod_pplan_contable")
    private String codPPlan;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "niveles")
    private int niveles;

    @Column(name = "longitud")
    private int longitud;

    @Column(name = "st_plan")
    private boolean stPlan;

    @Column(name = "valido")
    private boolean valido;

}
