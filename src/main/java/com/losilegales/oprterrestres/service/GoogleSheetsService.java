package com.losilegales.oprterrestres.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface GoogleSheetsService {
	List<List<Object>> getSpreadsheetValues(String maestro) throws IOException, GeneralSecurityException;
}