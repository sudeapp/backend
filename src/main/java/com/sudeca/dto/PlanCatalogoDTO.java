package com.sudeca.dto;

import com.sudeca.model.AuxiliarTipo;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
public class PlanCatalogoDTO implements Serializable {

    private Long idPlanCatalogo;
    private Long idPlanContable;
    private Integer nivel;
    private Integer longitud;
    private String cuenta;
    private String descripcion;
    private Long idCuentaAsc;
    private Long idTaux;
    private AuxiliarTipo auxiliarTipo;
    private Boolean movimiento;
    private Boolean monetaria;
    private Boolean estatusCuenta;
    private Integer tplan = 1;
}