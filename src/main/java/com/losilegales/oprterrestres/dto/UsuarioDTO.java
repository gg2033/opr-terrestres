package com.losilegales.oprterrestres.dto;

import java.sql.Date;
import java.time.LocalDate;

public class UsuarioDTO {
	private Integer idUsuario;
	private String nombre;
	private String apellido;
	private String codigoUsuario;
	private String contraseña;
	private LocalDate fechaCreacion;
	private String nombreCreador;
	private LocalDate fechaModificacion;
	private String nombreModificador;
	private boolean activo;
	private Integer tipoUsuario;
}
