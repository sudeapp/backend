package com.sudeca.repository;

import com.sudeca.model.CajaAhorro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CajaAhorroRepository extends JpaRepository<CajaAhorro, Long> {
    CajaAhorro findByCodigoCaho(String codigo);
    CajaAhorro findByRif(String codigo);
}
