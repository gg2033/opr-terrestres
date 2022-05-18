package com.losilegales.oprterrestres.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.losilegales.oprterrestres.entity.CondicionEspecial;
import com.losilegales.oprterrestres.entity.Vuelo;
@Repository
public interface CondicionEspecialRepository extends JpaRepository<CondicionEspecial, Integer>{
	
	public Optional<CondicionEspecial> findByNombre(String codigo);

}
