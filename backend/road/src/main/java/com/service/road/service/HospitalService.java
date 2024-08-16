package com.service.road.service;

import com.service.road.model.Hospital;
import jakarta.annotation.PostConstruct;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalService {
    private static final String CSV_URL = "https://raw.githubusercontent.com/mmillox/Millox_Matthieu_11_Poc/main/backend/road/src/main/resources/static/adresse.csv";
    private static final String CSV_FILE_PATH = "src/main/resources/static/adresse.csv";

    private List<Hospital> hospitals = new ArrayList<>();

    @PostConstruct
    public void loadHospitals() throws IOException, CsvException {
        downloadCSVFile(CSV_URL, CSV_FILE_PATH);

        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH))) {
            List<String[]> lines = reader.readAll();
            for (String[] line : lines.subList(1, lines.size())) { // Skip header
                Hospital hospital = new Hospital();
                hospital.setName(line[0]);
                hospital.setSpecialtyGroup(line[1]);
                hospital.setSpecialty(line[2]);
                hospital.setLongitude(Double.parseDouble(line[3]));
                hospital.setLatitude(Double.parseDouble(line[4]));
                hospitals.add(hospital);
            }
        }
    }

    private void downloadCSVFile(String url, String destination) {
        File csvFile = new File(destination);
        if (!csvFile.exists()) {
            try {
                FileUtils.copyURLToFile(new URL(url), csvFile);
                System.out.println("CSV file downloaded successfully.");
            } catch (IOException e) {
                throw new RuntimeException("Failed to download CSV file from " + url, e);
            }
        } else {
            System.out.println("CSV file already exists. Skipping download.");
        }
    }

    public List<Hospital> getHospitalsBySpecialty(String specialty) {
        List<Hospital> result = new ArrayList<>();
        for (Hospital hospital : hospitals) {
            if (hospital.getSpecialty().equalsIgnoreCase(specialty)) {
                result.add(hospital);
            }
        }
        return result;
    }
}
