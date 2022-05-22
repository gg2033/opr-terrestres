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
@Table(name = "vuelos_historicos", schema = "public")
@Data
public class VueloHistorico {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_vuelo_historico")
	private Integer idVueloHistorico;

	@Column(name = "id_vuelo")
	private Integer idVuelo;

	@Column(name = "id_aeronave")
	private Integer idAeronave;
	
	@Column(name = "origen")
	private String origen;
	
	@Column(name = "destino")
	private String destino;
	
	@Column(name = "id_clima")
	private Integer idClima;
	
	@Column(name = "id_emergencia")
	private Integer idEmergencia;
	
	@Column(name = "id_compania")
	private Integer idCompania;
	
	@Column(name = "regla_vuelo")
	private String reglaVuelo;
	
	@Column(name = "tipo_vuelo")
	private String tipoVuelo;
	
	@Column(name = "hora_salida_estimada")
	private String horaSalidaEstimada;
	
	@Column(name = "hora_llegada_estimada")
	private String horaLlegadaEstimada;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "ruta")
	private String ruta;
	
	@Column(name = "pista_despegue")
	private String pistaDespegue;
	
	@Column(name = "pista_aterrizaje")
	private String pistaAterrizaje;
	
	@Column(name = "combustible_estimado")
	private Integer combustibleEstimado;
	
	@Column(name = "combustible_real")
	private Integer combustibleReal;
	
	@Column(name = "lubricante_estimado")
	private Integer lubricanteEstimado;
	
	@Column(name = "lubricante_real")
	private Integer lubricanteReal;
	
	@Column(name = "kilometraje_estimado")
	private Integer kilometrajeEstimado;
	
	@Column(name = "kilometraje_real")
	private Integer kilometrajeReal;
	
	@Column(name = "verificaciones")
	private Boolean verificaciones;
	
	@Column(name = "cantidad_pasajeros")
	private Integer cantidadPasajeros;
	
	@Column(name = "peso_total_carga")
	private Integer pesoTotalCarga;
	
	@Column(name = "emergencia")
	private Boolean emergencia;
	
	@Column(name = "hora_salida_real")
	private String horaSalidaReal;
	
	@Column(name = "hora_llegada_real")
	private String horaLlegadaReal;
	
	@Column(name = "duracion_estimada")
	private String duracionEstimada;
	
	@Column(name = "duracion_real")
	private String duracionReal;
	
	//COLUMNAS BY DEFAULT

	@Column(name = "creado")
	private Date creado;

	@Column(name = "creadoPor")
	private String creadoPor;

	@Column(name = "modificado")
	private LocalDateTime modificado;

	@Column(name = "modificado_por")
	private String modificadoPor;

	@Column(name = "activo")
	private Boolean activo;
	
	//RELATIONSHIP
	//@JoinTable(name = "rel_vuelo_cargas", joinColumns = @JoinColumn(name = "id_vuelo", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_carga", nullable = false))
	//@ManyToMany(cascade = CascadeType.ALL)
	//private List<Carga> cargas;
}
