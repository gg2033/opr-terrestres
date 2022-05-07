package com.losilegales.oprterrestres.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="PERMISOS_ROL",schema="public")
public class RolPermiso implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	  
	@Column(name="id_permiso_rol")
	private Integer idPermisoRol;
	
	@Column(name="id_rol_usuario")
	private Integer idRolUsuario;
	
	@Column(name="id_permiso")
	private Integer idPermiso;

	@Column(name="creado")
	private LocalDate fechaCreacion;
	
	@Column(name="creado_por")
	private String nombreCreador;
	
	@Column(name="modificado", nullable = true)
	private LocalDate fechaModificacion;

	@Column(name="modificado_por", nullable = true)
	private String nombreModificador;

	@Column(name="activo")
	private boolean activo;
}
