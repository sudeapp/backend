package com.sudeca.dto;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record CajaAhorroDTO(
        Long idCaho,
        @NotBlank @Size(max = 10) String codigoCaho,
        @NotBlank @Size(max = 11) String rif,

        @NotBlank String nombre,
        String descripcion,

        String patrono,
        @NotNull Long sector,
        @NotNull Long idPlan,
        @NotNull Short periodosEjercicio,
        @NotNull LocalDate ultimoMesCerrado,
        @NotNull Short ultimoPeriodoCerrado,
        Short mesCierre,
        Short lapsoCierreMensual,
        Long idMonedaLocal,
        LocalDate inicioVigencia,
        LocalDate finVigencia,
        LocalDate ultimoLapsoGenerado,
        @NotNull Long usuarioCreacion,
        @NotNull Integer estatus,
        List<CategoriaDTO> categorias,
        List<UsuarioCajaDTO> usuarios,

        Long idPPlanContable
) {}

