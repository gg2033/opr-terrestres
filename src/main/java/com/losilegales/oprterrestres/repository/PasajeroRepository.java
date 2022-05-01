package com.losilegales.oprterrestres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.losilegales.oprterrestres.entity.Pasajero;
@Repository
public interface PasajeroRepository extends JpaRepository<Pasajero, Integer>{

}
