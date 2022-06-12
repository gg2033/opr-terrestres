package com.losilegales.oprterrestres.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.losilegales.oprterrestres.entity.CheckinTest;
import com.losilegales.oprterrestres.service.AutomatizacionCheckinCargaService;
import com.losilegales.oprterrestres.utils.DatosGeneradorCheckin;
import com.losilegales.oprterrestres.utils.OprConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(OprConstants.BASE_ENDPOINT)
@Tag(name = "Operaciones de Automatizacion", description = "Operaciones de test automatizacion")
public class OprTerrestresAutomatizacionTestController {
	
	@Autowired
	private AutomatizacionCheckinCargaService autocheckinservice;
	
	@SuppressWarnings("unchecked")
	@GetMapping("/automatizacion")
	org.json.simple.JSONObject verNombre() {
		org.json.simple.JSONObject nombre = new org.json.simple.JSONObject();
		nombre.put("nombre", autocheckinservice.generarNombre());
		return nombre;
	}
	
	@PostMapping("automatizacion/testreal")
	void testAutomatizacion() {
		autocheckinservice.ejecutarAutomatizacion();
		return;
	}
	
	@PostMapping("/automatizacion/test")
	void testAutomatizacion(@RequestBody DatosGeneradorCheckin dtc){
		List<DatosGeneradorCheckin> lista = new ArrayList<DatosGeneradorCheckin>();
		lista.add(dtc);
		autocheckinservice.crearCheckinConVuelos(lista);
		return;
	}
	
	@PostMapping("/automatizacion/testmultiple")
	void testAutomatizacion(@RequestBody List<DatosGeneradorCheckin> listadtc){
		autocheckinservice.crearCheckinConVuelos(listadtc);
		return;
	}
	
	@GetMapping("/automatizacion/getcheckintest")
	List<CheckinTest> getListaCheckinTest(){
		return autocheckinservice.getListaCheckinTest();
	}
	
	@GetMapping("/automatizacion/getcheckintest/{codigo}")
	CheckinTest getCheckinTestConCodigo(@PathVariable String codigo){
		return autocheckinservice.getCheckinTestConCodigo(codigo);
	}

}
