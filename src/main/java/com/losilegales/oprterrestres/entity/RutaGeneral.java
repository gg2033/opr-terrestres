package com.losilegales.oprterrestres.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "rutas_generales", schema = "public")
@Data
public class RutaGeneral {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idRutaGeneral;
	@Column(name = "codigo")
	private String codigo;
	@Column(name = "creado")
	private Integer creado;
	@Column(name = "creado_por")
	private Integer creadoPor;
	@Column(name = "modificado")
	private Integer modificado;
	@Column(name = "modificado_por")
	private Integer modificadoPor;
	@Column(name = "activo")
	private Integer activo;
	@OneToMany(mappedBy = "rutaGeneral", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PlanRuta> planesRuta;

}
