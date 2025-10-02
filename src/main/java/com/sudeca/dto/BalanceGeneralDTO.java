package com.sudeca.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
public class BalanceGeneralDTO implements Serializable {
    @Id
    private int id;
    private String cuenta;
    private String descripcion;
    private String cod_auxi;
    private String desc_auxi;
    private Double saldo_anterior;
    private Double debitos_mes;
    private Double creditos_mes;
    private Double saldo_actual;
}
