package com.losilegales.oprterrestres.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.losilegales.oprterrestres.entity.Permiso;
import com.losilegales.oprterrestres.exceptions.PermisoServiceException;
import com.losilegales.oprterrestres.exceptions.RolUsuarioServiceException;
import com.losilegales.oprterrestres.exceptions.UsuarioServiceException;
import com.losilegales.oprterrestres.repository.PermisoRepository;
import com.losilegales.oprterrestres.repository.RolUsuarioRepository;
import com.losilegales.oprterrestres.service.OpTerrGoogleSheetService;
import com.losilegales.oprterrestres.utils.OprConstants;

import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping(OprConstants.BASE_ENDPOINT)
@Tag(name = "Permisos", description = "Endpoints la gestion de permisos de usuario")
public class OprTerrestresPermisosController {
	
	
	@Autowired
	public OpTerrGoogleSheetService opTerrGoogleSheetService;
	@Autowired
	public PermisoRepository permisosRepository;
	
	@GetMapping("/permisos")
	public List<Permiso> getPermisos(){
		List<Permiso> listaPermisos = permisosRepository.findAll();
		return listaPermisos;
	}
	
	@GetMapping("/permiso/{id}/")
	public Permiso getPermisoById(@PathVariable Integer id) throws PermisoServiceException{
		verificarPermisoExistente(id);
		
		Permiso permiso = permisosRepository.findById(id).get();
		return permiso;
	}

	private void verificarPermisoExistente(Integer id) throws PermisoServiceException{
		if (! permisosRepository.existsById(id)) {
			throw new PermisoServiceException("El permiso con id " + id + " no existe.");
		}
	}

}
