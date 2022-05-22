package com.losilegales.oprterrestres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.losilegales.oprterrestres.entity.Carga;
@Repository
public interface CargaRepository extends JpaRepository<Carga, Integer>{
	

}
