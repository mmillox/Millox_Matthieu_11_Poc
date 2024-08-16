package com.service.road.config;

import com.graphhopper.GraphHopper;
import com.graphhopper.config.Profile;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Configuration
public class GraphHopperConfig {

    private static final String OSM_URL = "https://download.geofabrik.de/europe/france/ile-de-france-latest.osm.pbf";
    private static final String OSM_FILE_PATH = "src/main/resources/static/ile-de-france-latest.osm.pbf";
    private static final String GRAPH_LOCATION = "src/main/resources/templates/graphhopper";

    @Bean
    public GraphHopper graphHopper() {
        downloadOSMFile(OSM_URL, OSM_FILE_PATH);

        GraphHopper hopper = new GraphHopper();
        hopper.setOSMFile(OSM_FILE_PATH);
        hopper.setGraphHopperLocation(GRAPH_LOCATION);
        hopper.setProfiles(new Profile("car").setVehicle("car").setWeighting("fastest"));
        hopper.importOrLoad();
        return hopper;
    }

    private void downloadOSMFile(String url, String destination) {
        File osmFile = new File(destination);
        if (!osmFile.exists()) {
            try {
                FileUtils.copyURLToFile(new URL(url), osmFile);
                System.out.println("OSM file downloaded successfully.");
            } catch (IOException e) {
                throw new RuntimeException("Failed to download OSM file from " + url, e);
            }
        } else {
            System.out.println("OSM file already exists. Skipping download.");
        }
    }
}
