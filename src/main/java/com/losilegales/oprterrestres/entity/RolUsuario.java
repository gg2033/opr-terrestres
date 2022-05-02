package com.losilegales.oprterrestres.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ROL_USUARIOS",schema="public")
public class RolUsuario implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_rol_usuario")
	private Integer idRolUsuario;
	
	@Column(name="nombre_tipo")
	private String nombreRolUsuario;

	public Integer getIdRolUsuario() {
		return idRolUsuario;
	}

	public void setIdRolUsuario(Integer idRolUsuario) {
		this.idRolUsuario = idRolUsuario;
	}

	public String getNombreRolUsuario() {
		return nombreRolUsuario;
	}

	public void setNombreRolUsuario(String nombreRolUsuario) {
		this.nombreRolUsuario = nombreRolUsuario;
	}

}
