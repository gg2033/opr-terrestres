package com.losilegales.oprterrestres.repository;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.losilegales.oprterrestres.entity.Checkin;

@Repository
public interface CheckinRepository extends JpaRepository<Checkin, Integer> {
	
	@Query(value="SELECT * from checkin WHERE codigo_vuelo = :codigo_vuelo", nativeQuery = true)
	List<Checkin> checkinPorVuelo(@Param(value = "codigo_vuelo") String codigoVuelo);

}