package com.losilegales.oprterrestres.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.losilegales.oprterrestres.config.AppConfig;
import com.losilegales.oprterrestres.dto.CheckIn.CargaDTO;
import com.losilegales.oprterrestres.entity.Carga;
import com.losilegales.oprterrestres.repository.CargaRepository;
import com.losilegales.oprterrestres.utils.OprConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(OprConstants.BASE_ENDPOINT)
@Tag(name = "Operaciones de la Carga", description = "Operaciones Terrestres de Carga")
public class OprTerrestresCargaController {
	
	@Autowired
	CargaRepository cargaRepository;
	
//	@Autowired
//	CargaRepository checkInRepository;
	
	private DozerBeanMapper mapper = AppConfig.dozerBeanMapper();
	
	@GetMapping("/carga/{codigoVuelo}")
	public List<CargaDTO> getCargaPorVuelo(String codigoVuelo){
//		List<CheckIn> checkin = checkInRepository.findByCodigoVuelo(codigoVuelo);
		List<Carga> cargas = new ArrayList<Carga>(); //checkin.getCargas();
		List<CargaDTO> cargasDTO = new ArrayList<CargaDTO>();
		for (Carga carga : cargas) {
			cargasDTO.add(mapper.map(carga, CargaDTO.class));
		}
		return cargasDTO;
		
	}
	
	@PutMapping("/carga/{estado}")
	@ResponseBody
	public boolean actualizarEstadoCarga(@RequestBody List<CargaDTO> cargas, @PathVariable String estado){
		for (CargaDTO cargaDTO : cargas) {
			Optional<Carga> carga = cargaRepository.findById(cargaDTO.getIdCarga());
			if(carga.isPresent()) {
				carga.get().setEstadoCarga(estado);
				cargaRepository.save(carga.get());
			}
		
		}
		
		return true;
		
	}

}
