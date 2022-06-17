package com.losilegales.oprterrestres.automatizacion;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.losilegales.oprterrestres.service.AutomatizacionCheckinCargaService;

public class Task implements Job{

	@Autowired
	AutomatizacionCheckinCargaService accservice;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		accservice.ejecutarAutomatizacion();
	}

}
