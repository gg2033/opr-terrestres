package com.losilegales.oprterrestres.dto.CheckIn;

import com.losilegales.oprterrestres.enums.TipoCarga;
import com.losilegales.oprterrestres.enums.TipoTag;

import lombok.Data;

@Data
public class CargaDTO {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id_carga")
//	private Integer idCarga;
	
	private Integer peso;

	private String codigo;
	
	private String codigoPasajero;

	private TipoCarga tipoCarga;

	private TipoTag tagCarga;
	
	private String estadoCarga;

}
