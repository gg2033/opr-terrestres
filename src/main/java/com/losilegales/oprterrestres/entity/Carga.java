package com.losilegales.oprterrestres.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "CARGAS", schema = "public")
@Data
public class Carga {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_carga")
	private int idCarga;
	
	@Column(name = "codigo")
	private String codigo;

	@Column(name = "envio")
	private int envio;
	
	@Column(name = "codigo_carga")
	private String codigoCarga;
	
	@Column(name = "codigo_pasajero")
	private String codigoPasajero;
	
	@Column(name = "tipo")
	private String tipo;
	
	@Column(name = "peso_kg")
	private int peso;

	@Column(name = "tag")
	private String tag;
	
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
	
	@Column(name = "estado_carga")
	private String estadoCarga;
	
	@Column(name = "codigo_vuelo")
	private String codigoVuelo;
	
	@Column(name = "fecha_partida")
	private Date fechaHoraVuelo;

}
