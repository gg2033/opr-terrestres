package com.losilegales.oprterrestres.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.losilegales.oprterrestres.dto.AeroNaves.AeroNavesResponse;

@Service
public class AeroNavesService {
	
	@Autowired
	@Qualifier("simpleRestTemplate")
	private RestTemplate simpleRestTemplate;
	
	public AeroNavesResponse obtenerAeronaves(){
		AeroNavesResponse aeronavesResponse = new AeroNavesResponse();
		final String baseUrl = "https://tinyurl.com/familiasdeaeronaves";
		URI uri;
		try {
			uri = new URI(baseUrl);
			ResponseEntity<AeroNavesResponse> result = simpleRestTemplate.getForEntity(uri, AeroNavesResponse.class);
			return result.getBody();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return aeronavesResponse;
		
	}
	

}
