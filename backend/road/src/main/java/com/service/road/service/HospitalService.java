package com.service.road.service;

import com.service.road.model.Hospital;

import jakarta.annotation.PostConstruct;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalService {
    private List<Hospital> hospitals = new ArrayList<>();

    @PostConstruct
    public void loadHospitals() throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader("src/main/resources/static/adresse.csv"))) {
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
