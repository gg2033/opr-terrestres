package com.losilegales.oprterrestres.controller;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.hash.Hashing;
import com.losilegales.oprterrestres.config.AppConfig;
import com.losilegales.oprterrestres.dto.UsuarioDTO;
import com.losilegales.oprterrestres.entity.Usuario;
import com.losilegales.oprterrestres.exceptions.UsuarioServiceException;
import com.losilegales.oprterrestres.repository.RolUsuarioRepository;
import com.losilegales.oprterrestres.repository.UsuarioRepository;
import com.losilegales.oprterrestres.service.OpTerrGoogleSheetService;
import com.losilegales.oprterrestres.utils.OprConstants;

import converter.LocalDateAndStringConverter;
import converter.LocalDateAttributeConverter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(OprConstants.BASE_ENDPOINT)
@Tag(name = "Usuarios", description = "Endpoints la gestion de usuarios")
public class OprUsuariosController {
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class UsuarioResponseTransfer {
	    private String text;
	    private Integer idUsuario;
	    private String codigoUsuario;
	}

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	public OpTerrGoogleSheetService opTerrGoogleSheetService;
	@Autowired
	public RolUsuarioRepository rolUsuarioRepository;
	
	private DozerBeanMapper mapper = AppConfig.dozerBeanMapper();
	
	//TODO Ver como usar el DTO para usuario
	@GetMapping("/usuarios")
	public List<UsuarioDTO> getUsuarios() {	
		List<Usuario> usuarios = usuarioRepository.findAll();
		List<UsuarioDTO> usuariosdto = new ArrayList<UsuarioDTO>(usuarios.size());
		for (Usuario u : usuarios) {
			UsuarioDTO udto = mapper.map(u, UsuarioDTO.class);
			setFechasUtoUDTO(u, udto);
		    usuariosdto.add(udto);
		}
		return usuariosdto;
	}
	
	private void setFechasUtoUDTO(Usuario u, UsuarioDTO udto) {
		udto.setFechaCreacion(LocalDateAndStringConverter.localDateToString(u.getFechaCreacion()));
		udto.setFechaModificacion(LocalDateAndStringConverter.localDateToString(u.getFechaModificacion()));
	}
	
	private void setFechasUDTOtoU(UsuarioDTO udto, Usuario u) {
		u.setFechaCreacion(LocalDateAndStringConverter.stringToLocalDate(udto.getFechaCreacion()));
		u.setFechaModificacion(LocalDateAndStringConverter.stringToLocalDate(udto.getFechaModificacion()));
	}
	
	@GetMapping("/usuario/{id}/")
	public UsuarioDTO getUsuarioById(@PathVariable Integer id) throws UsuarioServiceException{
		verificarUsuarioExistente(id);
		
		Usuario usuario = usuarioRepository.findById(id).get();
		UsuarioDTO udto = mapper.map(usuario, UsuarioDTO.class);
		setFechasUtoUDTO(usuario, udto);
		return udto;
	}
	
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
			dni = String.valueOf(Integer.parseInt(dni) + 1);
		}
		while(usuarioRepository.usuarioConCodigo(codigoUsuario) != null);
		return codigoUsuario;
	}
	
	//TODO comentar informacion de esto y ver donde ubicarlo
	private LocalDate getFechaActual() {
		return new LocalDateAttributeConverter().convertToEntityAttribute(new Date(System.currentTimeMillis()));
	}
	
	private String HashContrase??a(String c) {
		String contrase??aSha256hex = Hashing.sha256()
				  .hashString(c, StandardCharsets.UTF_8)
				  .toString();
		return contrase??aSha256hex;
	}
	
	@ResponseBody
	//TODO ver si es bueno usar excepciones y si no cambiarlo
	@PostMapping(value = "/usuario")
	public UsuarioResponseTransfer crearUsuario(@RequestBody UsuarioDTO usuariodto) throws UsuarioServiceException {
		Usuario usuario = mapper.map(usuariodto, Usuario.class);
		setFechasUDTOtoU(usuariodto, usuario);
		
		verificarCorreo(usuario);
		verificarDatosTexto(usuario);
		verificarRolUsuarioExistente(usuario);
		verificarBodyParaPOST(usuario);
		verificarIata(usuario);
		
		usuario.setActivo(true);
		usuario.setCodigoUsuario(generarCodigoUsuario(usuario));
		usuario.setFechaCreacion(getFechaActual());
		usuario.setContrase??a(HashContrase??a(usuario.getContrase??a()));

		usuarioRepository.save(usuario);
		return new UsuarioResponseTransfer("Usuario creado correctamente", usuario.getIdUsuario(), usuario.getCodigoUsuario());
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
	
	private void verificarCorreoPUT(Usuario usuario) throws UsuarioServiceException{
		 Pattern p = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
		 Matcher m = p.matcher(usuario.getCorreo());
		 if (!m.matches()) {
			 throw new UsuarioServiceException("Formato de correo incorrecto.");
		 }
		 //TODO Cambiarlo mas adelante
		 //Se lee: Si el usuario con ese correo existe y ademas no es el mismo usuario que te estoy pasando, tira el error.
		 else if(usuarioRepository.usuarioConCorreo(usuario.getCorreo()) != null && !usuarioRepository.usuarioConCorreo(usuario.getCorreo()).equals(usuario)){
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
		if(usuario.getContrase??a().length() > 64) {
			throw new UsuarioServiceException("Los caracteres para contrase??a sobrepasan los permitidos " + String.valueOf(64));
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
	@ResponseBody
	@CrossOrigin
	@PutMapping(value= "/usuario")
	public UsuarioResponseTransfer modificarUsuario(@RequestBody UsuarioDTO usuariodto) throws UsuarioServiceException{
		Usuario usuario = mapper.map(usuariodto, Usuario.class);
		setFechasUDTOtoU(usuariodto, usuario);
		
		verificarUsuarioExistente(usuario.getIdUsuario());
		verificarCorreoPUT(usuario);
		verificarDatosTexto(usuario);
		verificarRolUsuarioExistente(usuario);
		verificarIata(usuario);
		
		Usuario usuarioModificado = usuarioRepository.findById(usuario.getIdUsuario()).get();

		mapper.map(usuario, usuarioModificado);
		//TODO Creo que ya no es necesario esta linea de abajo... verificar
		usuarioModificado.setFechaModificacion(getFechaActual());
		usuarioModificado.setContrase??a(HashContrase??a(usuario.getContrase??a()));
		
		usuarioRepository.save(usuarioModificado);
		return new UsuarioResponseTransfer("Usuario ha sido modificado correctamente.", usuario.getIdUsuario(), usuario.getCodigoUsuario());
	}
	
//	//TODO verificar
//	@GetMapping("/usuario_login/{id}/")
//	public String logInUsuario(@PathVariable Usuario usuario) {
//		String ret; 
//		if(usuario.getCodigoUsuario() == null || usuario.getContrase??a() == null) {
//			return "Los campos no pueden estar vac??os. Verifique.";
//		}
//		String hashContrasena = Integer.toString(usuario.getContrase??a().hashCode());
//		
//		usuario.setContrase??a(hashContrasena);
//		
//		boolean flag = usuarioRepository.logInUsuario(usuario.getCodigoUsuario(), usuario.getContrase??a());
//		if(flag) {
//			return "La verificaci??n fue exitosa";
//		}else {
//			return "Usuario y/o contrase??a incorrecta. Verifique.";
//		}
//	}
	
	//comentario
	@ResponseBody
	@GetMapping("/usuarioLogin")
	public UsuarioResponseTransfer logInUsuario(@RequestParam("codigoUsuario") String codigoUsuario, @RequestParam("contrase??a") String contrase??a) {
		Usuario ulogin = usuarioRepository.usuarioLogin(codigoUsuario, HashContrase??a(contrase??a));
		UsuarioResponseTransfer urf = new UsuarioResponseTransfer();
		if(ulogin != null && ulogin.getActivo()) {
			urf.setText("Login exitoso.");
			urf.setIdUsuario(ulogin.getIdUsuario());
			urf.setCodigoUsuario(ulogin.getCodigoUsuario());
			return urf;
		}
		else {
			urf.setText("Verifique los datos pasados.");
			return urf;
		}
	}
}
