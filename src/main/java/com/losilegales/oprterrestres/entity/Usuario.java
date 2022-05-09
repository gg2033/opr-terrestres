package com.losilegales.oprterrestres.entity;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Entity
@Table(name="USUARIOS",schema="public")
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  
	@Column(name="id_usuario")
	private Integer idUsuario;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="apellido")
	private String apellido;
	
	@Column(name="dni")
	private int dni;
	
	@Column(name="iata_aeropuerto")
	private String iataAeropuerto;

	//	@Column(name="codigo_usuario", insertable=false)
	@Column(name="codigo_usuario")
	private String codigoUsuario;
	
	@Column(name="contrasena")
	private String contraseña;
	
//	@Column(name="creado", insertable=false)
	@Column(name="creado")
	private LocalDate fechaCreacion;
	
	@Column(name="creado_por")
	private String nombreCreador;
	
	@Column(name="modificado", nullable = true)
	private LocalDate fechaModificacion;

	@Column(name="modificado_por", nullable = true)
	private String nombreModificador;

//	@Column(name="activo", insertable=false, updatable = true)
	@Column(name="activo")
	private boolean activo;
	
//	@ManyToOne
//	@JoinColumn(name = "id_tipo_usuario")
	@Column(name = "id_rol_usuario")
	private Integer rolUsuario;
	
	@Column(name = "correo")
	private String correo;

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", nombre=" + nombre + ", apellido=" + apellido + ", codigoUsuario="
				+ codigoUsuario + ", contraseña=" + contraseña + ", fechaCreacion=" + fechaCreacion + ", nombreCreador="
				+ nombreCreador + ", fechaModificacion=" + fechaModificacion + ", nombreModificador="
				+ nombreModificador + ", activo=" + activo + ", tipoUsuario=" + rolUsuario + "]";
	}

	public Usuario() {
		super();
	}
	

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(String codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate date) {
		this.fechaCreacion = date;
	}

	public String getNombreCreador() {
		return nombreCreador;
	}

	public void setNombreCreador(String nombreCreador) {
		this.nombreCreador = nombreCreador;
	}

	public LocalDate getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(LocalDate fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getNombreModificador() {
		return nombreModificador;
	}

	public void setNombreModificador(String nombreModificador) {
		this.nombreModificador = nombreModificador;
	}

	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Integer getRolUsuario() {
		return rolUsuario;
	}

	public void setRolUsuario(Integer rolUsuario) {
		this.rolUsuario = rolUsuario;
	}
	
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getIataAeropuerto() {
		return iataAeropuerto;
	}

	public void setIataAeropuerto(String iataAeropuerto) {
		this.iataAeropuerto = iataAeropuerto;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(idUsuario, other.idUsuario);
	}

}
