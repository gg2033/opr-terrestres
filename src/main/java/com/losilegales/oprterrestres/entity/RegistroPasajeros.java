package com.losilegales.oprterrestres.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="registros_pasajeros",schema="public")
@Data
public class RegistroPasajeros {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_registro_pasajeros")
	private Integer idVuelo;
	
	@OneToOne
	@JoinColumn(name="id_registro_pasajero")
	private Vuelo vuelo;
	
	@Column(name="pasajeros_totales")
	private Integer pasajerosTotales;
	

}
