package com.losilegales.oprterrestres.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tripulante", schema = "public")
public class Tripulante implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tripulacion")
	private Integer idTripulacion;

//	@Column(name="id_vuelo")
//	private Integer idVuelo;
//	
//	@Column(name="id_tipo_tripulacion")
//	private Integer idTipoTripulacion;

	@ManyToOne
	@JoinColumn(name = "id_vuelo")
	private Vuelo vuelo;

	@Column(name = "nombre")
	private Integer nombre;

	@Column(name = "apellido")
	private Integer apellido;

}
