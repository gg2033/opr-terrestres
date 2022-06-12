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
import com.losilegales.oprterrestres.exceptions.VerificacionComisariatoServiceException;
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
	private OprTerrestresComisariatoService comisariatoService;
	
	//TODO Agregar throws de VerificacionComisariatoServiceException
	@GetMapping("/comisariato/{codigo}")
	public List<VerificacionComisariato> listaDeVerificacionPorVuelo(@PathVariable String codigo) throws VerificacionComisariatoServiceException {
		//Verificaciones...
		verificarCodigoExistenteGET(codigo);
		
		return comisariatoService.listaDeVerificacionesPorVuelo(codigo);
	}
	
	//TODO Agregar throws de VerificacionComisariatoServiceException
	@ResponseBody
	@PostMapping("/comisariato")
	public VerificacionComisariato persistirVerificacion(@RequestBody VerificacionComisariato vc) throws VerificacionComisariatoServiceException {
		//Verificaciones...
		verificarDatosNullPOST(vc);
		verificarCodigoExistentePOST(vc.getCodigo());
		
		return comisariatoService.persistirVerificacion(vc);
	}
	
	
	private void verificarCodigoExistenteGET(String codigo) throws VerificacionComisariatoServiceException {
		if (!comisariatoService.existeVerificacacionPorVuelo(codigo)) {
			throw new VerificacionComisariatoServiceException("La validacion de comisariato con el codigo ingresado [" + codigo + "] no existe.");
		}
	}
	
	private void verificarCodigoExistentePOST(String codigo) throws VerificacionComisariatoServiceException {
		if (comisariatoService.existeVerificacacionPorVuelo(codigo)) {
			throw new VerificacionComisariatoServiceException("La validacion de comisariato con el codigo ingresado [" + codigo + "] ya existe.");
		}
	}
	
	private void verificarDatosNullPOST(VerificacionComisariato vc) throws VerificacionComisariatoServiceException {
		if (vc.getCodigo() == null ||
			vc.getVerificacionCartillasBolsas() == null||
			vc.getVerificacionElementosSeguridad() == null ||
			vc.getVerificacionInsumos() == null ||
			vc.getVerificacionInterna() == null ||
			vc.getVerificacionLimpieza() == null
			) {
			throw new VerificacionComisariatoServiceException("Datos no rellenados en el body");
		}
	}

}
