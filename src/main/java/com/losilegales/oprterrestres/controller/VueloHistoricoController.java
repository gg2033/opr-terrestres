package com.losilegales.oprterrestres.controller;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.losilegales.oprterrestres.config.AppConfig;
import com.losilegales.oprterrestres.dto.UsuarioDTO;
import com.losilegales.oprterrestres.dto.VueloHistoricoDTO;
import com.losilegales.oprterrestres.entity.Usuario;
import com.losilegales.oprterrestres.entity.VueloHistorico;
import com.losilegales.oprterrestres.repository.UsuarioRepository;
import com.losilegales.oprterrestres.repository.VueloHistoricoRepository;
import com.losilegales.oprterrestres.service.VueloHistoricoService;
import com.losilegales.oprterrestres.utils.OprConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(OprConstants.BASE_ENDPOINT)
@Tag(name = "VueloHistorico", description = "Endpoints Vuelos Historicos")
public class VueloHistoricoController {

	@Autowired
	private VueloHistoricoRepository vueloHistoricoRepository;
	
	DozerBeanMapper mapper = AppConfig.dozerBeanMapper();
	
	VueloHistoricoService vueloHistoricoService;

	//TODO Ver como usar el DTO para usuario
	@GetMapping("/vueloshistoricos")
	public List<VueloHistoricoDTO> getVuelosHistoricos() {	
		vueloHistoricoService.getDataVueloHistoricoJson();
		
		List<VueloHistorico> vh = vueloHistoricoRepository.findAll();
		List<VueloHistoricoDTO> vhDTO = new ArrayList<VueloHistoricoDTO>(vh.size());
		for (VueloHistorico vueloHistorico : vh) {
			VueloHistoricoDTO vueloHistoricoDTO = mapper.map(vueloHistorico, VueloHistoricoDTO.class);
		    vhDTO.add(vueloHistoricoDTO);
		}
		return vhDTO;
	}
	
}
