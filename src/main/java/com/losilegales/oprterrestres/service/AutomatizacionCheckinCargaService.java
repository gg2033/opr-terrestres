package com.losilegales.oprterrestres.service;

import java.time.format.DateTimeFormatter;

import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import org.json.simple.JSONObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.losilegales.oprterrestres.entity.CargaTest;
import com.losilegales.oprterrestres.entity.CheckinTest;
import com.losilegales.oprterrestres.repository.CargaTestRepository;
import com.losilegales.oprterrestres.repository.CheckinTestRepository;
import com.losilegales.oprterrestres.utils.DatosGeneradorCheckin;
import com.losilegales.oprterrestres.utils.OprUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import converter.LocalDateAndStringConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public class AutomatizacionCheckinCargaService {
	private final String CREADOR_GENERICO = "SISTEMA";

	@Autowired
	private CheckinTestRepository checkinTRepo;
	@Autowired
	private CargaTestRepository cargaTRepo;

	private String ultimoAsiento = "";

	public void crearCheckinConVuelos(List<DatosGeneradorCheckin> vuelos) {
		for (DatosGeneradorCheckin vuelo : vuelos)
			if (!existeCheckin(vuelo.getCodigo())) {
				ultimoAsiento = "";
				List<CheckinTest> checkinAPersistir = new ArrayList<CheckinTest>(vuelo.getCantPasajeros());
				Integer randomCargaNumber = randomCargaNumber(vuelo.getCantPasajeros());
				List<CargaTest> cargaAPersistir = new ArrayList<CargaTest>(randomCargaNumber);
				//TODO optimizar para usar algunas iteraciones del for del checkin para la carga
				for (int numPasajero = 0; numPasajero < vuelo.getCantPasajeros(); numPasajero++) {
					// generar rows
					generarRowCheckin(numPasajero, vuelo, checkinAPersistir);

				}
				for (int iteracion = 0; iteracion < randomCargaNumber; iteracion++) {
					generarRowCarga(randomCargaNumber, vuelo, cargaAPersistir);
				}
				// persistir rows
				checkinTRepo.saveAll(checkinAPersistir);
				cargaTRepo.saveAll(cargaAPersistir);
			}
	}

	private Integer randomCargaNumber(Integer cantPasajeros) {
		int min = (cantPasajeros - 20) > 0 ? (cantPasajeros - 20) : cantPasajeros;
		int max = cantPasajeros + 20; 
		return (int) (Math.floor(Math.random() * (max - min) + min));
	}

	private void generarRowCarga(Integer randomCargaNumber, DatosGeneradorCheckin vuelo,
			List<CargaTest> cargaAPersistir) {
		CargaTest ct = new CargaTest();
		
		ct.setActivo(true);
		ct.setCodigo(vuelo.getCodigo());
		ct.setCodigoPasajero(generarPasajeroAleatorio(vuelo));
		ct.setCodigoVuelo(generarCodigoVuelo(vuelo.getCodigo()));
		ct.setEstadoCarga(generarEstadoCargaGenerico());
		ct.setFechaCreacion(OprUtils.getFechaActual());
		ct.setNombreCreador(CREADOR_GENERICO);
		ct.setTipo(generarTipo());
		ct.setTag(generarTag(ct.getTipo()));
		ct.setPeso(generarPeso(ct.getTipo()));
		ct.setFechaHoraVuelo(LocalDateAndStringConverter.stringToLocalDateCheckin(vuelo.getFechaPartida()));
		
		cargaAPersistir.add(ct);
	}
	
	// FUNCIONES PARA LA GENERACION DE DATOS DE CARGA
	
	private int generarPeso(String tipoCarga) {
		int min = 0;
		int max = 0;
	  if (tipoCarga.equals("de mano")){
	    min = 1;
	    max = 3;
	  }else{
	    min = 3;
	    max= 23;
	  }
	  return (int) Math.floor(Math.random() * (max - min) + min);
	}

	//	const facturado = "facturado";
	//	const de_mano = "de mano";
	private String generarTipo() {
		  String[] tipos = {"facturado", "de mano"};
		  int min = 0;
		  int max = tipos.length;
		  return tipos[(int) Math.floor(Math.random() * (max - min) + min)];
	}

	//	const tagEstandar = "normal";
	//	const tagPeligroso = "peligroso";
	//	const tagDelicado = "delicado";
	//	const tagAnimal = "animal";
	private String generarTag(String tipoCarga) {
		  if(tipoCarga.equals("de mano")){
		    return "normal";
		  }
		  else{
		    String[] tags = {"normal", "peligroso", "delicado", "animal"};
		    int min = 0;
		    int max = tags.length;
		    return tags[(int) Math.floor(Math.random() * (max - min) + min)];
		  }
	}

	private String generarEstadoCargaGenerico() {
		String estadoGenerico = "En espera";
		return estadoGenerico;
	}

	private String generarPasajeroAleatorio(DatosGeneradorCheckin vuelo) {
		int constante = 100000;
		  int min = 0;
		  int max = vuelo.getCantPasajeros();
		  int numero = (int)(Math.floor(Math.random() * (max - min) + min)) + constante;
		  
		  String compania = vuelo.getCodigo().substring(0, 2);
		  String codigoPasajero = compania + "-PJ-" + numero;
		  return codigoPasajero;
	}

	private boolean existeCheckin(String codigo) {
		return getCheckinTestConCodigo(codigo) != null ? true : false;
	}

	private void generarRowCheckin(int numPasajero, DatosGeneradorCheckin vuelo, List<CheckinTest> checkinAPersistir) {
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
		ct.setFechaPartida(LocalDateAndStringConverter.stringToLocalDateCheckin(vuelo.getFechaPartida()));
		ct.setNacionalidad(generarNacionalidad());
		ct.setNombre(generarNombre());
		ct.setOrigen(vuelo.getOrigen());
		ct.setSexo(generarSexo());
		ct.setTipoDocumento(generarTipoDocumento());
		ct.setNumeroDocumento(generarNumeroDocumento(ct.getTipoDocumento()));

		checkinAPersistir.add(ct);
	}

	// FUNCIONES PARA LA GENERACION DE DATOS DEL CHECKIN

	public String generarNombre() {
		String apiurl = "https://namey.muffinlabs.com/name.json";
		try {
			HttpResponse<JsonNode> res = Unirest.get(apiurl).asJson();
			String nombre = res.getBody().toString();
			nombre = nombre.toString().substring(2, nombre.toString().length() - 2);
//		    System.out.println(nombre.toString().substring(2, nombre.toString().length() - 2));
			return nombre;
		} catch (Exception e) {
			e.printStackTrace();
			return "Juan";
		}
	}

	private String generarApellido() {
		String apiurl = "https://namey.muffinlabs.com/name.json?&with_surname=true";
		try {
			HttpResponse<JsonNode> res = Unirest.get(apiurl).asJson();
			String apellido = res.getBody().toString();
			apellido = apellido.substring(2, apellido.toString().length() - 2).split(" ")[1];
			return apellido;
		} catch (Exception e) {
			e.printStackTrace();
			return "Hernandez";
		}
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
			return (int) Math.floor(Math.random() * (max - min) + min);

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
		String[] tiposDocumento = { "DNI", "Pasaporte", "Cedula" };
		int min = 0;
		int max = tiposDocumento.length;
		return tiposDocumento[(int) Math.floor(Math.random() * (max - min) + min)];

	}

	private char generarSexo() {
		Character[] sexo = { 'M', 'F' };
		int min = 0;
		int max = sexo.length;
		return sexo[(int) Math.floor(Math.random() * (max - min) + min)];

	}

	private String generarNacionalidad() {
		String[] nacionalidades = { "Estadounidense", "Colombiano", "Argentino", "Boliviano", "Peruano", "Uruguayo",
				"Chileno", "Brasilero", "Mexicano", "Canadiense", "Panameño", "Puerto Riqueño", "Venezolano",
				"Ecuatoriano", "Salvadoreño" };
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
		String[] condiciones = { "movilidad reducida", "paciente", "estandar" };
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
		int numero = numPasajero + constante;

		String compania = vuelo.getCodigo().substring(0, 2);

		String codigoPasajero = compania + "-PJ-" + numero;
		return codigoPasajero;
	}

	private String generarClase() {
		String[] clases = { "business", "estandar" };
		int min = 0;
		int max = clases.length;
		return clases[(int) Math.floor(Math.random() * (max - min) + min)];

	}

	private String generarAsiento() {
		if (ultimoAsiento.isEmpty()) {
			ultimoAsiento = "1A";
			return ultimoAsiento;
		}
		String[] array = { "A", "B", "C", "D", "E", "F", "G" };
		List<String> letters = Arrays.asList(array);

		String firstChars = ultimoAsiento.substring(0, (ultimoAsiento.length()) - 1);
		String lastChar = ultimoAsiento.substring(ultimoAsiento.length() - 1);
		if (letters.indexOf(lastChar) == -1 || lastChar.equals("G")) {
			ultimoAsiento = String.valueOf(Integer.parseInt(firstChars) + 1) + "A";
			return ultimoAsiento;
		} else {
			ultimoAsiento = firstChars + letters.get(letters.indexOf(lastChar) + 1);
			return ultimoAsiento;
		}
	}

	private String generarAlimentacion() {
		String[] alimentaciones = { "vegano", "estandar", "vegetariano", "celiaco" };
		int min = 0;
		int max = alimentaciones.length;
		return alimentaciones[(int) Math.floor(Math.random() * (max - min) + min)];
	}

	//Funciones para CONTROLLER

	public List<CheckinTest> getListaCheckinTest() {
		return checkinTRepo.findAll();
	}

	public CheckinTest getCheckinTestConCodigo(String codigo) {
		return checkinTRepo.getCheckinTestConCodigo(codigo);
	}

}
