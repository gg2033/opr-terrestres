package com.losilegales.oprterrestres.service;

import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.losilegales.oprterrestres.config.AppConfig;
import com.losilegales.oprterrestres.dto.CargaDTO;
import com.losilegales.oprterrestres.dto.CheckInDTO;
import com.losilegales.oprterrestres.dto.PasajeroDTO;
import com.losilegales.oprterrestres.dto.TipoCarga;
import com.losilegales.oprterrestres.dto.VueloHistoricoDTO;
import com.losilegales.oprterrestres.entity.Vuelo;
import com.losilegales.oprterrestres.entity.VueloHistorico;
import com.losilegales.oprterrestres.repository.VueloHistoricoRepository;
import com.losilegales.oprterrestres.utils.OprConstants;

import Excel.ExcelResponse;

public class VueloHistoricoService {
	
	@Autowired
	@Qualifier("simpleRestTemplate")
	private RestTemplate simpleRestTemplate;
	
	private DozerBeanMapper mapper = AppConfig.dozerBeanMapper();
	
	@Autowired
	private VueloHistoricoRepository vueloHistoricoRepository;
	
	public void getDataVueloHistoricoJson() {
		URI uri;
		try {
			uri = new URI(OprConstants.ENDPOINT_VUELOS_HISTORICOS_JSON);
			@SuppressWarnings("unchecked")
			List<VueloHistoricoDTO> json = (List<VueloHistoricoDTO>) simpleRestTemplate.getForEntity(uri, VueloHistoricoDTO.class).getBody();

			for (VueloHistoricoDTO vhDTO : json) {
				VueloHistorico vh = new VueloHistorico();
				vh = mapper.map(vhDTO, VueloHistorico.class);
				vueloHistoricoRepository.save(vh);	
			}
		}
		catch (Exception e ) {
			e.printStackTrace();
		}
	}
		
	
}
