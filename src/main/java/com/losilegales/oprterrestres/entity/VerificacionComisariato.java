package com.losilegales.oprterrestres.entity;


import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Entity
@Data
@Table(name="VALIDACIONES_COMISARIATO",schema="public")
public class VerificacionComisariato {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_validacion")
	private Integer idVerificacion;
	
	@Column(name = "codigo")
	private String codigo;
	
	@Column(name = "ver_limpieza")
	private Boolean verificacionLimpieza;
	
	@Column(name= "ver_interna")
	private Boolean verificacionInterna;
	
	@Column(name= "ver_elementos_seguridad")
	private Boolean verificacionElementosSeguridad;
	
	@Column(name= "ver_cartillas_bolsas")
	private Boolean verificacionCartillasBolsas;
	
	@Column(name= "ver_insumos")
	private Boolean verificacionInsumos;
	
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
