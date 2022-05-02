package com.losilegales.oprterrestres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.losilegales.oprterrestres.entity.RolUsuario;

@Repository
public interface RolUsuarioRepository extends JpaRepository<RolUsuario, Integer>{

}
