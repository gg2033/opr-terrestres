package com.losilegales.oprterrestres.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "aeropuertos", schema = "public")
@Data
public class AeroPuerto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_aeropuerto")
	private Integer idAeropuerto;

//	@ManyToOne
//	@JoinColumn(name="id_provincia")
//	private Provincia planRuta;
	@Column(name = "ciudad")
	private String ciudad;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "oaci")
	private String oaci;
	@Column(name = "iata")
	private String iata;

	@Column(name = "creado")
	private LocalDateTime creado;

	@Column(name = "creado_por")
	private String creadoPor;

	@Column(name = "modificado")
	private LocalDateTime modificado;

	@Column(name = "modificado_por")
	private String modificadoPor;

	@Column(name = "activo")
	private Boolean activo;

}
