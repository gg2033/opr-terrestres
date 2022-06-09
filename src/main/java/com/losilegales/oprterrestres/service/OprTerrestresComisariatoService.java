package com.losilegales.oprterrestres.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.losilegales.oprterrestres.entity.VerificacionComisariato;
import com.losilegales.oprterrestres.repository.VerificacionComisariatoRepository;
import com.losilegales.oprterrestres.utils.OprUtils;

public class OprTerrestresComisariatoService {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(OprTerrestresComisariatoService.class);
	
	@Autowired
	private VerificacionComisariatoRepository verificacionComisariatoRepository;
	
	
	public List<VerificacionComisariato> listaDeVerificacionesPorVuelo(String codigo){
		return verificacionComisariatoRepository.verificacionesPorVuelo(codigo);
	}
	
	public VerificacionComisariato persistirVerificacion(VerificacionComisariato vc) {
		vc.setNombreCreador("juan");
		vc.setFechaCreacion(OprUtils.getFechaActual());
		vc.setActivo(true);
		vc = verificacionComisariatoRepository.save(vc);
		
		return vc;
	}

}
