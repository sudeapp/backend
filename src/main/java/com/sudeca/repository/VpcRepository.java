package com.sudeca.repository;

import com.sudeca.model.Categoria;
import com.sudeca.model.view.Vpc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VpcRepository extends JpaRepository<Vpc, Long> {
    List<Vpc> findByIdCaho(Long id_caho);
}