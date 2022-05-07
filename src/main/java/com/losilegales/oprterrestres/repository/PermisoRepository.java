package com.losilegales.oprterrestres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.losilegales.oprterrestres.entity.Permiso;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Integer>{

}
