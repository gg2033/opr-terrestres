package com.losilegales.oprterrestres.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.losilegales.oprterrestres.dto.CheckIn.CargaDTO;
import com.losilegales.oprterrestres.dto.CheckIn.DatoEspecialPasajeroDTO;
import com.losilegales.oprterrestres.entity.Checkin;
import com.losilegales.oprterrestres.service.AeroNavesService;
import com.losilegales.oprterrestres.service.LogService;
import com.losilegales.oprterrestres.service.OprTerrestresCargaService;
import com.losilegales.oprterrestres.service.OprTerrestresCheckIngService;
import com.losilegales.oprterrestres.utils.OprConstants;
import com.losilegales.oprterrestres.utils.OprUtils;
import com.mashape.unirest.http.exceptions.UnirestException;

import io.swagger.v3.oas.annotations.Hidden;
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
	@Autowired
	private OprTerrestresCheckIngService oprTerrestresCheckinService;
	
	@Autowired
	private OprTerrestresCargaService oprTerrestresCargaService;
	
	@Autowired
	LogService logService;
	
	@SuppressWarnings("unchecked")
	@GetMapping("/pesoAeronave/{codigoVuelo}")
	org.json.simple.JSONObject checkSobrepesoAeronave(@PathVariable String codigoVuelo) throws UnirestException {
//		codigoVuelo = OprUtils.formatoCodigoVuelo(codigoVuelo);
		return oprTerrestresCheckIngService.sobrepasaPesoAeronave(codigoVuelo);
//		org.json.simple.JSONObject response = new org.json.simple.JSONObject();
//		if(oprTerrestresCheckIngService.sobrepasaPesoAeronave(codigoVuelo)){
//			response.put("mensaje", "Si supera la capacidad maxima");
//			response.put("supera", true);
//			return response;
//		}
//		else {
//			response.put("mensaje", "No supera la capacidad maxima");
//			response.put("supera", false);
//			return response;
//		}
	}
	
	
	@GetMapping("/logs/{fecha}")
	@Hidden
	public String verLogs(@PathVariable String fecha) {
		return logService.readLog(fecha);

	}
	
	//Para cargar los checkin y cargas en la base de datos por vuelo
	@PostMapping("/simularCheckin/{codigoVuelo}")
	@ResponseBody
	void getCheckin(@PathVariable String codigoVuelo) {
//		codigoVuelo = OprUtils.formatoCodigoVuelo(codigoVuelo);
		oprTerrestresCheckIngService.registrarDataCheckinJson(codigoVuelo);
		oprTerrestresCheckIngService.registrarDataEquipajeCheckin(codigoVuelo);
	}
	
	@GetMapping("/servicioCompras/{codigo_vuelo}")
	public List<Checkin> getCheckinPorVuelo(String codigoVuelo){
//		codigoVuelo = OprUtils.formatoCodigoVuelo(codigoVuelo);
		List<Checkin> listaCheckin = oprTerrestresCheckinService.getCheckinPorVuelo(codigoVuelo);
		return listaCheckin;
	}
	
	@GetMapping("/equipajePorVuelo/{codigo_vuelo}")
	public List<CargaDTO> getCargasPorVuelo(String codigoVuelo){
//		codigoVuelo = OprUtils.formatoCodigoVuelo(codigoVuelo);
		List<CargaDTO> listaCargas = oprTerrestresCheckinService.getCargasPorVuelo(codigoVuelo);
		return listaCargas;
	}
	
		
	@GetMapping("/aeronave/{codigoAvion}/{campo}")
	@ResponseBody
	ResponseEntity<String> getAeronavesJson(@PathVariable String codigoAvion, @PathVariable String campo ) {
		return ResponseEntity.ok(aeronavesService.obtenerAeronavesJson(codigoAvion, campo));

	}
	
	@GetMapping("/specialPassengerData/{vuelo}")
	@ResponseBody
	ResponseEntity<Optional<List<DatoEspecialPasajeroDTO>>> getDatosEspecialesPorVuelo(@PathVariable @NonNull String vuelo) {
//		vuelo = OprUtils.formatoCodigoVuelo(vuelo);
		return ResponseEntity.ok(oprTerrestresCheckIngService.getDatosEspecialesPorVuelo(vuelo));
	}
	
	@GetMapping("/pasajeros/{codigoVuelo}")
	List<org.json.simple.JSONObject> getPasajerosPorVuelo(@PathVariable String codigoVuelo){
//		codigoVuelo = OprUtils.formatoCodigoVuelo(codigoVuelo);
		return oprTerrestresCheckIngService.getPasajerosSegunVuelo(codigoVuelo);
	}

}
