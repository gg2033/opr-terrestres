package com.losilegales.oprterrestres.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.losilegales.oprterrestres.enums.TipoTag;
import com.losilegales.oprterrestres.enums.TipoCarga;

import lombok.Data;

@Entity
@Table(name = "carga", schema = "public")
@Data
public class Carga {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_carga")
	private Integer idCarga;
	
	@Column(name = "peso_kg")
	private Integer peso;

	@Column(name = "codigo_carga")
	private String codigo;
	
	@Column(name = "codigo_pasajero")
	private String codigoPasajero;

	@Column(name = "tipo", nullable = false, length = 15)
	@Enumerated(value = EnumType.STRING)
	private TipoCarga tipoCarga;

	@Column(name = "tag", nullable = false, length = 15)
	@Enumerated(value = EnumType.STRING)
	private TipoTag tagCarga;
	
	@Column(name = "estado_carga")
	private String estadoCarga;

	@Column(name = "creado")
	private LocalDate fechaCreacion;

	@Column(name = "creado_por")
	private String nombreCreador;

	@Column(name = "modificado", nullable = true)
	private LocalDate fechaModificacion;

	@Column(name = "modificado_por", nullable = true)
	private String nombreModificador;

	@Column(name = "activo")
	private boolean activo;

}
