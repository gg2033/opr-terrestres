package com.losilegales.oprterrestres.dto;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class UsuarioDTO {
	private Integer idUsuario;
	private String nombre;
	private String apellido;
	private String codigoUsuario;
	private String contraseña;
	private String fechaCreacion;
	private String nombreCreador;
	private String fechaModificacion;
	private String nombreModificador;
	private boolean activo;
	private Integer tipoUsuario;
}
