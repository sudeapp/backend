package com.sudeca.repository;
import com.sudeca.dto.LibroDiarioDTO;
import com.sudeca.dto.ListaComprobanteDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface RLibroDiarioRepository extends JpaRepository<LibroDiarioDTO, Long> {

    @Query(
            value = "SELECT * FROM contabilidad.fr_libro_diario (CAST(?1 AS bigint), CAST(?2 AS date)) AS f",
            nativeQuery = true
    )
    List<LibroDiarioDTO> findLibroDiario(Long idCaja, LocalDate fechaCorte);

}