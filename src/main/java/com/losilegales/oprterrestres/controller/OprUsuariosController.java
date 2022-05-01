package com.losilegales.oprterrestres.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
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

import converter.LocalDateAttributeConverter;
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
	
	//TODO Ver donde ubicar este metodo
	private String generarCodigoUsuario(Usuario usuario) {
		//TODO Falta una forma de agregar el id del usuario al codigo de usuario para que sea unico
		String apellido = usuario.getApellido();
		String nombre = usuario.getNombre();
		
		String codigoUsuario = 
				nombre.substring(0, 3) + 
				apellido.substring(0, 3) +
				apellido.substring(apellido.length() - 2);
		
		return codigoUsuario;
	}
	
	//TODO comentar informacion de esto y ver donde ubicarlo
	private LocalDate getFechaActual() {
		return new LocalDateAttributeConverter().convertToEntityAttribute(new Date(System.currentTimeMillis()));
	}
	
	//TODO Agregar check para largo de los valores de usuario (nombre, apellido, contraseña, creado por)
	//TODO Agregar check para el tipo de usuario (verificar si el tipo existe en la base)
	@PostMapping(value = "/usuario")
	public Usuario crearUsuario(@RequestBody Usuario usuario) {
//		verificarDatosTexto(usuario);
//		verificarTipoUsuarioExistente(usuario);
		usuario.setActivo(true);
		usuario.setCodigoUsuario(generarCodigoUsuario(usuario));
		usuario.setFechaCreacion(getFechaActual());

		usuarioRepository.save(usuario);
		return usuario;
	}

	//patch que funciona
//	@PutMapping(value= "/usuario/{id}/")
//	public String deshabilitarUsuario(@PathVariable Integer id) {
//		Usuario usuarioModificado = usuarioRepository.findById(id).get();
//		usuarioRepository.deshabilitarUsuario(id, false);
//		return "Usuario "+ usuarioModificado.getNombre() + " " + usuarioModificado.getApellido() +" deshabilitado";
//	}
	
//	@PutMapping(value= "/deshabilitar/{id}/")
//	public String deshabilitarUsuario(@PathVariable Integer id) {
//		Usuario usuarioModificado = usuarioRepository.findById(id).get();
//		usuarioModificado.setActivo(false);
//		usuarioRepository.save(usuarioModificado);
//		return "Usuario "+ usuarioModificado.getNombre() + " " + usuarioModificado.getApellido() +" deshabilitado";
//	}

	@PutMapping(value= "/update_usuario")
	public Usuario modificarUsuario(@RequestBody Usuario usuario) {
		//TODO agregar fecha de modificacion
		//TODO agregar verificacion a tipo de usuario 
		Usuario usuarioModificado = usuarioRepository.findById(usuario.getIdUsuario()).get();
		
		usuarioModificado.setApellido(usuario.getApellido());
		usuarioModificado.setNombre(usuario.getNombre());
		usuarioModificado.setContraseña(usuario.getContraseña());
		usuarioModificado.setCodigoUsuario(usuario.getCodigoUsuario());
		usuarioModificado.setNombreModificador(usuario.getNombreModificador());
		usuarioModificado.setFechaModificacion(getFechaActual());
		usuarioModificado.setActivo(usuario.getActivo());
		usuarioModificado.setTipoUsuario(usuario.getTipoUsuario());
		
		usuarioRepository.save(usuarioModificado);
		return usuario;
	}
}
