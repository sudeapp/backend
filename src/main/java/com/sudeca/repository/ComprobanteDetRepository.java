package com.sudeca.repository;

import com.sudeca.model.ComprobanteDet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComprobanteDetRepository extends JpaRepository<ComprobanteDet, Long> {
}
