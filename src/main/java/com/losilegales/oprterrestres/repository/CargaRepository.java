package com.losilegales.oprterrestres.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.losilegales.oprterrestres.entity.Carga;
@Repository
public interface CargaRepository extends JpaRepository<Carga, Integer>{
	
	@Query(value = "SELECT * from cargas WHERE codigo_vuelo = :codigo_vuelo", nativeQuery = true)
	List<Carga> cargasPorVuelo(@Param(value = "codigo_vuelo") String codigoVuelo);

	@Query(value = "SELECT * from cargas WHERE codigo_pasajero = :codigo_pasajero", nativeQuery = true)
	Collection<? extends Carga> cargasPorPasajero(@Param(value = "codigo_pasajero")String codigoPasajero);
	
	@Query(value = "UPDATE cargas set estado_carga = :estado WHERE codigo_vuelo = :codigo_vuelo", nativeQuery = true)
	@Modifying(clearAutomatically = true)
	void cambioEstadoCargasVuelo(@Param(value = "codigo_vuelo") String codigoVuelo, @Param(value = "estado") String estado);
	
	List<Carga> findByCodigoVuelo(String codigoVuelo);

	@Query(value = "TRUNCATE TABLE carga RESTART IDENTITY;", nativeQuery = true)
	void truncateTabla();

}
