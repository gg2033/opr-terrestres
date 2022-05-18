package com.losilegales.oprterrestres.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "diestas_especiales", schema = "public")
@Data
public class DietaEspecifica {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_dieta_especial")
	private Integer idDietaEspecial;

	@Column(name = "nombre")
	private String nombre;
	
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
