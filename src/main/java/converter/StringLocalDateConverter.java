package converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.dozer.CustomConverter;

import com.losilegales.oprterrestres.dto.UsuarioDTO;
import com.losilegales.oprterrestres.entity.Usuario;

public class StringLocalDateConverter implements CustomConverter{
//    @Override
//    public Object convert(Object dest, Object source, Class<?> arg2, Class<?> arg3) {
//        if (source == null) 
//            return null;
//        
//        if (source instanceof UsuarioDTO) {
//        	UsuarioDTO udto = (UsuarioDTO) source;
//            String fcreacion_udto = udto.getFechaCreacion();
//            String fmodificacion_udto = udto.getFechaModificacion();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//            LocalDate fcreacion_u = LocalDate.parse(fcreacion_udto, formatter);
//            LocalDate fmodificacion_u = LocalDate.parse(fmodificacion_udto, formatter);
//            return new Usuario(udto.getIdUsuario(), udto.getNombre(), udto.getApellido(), udto.getDni(), udto.getIataAeropuerto(), udto.getCodigoUsuario(), udto.getContraseña(), fcreacion_u, udto.getNombreCreador(), fmodificacion_u, udto.getNombreModificador(), udto.isActivo(), udto.getRolUsuario(), udto.getCorreo());
//
//        } else if (source instanceof Usuario) {
//        	Usuario u = (Usuario) source;
//            LocalDate fcreacion_u = u.getFechaCreacion();
//            LocalDate fmodificacion_u = u.getFechaModificacion();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//            String fcreacion_udto = fcreacion_u.format(formatter);
//            String fmodificacion_udto = fmodificacion_u.format(formatter);
//            return new UsuarioDTO(u.getIdUsuario(), u.getNombre(), u.getApellido(), u.getDni(), u.getCodigoUsuario(), u.getContraseña(), u.getCorreo(), u.getIataAeropuerto(), u.getRolUsuario(), fcreacion_udto, u.getNombreCreador(), fmodificacion_udto, u.getNombreModificador(), u.getActivo());            
//        }
//        else {
//        	return null;
//        }
//    }
	
//    @Override
//    public Object convert(Object dest, Object source, Class<?> arg2, Class<?> arg3) {
//        if (source == null) 
//            return null;
//        
//        if (source instanceof UsuarioDTO) {
//        	if(dest == null) {
//        		return new Usuario();
//        	}
//        	else {
//	        	UsuarioDTO udto = (UsuarioDTO) source;
//	            String fcreacion_udto = udto.getFechaCreacion();
//	            String fmodificacion_udto = udto.getFechaModificacion();
//	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//	            LocalDate fcreacion_u = LocalDate.parse(fcreacion_udto, formatter);
//	            LocalDate fmodificacion_u = LocalDate.parse(fmodificacion_udto, formatter);
//	            return new Usuario(udto.getIdUsuario(), udto.getNombre(), udto.getApellido(), udto.getDni(), udto.getIataAeropuerto(), udto.getCodigoUsuario(), udto.getContraseña(), fcreacion_u, udto.getNombreCreador(), fmodificacion_u, udto.getNombreModificador(), udto.isActivo(), udto.getRolUsuario(), udto.getCorreo());
//        	}
//        } else if (source instanceof Usuario) {
//        	if(dest == null) {
//        		return new UsuarioDTO();
//        	}
//        	else {
//	        	Usuario u = (Usuario) source;
//	            LocalDate fcreacion_u = u.getFechaCreacion();
//	            LocalDate fmodificacion_u = u.getFechaModificacion();
//	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//	            String fcreacion_udto = fcreacion_u.format(formatter);
//	            String fmodificacion_udto = fmodificacion_u.format(formatter);
//	            return new UsuarioDTO(u.getIdUsuario(), u.getNombre(), u.getApellido(), u.getDni(), u.getCodigoUsuario(), u.getContraseña(), u.getCorreo(), u.getIataAeropuerto(), u.getRolUsuario(), fcreacion_udto, u.getNombreCreador(), fmodificacion_udto, u.getNombreModificador(), u.getActivo());            
//        	}
//        }
//        else {
//        	return null;
//        }
//    }
	
    @Override
    public Object convert(Object destination, Object source, Class destClass, Class sourceClass) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        if(source instanceof String) {
            String sourceTime = (String) source;
            return LocalDate.parse(sourceTime, formatter);
        } else if (source instanceof LocalDate) {
            LocalDate sourceTime = (LocalDate) source;
            return sourceTime.format(formatter);
        }
        return null;
    }
}
