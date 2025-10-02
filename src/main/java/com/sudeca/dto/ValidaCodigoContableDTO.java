package com.sudeca.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ValidaCodigoContableDTO {
    @JsonProperty("es_valido")
    private Boolean esValido;

    @JsonProperty("mensaje")
    private String mensaje;

    @JsonProperty("nivel_cuenta")
    private Integer nivelCuenta;

    @JsonProperty("longitud_cuenta")
    private Integer longitudCuenta;

    @JsonProperty("acepta_movimiento")
    private Boolean aceptaMovimiento;

    @JsonProperty("id_cuenta_padre")
    private Long idCuentaPadre;

    @JsonProperty("cuenta_padre")
    private String cuentaPadre;

}
