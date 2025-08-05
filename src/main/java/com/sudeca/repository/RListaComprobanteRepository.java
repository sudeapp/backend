package com.sudeca.repository;
import com.sudeca.dto.LibroDiarioDTO;
import com.sudeca.dto.ListaComprobanteDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RListaComprobanteRepository extends JpaRepository<ListaComprobanteDTO, Long> {

    @Query(
            value = "SELECT * FROM contabilidad.fr_listado_comprobantes (CAST(?1 AS bigint), CAST(?2 AS date),CAST(?3 AS date),CAST(?4 AS smallint)) AS f",
            nativeQuery = true
    )
    List<ListaComprobanteDTO> findListaComprobante(Long idCaja, LocalDate fechaDesde,LocalDate fechaHasta,Integer tipo);
}