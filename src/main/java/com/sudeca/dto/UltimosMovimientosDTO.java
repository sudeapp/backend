package com.sudeca.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class UltimosMovimientosDTO {
    @Id
    private Long idCbte;
    private Long idCaho;
    private String nroCbte;
    private String usuario;
    private LocalDateTime fechaCbte;
    private String periodo;
    private String estatusCbte;
    private LocalDateTime fechaCreacion;
    private Long idUsuarioCreacion;
    private LocalDateTime fechaVerificacion;
    private Long idUsuarioVerificacion;
    private LocalDateTime fechaActualizacion;
    private Long idUsuarioActualizacion;
    private Long idTcbte;
    private Double bsMontoDiferencia;
    private Double bsMontoDebito;
    private Double bsMontoCredito;
}
