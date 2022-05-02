package com.losilegales.oprterrestres.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.losilegales.oprterrestres.entity.Vuelo;
@Repository
public interface VueloRepository extends JpaRepository<Vuelo, Integer>{
	
	public Optional<Vuelo> findByCodigoVuelo(String codigoVuelo);

}
