package com.losilegales.oprterrestres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.losilegales.oprterrestres.entity.Usuario;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	
	//TODO ver si esto servira de algo
//	@Modifying
//	@Transactional
//	@Query(value = "update usuarios set activo = :valor where id_usuario = :id", nativeQuery = true)
//	void deshabilitarUsuario(@Param(value = "id") int id, @Param(value = "valor") boolean valor);
}
