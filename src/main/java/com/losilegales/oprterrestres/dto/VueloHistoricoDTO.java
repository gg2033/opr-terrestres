package com.losilegales.oprterrestres.dto;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

@Data
public class VueloHistoricoDTO {

	private Integer idVueloHistorico;
	private Integer idVuelo;
	private Integer idAeronave;
	private String origen;
	private String destino;
	private Integer idClima;
	private Integer idEmergencia;
	private Integer idCompania;
	private String reglaVuelo;
	private String tipoVuelo;
	private String horaSalidaEstimada;
	private String horaLlegadaEstimada;
	private String estado;
	private String ruta;
	private String pistaDespegue;
	private String pistaAterrizaje;
	private Integer combustibleEstimado;
	private Integer combustibleReal;
	private Integer lubricanteEstimado;
	private Integer lubricanteReal;
	private Integer kilometrajeEstimado;
	private Integer kilometrajeReal;
	private Boolean verificaciones;
	private Integer cantidadPasajeros;
	private Integer pesoTotalCarga;
	private Boolean emergencia;
	private String horaSalidaReal;
	private String horaLlegadaReal;
	private String duracionEstimada;
	private String duracionReal;
	
	//private Date creado;
	//private String creadoPor;
	//private LocalDateTime modificado;
	//private String modificadoPor;
	//private Boolean activo;
}
