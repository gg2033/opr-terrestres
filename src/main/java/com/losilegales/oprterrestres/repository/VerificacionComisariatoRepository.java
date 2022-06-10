package com.losilegales.oprterrestres.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.losilegales.oprterrestres.entity.VerificacionComisariato;

@Repository
public interface VerificacionComisariatoRepository extends JpaRepository<VerificacionComisariato, Integer> {

	@Query(value = "SELECT * FROM validaciones_comisariato WHERE codigo = :codigo AND activo = true", nativeQuery = true)
	List<VerificacionComisariato> verificacionesPorVuelo(String codigo);

	@Query(value = "SELECT * FROM validaciones_comisariato WHERE codigo = :codigo LIMIT 1", nativeQuery = true)
	VerificacionComisariato existeVerificacionPorVuelo(String codigo);
}
