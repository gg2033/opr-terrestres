package com.losilegales.oprterrestres.dto;

import java.util.List;

import lombok.Data;

@Data
public class CheckInDTO {
	
	private String lineaAerea;	
	private String vuelo;	
	private String fecha;	
	private String partida;	
	private String origen;	
	private String destino;	
	private String tipoDocumento;	
	private String nroDocumento;
	private String apellidos	;	
	private String nombres;		
	private String nacionalidad;	
	private String edad;	
	private String sexo;
	private List<CargaDTO> carga;
	private String equipajeMano;	
	private String alimentacion;	
	private String condicion;
	
	
	

}
