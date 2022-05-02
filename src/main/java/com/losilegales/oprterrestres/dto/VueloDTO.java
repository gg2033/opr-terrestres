package com.losilegales.oprterrestres.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
@Data
public class VueloDTO {
	private Integer idVuelo;
	private String codigoVuelo;
	private LocalDate fechaVuelo;
	private Integer cantPasajeros;
//	@OneToMany(mappedBy = "vuelo")
//	private List<Tripulante> tripulantes;
	
	private List<PasajeroDTO> pasajeros;
}
