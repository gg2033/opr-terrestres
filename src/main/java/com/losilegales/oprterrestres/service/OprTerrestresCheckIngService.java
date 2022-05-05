package com.losilegales.oprterrestres.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.losilegales.oprterrestres.dto.CargaDTO;
import com.losilegales.oprterrestres.dto.CheckInDTO;
import com.losilegales.oprterrestres.dto.PasajeroDTO;
import com.losilegales.oprterrestres.dto.TipoCarga;
import com.losilegales.oprterrestres.entity.Carga;
import com.losilegales.oprterrestres.entity.Pasajero;
import com.losilegales.oprterrestres.entity.Vuelo;
import com.losilegales.oprterrestres.repository.PasajeroRepository;
import com.losilegales.oprterrestres.repository.VueloRepository;
import com.losilegales.oprterrestres.utils.OprConstants;

@Service
public class OprTerrestresCheckIngService {

	@Autowired
	private EmailService emailService;

	@Autowired
	private OpTerrGoogleSheetService opTerrGoogleSheetService;
	@Autowired
	private VueloRepository vueloRepository;
	
	@Autowired
	private PasajeroRepository pasajeroRepository;
	

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
				checkInDto.setApellidos((String) row.get(8) + " "+(String) row.get(9) );
				checkInDto.setNombres((String) row.get(10)+ " "+(String) row.get(11));
				checkInDto.setNacionalidad((String) row.get(12));
				checkInDto.setEdad((String) row.get(13));
				checkInDto.setSexo((String) row.get(14));
				List<CargaDTO> cargasDto = new ArrayList<CargaDTO>();
			
				int base = 15;
				for (int i = 0; i < 5; i++) {
					CargaDTO carga = new CargaDTO();
					PasajeroDTO pasajeroDto = new PasajeroDTO();
					pasajeroDto.setNombre(checkInDto.getNombres());
					if(StringUtils.isNotBlank((String)row.get(base+i))){
						carga.setPeso(Integer.parseInt((String) row.get(base+i)));
					}
			
					if(base+i==20) {
						carga.setTipoCarga(TipoCarga.DeMano);
					}else {
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

	public Optional<Vuelo> checkValidationData(Map<String, List<CheckInDTO>> checkByVuelo) {
		boolean isValidData = true;
//		List<Object> checkByVueloRows = checkByVuelo.get(checkByVuelo)
		Optional<Vuelo> vuelo = null;	
		for (List<CheckInDTO> checkin : checkByVuelo.values()) {
			vuelo = vueloRepository.findByCodigo((String) checkin.get(0).getVuelo());
			if(!vuelo.isPresent()) {

				vuelo.get().setCantPasajeros(checkin.size());
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
	//				else if (i == 3) {// check partida coresponder vuelo
	//					isValidData = false;
	//	//				
	//				} else if (i == 6) {// check TIPO DOCUMENTO DNI, pasaporte o cedula.
	//					isValidData = false;
	//	
	//				}
					List<Carga> cargas = new ArrayList<Carga>();
//					modelMapper.map(checkin.get(i).getCarga(), cargas);
				
					Pasajero pasajero = new Pasajero();
					for (CargaDTO cargaDto : checkin.get(i).getCarga()) {
						Carga carga = new Carga();
						carga.setPasajero(pasajero);
						carga.setPeso(cargaDto.getPeso());
						carga.setTipoCarga(carga.getTipoCarga());
						cargas.add(carga);
						
					}
					pasajero.setNombre(checkin.get(i).getNombres());
					pasajero.setCarga(cargas);
					pasajero.setVuelo(vuelo.get());
					pasajero.setCarga(cargas);
					pasajeroRepository.save(pasajero);
					
//					pasajeros.add(pasajero);
					
					
		
		//			-Por cada carga se debe guardar ID, peso, tipo ("Documentado" o "De mano") en estado se debe guardar "Check In
		
					
					// save info. guardar cantidad de pasajeros en registro de vuelo.
					//registro de carga
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

}
