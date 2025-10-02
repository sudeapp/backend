package com.sudeca.repository;

import com.sudeca.model.CajaAhorro;
import com.sudeca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CajaAhorroRepository extends JpaRepository<CajaAhorro, Long> {
    CajaAhorro findByCodigoCaho(String codigo);
    CajaAhorro findByRif(String codigo);

    @Query("SELECT c FROM CajaAhorro c WHERE c.codigoCaho = :codigo and c.idCaho != :id")
    CajaAhorro findByValCodigoCaho(@Param("codigo") String codigo, @Param("id") Long id);

    @Query("SELECT c FROM CajaAhorro c WHERE c.rif = :rif and c.idCaho != :id")
    CajaAhorro findByValRifCaho(@Param("rif") String rif, @Param("id") Long id);
}
