package com.losilegales.oprterrestres.utils;

public class CommandImpl implements Command {

	private String dataValidate;


	public CommandImpl(String dataValidate) {
		this.dataValidate = dataValidate;
	}


	@Override
	public void execute() {
		validar(dataValidate);
	}
	
	
	public void validar(String dataValidate) {
//		codigo_pasajero	linea_aerea	cod_ruta_vuelo	fecha_partida	hora_partida	origen	destino	asiento	clase	tipo_documento	numero_documento	primer_apelllido	segundo_apellido	primer_nombre	segundo_nombre	nacionalidad	edad	sexo	alimentacion	condicion	comentarios
		switch (dataValidate) {
		case "linea_aerea":
//			if ("SF".equals((String) checkin.get(i).getLineaAerea())) {
//				isValidData = false;
//			}
//
//			// check vuelo, debe estar vuelo en db
//			if (!vuelo.isPresent()) {
//				isValidData = false;
//			}
			break;

		default:
			break;
		}
	}

}
