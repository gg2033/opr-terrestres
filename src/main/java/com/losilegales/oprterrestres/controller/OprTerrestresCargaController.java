package com.losilegales.oprterrestres.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.losilegales.oprterrestres.dto.NotificacionDTO;
import com.losilegales.oprterrestres.dto.CheckIn.CargaDTO;
import com.losilegales.oprterrestres.entity.Carga;
import com.losilegales.oprterrestres.repository.CargaRepository;
import com.losilegales.oprterrestres.service.EmailService;
import com.losilegales.oprterrestres.service.OprTerrestresCargaService;
import com.losilegales.oprterrestres.service.ReportService;
import com.losilegales.oprterrestres.utils.OprConstants;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(OprConstants.BASE_ENDPOINT)
@Tag(name = "Operaciones de la Carga", description = "Operaciones Terrestres de Carga")
public class OprTerrestresCargaController {
	
	@Autowired
	CargaRepository cargaRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	ReportService reportService;
	
	@Autowired
	OprTerrestresCargaService oprTerrestresCargaService;
	
	@Autowired
	private ModelMapper modelMapper;
	@CrossOrigin
	@Operation(summary = "Cambia el estado de todas las cargas del vuelo al estado enviado. {estado} opciones: {'En espera', 'Cargada', 'Despachada'}")
	@PutMapping("/carga/{codigoVuelo}/{estado}")
	@ResponseBody
	boolean cambiarEstadoCargasPorVuelo(@PathVariable String codigoVuelo, @PathVariable String estado) {
		//TODO descomentar esto cuando se arregle el problema de el codigo de vuelo para cargas en DB
//		codigoVuelo = OprUtils.formatoCodigoVuelo(codigoVuelo);
		return oprTerrestresCargaService.cambiarEstadoCargasVuelo(codigoVuelo, estado);

	}
	
//	@Operation(summary = "Actualiza el estado de las cargas con el estado enviado.")
//	@PutMapping("/actualizarEstadoCarga/{estado}")
//	@ResponseBody
//	public boolean actualizarEstadoCarga(@PathVariable String estado){
////		for (CargaDTO cargaDTO : cargas) {
////			Optional<Carga> carga = cargaRepository.findById(cargaDTO.getIdCarga());
////			if(carga.isPresent()) {
////				carga.get().setEstadoCarga(estado);
////				cargaRepository.save(carga.get());
////			}
////		
////		}
//		
//		return true;
//		
//	}
	
	@Operation(summary = "Obtiene todas las cargas existentes de todos los vuelos.")
	@GetMapping("/cargas")
	public List<CargaDTO> getCargas() {	
		
		List<Carga> cargas = cargaRepository.findAll();
		List<CargaDTO> cargasDTO = new ArrayList<CargaDTO>();
		for (Carga carga : cargas) {
			cargasDTO.add(modelMapper.map(carga, CargaDTO.class));
		}
		return cargasDTO;
	}
	
	@Operation(summary = "Obtiene todas las cargas por vuelo")
	@GetMapping("/cargas/{codigoVuelo}")
	public List<CargaDTO> getCargasPorVuelo(@PathVariable String codigoVuelo) {
		//TODO descomentar esto cuando se arregle el problema de el codigo de vuelo para cargas en DB
//		codigoVuelo = OprUtils.formatoCodigoVuelo(codigoVuelo);
		List<Carga> cargas = cargaRepository.cargasPorVuelo(codigoVuelo);
		
		List<CargaDTO> cargasDTO = new ArrayList<CargaDTO>();
		for (Carga carga : cargas) {
			cargasDTO.add(modelMapper.map(carga, CargaDTO.class));
		}
		return cargasDTO;

	}
	
	@PostMapping("/email")
	@Operation(summary = "Envio de notificacion")
	public void sendEmail(@RequestBody NotificacionDTO notificacion) {
		emailService.sendEmail(notificacion.getTo(), notificacion.getSubject(), notificacion.getContent());
	}


	@Operation(summary = "Reporte de Carga de todos los vuelos")
	@GetMapping("/employee/records/report")
	public ResponseEntity<byte[]> getEmployeeRecordReport() {
		return reportService.reporteCarga();
	}
	
	@Operation(summary = "Reporte de Carga por vuelo")
	@GetMapping("/employee/records/report2/{codigoVuelo}")
	public ResponseEntity<byte[]> getEmployeeRecordReport(@PathVariable String codigoVuelo) {
		return reportService.reporteCargaPorVuelo(codigoVuelo);
	}
	
	

}
