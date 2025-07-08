package com.sudeca.repository;

import com.sudeca.model.view.Vpc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VpcRepository extends JpaRepository<Vpc, String> {

}