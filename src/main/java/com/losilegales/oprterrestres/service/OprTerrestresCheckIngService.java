package com.losilegales.oprterrestres.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
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
import com.losilegales.oprterrestres.dto.CheckIn.CargaDTO;
import com.losilegales.oprterrestres.dto.CheckIn.DatoEspecialPasajeroDTO;
import com.losilegales.oprterrestres.entity.Carga;
import com.losilegales.oprterrestres.entity.Vuelo;
import com.losilegales.oprterrestres.enums.TipoCarga;
import com.losilegales.oprterrestres.enums.TipoTag;
import com.losilegales.oprterrestres.repository.CargaRepository;
import com.losilegales.oprterrestres.repository.ClaseAsientoRepository;
import com.losilegales.oprterrestres.repository.CondicionEspecialRepository;
import com.losilegales.oprterrestres.repository.DietaEspecificaRepository;
import com.losilegales.oprterrestres.repository.VueloRepository;
import com.losilegales.oprterrestres.utils.OprConstants;

import Excel.Cell;
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
	private CargaRepository cargaRepository;

	@Autowired
	private ModelMapper modelMapper;

//	public List<CheckInDTO> getDataCheckIn() {
//
//		List<CheckInDTO> checkInDataLst = null;
//		List<List<Object>> dataCheckIn;
//		boolean isDataValid = true;
//		try {
//			dataCheckIn = opTerrGoogleSheetService.getSpreadsheetValues(OprConstants.CHECK_IN);
//
//			checkInDataLst = dataCheckIn.stream().map(row -> {// cada checkin individual
////			Equipaje de Mano	Alimentación	Condición
//				CheckInDTO checkInDto = new CheckInDTO();
//				checkInDto.setLineaAerea((String) row.get(0));
//				checkInDto.setVuelo((String) row.get(1));
//				checkInDto.setFecha((String) row.get(2));
//				checkInDto.setPartida((String) row.get(3));
//				checkInDto.setOrigen((String) row.get(4));
//				checkInDto.setDestino((String) row.get(5));
//				checkInDto.setTipoDocumento((String) row.get(6));
//				checkInDto.setNroDocumento((String) row.get(7));
//				checkInDto.setApellidos((String) row.get(8) + " " + (String) row.get(9));
//				checkInDto.setNombres((String) row.get(10) + " " + (String) row.get(11));
//				checkInDto.setNacionalidad((String) row.get(12));
//				checkInDto.setEdad((String) row.get(13));
//				checkInDto.setSexo((String) row.get(14));
//				List<CargaDTO> cargasDto = new ArrayList<CargaDTO>();
//
//				int base = 15;
//				for (int i = 0; i < 5; i++) {
//					CargaDTO carga = new CargaDTO();
//					PasajeroDTO pasajeroDto = new PasajeroDTO();
//					pasajeroDto.setNombre(checkInDto.getNombres());
//					if (StringUtils.isNotBlank((String) row.get(base + i))) {
//						carga.setPeso(Integer.parseInt((String) row.get(base + i)));
//					}
//
//					if (base + i == 20) {
//						carga.setTipoCarga(TipoCarga.DE_MANO);
//					} else {
//						carga.setTipoCarga(TipoCarga.FACTURADO);
//
//					}
//
//					cargasDto.add(carga);
//				}
//
//				// carga
//				checkInDto.setCarga(cargasDto);
//
//				checkInDto.setEquipajeMano((String) row.get(20));
//				// fin de carga
//				// datos de alimentacion
//				checkInDto.setAlimentacion((String) row.get(21));
//				checkInDto.setCondicion((String) row.get(22));
//
//				return checkInDto;
//
//			}).collect(Collectors.toList());
//
//			Map<String, List<CheckInDTO>> checkinByVuelo = checkInDataLst.stream()
//					.collect(Collectors.groupingBy(c -> c.getVuelo()));
//
//			Optional<Vuelo> vuelo = checkValidationData(checkinByVuelo); // checkinPorVuelo
//
//		} catch (IOException | GeneralSecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return checkInDataLst;
//
//	}

	
	public Optional<List<DatoEspecialPasajeroDTO>> getDatosEspecialesPorVuelo(int vuelo){
		List<DatoEspecialPasajeroDTO> datosEspeciales = new ArrayList<DatoEspecialPasajeroDTO>();
		
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

	public List<CargaDTO> getDataEquipajeCheckin(String codigoVuelo) {
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
			int indiceCodigoPasajero = result.getTable().getCols().stream().map(c -> c.getLabel()).collect(Collectors.toList()).indexOf("codigo_pasajero");
			
			List<Row> cargasPorPasajero = lstValidCargaCheckin.stream().filter(e -> e.getC().get(indiceCodigoPasajero).getV().toString().equals(codigoVuelo)).collect(Collectors.toList());
			
			//guardar las cargas en la db.
			for (Row row : cargasPorPasajero) {
				CargaDTO carga = new CargaDTO();
				carga.setCodigo(row.getC().get(0).getF());
				carga.setCodigoPasajero(row.getC().get(1).getV().toString());
				carga.setTipoCarga(TipoCarga.valueOf(row.getC().get(2).getV().toString()));
				int peso = Integer.parseInt(row.getC().get(3).getV().toString().split("\\.")[0]);
				carga.setPeso(peso);
				carga.setTagCarga(TipoTag.fromString(row.getC().get(4).getV().toString()));
				cargas.add(carga);
				
			}
		
			return cargas;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return cargas;
			
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
			Carga carga = new Carga();
//			for (Row datosPasajeroCheckin : rows) {
//				carga
//			}
			//seteo estado de carga al llegar al checkin.
//			cargaRepository.save(null);
			result.getTable().setRows(rows);
			return result;
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
