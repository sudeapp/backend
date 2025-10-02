package com.sudeca.repository;
import com.sudeca.dto.BalanceComprobacionDTO;
import com.sudeca.dto.BalanceGeneralDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RBalanceGeneralRepository extends JpaRepository<BalanceGeneralDTO, Long> {

    @Query(
            value = "SELECT * FROM contabilidad.fr_balance_general(CAST(?1 AS bigint), CAST(?2 AS date),CAST(?3 AS smallint),CAST(?4 AS boolean)) AS f",
            nativeQuery = true
    )
    List<BalanceGeneralDTO> findBalanceGeneral(Long idCaja, LocalDate fecha, Integer periodo, Boolean tipo);
}