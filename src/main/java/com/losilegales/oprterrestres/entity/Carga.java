package com.losilegales.oprterrestres.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "carga", schema = "public")
@Data
public class Carga {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_carga")
	private Integer idCarga;

	@ManyToMany(mappedBy = "cargas")
	private List<Vuelo> vuelos;

	@Column(name = "peso")
	private Integer peso;
	
	@Column(name = "codigo")
	private String codigo;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_tipo_carga")
	private TipoCarga tipoCarga;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_tag_carga")
	private TagCarga tagCarga;

//	@ManyToOne
//	@JoinColumn(name = "id_pasajero")
//	private Pasajero pasajero;

//	@Column(name="id_bulto")
//	private int cantBulto;
//
//	@Enumerated(EnumType.STRING)
//	@Column(name="tipo_carga")
//	private TipoCarga tipoCarga;
	
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

}
