package com.losilegales.oprterrestres.entity;

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
@Table(name = "planes_de_ruta", schema = "public")
@Data
public class PlanRuta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_plan_de_ruta")
	private Integer idPlanRuta;

	@Column(name = "codigo")
	private String codigo;

	@ManyToOne
	@JoinColumn(name = "id_ruta_general")
	private RutaGeneral rutaGeneral;
	
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
