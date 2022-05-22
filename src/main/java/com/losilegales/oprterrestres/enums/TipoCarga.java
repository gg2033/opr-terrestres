package com.losilegales.oprterrestres.enums;

public enum TipoCarga {
	
	FACTURADO("facturado"), DE_MANO("de mano");
	private String valor;
	
	private TipoCarga(String valor) {
		this.valor = valor;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	
}
