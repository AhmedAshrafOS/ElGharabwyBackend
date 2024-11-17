package com.project.elgharabwy.security;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Configuration
public class GoogleSheetsConfig {

    private static final String APPLICATION_NAME = "Elgharabwy Clinic";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @Bean
    public Sheets sheetsService() throws GeneralSecurityException, IOException {
        // Load credentials from the service account JSON file
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                new ClassPathResource("static/elgharabwyclinc-4fd894f5328f.json").getInputStream()
        ).createScoped(Collections.singletonList("https://www.googleapis.com/auth/spreadsheets"));

        // Return the Sheets instance
        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                new HttpCredentialsAdapter(credentials)
        )
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
