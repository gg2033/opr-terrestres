package com.losilegales.oprterrestres.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.losilegales.oprterrestres.GoogleAuthorizationConfig;
import com.losilegales.oprterrestres.dto.PasajeroDTO;

@Service
public class OpTerrGoogleSheetService  implements GoogleSheetsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpTerrGoogleSheetService.class);

    @Autowired
    private Environment env;

    @Autowired
    private GoogleAuthorizationConfig googleAuthorizationConfig;

    @Override
    public List<PasajeroDTO> getSpreadsheetValues(String maestro) throws IOException, GeneralSecurityException {
        Sheets sheetsService = googleAuthorizationConfig.getSheetsService();
        Sheets.Spreadsheets.Values.BatchGet request =
                sheetsService.spreadsheets().values().batchGet(maestro);
        request.setRanges(getSpreadSheetRange());
        request.setMajorDimension("ROWS");
        BatchGetValuesResponse response = request.execute();
        List<List<Object>> spreadSheetValues = response.getValueRanges().get(0).getValues();
        List<Object> headers = spreadSheetValues.remove(0);
        List<PasajeroDTO> pasajeros = new ArrayList<PasajeroDTO>();
        PasajeroDTO pasajeroDto = new PasajeroDTO();
        for ( List<Object> row : spreadSheetValues ) {
        	//buscar alternativa mejor.
        	pasajeroDto.setIdPasajero(Integer.parseInt((String) row.get(0)));
        	pasajeroDto.setNombre((String) row.get(1));
        	pasajeros.add(pasajeroDto);
        }
        return pasajeros;
    }

    private List<String> getSpreadSheetRange() throws IOException, GeneralSecurityException {
        Sheets sheetsService = googleAuthorizationConfig.getSheetsService();
        Sheets.Spreadsheets.Get request = sheetsService.spreadsheets().get(env.getProperty("spreadsheet.id"));
        Spreadsheet spreadsheet = request.execute();
        Sheet sheet = spreadsheet.getSheets().get(0);
        int row = sheet.getProperties().getGridProperties().getRowCount();
        int col = sheet.getProperties().getGridProperties().getColumnCount();
        return Collections.singletonList("R1C1:R".concat(String.valueOf(row))
                .concat("C").concat(String.valueOf(col)));
    }
    
}