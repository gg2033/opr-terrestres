package com.losilegales.oprterrestres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.losilegales.oprterrestres.repository.CargaRepository;

@Service
public class OprTerrestresCargaService {
	
	@Autowired
	private CargaRepository cargaRepository;
	
	public boolean cambiarEstadoCarga(String codigoVuelo) {
		
		return true;
		
	}

}
