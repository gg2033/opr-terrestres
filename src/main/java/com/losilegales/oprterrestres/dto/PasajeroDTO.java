package com.losilegales.oprterrestres.dto;

import java.util.List;

import lombok.Data;
@Data
public class PasajeroDTO {
	private int idPasajero;
	private String nombre;
	
	private List<CargaDTO> carga;
	
	
}
