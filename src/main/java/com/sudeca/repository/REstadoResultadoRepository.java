package com.sudeca.repository;
import com.sudeca.dto.EstadoResultadoDTO;
import com.sudeca.dto.LibroDiarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface REstadoResultadoRepository extends JpaRepository<EstadoResultadoDTO, Long> {

    @Query(
            value = "SELECT * FROM contabilidad.fr_estado_resultado(CAST(?1 AS bigint), CAST(?2 AS date),CAST(?3 AS smallint),CAST(?4 AS boolean)) AS f",
            nativeQuery = true
    )
    List<EstadoResultadoDTO> findEstadoResultado(Long idCaja, LocalDate fecha, Integer periodo, Boolean tipo);

}