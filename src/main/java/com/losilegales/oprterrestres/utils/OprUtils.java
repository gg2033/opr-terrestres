package com.losilegales.oprterrestres.utils;

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

    
  
	

}
