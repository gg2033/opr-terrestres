package com.losilegales.oprterrestres.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "vuelos", schema = "public")
@Data
public class Vuelo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_vuelo")
	private Integer idVuelo;

//	@ManyToOne
//	@JoinColumn(name="id_plan_de_ruta")
//	private PlanRuta planRuta;

	@Column(name = "id_aerolinea")
	private Integer idAerolinea;

	@Column(name = "codigo")
	private String codigo;
	@Column(name = "horario_salida")
	private Date horarioSalida;

	@Column(name = "horario_llegada")
	private Date horarioLlegada;

	@Column(name = "id_aeronave")
	private Integer idAeronave;

	@Column(name = "id_plan_de_ruta")
	private String codigoPlanRuta;
//	@Column(name="codigo_plan_ruta_esp")
//	private String codigoPlanRutaEspecifica;

	@Column(name = "creado")
	private Date creado;

	@Column(name = "creadoPor")
	private String creadoPor;

	@Column(name = "modificado")
	private LocalDateTime modificado;

	@Column(name = "modificado_por")
	private String modificadoPor;

//	@OneToOne
//	@Column(name = "id_registro_pasajeros")
//	private RegistroPasajeros idRegistroPasajeros;

	@JoinTable(name = "rel_vuelo_cargas", joinColumns = @JoinColumn(name = "id_vuelo", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_carga", nullable = false))
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Carga> cargas;

//	@OneToOne
//	private AeroPuerto aeropuertoSalida;

//	@OneToOne
//	private AeroPuerto aeropuertoLlegada;

//	@Column(name="fecha")
//	private LocalDate fechaVuelo;

//	@Column(name="cantidad_pasajeros")
//	private Integer cantPasajeros;

//	@OneToMany(mappedBy = "vuelo")
//	private List<Tripulante> tripulantes;

//	@OneToMany(mappedBy = "vuelo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<Pasajero> pasajeros;

	@Column(name = "activo")
	private Boolean activo;

}
