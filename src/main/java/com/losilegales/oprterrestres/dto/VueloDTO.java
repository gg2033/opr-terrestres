package com.losilegales.oprterrestres.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class VueloDTO {
	private Integer idVuelo;
	private Integer idPlanRuta;
	private Integer idAerolinea;
	private String codigo;
//	  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private Date horarioSalida;
//	  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private Date horarioLlegada;
	private Integer  idAeropuertoSalida;
	private Integer idAeropuertoLlegada;
	private Integer idAeronave;
	private Boolean activo;
	private String creadoPor;
	private String modificadoPor;

	

	
	
	
	
}
