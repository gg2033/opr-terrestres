package com.losilegales.oprterrestres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.losilegales.oprterrestres.entity.CargaTest;

@Repository
public interface CargaTestRepository extends JpaRepository<CargaTest, Integer> {

}
