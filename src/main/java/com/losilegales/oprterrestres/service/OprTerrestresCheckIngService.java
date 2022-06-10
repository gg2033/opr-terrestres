package com.losilegales.oprterrestres.service;

import java.net.URI;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.losilegales.oprterrestres.dto.CheckIn.CargaDTO;
import com.losilegales.oprterrestres.dto.CheckIn.DatoEspecialPasajeroDTO;
import com.losilegales.oprterrestres.entity.Carga;
import com.losilegales.oprterrestres.entity.Checkin;
import com.losilegales.oprterrestres.entity.Vuelo;
import com.losilegales.oprterrestres.repository.CargaRepository;
import com.losilegales.oprterrestres.repository.CheckinRepository;
import com.losilegales.oprterrestres.repository.ClaseAsientoRepository;
import com.losilegales.oprterrestres.repository.CondicionEspecialRepository;
import com.losilegales.oprterrestres.repository.DietaEspecificaRepository;
import com.losilegales.oprterrestres.repository.VueloRepository;
import com.losilegales.oprterrestres.utils.OprConstants;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import Excel.Cell;
import Excel.Col;
import Excel.ExcelResponse;
import Excel.Row;
import converter.LocalDateAttributeConverter;

@Service
public class OprTerrestresCheckIngService {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(OprTerrestresCheckIngService.class);

	@Autowired
	private EmailService emailService;

	@Autowired
	private OpTerrGoogleSheetService opTerrGoogleSheetService;
	@Autowired
	private VueloRepository vueloRepository;
	
	@Autowired
	private CondicionEspecialRepository condicionEspecialRepository;
	
	@Autowired
	private ClaseAsientoRepository claseAsientoRepository;
	
	@Autowired
	private DietaEspecificaRepository dietaEspecificaRepository;

	@Autowired
	@Qualifier("simpleRestTemplate")
	private RestTemplate simpleRestTemplate;
	@Autowired
	private CargaRepository cargaRepository;
	@Autowired
	private CheckinRepository checkinRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@SuppressWarnings("unchecked")
	public org.json.simple.JSONObject sobrepasaPesoAeronave(String codigoVuelo) throws UnirestException {
		org.json.simple.JSONObject ret = new org.json.simple.JSONObject();
		//Obtener los vuelos
		try {
			int capacidadEnToneladas = capacidadAeronaveEnToneladas(codigoVuelo);
		    //Codigo por si existe diferencia entre vuelos de pasajeros
	//		//Recorrer checkins y por cada uno obtener el codigo de pasajero
	//	    List<Checkin> listaCheckin = checkinRepository.checkinPorVuelo(codigoVuelo);
	//		//Obtener todas las cargas con ese codigo de pasajero
	//	    List<Carga> listaCargas = new LinkedList<Carga>();
	//	    for (Checkin p : listaCheckin) {
	//	    	String codigoPasajero = p.getCodigoPasajero();
	//	    	listaCargas.addAll(cargaRepository.cargasPorPasajero(codigoPasajero));
	//	    }
		    
		    List<Carga> listaCargas = cargaRepository.cargasPorVuelo(codigoVuelo);
		    List<Checkin> listaCheckin = checkinRepository.checkinPorVuelo(codigoVuelo);
			//Recorrer esa lista de cargas para obtener el peso de cada una
		    int pesoTotalCargasEnKG = getPesoSumadoCargas(listaCargas);
			int pesoTotalPasajerosEnKG = getPesoPromedioPorPasajero(listaCheckin);
			//Comparar la suma de las cargas con la capacidad de la aeronave
			//Se multiplica por 1000 porque esta en toneladas
		    if(pesoTotalCargasEnKG + pesoTotalPasajerosEnKG > capacidadEnToneladas*1000) {
				//Si sobrepasa
				ret.put("mensaje", "Si supera la capacidad maxima");
				ret.put("sobrecarga", true);
				ret.put("cantidadPasajeros", listaCheckin.size());
				ret.put("pesoKG", pesoTotalCargasEnKG + pesoTotalPasajerosEnKG);
		    	return ret;
		    }
		    else {
		    	//Si no sobrepasa
				ret.put("mensaje", "No supera la capacidad maxima");
				ret.put("sobrecarga", false);
				ret.put("cantidadPasajeros", listaCheckin.size());
				ret.put("pesoKG", pesoTotalCargasEnKG + pesoTotalPasajerosEnKG);
		    	return ret;
		    }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		ret.put("mensaje", "Hubo un error");
		ret.put("sobrecarga", null);
		ret.put("cantidadPasajeros", null);
		ret.put("pesoKG", null);
		return ret;
	}

	private String getCodigoVueloFormatoActualizado(String codigoVuelo) {
		StringBuilder sb = new StringBuilder(codigoVuelo);
		for(int i=0; i < codigoVuelo.length(); i++) {
	         if(Character.isWhitespace(codigoVuelo.charAt(i))) {
	        	 sb.replace(i, i+1, "-");
	        	 break;
	         }
		}
		return sb.toString();
		
	}
	private int capacidadAeronaveEnToneladas(String codigoVuelo) throws UnirestException {
		HttpResponse <JsonNode> response = Unirest.get("https://grops-backend-dnj2km2huq-rj.a.run.app/flight/getAll").asJson();
		codigoVuelo = getCodigoVueloFormatoActualizado(codigoVuelo);
		//Obtener de esa lista el vuelo con el codigo de vuelo pasado por parametro
		JSONObject vuelo = new JSONObject();
		String nombreAeronave = "";
		Iterator<Object> itr = response.getBody().getArray().iterator();
	    while(itr.hasNext() && vuelo.isNull("name")) {
	    	JSONObject element = (JSONObject)itr.next();
	    	if(element.get("code").equals(codigoVuelo)) {
	    		vuelo = element;
	    		nombreAeronave = element.getString("aircraft");
	    		break;
	    	}
	    }
	    
		//Obtener las aeronaves
	    HttpResponse <JsonNode> responseAeronave = Unirest.get("https://grops-backend-dnj2km2huq-rj.a.run.app/aircraft/getAll").asJson();
		
	    //Obtener de esa lista la aeronave que figura en el el vuelo con el codigo de vuelo pasado por parametro
		JSONObject aeronave = new JSONObject();
		int capacidadEnToneladas = 0;
		Iterator<Object> itrA = responseAeronave.getBody().getArray().iterator();
	    while(itrA.hasNext() && aeronave.isNull("name")) {
	    	JSONObject element = (JSONObject)itrA.next();
	    	if(element.get("model").equals(nombreAeronave)) {
	    		aeronave = element;
	    	    //Obtener la capacidad de esa aeronave
	    		capacidadEnToneladas = element.getInt("weightTolerance");
	    		break;
	    	}
	    }
		return capacidadEnToneladas;
	}

	//TODO se podria recibir la lista de checkin, verificar cuales son mujeres y cuales hombres
	//y sacar un promedio utilizando esos valores.
	private int getPesoPromedioPorPasajero(List<Checkin> pasajeros) {
		int pesoPromedioM = 75;
		int pesoPromedioF = 65;
		int cantPersonasM = (int) pasajeros.stream().filter(x -> (x.getSexo() == 'M' || x.getSexo() == 'm')).count();
		int cantPersonasF = (int) pasajeros.stream().filter(x -> (x.getSexo() == 'F' || x.getSexo() == 'f')).count();
		int pesoPromedioPorPersona = cantPersonasM * pesoPromedioM + cantPersonasF * pesoPromedioF;
		return pesoPromedioPorPersona;
	}

	private int getPesoSumadoCargas(List<Carga> listaCargas) {
		int suma = 0;
		for (Carga c : listaCargas) {
			suma += c.getPeso(); 
		}
		return suma;
	}

	
	public List<org.json.simple.JSONObject> getPasajerosSegunVuelo(String vuelo) {
		LOGGER.info("MOSTRANDO DATOS DE PASAJEROS");
		List<Checkin> listaCheckin = checkinRepository.checkinPorVuelo(vuelo);
		List<org.json.simple.JSONObject> ret = new ArrayList<org.json.simple.JSONObject>(listaCheckin.size());
		HashMap<String, Object> pasajero = new HashMap<String, Object>();
		for(Checkin p : listaCheckin) {
			pasajero.put("nombre", p.getNombre());
			pasajero.put("apellido", p.getApellido());
			pasajero.put("clase", p.getClase());
			pasajero.put("asiento", p.getAsiento());
			pasajero.put("alimentacion", p.getAlimentacion());
			pasajero.put("condicion", p.getCondicion());
			org.json.simple.JSONObject jsonPasajero= new org.json.simple.JSONObject(pasajero);
			ret.add(jsonPasajero);
		}
		return ret;
	}

	
	public Optional<List<DatoEspecialPasajeroDTO>> getDatosEspecialesPorVuelo(String codigoVuelo){
		List<DatoEspecialPasajeroDTO> datosEspeciales = new ArrayList<DatoEspecialPasajeroDTO>();
//		DatoEspecialPasajeroDTO datoEspecialPasajero = new DatoEspecialPasajeroDTO();
		ExcelResponse checkin = this.registrarDataCheckinJson(codigoVuelo);

		
		int indicePNombre = checkin.getTable().getCols().stream().map(c -> c.getLabel()).collect(Collectors.toList()).indexOf("primer_nombre");
		int indiceSNombre = checkin.getTable().getCols().stream().map(c -> c.getLabel()).collect(Collectors.toList()).indexOf("segundo_nombre");
		int indicePAapellido = checkin.getTable().getCols().stream().map(c -> c.getLabel()).collect(Collectors.toList()).indexOf("primer_apellido");
		int indiceSApellido = checkin.getTable().getCols().stream().map(c -> c.getLabel()).collect(Collectors.toList()).indexOf("segundo_apellido");
		int indiceCondicion = checkin.getTable().getCols().stream().map(c -> c.getLabel()).collect(Collectors.toList()).indexOf("condicion");
		int indiceClase = checkin.getTable().getCols().stream().map(c -> c.getLabel()).collect(Collectors.toList()).indexOf("clase");
		int indiceAlimentacion = checkin.getTable().getCols().stream().map(c -> c.getLabel()).collect(Collectors.toList()).indexOf("alimentacion");
		int indiceAsiento = checkin.getTable().getCols().stream().map(c -> c.getLabel()).collect(Collectors.toList()).indexOf("asiento");
		for (Row pasajero : checkin.getTable().getRows()) {
			boolean isEmptyField = false;
			//check colums por cada fila.
			for (Cell celda : pasajero.getC()) {
				if(Objects.isNull(celda) || Objects.isNull(celda.getV()) || StringUtils.isEmpty(celda.getV().toString())){
					isEmptyField=true;
				}
			}
			if(!isEmptyField) {
				DatoEspecialPasajeroDTO datoEspecialPasajero = new DatoEspecialPasajeroDTO();
				datoEspecialPasajero.setAlimentacion(pasajero.getC().get(indiceAlimentacion).getV().toString());
				datoEspecialPasajero.setAsiento(pasajero.getC().get(indiceAsiento).getV().toString());
				datoEspecialPasajero.setClase(pasajero.getC().get(indiceClase).getV().toString());
				datoEspecialPasajero.setCondicion(pasajero.getC().get(indiceCondicion).getV().toString());
				
				String pNombre = pasajero.getC().get(indicePNombre).getV().toString();
				String sNombre= pasajero.getC().get(indiceSNombre).getV().toString();
				String sApellido= pasajero.getC().get(indiceSApellido).getV().toString();
				String pApellido= pasajero.getC().get(indicePAapellido).getV().toString();
				
				String nombreCompleto = pNombre+" "+sNombre+" "+pApellido+" "+sApellido;
				datoEspecialPasajero.setNombreCompletoPasajero(nombreCompleto);
				datosEspeciales.add(datoEspecialPasajero);	
			}
		}
		
		return Optional.of(datosEspeciales);
	}

	public List<CargaDTO> registrarDataEquipajeCheckin(String codigoVuelo) {
		ExcelResponse result = new ExcelResponse();
		URI uri;
		List<CargaDTO> cargas = new ArrayList<CargaDTO>();
		try {
			uri = new URI(OprConstants.CHECK_IN_EQUIPAJE);
	
			String json = simpleRestTemplate.getForEntity(uri, String.class).getBody();
			json = json.substring(47);
			json = json.substring(0, json.length() - 2);
	
			ObjectMapper objectMapper = new ObjectMapper();
			result = objectMapper.readValue(json, ExcelResponse.class);
			
			List<Row> lstValidCargaCheckin = new ArrayList<Row>();
			for (Row cargaPasajero : result.getTable().getRows()) {
				boolean isEmptyField = false;
				//check colums por cada fila.
				for (Cell celda : cargaPasajero.getC()) {
					if(Objects.isNull(celda) || Objects.isNull(celda.getV()) || StringUtils.isEmpty(celda.getV().toString())){
						isEmptyField=true;
					}
				}
				if(!isEmptyField) {
					lstValidCargaCheckin.add(cargaPasajero);
				}
			}
			
			int indiceCodigoVuelo = result.getTable().getCols().stream().map(c -> c.getLabel()).collect(Collectors.toList()).indexOf("codigo_vuelo");
			
			List<Row> rows = lstValidCargaCheckin.stream().filter(e -> e.getC().get(indiceCodigoVuelo).getV().toString().equals(codigoVuelo)).collect(Collectors.toList());

			result.getTable().setRows(rows);
			
			persistirDatosCarga(result, codigoVuelo);
		
			return cargas;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return cargas;
			
	}
	
	public List<Checkin> getCheckinPorVuelo(String codigoVuelo){
		List<Checkin> listaCheckin = checkinRepository.checkinPorVuelo(codigoVuelo);
		return listaCheckin;
	}
	
	public List<CargaDTO> getCargasPorVuelo(String codigoVuelo){
		List<Carga> listaCargasPorVuelo =  cargaRepository.cargasPorVuelo(codigoVuelo);
		List<CargaDTO> listaCargasDTO = new ArrayList<CargaDTO>(listaCargasPorVuelo.size());
		for (Carga c : listaCargasPorVuelo) {
			listaCargasDTO.add(modelMapper.map(c, CargaDTO.class));
		}
		return listaCargasDTO;
	}
	
	public ExcelResponse registrarDataCheckinJson(String codigoVuelo) {
		Optional<Vuelo> vuelo = null;
		ExcelResponse result = new ExcelResponse();
		URI uri;
		try {
			uri = new URI(OprConstants.CHECK_IN_JSON);
	
			String json = simpleRestTemplate.getForEntity(uri, String.class).getBody();
			json = json.substring(47);
			json = json.substring(0, json.length() - 2);
	
			ObjectMapper objectMapper = new ObjectMapper();
			result = objectMapper.readValue(json, ExcelResponse.class);
			
			
			List<Row> lstValidCheckin = new ArrayList<Row>();
			for (Row pasajero : result.getTable().getRows()) {
				boolean isEmptyField = false;
				//check colums por cada fila.
				for (Cell celda : pasajero.getC()) {
					if(Objects.isNull(celda) || Objects.isNull(celda.getV()) || StringUtils.isEmpty(celda.getV().toString())){
						isEmptyField=true;
					}
				}
				if(!isEmptyField) {
					lstValidCheckin.add(pasajero);
				}
			}
			
			int indiceCodigoVuelo = result.getTable().getCols().stream().map(c -> c.getLabel()).collect(Collectors.toList()).indexOf("codigo_vuelo");

			List<Row> rows = lstValidCheckin.stream().filter(e -> e.getC().get(indiceCodigoVuelo).getV().toString().equals(codigoVuelo)).collect(Collectors.toList());

			result.getTable().setRows(rows);
			persistirDatosCheckin(result);
			//crear una lista de checkins con el vuelo especificado, y devolverla
			return result;
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	private void persistirDatosCheckin(ExcelResponse checkinData) {
		//19 columnas tiene el checkin
//		checkinRepository.truncateTabla();
		List<String> nombreColumn = new ArrayList<String>(19);
		//Recorro el excel fila por columna y obtengo los nombres de las columnas
		for (Col c : checkinData.getTable().getCols()) {
			nombreColumn.add(c.getLabel());
		}
		
		int indice;
		//Agrego los datos de las filas a un objeto Checkin
		for(Row r : checkinData.getTable().getRows()) {
			indice = 0;
			Checkin chck = new Checkin();
			for(Cell c : r.getC()) {
				mapearDatosACheckin(nombreColumn.get(indice), c, chck);
				indice++;
			}
			//Persisto el objeto Checkin y sigo con la siguiente fila
			chck.setCreadoPor("juan");
			chck.setActivo(true);
			chck.setCreado(getFechaActual());;
			checkinRepository.save(chck);
		}
	}
	
	private void persistirDatosCarga(ExcelResponse cargaData, String codigoVuelo) {
		//19 columnas tiene el checkin
//		cargaRepository.truncateTabla();
		List<String> nombreColumn = new ArrayList<String>(6);
		//Recorro el excel fila por columna y obtengo los nombres de las columnas
		for (Col c : cargaData.getTable().getCols()) {
			nombreColumn.add(c.getLabel());
		}
		
		int indice;
		//Agrego los datos de las filas a un objeto Carga
		for(Row r : cargaData.getTable().getRows()) {
			indice = 0;
			Carga crga = new Carga();
			for(Cell c : r.getC()) {
				mapearDatosACarga(nombreColumn.get(indice), c, crga);
				indice++;
			}
			//Persisto el objeto Carga y sigo con la siguiente fila
			crga.setNombreCreador("juan");
			crga.setActivo(true);
			crga.setFechaCreacion(getFechaActual());
			crga.setEstadoCarga("En espera");
			cargaRepository.save(crga);
		}
	}

	private void mapearDatosACarga(String nombreColumn, Cell celda, Carga crga) {
		switch(nombreColumn) {
			case("envio"):
				Integer envio = Integer.parseInt(celda.getF());
				crga.setEnvio(envio);
				break;
			case("codigo_equipaje"):
				Integer codigo_equipaje = Integer.parseInt(celda.getF());
				crga.setCodigoCarga(codigo_equipaje.toString());
				break;
			case("codigo_pasajero"):
				String codigo_pasajero = celda.getV().toString();
				crga.setCodigoPasajero(codigo_pasajero);
				break;
			case("tipo"):
				String tipo = celda.getV().toString();
				crga.setTipo(tipo);
				break;
			case("peso"):
				Integer peso = Integer.parseInt(celda.getF());
				crga.setPeso(peso);
				break;
			case("tag"):
				String tag = celda.getV().toString();
				crga.setTag(tag);
				break;
			case("codigo_vuelo"):
				String codigo_vuelo = celda.getV().toString();
				crga.setCodigoVuelo(codigo_vuelo);
//			case("fecha_partida"):
//				String fecha_vuelo = celda.getV().toString();
//				crga.setFechaHoraVuelo(fecha_vuelo);
			default:
				break;
		}
	}
	
	private void mapearDatosACheckin(String nombreColumn, Cell celda, Checkin chck) {
		switch(nombreColumn) {
			case("envio"):
				Integer envio = Integer.parseInt(celda.getF());
				chck.setEnvio(envio);
				break;
			case("codigo_pasajero"):
				String codigo_pasajero = celda.getV().toString();
				chck.setCodigoPasajero(codigo_pasajero);
				break;
			case("linea_aerea"):
				String linea_aerea = celda.getV().toString();
				chck.setCompania(linea_aerea);
				break;
			case("codigo_vuelo"):
				String codig_vuelo = celda.getV().toString();
				chck.setCodigoVuelo(codig_vuelo);
				break;
			case("fecha_partida"):
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDate fecha_partida = LocalDate.parse(celda.getF().toString(), dtf);
				chck.setFechaPartida(fecha_partida);
				break;
			case("origen"):
				String origen = celda.getV().toString();
				chck.setOrigen(origen);
				break;
			case("destino"):
				String destino = celda.getV().toString();
				chck.setDestino(destino);
				break;
			case("asiento"):
				String asiento = celda.getV().toString();
				chck.setAsiento(asiento);
				break;
			case("clase"):
				String clase = celda.getV().toString();
				chck.setClase(clase);
				break;
			case("tipo_documento"):
				String tipo_documento = celda.getV().toString();
				chck.setTipoDocumento(tipo_documento);
				break;
			case("numero_documento"):
				Integer numero_documento = Integer.parseInt(celda.getF());
				chck.setNumeroDocumento(numero_documento);
				break;
			case("primer_apelllido"):
				String primer_apellido = celda.getV().toString();
				chck.setApellido(primer_apellido);
				break;
			case("primer_nombre"):
				String primer_nombre = celda.getV().toString();
				chck.setNombre(primer_nombre);
				break;
			case("nacionalidad"):
				String nacionalidad = celda.getV().toString();
				chck.setNacionalidad(nacionalidad);
				break;
			case("edad"):
				Integer edad = Integer.parseInt(celda.getF());
				chck.setEdad(edad);
				break;
			case("sexo"):
				char sexo = celda.getV().toString().charAt(0);
				chck.setSexo(sexo);
				break;
			case("alimentacion"):
				String alimentacion = celda.getV().toString();
				chck.setAlimentacion(alimentacion);
				break;
			case("condicion"):
				String condicion = celda.getV().toString();
				chck.setCondicion(condicion);
				break;
			case("comentarios"):
				String comentarios = celda.getV().toString();
				chck.setComentario(comentarios);
				break;
			default:
				break;
		}
	}
	
	private LocalDate getFechaActual() {
		return new LocalDateAttributeConverter().convertToEntityAttribute(new Date(System.currentTimeMillis()));
	}

}
