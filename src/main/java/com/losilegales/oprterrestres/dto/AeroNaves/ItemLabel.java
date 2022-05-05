package com.losilegales.oprterrestres.dto.AeroNaves;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ItemLabel {
	
    @JsonProperty("xml:lang") 
    private String xmlLang;
    private String type;
    private String value;

}
