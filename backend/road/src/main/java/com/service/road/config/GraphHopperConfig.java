package com.service.road.config;

import com.graphhopper.GraphHopper;
import com.graphhopper.config.Profile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphHopperConfig {

    @Bean
    public GraphHopper graphHopper() {
        GraphHopper hopper = new GraphHopper();
        hopper.setOSMFile("src/main/resources/static/ile-de-france-latest.osm.pbf"); // Remplacez par le chemin de votre fichier OSM
        hopper.setGraphHopperLocation("src/main/resources/templates/graphhopper"); // Remplacez par le chemin où GraphHopper stockera ses fichiers
        hopper.setProfiles(new Profile("car").setVehicle("car").setWeighting("fastest"));
        hopper.importOrLoad();
        return hopper;
    }
}
