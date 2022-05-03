package com.losilegales.oprterrestres.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
@Entity
@Table(name="vuelos",schema="public")
@Data
public class Vuelo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_vuelo")
	private Integer idVuelo;
	
	@ManyToOne
	@JoinColumn(name="id_plan_de_ruta")
	private PlanRuta planRuta;
	
	@Column(name="id_aerolinea")
	private Integer idAerolinea;
	
	@Column(name="codigo")
	private String codigoVuelo;
	@Column(name="horario_salida")
	private LocalDateTime horarioSalida;
	@Column(name="horario_llegada")
	private LocalDateTime horarioLlegada;
	
//	@OneToOne
//	private AeroPuerto aeropuertoSalida;
	
//	@OneToOne
//	private AeroPuerto aeropuertoLlegada;
	
//	@Column(name="fecha")
//	private LocalDate fechaVuelo;
	
	@Column(name="cantidad_pasajeros")
	private Integer cantPasajeros;
	
//	@OneToMany(mappedBy = "vuelo")
//	private List<Tripulante> tripulantes;
	
//	@OneToMany(mappedBy = "vuelo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<Pasajero> pasajeros;

//	@Column(name="id_aeronave")
//	private Integer idAeronave;
	


}
