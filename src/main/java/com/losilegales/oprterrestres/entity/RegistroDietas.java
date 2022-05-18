package com.losilegales.oprterrestres.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
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
@Table(name = "registros_dietas", schema = "public")
@Data
public class RegistroDietas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_registro_dietas")
	private Integer idRegistroDietas;

	@JoinColumn(name = "id_registro_pasajeros")
	private Integer idRegistroPasajero;

	@Column(name = "cantidad_pasajeros")
	private Integer cantidadPasajeros;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_dieta_especial")
	private DietaEspecifica dietaEspecifica;

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
