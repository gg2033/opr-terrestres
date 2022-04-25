package com.losilegales.oprterrestres.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.losilegales.oprterrestres.constants.OprConstants;
import com.losilegales.oprterrestres.dto.PasajeroDTO;
import com.losilegales.oprterrestres.repository.UsuarioRepository;
import com.losilegales.oprterrestres.service.OpTerrGoogleSheetService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(OprConstants.BASE_ENDPOINT)
@Tag(name = "Rutas", description = "Endpoints de Rutas")
public class OprTerrestresController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	public OpTerrGoogleSheetService opTerrGoogleSheetService;

	@GetMapping("/rutas")
    @ResponseBody
	 List<PasajeroDTO> home() {
		 List<PasajeroDTO> pasajerosDto = null;
//	List<Usuario> usuarios = usuarioRepository.findAll();
	try {
		 pasajerosDto=opTerrGoogleSheetService.getSpreadsheetValues(OprConstants.MAESTRO_RUTAS_VUELO);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (GeneralSecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return pasajerosDto;
//       return usuarios.toString();
    }

}
