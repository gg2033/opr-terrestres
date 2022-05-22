package com.losilegales.oprterrestres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.losilegales.oprterrestres.entity.VueloHistorico;

@Repository
public interface VueloHistoricoRepository extends JpaRepository<VueloHistorico, Integer>{
	
}
