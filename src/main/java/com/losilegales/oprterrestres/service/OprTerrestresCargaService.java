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
	public boolean cambiarEstadoCargaDespachada(String codigoVuelo) {
		
		List<Carga> cargasPorVuelo = cargaRepository.findByCodigoVuelo(codigoVuelo);
		cargaRepository.cambioEstadoCargasDespachada(codigoVuelo, OprConstants.ESTADO_CARGA_DESPACHADA);
			
		return true;
		
	}

}
