package com.losilegales.oprterrestres.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class VueloCanceladoDTO {

    private String idvuelo;
    private String estado;
    @JsonProperty("aeronave_matricula_fk")
    private Object aeronaveMatriculaFk;
    private Object modeloaeronave;

    private String origenteoricoCodiata;
    @JsonProperty("origenteorico_codiata")
    private Object origenrealCodiata;

    private String destinoteoricoCodiata;
    @JsonProperty("destinoteorico_codiata")
    private Object destinorealCodiata;
    private String nombrecompania;
    private String rutateorica;
    private Object rutareal;
    private String regladevuelo;
    private String tipodevuelo;
    private String diadespegue;
    private String fechadespegueestimado;
    private String horadespegueestimado;
    private Object fechadespeguereal;
    private Object horadespeguereal;
    private String fechaaterrizajeestimado;
    private String horaaterrizajeestimado;
    private Object fechaaterrizajereal;
    private Object horaaterrizajereal;
    private Object climadestino;
    private Object gradostemperaturadestino;
    private Object velocidadvientokm;
    private Object ltscombustibleestimado;
    private Object ltscombustiblereal;
    private Object lubricanteestimado;
    private Object lubricantereal;
    private Object kilometrajeestimado;
    private Object kilometrajereal;
    private String duracionestimada;
    private Object duracionreal;
    private Boolean checkin;
    private Boolean controlcabina;
    private Integer totalpersonasabordo;
    private Integer pesocargaorigen;
    private Object pesocargadestino;
    private Object motivoestado;
}
