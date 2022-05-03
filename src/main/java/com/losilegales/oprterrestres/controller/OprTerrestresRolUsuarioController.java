package com.losilegales.oprterrestres.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.losilegales.oprterrestres.entity.RolUsuario;
import com.losilegales.oprterrestres.entity.Usuario;
import com.losilegales.oprterrestres.exceptions.RolUsuarioServiceException;
import com.losilegales.oprterrestres.exceptions.UsuarioServiceException;
import com.losilegales.oprterrestres.repository.RolUsuarioRepository;
import com.losilegales.oprterrestres.repository.UsuarioRepository;
import com.losilegales.oprterrestres.service.OpTerrGoogleSheetService;
import com.losilegales.oprterrestres.utils.OprConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(OprConstants.BASE_ENDPOINT)
@Tag(name = "Rol Usuarios", description = "Endpoints de la gestion de roles de usuarios")
public class OprTerrestresRolUsuarioController {
	
	@Autowired
	public OpTerrGoogleSheetService opTerrGoogleSheetService;
	@Autowired
	public RolUsuarioRepository rolUsuarioRepository;
	
	@GetMapping("/rolUsuarios")
	public List<RolUsuario> getRolesDeUsuario(){
		List<RolUsuario> listaRolesDeUsuarios = rolUsuarioRepository.findAll();
		return listaRolesDeUsuarios;
	}
	
	@GetMapping("/rolUsuario/{id}/")
	public RolUsuario getRolUsuarioById(@PathVariable Integer id) throws RolUsuarioServiceException{
		verificarRolUsuarioExistente(id);
		
		RolUsuario rolUsuario = rolUsuarioRepository.findById(id).get();
		return rolUsuario;
	}

	private void verificarRolUsuarioExistente(Integer id) throws RolUsuarioServiceException{
		if (! rolUsuarioRepository.existsById(id)) {
			throw new UsuarioServiceException("El rol de usuario con id " + id + " no existe.");
		}
	}

}
