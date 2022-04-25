package com.losilegales.oprterrestres.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.losilegales.oprterrestres.constants.OprConstants;
import com.losilegales.oprterrestres.entity.Usuario;
import com.losilegales.oprterrestres.repository.UsuarioRepository;
import com.losilegales.oprterrestres.service.OpTerrGoogleSheetService;

@RestController
@RequestMapping(OprConstants.BASE_ENDPOINT)
public class OprUsuariosController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	public OpTerrGoogleSheetService opTerrGoogleSheetService;

	@GetMapping("/usuarios")
	@ResponseBody
	String home() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarios.toString();
	}
}
