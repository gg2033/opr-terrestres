package com.losilegales.oprterrestres.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="vuelo",schema="public")
public class Vuelo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_vuelo")
	private Integer idVuelo;
	
	@Column(name="codigo_vuelo")
	private String codigoVuelo;
	
	@Column(name="fecha")
	private LocalDate fechaVuelo;
	
	@Column(name="cantidad_pasajeros")
	private Integer cantPasajeros;
	
	@OneToMany
	private List<Tripulante> tripulantes;
	
	@OneToMany
	private List<Pasajero> pasajeros;
	
	@Column(name="id_aeronave")
	private Integer idAeronave;


}
