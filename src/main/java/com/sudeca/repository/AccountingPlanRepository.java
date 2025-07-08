package com.sudeca.repository;

import com.sudeca.model.PlanContable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountingPlanRepository extends JpaRepository<PlanContable, Long> {
  PlanContable findByCodPlanContainingIgnoreCase(String cod);
}
