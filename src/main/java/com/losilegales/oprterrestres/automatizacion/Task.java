package com.losilegales.oprterrestres.automatizacion;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.losilegales.oprterrestres.repository.CheckinRepository;
import com.losilegales.oprterrestres.service.AutomatizacionCheckinCargaService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
//14:00
//

@Component
public class Task implements Job{

	@Autowired
	AutomatizacionCheckinCargaService accservice;
	
	@Autowired
	CheckinRepository checkinRepository;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		accservice.ejecutarAutomatizacion();
		cargarDatosInsumos();
	}
	
	public void cargarDatosInsumos() {
		List<String> codigos = checkinRepository.getCodigosCheckin();
		String url = "https://operaciones-mantenimiento.herokuapp.com/Vuelo/saveInsumosCateringXVuelo/";
		for (String codigo : codigos) {
			try {
				HttpResponse <JsonNode> response = Unirest.post(url + codigo).asJson();
				System.out.println("Response status: " + response.getStatus());
			} catch (UnirestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
