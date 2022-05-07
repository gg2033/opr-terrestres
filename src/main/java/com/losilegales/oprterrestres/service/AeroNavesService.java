package com.losilegales.oprterrestres.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.losilegales.oprterrestres.dto.AeroNaves.AeroNavesResponse;
import com.losilegales.oprterrestres.utils.OprConstants;

import Excel.ExcelResponse;
import Excel.Row;

@Service
public class AeroNavesService {

	@Autowired
	@Qualifier("simpleRestTemplate")
	private RestTemplate simpleRestTemplate;

	public String obtenerAeronavesJson(String codigoAvion, String campo) {
		ExcelResponse result = new ExcelResponse();
		URI uri;

		try {
			uri = new URI(OprConstants.ENDPOINT_AERONAVES_JSON);

			String json = simpleRestTemplate.getForEntity(uri, String.class).getBody();
			json = json.substring(47);
			json = json.substring(0, json.length() - 2);

			ObjectMapper objectMapper = new ObjectMapper();

			result = objectMapper.readValue(json, ExcelResponse.class);
			Row row = (Row) result.getTable().getRows().stream().filter(
					e -> e.getC().get(0).getV().equals(codigoAvion)).findFirst().get();
			//Ojo si nos ponene un excel con una fila tipo columnas agregar esta linea.
//			int indice = result.getTable().getCols().stream().map(e -> e.getLabel()).collect(Collectors.toList()).indexOf(campo);
			
			int indice = result.getTable().getRows().get(0).getC().stream().map(e -> e.getV()).collect(Collectors.toList()).indexOf(campo);
			String value = row.getC().get(indice).getV().toString();
			return value;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

}
