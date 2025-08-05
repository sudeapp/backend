package com.sudeca.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
@Data
@Entity
public class LibroDiarioDTO implements Serializable {
    @Id
    private int id;
    private int idCaja;
    private String nroCbte;
    private LocalDate fechaCte;
    private String periodo;
    private String idPlanCatalogo;
    private String cuenta;
    private String descripcionCuenta;
    private String codigoAuxiliar;
    private String nombreAuxiliar;
    private String descripcion;
    private Double debitos;
    private Double creditos;
}
