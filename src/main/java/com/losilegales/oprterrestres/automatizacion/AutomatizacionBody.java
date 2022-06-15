package com.losilegales.oprterrestres.automatizacion;

import java.util.List;

import com.losilegales.oprterrestres.utils.DatosGeneradorCheckin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutomatizacionBody {
	/*
	 * usarEstados = true -> se usa la listaDeEstadosCheckin / usarEstados = false -> no se usa la listaDeEstadosCheckin
	 *  
	 * usarDatosDeEnpoint = true -> se consiguen los vuelos del enpoint. Si listaDeCodigosDeVuelo esta vacia, se leeran
	 * todos los vuelos del enpoint. De otra forma solo se usaran los vuelos que esten listados en listaDeCodigosDeVuelo.
	 * usarDatosDeEnpoint = false -> se utiliza la listaDeDatosGeneradorCheckin pasada.
	 * 
	 */
	private Boolean usarEstados;
	private Boolean usarDatosDeEnpoint;
	private List<String> listaDeEstadosCheckin;
	private List<String> listaDeCodigosDeVuelo;
	private List<DatosGeneradorCheckin> listaDeDatosGeneradorCheckin;
	
	

}
