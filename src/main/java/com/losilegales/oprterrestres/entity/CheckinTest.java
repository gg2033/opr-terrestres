package com.losilegales.oprterrestres.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="CHECKIN_TEST",schema="public")
public class CheckinTest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_checkin")
	private int idCheckin;
	
	@Column(name = "codigo")
	private String codigo;
	
	@Column(name="codigo_pasajero")
	private String codigoPasajero;
	
	@Column(name="compania")
	private String compania;
	
	@Column(name="codigo_vuelo")
	private String codigoVuelo;
	
	@Column(name="fecha_partida")
	private LocalDateTime fechaPartida;
	
	@Column(name="origen")
	private String origen;
	
	@Column(name="destino")
	private String destino;
	
	@Column(name="asiento")
	private String asiento;
	
	@Column(name="clase")
	private String clase;
	
	@Column(name="tipo_documento")
	private String tipoDocumento;
	
	@Column(name="numero_documento")
	private int numeroDocumento;
	
	@Column(name="apellido")
	private String apellido;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="nacionalidad")
	private String nacionalidad;
	
	@Column(name="edad")
	private int edad;
	
	@Column(name="sexo")
	private char sexo;
	
	@Column(name="alimentacion")
	private String alimentacion;
	
	@Column(name="condicion")
	private String condicion;
	
	@Column(name="comentario")
	private String comentario;
	
	@Column(name="creado")
	private LocalDate creado;
	
	@Column(name="creado_por")
	private String creadoPor;
	
	@Column(name="modificado")
	private LocalDate modificado;
	
	@Column(name="modificado_por")
	private String modificadoPor;
	
	@Column(name="activo")
	private boolean activo;
}
