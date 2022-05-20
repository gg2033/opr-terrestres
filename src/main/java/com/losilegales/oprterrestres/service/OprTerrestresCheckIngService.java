package com.losilegales.oprterrestres.service;

import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.losilegales.oprterrestres.dto.CargaDTO;
import com.losilegales.oprterrestres.dto.CheckInDTO;
import com.losilegales.oprterrestres.dto.PasajeroDTO;
import com.losilegales.oprterrestres.dto.TipoCarga;
import com.losilegales.oprterrestres.dto.CheckIn.DatoEspecialPasajeroDTO;
import com.losilegales.oprterrestres.entity.Carga;
import com.losilegales.oprterrestres.entity.ClaseAsiento;
import com.losilegales.oprterrestres.entity.CondicionEspecial;
import com.losilegales.oprterrestres.entity.RegistroClases;
import com.losilegales.oprterrestres.entity.RegistroCondiciones;
import com.losilegales.oprterrestres.entity.RegistroPasajeros;
import com.losilegales.oprterrestres.entity.Vuelo;
import com.losilegales.oprterrestres.repository.ClaseAsientoRepository;
import com.losilegales.oprterrestres.repository.CondicionEspecialRepository;
import com.losilegales.oprterrestres.repository.DietaEspecificaRepository;
import com.losilegales.oprterrestres.repository.VueloRepository;
import com.losilegales.oprterrestres.utils.OprConstants;

import Excel.Cell;
import Excel.Col;
import Excel.ExcelResponse;
import Excel.Row;


@Service
public class OprTerrestresCheckIngService {

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
	private ModelMapper modelMapper;

	public List<CheckInDTO> getDataCheckIn() {

		List<CheckInDTO> checkInDataLst = null;
		List<List<Object>> dataCheckIn;
		boolean isDataValid = true;
		try {
			dataCheckIn = opTerrGoogleSheetService.getSpreadsheetValues(OprConstants.CHECK_IN);

			checkInDataLst = dataCheckIn.stream().map(row -> {// cada checkin individual
//			Equipaje de Mano	Alimentación	Condición
				CheckInDTO checkInDto = new CheckInDTO();
				checkInDto.setLineaAerea((String) row.get(0));
				checkInDto.setVuelo((String) row.get(1));
				checkInDto.setFecha((String) row.get(2));
				checkInDto.setPartida((String) row.get(3));
				checkInDto.setOrigen((String) row.get(4));
				checkInDto.setDestino((String) row.get(5));
				checkInDto.setTipoDocumento((String) row.get(6));
				checkInDto.setNroDocumento((String) row.get(7));
				checkInDto.setApellidos((String) row.get(8) + " " + (String) row.get(9));
				checkInDto.setNombres((String) row.get(10) + " " + (String) row.get(11));
				checkInDto.setNacionalidad((String) row.get(12));
				checkInDto.setEdad((String) row.get(13));
				checkInDto.setSexo((String) row.get(14));
				List<CargaDTO> cargasDto = new ArrayList<CargaDTO>();

				int base = 15;
				for (int i = 0; i < 5; i++) {
					CargaDTO carga = new CargaDTO();
					PasajeroDTO pasajeroDto = new PasajeroDTO();
					pasajeroDto.setNombre(checkInDto.getNombres());
					if (StringUtils.isNotBlank((String) row.get(base + i))) {
						carga.setPeso(Integer.parseInt((String) row.get(base + i)));
					}

					if (base + i == 20) {
						carga.setTipoCarga(TipoCarga.DeMano);
					} else {
						carga.setTipoCarga(TipoCarga.Documentado);

					}

					cargasDto.add(carga);
				}

				// carga
				checkInDto.setCarga(cargasDto);

				checkInDto.setEquipajeMano((String) row.get(20));
				// fin de carga
				// datos de alimentacion
				checkInDto.setAlimentacion((String) row.get(21));
				checkInDto.setCondicion((String) row.get(22));

				return checkInDto;

			}).collect(Collectors.toList());

			Map<String, List<CheckInDTO>> checkinByVuelo = checkInDataLst.stream()
					.collect(Collectors.groupingBy(c -> c.getVuelo()));

			Optional<Vuelo> vuelo = checkValidationData(checkinByVuelo); // checkinPorVuelo

		} catch (IOException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return checkInDataLst;

	}

	
	public Optional<List<DatoEspecialPasajeroDTO>> getDatosEspecialesPorVuelo(int vuelo){
		List<DatoEspecialPasajeroDTO> datosEspeciales = new ArrayList<DatoEspecialPasajeroDTO>();
		
		
		DatoEspecialPasajeroDTO datoEspecialPasajero = new DatoEspecialPasajeroDTO();
		
		
		ExcelResponse checkin = this.getDataCheckinJson(vuelo);
		
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
	public Optional<Vuelo> checkValidationData(Map<String, List<CheckInDTO>> checkByVuelo) {
		boolean isValidData = true;
//		List<Object> checkByVueloRows = checkByVuelo.get(checkByVuelo)
		Optional<Vuelo> vuelo = null;
		for (List<CheckInDTO> checkin : checkByVuelo.values()) {
			vuelo = vueloRepository.findByCodigo((String) checkin.get(0).getVuelo());
			if (!vuelo.isPresent()) {

//				vuelo.get().setCantPasajeros(checkin.size());
//				List<Pasajero> pasajeros = new ArrayList<Pasajero>();

				for (int i = 0; i < checkin.size(); i++) {
					// check aerolinea debe ser SF
					if ("SF".equals((String) checkin.get(i).getLineaAerea())) {
						isValidData = false;
					}

					// check vuelo, debe estar vuelo en db
					if (!vuelo.isPresent()) {
						isValidData = false;
					}
//					if ("SF".equals((String) checkin.get(i).getFecha())) {
//							isValidData = false;
//					}
					// else if (i == 3) {// check partida coresponder vuelo
					// isValidData = false;
					// //
					// } else if (i == 6) {// check TIPO DOCUMENTO DNI, pasaporte o cedula.
					// isValidData = false;
					//
					// }
					List<Carga> cargas = new ArrayList<Carga>();
//					modelMapper.map(checkin.get(i).getCarga(), cargas);

//					pasajeros.add(pasajero);

					// -Por cada carga se debe guardar ID, peso, tipo ("Documentado" o "De mano") en
					// estado se debe guardar "Check In

					// save info. guardar cantidad de pasajeros en registro de vuelo.
					// registro de carga
					/*-Por cada carga se debe guardar ID, peso, tipo 
					 * ("Documentado" o "De mano") en estado se debe guardar "Check In"*/
				}

//				vuelo.get().setPasajeros(pasajeros);
//				if(!vuelo.isEmpty()) {
//					vueloRepository.save(vuelo.get());
//				}
			}
		}

		if (!isValidData) {
//			emailService.sendConfirmationEmail("token", "emailSupervisor");
		}

		return vuelo;
	}

	public ExcelResponse getDataCheckinJson(Integer lote) {
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
			
			
			
			
			List<Row> rows = lstValidCheckin.stream().filter(e -> Integer.parseInt(e.getC().get(0).getV().toString().subSequence(0, 1).toString()) ==lote).collect(Collectors.toList());
			
			result.getTable().setRows(rows);
			return result;
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
