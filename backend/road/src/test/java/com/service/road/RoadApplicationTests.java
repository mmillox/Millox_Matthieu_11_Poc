package com.service.road;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.config.Profile;
import com.graphhopper.util.shapes.GHPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class RoadApplicationTests {

    @Autowired
    private GraphHopper graphHopper;

    @BeforeEach
    public void setup() {
        graphHopper.setProfiles(new Profile("car").setVehicle("car").setWeighting("fastest"));
        graphHopper.importOrLoad();
    }

    @Test
    public void testRouteCalculation() {
        GHRequest request = new GHRequest()
                .addPoint(new GHPoint(48.8566, 2.3522))  // Paris
                .addPoint(new GHPoint(48.8584, 2.2945))  // Eiffel Tower
                .setProfile("car");

        GHResponse response = graphHopper.route(request);

        assertThat(response.hasErrors()).isFalse();
        assertThat(response.getBest().getDistance()).isGreaterThan(0);
    }
}
