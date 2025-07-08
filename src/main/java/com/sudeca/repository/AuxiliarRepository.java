package com.sudeca.repository;

import com.sudeca.model.Auxiliar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuxiliarRepository extends JpaRepository<Auxiliar, Long> {
  Auxiliar findByCodAuxiContainingIgnoreCase(String cod);
  Auxiliar findByRifContainingIgnoreCase(String rif);
}
