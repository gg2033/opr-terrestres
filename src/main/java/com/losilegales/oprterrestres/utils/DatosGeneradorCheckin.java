package com.losilegales.oprterrestres.utils;

import com.losilegales.oprterrestres.service.AutomatizacionCheckinCargaService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatosGeneradorCheckin {
	private String codigo; //formato [SF0000-yyyymmddhhmm]
	private Integer cantPasajeros;
	private String origen; //iata
	private String destino; //iata
	private String fechaPartida; //formato [yyyy/mm/dd hh:mm:ss]
}
