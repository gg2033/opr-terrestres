package com.losilegales.oprterrestres.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.losilegales.oprterrestres.entity.DietaEspecifica;
@Repository
public interface DietaEspecificaRepository extends JpaRepository<DietaEspecifica, Integer>{
	
	public Optional<DietaEspecifica> findByNombre(String codigo);

}
