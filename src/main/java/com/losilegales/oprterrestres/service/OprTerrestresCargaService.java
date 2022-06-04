package com.losilegales.oprterrestres.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.losilegales.oprterrestres.entity.Carga;
import com.losilegales.oprterrestres.repository.CargaRepository;
import com.losilegales.oprterrestres.utils.OprConstants;

@Service
public class OprTerrestresCargaService {
	
	@Autowired
	private CargaRepository cargaRepository;
	@Transactional
	public boolean cambiarEstadoCargasVuelo(String codigoVuelo, String estado) {
		
		List<Carga> cargasPorVuelo = cargaRepository.cargasPorVuelo(codigoVuelo);
		cargaRepository.cambioEstadoCargasVuelo(codigoVuelo, estado);
			
		return true;
		
	}

}
