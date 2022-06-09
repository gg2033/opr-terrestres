package com.losilegales.oprterrestres.controller;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.losilegales.oprterrestres.entity.VerificacionComisariato;
import com.losilegales.oprterrestres.repository.VerificacionComisariatoRepository;
import com.losilegales.oprterrestres.service.OprTerrestresComisariatoService;
import com.losilegales.oprterrestres.utils.OprConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(OprConstants.BASE_ENDPOINT)
@Tag(name = "Comisariato", description = "Endpoints la gestion de validaciones del comisariato")
public class OprTerrestresComisariatoController {
	
	//TODO agregar mapper cuando se tengan DTOs
//	private DozerBeanMapper mapper = new DozerBeanMapper();
	
	@Autowired
	private OprTerrestresComisariatoService comisariatoService = new OprTerrestresComisariatoService();
	
	//TODO agregar verificaciones en el controller, no en el service / verificar codigo de vuelo existente
	@GetMapping("/comisariato/{codigo}")
	public List<VerificacionComisariato> listaDeVerificacionPorVuelo(@PathVariable String codigo) {
		//Verificaciones...
		return comisariatoService.listaDeVerificacionesPorVuelo(codigo);
	}
	
	//TODO agregar verificacion de codigo existente y verificar datos no null
	@ResponseBody
	@PostMapping("/comisariato")
	public VerificacionComisariato persistirVerificacion(@RequestBody VerificacionComisariato vc) {
		//Verificaciones...
		return comisariatoService.persistirVerificacion(vc);
	}

}
