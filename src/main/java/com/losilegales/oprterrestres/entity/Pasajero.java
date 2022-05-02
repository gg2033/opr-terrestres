package com.losilegales.oprterrestres.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="pasajeros",schema="public")
public class Pasajero implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_pasajero")
	private Integer idUsuario;
	
	@Column(name="nombre")
	private Integer nombre;
	
	@Column(name="id_carga")
	private Integer idCarga;
	
	@Column(name="id_dieta")
	private Integer idDieta;


}
