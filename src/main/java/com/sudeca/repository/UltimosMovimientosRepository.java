package com.sudeca.repository;
import com.sudeca.dto.ListaComprobanteDTO;
import com.sudeca.dto.UltimosMovimientosDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface UltimosMovimientosRepository extends JpaRepository<UltimosMovimientosDTO, Long> {

    @Query(
            value = "SELECT * FROM contabilidad.fr_ultimos_movimientos (CAST(?1 AS bigint), CAST(?2 AS smallint)) AS f",
            nativeQuery = true
    )
    List<UltimosMovimientosDTO> findUltimosMovimientos(Long idCaja, Integer dias);
}