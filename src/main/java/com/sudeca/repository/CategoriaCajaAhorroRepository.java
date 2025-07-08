package com.sudeca.repository;

import com.sudeca.model.CajaAhorro;
import com.sudeca.model.Categoria;
import com.sudeca.model.CategoriaCajaAhorro;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoriaCajaAhorroRepository extends JpaRepository<CategoriaCajaAhorro, Long> {
    void deleteByCajaAhorro(CajaAhorro caja);

    @Modifying
    @Transactional
    @Query("DELETE FROM CategoriaCajaAhorro c WHERE c.cajaAhorro.idCaho = :idCaho")
    void deleteByIdCaho(@Param("idCaho") Long idCaho);
}
