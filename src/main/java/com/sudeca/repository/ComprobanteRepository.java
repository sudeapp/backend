package com.sudeca.repository;
import com.sudeca.model.Comprobante;
import com.sudeca.model.PlanCatalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ComprobanteRepository extends JpaRepository<Comprobante, Long>, JpaSpecificationExecutor<Comprobante> {


}
