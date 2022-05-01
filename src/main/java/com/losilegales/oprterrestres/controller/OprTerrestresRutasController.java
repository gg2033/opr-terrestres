package com.losilegales.oprterrestres.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.losilegales.oprterrestres.dto.RutaVueloDTO;
import com.losilegales.oprterrestres.repository.UsuarioRepository;
import com.losilegales.oprterrestres.service.OpTerrGoogleSheetService;
import com.losilegales.oprterrestres.service.OprTerrestresCheckIngService;
import com.losilegales.oprterrestres.utils.OprConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(OprConstants.BASE_ENDPOINT)
@Tag(name = "Rutas", description = "Endpoints de Rutas")
public class OprTerrestresRutasController {

	@Autowired
	public OpTerrGoogleSheetService opTerrGoogleSheetService;
	

	@GetMapping(OprConstants.ENDPOINT_RUTAS)
	@ResponseBody
	List<RutaVueloDTO> home() {

		List<RutaVueloDTO> rutas = null;
		try {
			List<List<Object>> rows = opTerrGoogleSheetService.getSpreadsheetValues(OprConstants.MAESTRO_RUTAS_VUELO);

			rutas = rows.stream().map(row -> {
				RutaVueloDTO rutaDto = new RutaVueloDTO();
				//la parte aburrida.
				rutaDto.setIdVuelo((String) row.get(0));
				rutaDto.setOrigen((String) row.get(1));
				rutaDto.setDestino((String) row.get(2));
				return rutaDto;

			}

			).collect(Collectors.toList());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rutas;
	}
	
	

}
