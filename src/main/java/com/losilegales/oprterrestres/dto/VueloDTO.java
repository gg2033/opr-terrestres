package com.losilegales.oprterrestres.dto;

import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class VueloDTO {
	@JsonIgnore
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
	private String codigoPlanRuta;
	private String codigoPlanRutaEspecifica;
	
	
}
