package com.losilegales.oprterrestres.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.losilegales.oprterrestres.entity.Carga;
@Repository
public interface CargaRepository extends JpaRepository<Carga, Integer>{
	
	@Query(value = "SELECT * from cargas WHERE codigo = :codigo AND activo = true", nativeQuery = true)
	List<Carga> cargasPorVuelo(@Param(value = "codigo") String codigo);
	
	@Query(value = "SELECT * from cargas WHERE codigo = :codigo_vuelo", nativeQuery = true)
	List<Carga> cargasGeneralesPorVuelo(@Param(value = "codigo_vuelo") String codigoVuelo);

	@Query(value = "SELECT * from cargas WHERE codigo_pasajero = :codigo_pasajero", nativeQuery = true)
	Collection<? extends Carga> cargasPorPasajero(@Param(value = "codigo_pasajero")String codigoPasajero);
	
	@Query(value = "UPDATE cargas set estado_carga = :estado WHERE codigo = :codigo", nativeQuery = true)
	@Modifying(clearAutomatically = true)
	void cambioEstadoCargasVuelo(@Param(value = "codigo") String codigoVuelo, @Param(value = "estado") String estado);
	
	List<Carga> findByCodigoVuelo(String codigoVuelo);
	
	@Query(value = "select tag as tag, count(*) as cantidad from cargas  where fecha_partida > :fechaHora group by tag",  nativeQuery = true)
	List<Object[]> cantidadCargasPorTag(@Param(value="fechaHora") Date fechaHora);
	
	@Query(value = "select tag as tag, count(*) as cantidad from cargas  where codigo_vuelo = :codigoVuelo and fecha_partida > :fechaHora group by tag",  nativeQuery = true)
	List<Object[]> cantidadCargasPorTagCodigoFechaVuelo(@Param(value="codigoVuelo") String codigoVuelo, @Param(value="fechaHora") Date fechaHora);
	
	@Query(value = "select tag as tag, count(*) as cantidad from cargas where codigo = :codigoVuelo group by tag",  nativeQuery = true)
	List<Object[]> cantidadTipoCargasPorVuelo(@Param(value="codigoVuelo") String codigoVuelo);
	
	@Query(value = "select COUNT ( DISTINCT codigo_vuelo ) AS cantidad from cargas",  nativeQuery = true)
	Integer cantidadVuelos();

	@Query(value = "TRUNCATE TABLE carga RESTART IDENTITY;", nativeQuery = true)
	void truncateTabla();

}
