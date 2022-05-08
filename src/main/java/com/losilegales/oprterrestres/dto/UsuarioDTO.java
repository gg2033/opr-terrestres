package com.losilegales.oprterrestres.dto;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import lombok.Data;

@Data
public class UsuarioDTO {
	private Integer idUsuario;
	private String nombre;
	private String apellido;
	private Integer dni;
	private String codigoUsuario;
	private String contraseña;
	private String correo;
	private String iataAeropuerto;
	private Integer rolUsuario;
	private String fechaCreacion;
	private String nombreCreador;
	private String fechaModificacion;
	private String nombreModificador;
	private boolean activo;

	public UsuarioDTO() {
		super();
	}

	public UsuarioDTO(Integer idUsuario, String nombre, String apellido, Integer dni, String codigoUsuario,
			String contraseña, String correo, String iataAeropuerto, Integer rolUsuario, String fechaCreacion,
			String nombreCreador, String fechaModificacion, String nombreModificador, boolean activo) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.codigoUsuario = codigoUsuario;
		this.contraseña = contraseña;
		this.correo = correo;
		this.iataAeropuerto = iataAeropuerto;
		this.rolUsuario = rolUsuario;
		this.fechaCreacion = fechaCreacion;
		this.nombreCreador = nombreCreador;
		this.fechaModificacion = fechaModificacion;
		this.nombreModificador = nombreModificador;
		this.activo = activo;
	}
}
