package com.losilegales.oprterrestres.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.losilegales.oprterrestres.dto.CheckInDTO;
import com.losilegales.oprterrestres.service.AeroNavesService;
import com.losilegales.oprterrestres.service.OprTerrestresCheckIngService;
import com.losilegales.oprterrestres.utils.OprConstants;

import Excel.ExcelResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(OprConstants.BASE_ENDPOINT)
@Tag(name = "Operaciones Terrestres", description = "Operaciones Terrestres")
public class OprTerrestresController {

	@Autowired
	public OprTerrestresCheckIngService oprTerrestresCheckIngService;
	@Autowired
	private AeroNavesService aeronavesService;
	
	// NO BORRAR
//	@GetMapping("/vuelo")
//	@ResponseBody
//	List<CheckInDTO> registrarVuelo() {
//		List<CheckInDTO> data = oprTerrestresCheckIngService.getDataCheckIn();
//
//		return data;
//
//	}
	// NO BORRAR
//	@GetMapping("/check")
//	@ResponseBody
//	ExcelResponse getCheckin() {
//		ExcelResponse  data = oprTerrestresCheckIngService.getDataCheckinJson();
//
//		return data;
//
//	}
		
	@GetMapping("/aeronave/{codigoAvion}/{campo}")
	@ResponseBody
	ResponseEntity<String> getAeronavesJson(@PathVariable String codigoAvion, @PathVariable String campo ) {
		return ResponseEntity.ok(aeronavesService.obtenerAeronavesJson(codigoAvion, campo));

	}
	
	
	
	
	

}
