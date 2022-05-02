package com.losilegales.oprterrestres.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
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
import com.losilegales.oprterrestres.repository.RolUsuarioRepository;
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
	@Autowired
	public RolUsuarioRepository rolUsuarioRepository;

	//TODO Ver como usar el DTO para usuario
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
	
	//TODO ver si es bueno usar excepciones y si no cambiarlo
	@PostMapping(value = "/usuario")
	public Usuario crearUsuario(@RequestBody Usuario usuario) throws IllegalArgumentException {
		verificarCorreo(usuario);
		verificarDatosTexto(usuario);
		verificarRolUsuarioExistente(usuario);
		
		usuario.setActivo(true);
		usuario.setCodigoUsuario(generarCodigoUsuario(usuario));
		usuario.setFechaCreacion(getFechaActual());

		usuarioRepository.save(usuario);
		return usuario;
	}

	//TODO agregar verificacion correo
	private void verificarCorreo(Usuario usuario) throws IllegalArgumentException{
		 Pattern p = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
		 Matcher m = p.matcher(usuario.getCorreo());
		 if (!m.matches()) {
			 throw new IllegalArgumentException("Formato de correo incorrecto.");
		 }
	}

	//TODO verificar que este funciona
	private void verificarRolUsuarioExistente(Usuario usuario) throws IllegalArgumentException{		
//		if(tipoUsuarioRepository.getById(usuario.getRolUsuario())) {
		if( ! rolUsuarioRepository.existsById(usuario.getRolUsuario()) ) {
			throw new IllegalArgumentException("No existe el tipo de usuario " + usuario.getRolUsuario());
		}
	}

	private void verificarDatosTexto(Usuario usuario) throws IllegalArgumentException{
		int cantCaracteresPermitidos = 100;
		if(usuario.getApellido().length() > cantCaracteresPermitidos) {
			throw new IllegalArgumentException("Los caracteres para apellido sobrepasan los permitidos " + cantCaracteresPermitidos);
		}
		if(usuario.getNombre().length() > cantCaracteresPermitidos) {
			throw new IllegalArgumentException("Los caracteres para nombre sobrepasan los permitidos " + cantCaracteresPermitidos);
		}
		if(usuario.getContraseña().length() > cantCaracteresPermitidos) {
			throw new IllegalArgumentException("Los caracteres para contraseña sobrepasan los permitidos " + cantCaracteresPermitidos);
		}
		if(usuario.getNombreCreador().length() > cantCaracteresPermitidos) {
			throw new IllegalArgumentException("Los caracteres para nombre de creador sobrepasan los permitidos " + cantCaracteresPermitidos);
		}
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

	//TODO Ver si es necesaria la verificacion con excepciones
	@PutMapping(value= "/usuario")
	public Usuario modificarUsuario(@RequestBody Usuario usuario) throws IllegalArgumentException{
		verificarCorreo(usuario);
		verificarDatosTexto(usuario);
		verificarRolUsuarioExistente(usuario);
		
		Usuario usuarioModificado = usuarioRepository.findById(usuario.getIdUsuario()).get();

		Mapper mapper = new DozerBeanMapper();
		mapper.map(usuario, usuarioModificado);
		
		usuarioModificado.setFechaModificacion(getFechaActual());
		
//		usuarioModificado.setApellido(usuario.getApellido());
//		usuarioModificado.setNombre(usuario.getNombre());
//		usuarioModificado.setContraseña(usuario.getContraseña());
//		usuarioModificado.setCodigoUsuario(usuario.getCodigoUsuario());
//		usuarioModificado.setNombreCreador(usuario.getNombreCreador());
//		usuarioModificado.setFechaCreacion(usuario.getFechaCreacion());
//		usuarioModificado.setNombreModificador(usuario.getNombreModificador());
//		usuarioModificado.setFechaModificacion(getFechaActual());
//		usuarioModificado.setActivo(usuario.getActivo());
//		usuarioModificado.setTipoUsuario(usuario.getTipoUsuario());
		
		usuarioRepository.save(usuarioModificado);
		return usuario;
	}
}
