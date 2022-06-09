package com.losilegales.oprterrestres.dto.CheckIn;

import javax.persistence.Column;

import lombok.Data;

@Data
public class CargaDTO {
	private int idCarga;

	private String codigo;

	private int envio;
	
	private String codigoCarga;
	
	private String codigoPasajero;
	
	private String tipo;
	
	private int peso;

	private String tag;
	
	private String fechaCreacion;
	
	private String nombreCreador;
	
	private String fechaModificacion;

	private String nombreModificador;

	private boolean activo;
	
	private String estadoCarga;
	
	private String codigoVuelo;

}
