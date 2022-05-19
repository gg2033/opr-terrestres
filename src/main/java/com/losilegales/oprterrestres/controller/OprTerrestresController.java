package com.losilegales.oprterrestres.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.losilegales.oprterrestres.dto.CheckIn.DatoEspecialPasajeroDTO;
import com.losilegales.oprterrestres.service.AeroNavesService;
import com.losilegales.oprterrestres.service.OprTerrestresCheckIngService;
import com.losilegales.oprterrestres.utils.OprConstants;

import Excel.ExcelResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;

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
	
	@GetMapping("/servicioCompras/{lote}")
	@ResponseBody
	@Validated
	ExcelResponse getCheckin(@PathVariable @NonNull @Size(min = 1, max = 25) Integer lote) {
		ExcelResponse  data = oprTerrestresCheckIngService.getDataCheckinJson(lote);

		return data;

	}
		
	@GetMapping("/aeronave/{codigoAvion}/{campo}")
	@ResponseBody
	ResponseEntity<String> getAeronavesJson(@PathVariable String codigoAvion, @PathVariable String campo ) {
		return ResponseEntity.ok(aeronavesService.obtenerAeronavesJson(codigoAvion, campo));

	}
	
	@GetMapping("/specialPassengerData/{vuelo}")
	@ResponseBody
	ResponseEntity<Optional<List<DatoEspecialPasajeroDTO>>> getDatosEspecialesPorVuelo(@PathVariable @NonNull @Size(min = 1, max = 25) Integer vuelo) {
		
		return ResponseEntity.ok(oprTerrestresCheckIngService.getDatosEspecialesPorVuelo(vuelo));

	}

}
