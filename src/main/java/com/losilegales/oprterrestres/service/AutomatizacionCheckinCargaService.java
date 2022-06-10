package com.losilegales.oprterrestres.service;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.format.datetime.joda.DateTimeFormatterFactory;

import com.losilegales.oprterrestres.entity.CargaTest;
import com.losilegales.oprterrestres.entity.CheckinTest;
import com.losilegales.oprterrestres.repository.CargaTestRepository;
import com.losilegales.oprterrestres.repository.CheckinTestRepository;
import com.losilegales.oprterrestres.utils.OprUtils;

import converter.LocalDateAndStringConverter;
import lombok.Data;

public class AutomatizacionCheckinCargaService {
	private final String CREADOR_GENERICO = "juan";
	private CheckinTestRepository checkinTRepo;
	private CargaTestRepository cargaTRepo;
	
	@Data
	public class DatosGeneradorCheckin {
		private String codigo;
		private Integer cantPasajeros;
		private String origen;
		private String destino;
		private String fechaPartida;
	}
	
	//recibe una lista de tuplas, las tuplas tienen en String el codigo (vuelo) y como Integer la cantidad de pasajeros
	public void crearCheckinConVuelos(List<DatosGeneradorCheckin> vuelos) {
		for (DatosGeneradorCheckin vuelo : vuelos) {
			List<CheckinTest> checkinAPersistir = new ArrayList<CheckinTest>(vuelo.getCantPasajeros());
			//TODO Hacerlo realmente random teniendo en cuenta el length de vuelo.getE()
			Integer randomNumber = 1;
			//TODO el valor dado al length de cargaAPersistir debe ser random en un rango de mas o menos el mismo numero de vuelo.getE()
			//TODO tambien habria que ver como rellerlo luego por que no tendra el mismo for
			List<CargaTest> cargaAPersistir = new ArrayList<CargaTest>(randomNumber);
			for (int numPasajero = 0;numPasajero < vuelo.getCantPasajeros(); numPasajero++) {
				//generar rows
				generarRowCheckin(numPasajero, vuelocheckinAPersistir);
				
			}
			//persistir rows
			checkinTRepo.saveAll(checkinAPersistir);
		}
	}

	private void generarRowCheckin(int numPasajero,DatosGeneradorCheckin vuelo, List<CheckinTest> checkinAPersistir) {
		CheckinTest ct = new CheckinTest();
		
		ct.setActivo(true);
		ct.setAlimentacion(generarAlimentacion());
		ct.setApellido(generarApellido());
		ct.setAsiento(generarAsiento());
		ct.setClase(generarClase());
		ct.setCodigo(vuelo.getCodigo());
		ct.setCodigoPasajero(generarCodigoPasajero(numPasajero, vuelo));
		ct.setCodigoVuelo(generarCodigoVuelo(vuelo.getCodigo()));
		ct.setComentario(generarComentarioGenerico());
		ct.setCompania(generarCompania(vuelo.getCodigo()));
		ct.setCondicion(generarCondicion());
		ct.setCreado(OprUtils.getFechaActual());
		ct.setCreadoPor(CREADOR_GENERICO);
		ct.setDestino(vuelo.getDestino());
		ct.setEdad(generarEdad());
		ct.setFechaPartida(LocalDateAndStringConverter.stringToLocalDate(vuelo.getFechaPartida()));
		ct.setNacionalidad(generarNacionalidad());
		ct.setNombre(generarNombre());
		ct.setOrigen(vuelo.getOrigen());
		ct.setSexo(generarSexo());
		ct.setTipoDocumento(generarTipoDocumento());
		ct.setNumeroDocumento(generarNumeroDocumento(ct.getTipoDocumento()));
		
		checkinAPersistir.add(ct);
	}

	private int generarNumeroDocumento(String tipoDocumento) {
		int min;
		int max;
		switch (tipoDocumento) {
		  case "DNI":
		    min = 25000000;
		    max = 50000000;
		    return (int) Math.floor(Math.random() * (max - min) + min);
		  case "Pasaporte":
		    min = 100000000;
		    max = 999999999;
		    return (int)Math.floor(Math.random() * (max - min) + min);

		  case "Cédula":
		    min = 100000000;
		    max = 999999999;
		    return (int) Math.floor(Math.random() * (max - min) + min);

		default:
			min = 25000000;
		    max = 50000000;
		    return (int) Math.floor(Math.random() * (max - min) + min);

		}
	}

	private String generarTipoDocumento() {
		String[] tiposDocumento = {"DNI", "Pasaporte", "Cedula"};
		int min = 0;
		int max = tiposDocumento.length;
		return tiposDocumento[(int) Math.floor(Math.random() * (max - min) + min)];

	}

	private char generarSexo() {
		  Character[] sexo = {'M', 'F'};
		  int min = 0;
		  int max = sexo.length;
		  return sexo[(int) Math.floor(Math.random() * (max - min) + min)];
		

	}

	private String generarNacionalidad() {
	  String[] nacionalidades = {"Estadounidense", "Colombiano", "Argentino", "Boliviano", "Peruano", "Uruguayo", "Chileno", "Brasilero", "Mexicano", "Canadiense", "Panameño", "Puerto Riqueño", "Venezolano", "Ecuatoriano", "Salvadoreño"};
	  int min = 0;
	  int max = nacionalidades.length;
	  return nacionalidades[(int) Math.floor(Math.random() * (max - min) + min)];
	}

	private int generarEdad() {
		int min = 21;
		int max = 80;
		return (int) Math.floor(Math.random() * (max - min) + min);
	}

	private String generarCondicion() {
		  String[] condiciones = {"movilidad reducida", "paciente", "estandar"};
		  int min = 0;
		  int max = condiciones.length;
		  return condiciones[(int) Math.floor(Math.random() * (max - min) + min)];
		
	}

	private String generarCompania(String codigo) {
		String compania = codigo.substring(0, 2);
		return compania;
	}

	private String generarComentarioGenerico() {
		String comentario = "Ninguno";
		return comentario;
	}

	private String generarCodigoVuelo(String codigo) {
		String codigoVuelo = codigo.substring(0, 6);
		return codigoVuelo;
	}

	private String generarCodigoPasajero(int numPasajero, DatosGeneradorCheckin vuelo) {
		int constante = 100000;
		int numero = numPasajero * constante;
		
		String compania = vuelo.getCodigo().substring(0, 2);
		
		String codigoPasajero = compania + "-PJ-" + numero;
		return codigoPasajero;
	}

	private String generarClase() {
		  String[] clases = {"business", "estandar"};
		  int min = 0;
		  int max = clases.length;
		  return clases[(int) Math.floor(Math.random() * (max - min) + min)];
	
	}

	private String ultimoAsiento;
	private String generarAsiento() {
		if(ultimoAsiento.isEmpty()){
			return "1A";
		}
		String[] array = {"A", "B", "C", "D", "E", "F", "G"};
		List<String> letters = Arrays.asList(array); 

		String firstChars = ultimoAsiento.substring(0, (ultimoAsiento.length()) - 1);
		String lastChar = ultimoAsiento.substring(-1);
		if(letters.indexOf(lastChar) == -1 || lastChar == "G"){
			ultimoAsiento = String.valueOf(Integer.parseInt(firstChars) + 1) + "A";
			return ultimoAsiento;
		}
		else{
			ultimoAsiento = firstChars + letters.get(letters.indexOf(lastChar) + 1);
			return ultimoAsiento;
		}
	}

	private String generarAlimentacion() {
	  String[] alimentaciones = {"vegano", "estandar", "vegetariano", "celiaco"};
	  int min = 0;
	  int max = alimentaciones.length;
	  return alimentaciones[(int) Math.floor(Math.random() * (max - min) + min)];
	}

//

//

//
//	async function
//
//	NOMBREALEATORIO(){
//		  apiurl = 'https://namey.muffinlabs.com/name.json';
//		  try {
//		    res = await UrlFetchApp.fetch(apiurl);
//		    return await res.toString().slice(2, res.toString().length - 2);
//		  } catch (error) {
//		    return error.toString();
//		  }
//		}
//
//	async function
//
//	APELLIDOALEATORIO(cellbelow){
//		  apiurl = 'https://namey.muffinlabs.com/name.json?&with_surname=true';
//		  try {
//		    res = await UrlFetchApp.fetch(apiurl);
//		    return await res.toString().slice(2, res.toString().length - 2).split(" ")[1];
//		  } catch (error) {
//		    return error.toString();
//		  }
//		}
//
}
