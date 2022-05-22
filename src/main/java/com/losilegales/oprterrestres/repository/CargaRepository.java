package com.losilegales.oprterrestres.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.losilegales.oprterrestres.entity.Carga;
@Repository
public interface CargaRepository extends JpaRepository<Carga, Integer>{

	@Query(value = "SELECT * from cargas WHERE codigo_vuelo = :codigo_vuelo", nativeQuery = true)
	List<Carga> cargasPorVuelo(@Param(value = "codigo_vuelo") String codigoVuelo);
	

}
