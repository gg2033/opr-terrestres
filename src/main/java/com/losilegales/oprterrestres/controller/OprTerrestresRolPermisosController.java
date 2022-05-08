package com.losilegales.oprterrestres.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.losilegales.oprterrestres.entity.RolPermiso;
import com.losilegales.oprterrestres.entity.RolUsuario;
import com.losilegales.oprterrestres.exceptions.RolPermisoServiceException;
import com.losilegales.oprterrestres.exceptions.RolUsuarioServiceException;
import com.losilegales.oprterrestres.exceptions.UsuarioServiceException;
import com.losilegales.oprterrestres.repository.RolPermisoRepository;
import com.losilegales.oprterrestres.repository.RolUsuarioRepository;
import com.losilegales.oprterrestres.service.OpTerrGoogleSheetService;
import com.losilegales.oprterrestres.utils.OprConstants;

import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping(OprConstants.BASE_ENDPOINT)
@Tag(name = "Permisos de Rol", description = "Endpoints la gestion de permisos de un rol")
public class OprTerrestresRolPermisosController {
	
	
	@Autowired
	public OpTerrGoogleSheetService opTerrGoogleSheetService;
	@Autowired
	public RolPermisoRepository rolPermisosRepository;
	
	@GetMapping("/rolPermisos")
	public List<RolPermiso> getPermisos(){
		List<RolPermiso> listaPermisos = rolPermisosRepository.findAll();
		return listaPermisos;
	}
	
	@GetMapping("/rolPermiso/{id}/")
	public RolPermiso getRolPermisoById(@PathVariable Integer id) throws RolPermisoServiceException{
		verificarRolPermisoExistente(id);
		
		RolPermiso rolPermiso = rolPermisosRepository.findById(id).get();
		return rolPermiso;
	}

	private void verificarRolPermisoExistente(Integer id) throws RolPermisoServiceException{
		if (! rolPermisosRepository.existsById(id)) {
			throw new RolPermisoServiceException("El permiso de rol con id " + id + " no existe.");
		}
	}

}
