package com.losilegales.oprterrestres.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.losilegales.oprterrestres.dto.PasajeroDTO;

public interface GoogleSheetsService {
	 List<PasajeroDTO> getSpreadsheetValues(String maestro) throws IOException, GeneralSecurityException;
}