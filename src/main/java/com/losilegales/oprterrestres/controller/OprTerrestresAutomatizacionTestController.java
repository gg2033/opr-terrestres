package com.losilegales.oprterrestres.controller;

import java.util.ArrayList;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.losilegales.oprterrestres.entity.Checkin;
import com.losilegales.oprterrestres.entity.CheckinTest;
import com.losilegales.oprterrestres.service.AutomatizacionCheckinCargaService;
import com.losilegales.oprterrestres.utils.DatosGeneradorCheckin;
import com.losilegales.oprterrestres.utils.OprConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(OprConstants.BASE_ENDPOINT)
@Tag(name = "Operaciones de Automatizacion", description = "Operaciones de test automatizacion")
public class OprTerrestresAutomatizacionTestController {
	
	public class ListaCodigosCheckin{
		private List<String> lista;
		
		public ListaCodigosCheckin(List<String> lista) {
			this.lista = new ArrayList<String>(lista);
		}
		
		public ListaCodigosCheckin() {
			this.lista = new ArrayList<String>();
		}
		
		public List<String> getLista() {
			return this.lista;
		}
	}
	
	@Autowired
	private AutomatizacionCheckinCargaService autocheckinservice;
	
//	@SuppressWarnings("unchecked")
//	@GetMapping("/automatizacion")
//	org.json.simple.JSONObject verNombre() {
//		org.json.simple.JSONObject nombre = new org.json.simple.JSONObject();
//		nombre.put("nombre", autocheckinservice.generarNombre());
//		return nombre;
//	}
	
	@PostMapping("automatizacion/testreal")
	void testAutomatizacion() {
		autocheckinservice.ejecutarAutomatizacion();
		return;
	}
	
	@PostMapping("automatizacion/testmultipleautomatico")
	void testAutomatizacion(@RequestBody JSONObject json){
		try {
			List<String> lista = (List<String>)json.get("codigos");
	//		for (String s : lista) {
	//			System.out.println(s);
	//		}
	//		System.out.println(" ");
	//		System.out.println(lista.toString());
			autocheckinservice.ejecutarAutomatizacionProgramada(lista);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
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
	
	@GetMapping("/automatizacion/getcheckin")
	List<Checkin> getListaCheckin(){
		return autocheckinservice.getListaCheckin();
	}
	
	@GetMapping("/automatizacion/getcheckin/{codigo}")
	Checkin getCheckinConCodigo(@PathVariable String codigo){
		return autocheckinservice.getCheckinConCodigo(codigo);
	}

}
