package com.sudeca.dto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ComprobanteDetDTO {
    private Long id;
    private Long idPlanContable;
    private Long idPlanCatalogo;
    private Long idTaux;
    private Long idAuxi;
    private Long idOpec;
    private String nroDoc;
    private LocalDate fechaDoc;
    private String dbcr;
    private BigDecimal bsMonto;
    private String descripcion;
    private Integer linea;
    private Integer tplan;
}
