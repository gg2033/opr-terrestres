package com.losilegales.oprterrestres.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.losilegales.oprterrestres.dto.UsuarioDTO;
import com.losilegales.oprterrestres.entity.Carga;
import com.losilegales.oprterrestres.entity.Usuario;
import com.losilegales.oprterrestres.repository.CargaRepository;
import com.losilegales.oprterrestres.utils.OprConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(OprConstants.BASE_ENDPOINT)
@Tag(name = "Cargas", description = "Endpoints de cargas")
public class OprTerrestresCargasController {

	
	@Autowired
	CargaRepository cargaRepository;
	
	
	//TODO implementar DTO
	@GetMapping("/cargas")
	public List<Carga> getCargas() {	
		List<Carga> cargas = cargaRepository.findAll();
		return cargas;
	}
	
	//TODO implementar DTO
	@GetMapping("/cargas/{codigo_vuelo}")
	public List<Carga> getCargasPorVuelo(@PathVariable String codigo_vuelo) {	
		List<Carga> cargas = cargaRepository.cargasPorVuelo(codigo_vuelo);
		return cargas;
	}

}
