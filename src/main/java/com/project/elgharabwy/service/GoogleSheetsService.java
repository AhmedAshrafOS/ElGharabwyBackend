package com.project.elgharabwy.service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;


@Service
public class GoogleSheetsService {

    private static final String SPREADSHEET_ID = "1tphdlhtqxsH6YOUCd08sUv98OUhIWoIV8zSzxwjjcv0"; // Replace with your Sheet ID
    private final Sheets sheetsService;


    @Autowired
    public GoogleSheetsService(Sheets sheetsService) {
        this.sheetsService = sheetsService;
    }

    public String getOrCreateSheetForDate(String date) throws IOException {
        // Get the list of sheets in the spreadsheet
        Spreadsheet spreadsheet = sheetsService.spreadsheets().get(SPREADSHEET_ID).execute();
        List<Sheet> sheets = spreadsheet.getSheets();

        // Check if a sheet with the given date exists
        for (Sheet sheet : sheets) {
            if (sheet.getProperties().getTitle().equals(date)) {
                return date; // Return existing sheet name
            }
        }

        // If no sheet exists, clone `Sheet1` and rename it to the date
        int sheetId = sheets.get(0).getProperties().getSheetId(); // Assuming Sheet1 is the first sheet
        DuplicateSheetRequest duplicateRequest = new DuplicateSheetRequest()
                .setSourceSheetId(sheetId)
                .setNewSheetName(date);

        BatchUpdateSpreadsheetRequest batchRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(Collections.singletonList(
                        new Request().setDuplicateSheet(duplicateRequest)
                ));

        sheetsService.spreadsheets().batchUpdate(SPREADSHEET_ID, batchRequest).execute();

        return date; // Return the new sheet name
    }

    public void appendDataToSheet(String range, List<Object> rowData) throws IOException {
        ValueRange appendBody = new ValueRange().setValues(Collections.singletonList(rowData));
        sheetsService.spreadsheets().values()
                .append(SPREADSHEET_ID, range, appendBody)
                .setValueInputOption("RAW")
                .execute();
}
}