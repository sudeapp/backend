package com.sudeca.repository;
import com.sudeca.dto.BalanceComprobacionDTO;
import com.sudeca.dto.EstadoResultadoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RBalanceComprobacionRepository extends JpaRepository<BalanceComprobacionDTO, Long> {

    /*
    select * from fr_balance_comprobacion1(20::bigint, '2025-06-30'::date, 6::smallint, false::boolean);
     */
    @Query(
            value = "SELECT * FROM contabilidad.fr_balance_comprobacion(CAST(?1 AS bigint), CAST(?2 AS date),CAST(?3 AS smallint),CAST(?4 AS boolean)) AS f",
            nativeQuery = true
    )
    List<BalanceComprobacionDTO> findBalanceComprobacion(Long idCaja, LocalDate fecha, Integer periodo, Boolean tipo);

}