package com.losilegales.oprterrestres.dto.CheckIn;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.losilegales.oprterrestres.enums.TipoCarga;
import com.losilegales.oprterrestres.enums.TipoTag;

import lombok.Data;

@Data
public class CargaDTO {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id_carga")
//	private Integer idCarga;
	
//	private Integer peso;
//
//	private String codigo;
//	
//	private String codigoPasajero;
//
//	private TipoCarga tipoCarga;
//
//	private TipoTag tagCarga;
//	
//	private String estadoCarga;

	private int idCarga;

	private int envio;

	private String codigoCarga;

	private String codigoPasajero;

	private String tipo;

	private int peso;

	private String tag;

	private LocalDate fechaCreacion;

	private String nombreCreador;

	private LocalDate fechaModificacion;

	private String nombreModificador;

	private boolean activo;

	private String estadoCarga;

	private String codigoVuelo;

}
