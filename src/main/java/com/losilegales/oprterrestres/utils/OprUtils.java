package com.losilegales.oprterrestres.utils;

import java.sql.Date;
import java.time.LocalDate;

import converter.LocalDateAttributeConverter;

public final class OprUtils {
	
	public static String formatoCodigoVuelo(String codigoVuelo) {
		StringBuilder sb = new StringBuilder(codigoVuelo);
		for(int i=0; i < codigoVuelo.length(); i++) {
	         if(Character.isDigit(codigoVuelo.charAt(i))) {
	        	 sb.insert(i, " ");
	        	 break;
	         }
		}
		return sb.toString();
	}
	
	public static LocalDate getFechaActual() {
		return new LocalDateAttributeConverter().convertToEntityAttribute(new Date(System.currentTimeMillis()));
	}

    
  
	

}
