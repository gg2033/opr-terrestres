package com.losilegales.oprterrestres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.losilegales.oprterrestres.entity.RolPermiso;

@Repository
public interface RolPermisoRepository extends JpaRepository<RolPermiso, Integer>{

}
