package com.losilegales.oprterrestres.dto;

import java.util.Date;

import lombok.Data;

@Data
public class VueloHistoricoDTO {

    public String idvuelo;
    public String estado;
    public String aeronave_matricula_fk;
    public String modeloaeronave;
    public String origenteorico_codiata;
    public String origenreal_codiata;
    public String destinoteorico_codiata;
    public String destinoreal_codiata;
    public String nombrecompania;
    public String rutateorica;
    public String rutareal;
    public String regladevuelo;
    public String tipodevuelo;
    public String diadespegue;
    public Date fechadespegueestimado;
    public String horadespegueestimado;
    public Date fechadespeguereal;
    public String horadespeguereal;
    public Date fechaaterrizajeestimado;
    public String horaaterrizajeestimado;
    public Date fechaaterrizajereal;
    public String horaaterrizajereal;
    public String climadestino;
    public double gradostemperaturadestino;
    public double velocidadvientokm;
    public int ltscombustibleestimado;
    public int ltscombustiblereal;
    public int lubricanteestimado;
    public int lubricantereal;
    public int kilometrajeestimado;
    public int kilometrajereal;
    public String duracionestimada;
    public String duracionreal;
    public boolean checkin;
    public boolean controlcabina;
    public int totalpersonasabordo;
    public int pesocargaorigen;
    public int pesocargadestino;
    public String motivoestado;
	
	//private Date creado;
	//private String creadoPor;
	//private LocalDateTime modificado;
	//private String modificadoPor;
	//private Boolean activo;
}
