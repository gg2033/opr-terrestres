package com.losilegales.oprterrestres.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.losilegales.oprterrestres.dto.CheckInDTO;
import com.losilegales.oprterrestres.dto.AeroNaves.AeroNavesResponse;
import com.losilegales.oprterrestres.service.AeroNavesService;
import com.losilegales.oprterrestres.service.OprTerrestresCheckIngService;
import com.losilegales.oprterrestres.utils.OprConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(OprConstants.BASE_ENDPOINT)
@Tag(name = "Operaciones Terrestres", description = "Operaciones Terrestres")
public class OprTerrestresController {

	@Autowired
	public OprTerrestresCheckIngService oprTerrestresCheckIngService;
	@Autowired
	private AeroNavesService aeronavesService;
	
	@GetMapping("/vuelo")
	@ResponseBody
	List<CheckInDTO> registrarVuelo() {
		List<CheckInDTO> data = oprTerrestresCheckIngService.getDataCheckIn();

		return data;

	}
	
	
	@GetMapping("/aeronaves")
	@ResponseBody
	ResponseEntity<AeroNavesResponse> getAeronaves() {
		return ResponseEntity.ok(aeronavesService.obtenerAeronaves());

	}
	
	
	

}
