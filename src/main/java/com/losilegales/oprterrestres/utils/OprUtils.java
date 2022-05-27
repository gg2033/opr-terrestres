package com.losilegales.oprterrestres.utils;

public final class OprUtils {
	
//	
//	public static final
//    LocalDateTime currentLocalDateTime = LocalDateTime.now();
//    
//    // Create DateTimeFormatter instance with specified format
//    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//
//    // Format LocalDateTime to String
//    String formattedDateTime = currentLocalDateTime.format(dateTimeFormatter);
//
//    System.out.println("Formatted LocalDateTime in String format : " + formattedDateTime);
//	
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
