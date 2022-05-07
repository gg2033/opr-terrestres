package com.losilegales.oprterrestres.controller;

import java.nio.charset.StandardCharsets;
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

import com.google.common.hash.Hashing;
import com.losilegales.oprterrestres.dto.UsuarioDTO;
import com.losilegales.oprterrestres.entity.Usuario;
import com.losilegales.oprterrestres.exceptions.UsuarioServiceException;
import com.losilegales.oprterrestres.repository.RolUsuarioRepository;
import com.losilegales.oprterrestres.repository.UsuarioRepository;
import com.losilegales.oprterrestres.service.OpTerrGoogleSheetService;
import com.losilegales.oprterrestres.utils.OprConstants;

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
	public List<Usuario> getUsuarios() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarios;
	}
	
	@GetMapping("/usuario/{id}/")
	public Usuario getUsuarioById(@PathVariable Integer id) throws UsuarioServiceException{
		verificarUsuarioExistente(id);
		
		Usuario usuario = usuarioRepository.findById(id).get();
		return usuario;
	}
	
	//TODO Ver donde ubicar este metodo
	private String generarCodigoUsuario(Usuario usuario) {
		String apellido = usuario.getApellido();
		String nombre = usuario.getNombre();
		String dni = String.valueOf(usuario.getDni());
		
		//Ver si es realmente necesario el do while loop...
		String codigoUsuario;
		do {
			//Las primeras 2 letras del apellido y nombre en mayusculas junto con los ultimos 4 digitos del dni
			codigoUsuario = 
					apellido.substring(0, 2).toUpperCase() + 
					nombre.substring(0, 2).toUpperCase() + 
					dni.substring(dni.length() - 4);
			
			//Uso para la proxima iteracion (si existe)
			dni = String.valueOf(usuario.getDni() + 1);
		}
		while(usuarioRepository.usuarioConCodigo(codigoUsuario) != null);
		return codigoUsuario;
	}
	
	//TODO comentar informacion de esto y ver donde ubicarlo
	private LocalDate getFechaActual() {
		return new LocalDateAttributeConverter().convertToEntityAttribute(new Date(System.currentTimeMillis()));
	}
	
	private String HashContraseña(String c) {
		String contraseñaSha256hex = Hashing.sha256()
				  .hashString(c, StandardCharsets.UTF_8)
				  .toString();
		return contraseñaSha256hex;
	}
	
	
	//TODO ver si es bueno usar excepciones y si no cambiarlo
	@PostMapping(value = "/usuario")
	public String crearUsuario(@RequestBody Usuario usuario) throws UsuarioServiceException {
		verificarCorreo(usuario);
		verificarDatosTexto(usuario);
		verificarRolUsuarioExistente(usuario);
		verificarBodyParaPOST(usuario);
		verificarIata(usuario);
		
		usuario.setActivo(true);
		usuario.setCodigoUsuario(generarCodigoUsuario(usuario));
		usuario.setFechaCreacion(getFechaActual());
		usuario.setContraseña(HashContraseña(usuario.getContraseña()));

		usuarioRepository.save(usuario);
		return "Usuario creado correctamente. id: " + usuario.getIdUsuario() + " codigo_usuario: " + usuario.getCodigoUsuario();
	}

	private void verificarCorreo(Usuario usuario) throws UsuarioServiceException{
		 Pattern p = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
		 Matcher m = p.matcher(usuario.getCorreo());
		 if (!m.matches()) {
			 throw new UsuarioServiceException("Formato de correo incorrecto.");
		 }
		 //TODO probar esto
		 else if(usuarioRepository.usuarioConCorreo(usuario.getCorreo()) != null){
			 throw new UsuarioServiceException("El correo que ha introducido [" + usuario.getCorreo() + "] ya esta registrado.");
		 }
	}
	
	//TODO verificar si es necesario implementar una verificacion para los DNI
//	private void verificarDni(Usuario usuario) throws UsuarioServiceException
	
	private void verificarIata(Usuario usuario) throws UsuarioServiceException{
		Pattern p = Pattern.compile("[a-zA-Z]{3}");
		Matcher m = p.matcher(usuario.getIataAeropuerto());
		if(!m.matches()) {
			throw new UsuarioServiceException("Formato para iata de usuario incorrecto [Debe ser 3 caracteres de la A a la Z]");
		}
	}
	
	private void verificarUsuarioExistente(Integer id) throws UsuarioServiceException{
		if (! usuarioRepository.existsById(id)) {
			throw new UsuarioServiceException("El usuario con id " + id + " no existe.");
		}
	}

	private void verificarRolUsuarioExistente(Usuario usuario) throws UsuarioServiceException{		
		if( ! rolUsuarioRepository.existsById(usuario.getRolUsuario()) ) {
			throw new UsuarioServiceException("No existe el tipo de usuario " + usuario.getRolUsuario());
		}
	}

	private void verificarDatosTexto(Usuario usuario) throws UsuarioServiceException{
		int cantCaracteresPermitidos = 20;
		if(usuario.getApellido().length() > cantCaracteresPermitidos) {
			throw new UsuarioServiceException("Los caracteres para apellido sobrepasan los permitidos " + cantCaracteresPermitidos);
		}
		if(usuario.getNombre().length() > cantCaracteresPermitidos) {
			throw new UsuarioServiceException("Los caracteres para nombre sobrepasan los permitidos " + cantCaracteresPermitidos);
		}
		if(usuario.getContraseña().length() > 64) {
			throw new UsuarioServiceException("Los caracteres para contraseña sobrepasan los permitidos " + String.valueOf(64));
		}
		if(usuario.getNombreCreador().length() > cantCaracteresPermitidos) {
			throw new UsuarioServiceException("Los caracteres para nombre de creador sobrepasan los permitidos " + cantCaracteresPermitidos);
		}
	}
	
	private void verificarBodyParaPOST(Usuario usuario) throws UsuarioServiceException{
		if (usuario.getIdUsuario() != null) {
			throw new UsuarioServiceException("Argumento invalido en el body. No pasar id de usuario");
		}
	}

	//TODO el mensaje final debe ser si se pudo modificar el usuario.
	//TODO si puede cambiar cualquier dato, hay que verificar que e
	@PutMapping(value= "/usuario")
	public Usuario modificarUsuario(@RequestBody Usuario usuario) throws UsuarioServiceException{
		verificarUsuarioExistente(usuario.getIdUsuario());
		verificarCorreo(usuario);
		verificarDatosTexto(usuario);
		verificarRolUsuarioExistente(usuario);
		verificarIata(usuario);
		
		Usuario usuarioModificado = usuarioRepository.findById(usuario.getIdUsuario()).get();

		Mapper mapper = new DozerBeanMapper();
		mapper.map(usuario, usuarioModificado);
		usuarioModificado.setFechaModificacion(getFechaActual());
		
		usuarioRepository.save(usuarioModificado);
		return usuario;
	}
	
	//TODO verificar
	@GetMapping("/usuario_login/{id}/")
	public String logInUsuario(@PathVariable Usuario usuario) {
		String ret; 
		if(usuario.getCodigoUsuario() == null || usuario.getContraseña() == null) {
			return "Los campos no pueden estar vacíos. Verifique.";
		}
		String hashContrasena = Integer.toString(usuario.getContraseña().hashCode());
		
		usuario.setContraseña(hashContrasena);
		
		boolean flag = usuarioRepository.logInUsuario(usuario.getCodigoUsuario(), usuario.getContraseña());
		if(flag) {
			return "La verificación fue exitosa";
		}else {
			return "Usuario y/o contraseña incorrecta. Verifique.";
		}
	}
}
