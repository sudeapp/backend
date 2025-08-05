package com.sudeca.dto;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record CajaAhorroDTO(
        long idCaho,
        @NotBlank @Size(max = 10) String codigoCaho,
        @NotBlank @Size(max = 11) String rif,

        @NotBlank String nombre,
        String descripcion,

        String patrono,
        @NotNull Integer sector,
        @NotNull Integer idPlan,
        @NotNull Short periodosEjercicio,
        @NotNull LocalDate ultimoMesCerrado,
        @NotNull Short ultimoPeriodoCerrado,
        Short mesCierre,
        Short lapsoCierreMensual,
        Integer idMonedaLocal,
        LocalDate inicioVigencia,
        LocalDate finVigencia,
        LocalDate ultimoLapsoGenerado,
        @NotNull Integer usuarioCreacion,
        @NotNull Integer estatus,
        List<CategoriaDTO> categorias,
        List<UsuarioCajaDTO> usuarios
) {}

