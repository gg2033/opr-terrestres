package com.losilegales.oprterrestres.dto;

public class RutaVueloDTO {
	
	String idVuelo;
	String Origen;
	String Destino;
	
	
	public RutaVueloDTO() {
		super();
	}
	public String getIdVuelo() {
		return idVuelo;
	}
	public void setIdVuelo(String idVuelo) {
		this.idVuelo = idVuelo;
	}
	public String getOrigen() {
		return Origen;
	}
	public void setOrigen(String origen) {
		Origen = origen;
	}
	public String getDestino() {
		return Destino;
	}
	public void setDestino(String destino) {
		Destino = destino;
	}
	
	

}
