package com.losilegales.oprterrestres.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.losilegales.oprterrestres.entity.ClaseAsiento;
@Repository
public interface ClaseAsientoRepository extends JpaRepository<ClaseAsiento, Integer>{
	
	public Optional<ClaseAsiento> findByNombre(String codigo);

}
