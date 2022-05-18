package com.losilegales.oprterrestres.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.losilegales.oprterrestres.entity.Vuelo;
import com.losilegales.oprterrestres.repository.VueloRepository;
@Service
public class FlightService {
	
	@Autowired
	private VueloRepository flightRepository;
	
	//comentario
	public List<Vuelo> getVuelos(){
		return flightRepository.findAll();
	}
	
	public Vuelo insertFlight(Vuelo vueloRequest) {
		vueloRequest.setCreado(new Date());
		vueloRequest.setCreadoPor(vueloRequest.getCreadoPor());
		vueloRequest.setActivo(true);
		return flightRepository.save(vueloRequest);
		
	}
	
	public Optional<Vuelo> updateFlight(Vuelo vueloRequest) {
		
		Optional<Vuelo> vuelo = flightRepository.findById(vueloRequest.getIdVuelo());
		if(!vuelo.isPresent()) {
			//campos que se pueden modificar
			vuelo.get().setActivo(vuelo.get().getActivo());
			vuelo.get().setModificado(LocalDateTime.now());
			vuelo.get().setModificadoPor(vueloRequest.getModificadoPor());
			return Optional.of(flightRepository.save(vuelo.get()));
		}
		return Optional.empty();
		
	}
	


}
