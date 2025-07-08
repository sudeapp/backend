package com.sudeca.dto;

import lombok.Data;

import java.util.List;
@Data
public class ValidaComprobanteDTO {
    private Long idUsuario;
    private List<ComprobanteDTO> comprobantes;
}
