package com.losilegales.oprterrestres.utils;

public final class OprConstants {
	
	public static final String MAESTRO_AERONAVES = "1_cs5a9apFqW_oc74Nc438T-2mIfW6Q6EQ-XbvNxOEaY";
	public static final String MAESTRO_RUTAS_VUELO = "1NapgFLfxYSsVvbdbajPK4EdOYgnRgiv7mtMiKlpQL_E";
	public static final String MAESTRO_SERVICIO_VENTA = "1kuxf7pDYzR7KNrFj7lcjeutZ3gdP3XLx0PcSHq2HPN8";
	public static final String MAESTRO_AERONAVES_V2 = "https://query.wikidata.org/sparql?query=SELECT%20DISTINCT%20%3Fitem%20%3FitemLabel%20WHERE%20%7B%0A%20%20SERVICE%20wikibase%3Alabel%20%7B%20bd%3AserviceParam%20wikibase%3Alanguage%20%22es%22.%20%7D%0A%20%20%7B%0A%20%20%20%20SELECT%20DISTINCT%20%3Fitem%20WHERE%20%7B%0A%20%20%20%20%20%20%3Fitem%20p%3AP31%20%3Fstatement0.%0A%20%20%20%20%20%20%3Fstatement0%20(ps%3AP31%2F(wdt%3AP279*))%20wd%3AQ15056993.%0A%20%20%20%20%7D%0A%20%20%7D%0A%7D&format=json";
	public static final String CHECK_IN = "1ao7d3MhiUghl1AWw74FltDkA7dzcPnMXELeOLITDiJ0";
	
	public static final String BASE_ENDPOINT = "/v1/losilegales";
	
	//Urls
	public static final String ENDPOINT_RUTAS = "/rutas";
	
	//formats date
	public static final String  DATE_FORMAT = "yyyy-MM-dd HH:mm";

}

