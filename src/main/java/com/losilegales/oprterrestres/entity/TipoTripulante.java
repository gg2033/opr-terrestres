package com.losilegales.oprterrestres.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="tipo_tripulante",schema="public")
public class TipoTripulante implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_tipo_tripulante")
	private Integer idTipoTripulante;
	
	@Column(name="tipo_tripulante")
	private String tipoTripulante;
	


}
