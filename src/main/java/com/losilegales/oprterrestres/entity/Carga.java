package com.losilegales.oprterrestres.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.losilegales.oprterrestres.dto.TipoCarga;

import lombok.Data;

@Entity
@Table(name = "carga", schema = "public")
@Data
public class Carga {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_carga")
	private Integer idCarga;

	@Column(name = "peso")
	private Integer peso;

	@ManyToOne
	@JoinColumn(name = "id_pasajero")
	private Pasajero pasajero;

//	@Column(name="id_bulto")
//	private int cantBulto;

	@Enumerated(EnumType.STRING)
	@Column(name="tipo_carga")
	private TipoCarga tipoCarga;

}
