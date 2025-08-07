package com.sudeca.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ComprobanteDTO {
    private Long idComprobante;
    private LocalDate fechaCbte;
    private Integer periodo;
    private Short estatusCbte;
    private LocalDate fechaVerificacion;
    private Long idUsuarioVerificacion;
    private Long idUsuarioActualizacion;
    private Long idUsuarioCreacion;
    private Long idTcbte;
    private Long idCaho;
    private Long nroCbte;
    private List<ComprobanteDetDTO> comprobanteDet;
}
