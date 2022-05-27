package com.losilegales.oprterrestres.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.losilegales.oprterrestres.dto.CheckIn.CargaDTO;
import com.losilegales.oprterrestres.entity.Carga;
import com.losilegales.oprterrestres.repository.CargaRepository;
import com.losilegales.oprterrestres.service.OprTerrestresCargaService;
import com.losilegales.oprterrestres.utils.OprConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(OprConstants.BASE_ENDPOINT)
@Tag(name = "Operaciones de la Carga", description = "Operaciones Terrestres de Carga")
public class OprTerrestresCargaController {
	
	@Autowired
	CargaRepository cargaRepository;
	
	@Autowired
	OprTerrestresCargaService oprTerrestresCargaService;
	
	@Autowired
	private ModelMapper modelMapper;
	@Operation(summary = "Cambia el estado de todas las cargas del vuelo al estado enviado. {estado} opciones: {'En espera', 'Cargada', 'Despachada'}")
	@PutMapping("/carga/{codigoVuelo}/{estado}")
	@ResponseBody
	boolean cambiarEstadoCargasPorVuelo(@PathVariable String codigoVuelo, @PathVariable String estado) {
		return oprTerrestresCargaService.cambiarEstadoCargaDespachada(codigoVuelo, estado);

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
	@GetMapping("/cargas/{codigo_vuelo}")
	public List<CargaDTO> getCargasPorVuelo(@PathVariable String codigo_vuelo) {	
		List<Carga> cargas = cargaRepository.cargasPorVuelo(codigo_vuelo);
		
		List<CargaDTO> cargasDTO = new ArrayList<CargaDTO>();
		for (Carga carga : cargas) {
			cargasDTO.add(modelMapper.map(carga, CargaDTO.class));
		}
		return cargasDTO;

	}

}
