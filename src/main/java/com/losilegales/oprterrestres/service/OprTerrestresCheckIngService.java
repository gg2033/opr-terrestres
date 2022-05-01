package com.losilegales.oprterrestres.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.losilegales.oprterrestres.dto.CheckInDTO;
import com.losilegales.oprterrestres.entity.Vuelo;
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

	public List<CheckInDTO> getDataCheckIn() {

		List<CheckInDTO> checkInDataLst = null;
		List<List<Object>> dataCheckIn;
		boolean isDataValid=true;
		try {
			dataCheckIn = opTerrGoogleSheetService.getSpreadsheetValues(OprConstants.CHECK_IN);

			checkInDataLst = dataCheckIn.stream().map(row -> {
				checkValidationData(row);
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
				checkInDto.setApellidos((String) row.get(8));
				checkInDto.setNombres((String) row.get(9));
				checkInDto.setNacionalidad((String) row.get(10));
				checkInDto.setEdad((String) row.get(11));
				checkInDto.setSexo((String) row.get(12));
				checkInDto.setBulto1((String) row.get(13));
				checkInDto.setBulto2((String) row.get(14));
				checkInDto.setBulto3((String) row.get(15));
				checkInDto.setBulto4((String) row.get(16));
				checkInDto.setBulto5((String) row.get(17));
				checkInDto.setEquipajeMano((String) row.get(18));
				checkInDto.setAlimentacion((String) row.get(19));
				checkInDto.setCondicion((String) row.get(20));
				
				// la parte aburrida.
//			checkInDto.setIdVuelo((String) row.get(0));
//			checkInDto.setOrigen((String) row.get(1));
//			checkInDto.setDestino((String) row.get(2));
				return checkInDto;

			}).collect(Collectors.toList());

		} catch (IOException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return checkInDataLst;

	}

	public void checkValidationData(List<Object> row) {
		boolean isValidData = true;
		
		for (int i = 0; i < row.size(); i++) {
		
			if (i == 0) {// check aerolinea debe ser SF
				if("SF".equals((String)row.get(i))){
					isValidData = false;
				}
			} else if (i == 1) {// check vuelo, debe estar vuelo en db
				Vuelo vuelo = vueloRepository.findByCodigoVuelo((String)row.get(i));
				if(Objects.isNull(vuelo)) {
					isValidData = false;
				}
			
			} else if (i == 2) {// check fecha coresponder vuelo
				if("SF".equals((String)row.get(i))){
					isValidData = false;
				}
			} else if (i == 3) {// check partida coresponder vuelo
				isValidData = false;
//				
			} else if (i == 6) {// check TIPO DOCUMENTO DNI, pasaporte o cedula.
				isValidData = false;

			}
			
//			-Por cada carga se debe guardar ID, peso, tipo ("Documentado" o "De mano") en estado se debe guardar "Check In
			
		}
		
		if(!isValidData) {
//			emailService.sendConfirmationEmail("token", "emailSupervisor");
		}
	}

}
