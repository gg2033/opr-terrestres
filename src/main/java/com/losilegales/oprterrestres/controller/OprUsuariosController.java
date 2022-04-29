package com.losilegales.oprterrestres.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.losilegales.oprterrestres.constants.OprConstants;
import com.losilegales.oprterrestres.entity.Usuario;
import com.losilegales.oprterrestres.repository.UsuarioRepository;
import com.losilegales.oprterrestres.service.OpTerrGoogleSheetService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(OprConstants.BASE_ENDPOINT)
@Tag(name = "Usuarios", description = "Endpoints la gestion de usuarios")
public class OprUsuariosController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	public OpTerrGoogleSheetService opTerrGoogleSheetService;

	@GetMapping("/usuarios")
//	@ResponseBody
	public List<Usuario> getUsuarios() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarios;
	}
	
	@PostMapping(value = "/crear")
	public String crearUsuario(@RequestBody Usuario usuario) {
		usuarioRepository.save(usuario);
		return "Creado el usuario " + usuario.getNombre() + " " + usuario.getApellido();
	}
	
	@PutMapping(value= "/deshabilitar/{id}/")
	public String deshabilitarUsuario(@PathVariable Integer id) {
		Usuario usuarioModificado = usuarioRepository.findById(id).get();
		usuarioRepository.deshabilitarUsuario(id, false);
		return "Usuario "+ usuarioModificado.getNombre() + " " + usuarioModificado.getApellido() +" deshabilitado";
	}
	
//	@PutMapping(value= "/deshabilitar/{id}/")
//	public String deshabilitarUsuario(@PathVariable Integer id) {
//		Usuario usuarioModificado = usuarioRepository.findById(id).get();
//		usuarioModificado.setActivo(false);
//		usuarioRepository.save(usuarioModificado);
//		return "Usuario "+ usuarioModificado.getNombre() + " " + usuarioModificado.getApellido() +" deshabilitado";
//	}
	
//	@PutMapping(value= "/deshabilitar/{id}/")
//	public String modificarUsuario(@PathVariable Integer id,@RequestBody Usuario usuario) {
//		Usuario usuarioModificado = usuarioRepository.findById(id).get();
//		usuarioModificado.setActivo(usuario.isActivo());
//		return "";
//	}
}
