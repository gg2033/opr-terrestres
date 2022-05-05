package com.losilegales.oprterrestres.controller;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.losilegales.oprterrestres.dto.VueloDTO;
import com.losilegales.oprterrestres.entity.Vuelo;
import com.losilegales.oprterrestres.service.FlightService;
import com.losilegales.oprterrestres.utils.OprConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(OprConstants.BASE_ENDPOINT)
@Tag(name = "Vuelos", description = "Endpoints la gestion de de vuelos")
public class OprTerrestresVuelosController {
	
	@Autowired
	private FlightService flightService; 
	
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/vuelo")
	public VueloDTO insertVuelo(@RequestBody VueloDTO vueloDto) {
		Vuelo vuelo = modelMapper.map(vueloDto, Vuelo.class);
		return modelMapper.map(flightService.insertFlight(vuelo), VueloDTO.class);
		
	}
	
	@PutMapping("/vuelo")
	public Optional<VueloDTO> updateVuelo(@RequestBody VueloDTO vueloDto) {
		Vuelo vuelo = modelMapper.map(vueloDto, Vuelo.class);
		
		Optional<Vuelo> response = flightService.updateFlight(vuelo);
		if(!response.isPresent()) {
			return Optional.of(modelMapper.map(response, VueloDTO.class));
		}
		return Optional.empty();
		
		
	}
}
