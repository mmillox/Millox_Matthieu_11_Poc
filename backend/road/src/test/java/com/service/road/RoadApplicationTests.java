package com.service.road;

import com.service.road.controllers.RoadController;
import com.service.road.services.RouteService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RoadApplicationTests {

    @Mock
    private RouteService routeService;

    @InjectMocks
    private RoadController roadController;

    @Test
    void testGetRoute() {
        String mockRoute = "Sample Route Data";
        
        when(routeService.getRoute(any(String.class), any(String.class))).thenReturn(mockRoute);
        
        ResponseEntity<?> response = roadController.getRoute("pointA", "pointB");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRoute, response.getBody());
    }
}
