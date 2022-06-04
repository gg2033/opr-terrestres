package com.losilegales.oprterrestres.utils;

public final class OprConstants {
	
	public static final String MAESTRO_AERONAVES = "1_cs5a9apFqW_oc74Nc438T-2mIfW6Q6EQ-XbvNxOEaY";
	public static final String MAESTRO_RUTAS_VUELO = "1NapgFLfxYSsVvbdbajPK4EdOYgnRgiv7mtMiKlpQL_E";
	public static final String MAESTRO_SERVICIO_VENTA = "1kuxf7pDYzR7KNrFj7lcjeutZ3gdP3XLx0PcSHq2HPN8";
	public static final String MAESTRO_AERONAVES_V2 = "https://query.wikidata.org/sparql?query=SELECT%20DISTINCT%20%3Fitem%20%3FitemLabel%20WHERE%20%7B%0A%20%20SERVICE%20wikibase%3Alabel%20%7B%20bd%3AserviceParam%20wikibase%3Alanguage%20%22es%22.%20%7D%0A%20%20%7B%0A%20%20%20%20SELECT%20DISTINCT%20%3Fitem%20WHERE%20%7B%0A%20%20%20%20%20%20%3Fitem%20p%3AP31%20%3Fstatement0.%0A%20%20%20%20%20%20%3Fstatement0%20(ps%3AP31%2F(wdt%3AP279*))%20wd%3AQ15056993.%0A%20%20%20%20%7D%0A%20%20%7D%0A%7D&format=json";
	public static final String CHECK_IN = "1ao7d3MhiUghl1AWw74FltDkA7dzcPnMXELeOLITDiJ0";
	
	public static final String CHECK_IN_JSON = "https://docs.google.com/spreadsheets/d/1jaSPOFKpTcnC3V1i3agi-1gEQ9D7AdfiGmKab0LW5Qc/gviz/tq?tqx=out:json&gid=0";//"https://docs.google.com/spreadsheets/d/1lWDgQ-a8-wPi2ob9wa-UP_LhXgJGP5-TY9VHkStIcw0/gviz/tq?tqx=out:json&gid=0";
	public static final String ENDPOINT_AERONAVES_JSON = "https://docs.google.com/spreadsheets/d/10KS3ai62T5_Y71pdkibZYHX9Vo7JKcw8IUqEymvGogg/gviz/tq?tqx=out:json&gid=0";
	public static final String BASE_ENDPOINT = "/v1/losilegales";
	public static final String CHECK_IN_EQUIPAJE = "https://docs.google.com/spreadsheets/d/1DIL6CoNqicnKlGQ9NO22Q8ylINPNt9zkijX8ddBMjq8/gviz/tq?tqx=out:json&gid=0";
	public static final String ENDPOINT_VUELOS_HISTORICOS_JSON = "https://proyecto-icarus.herokuapp.com/vuelos";

	//Urls
	public static final String ENDPOINT_RUTAS = "/rutas";
	
	//formats date
	public static final String  DATE_FORMAT = "yyyy-MM-dd HH:mm";
	
	//estados carga
	public static final String ESTADO_CARGA_EN_ESPERA = "En espera";
	public static final String ESTADO_CARGA_CARGADA = "Cargada";
	public static final String ESTADO_CARGA_DESPACHADA = "Despachada";
	

}

