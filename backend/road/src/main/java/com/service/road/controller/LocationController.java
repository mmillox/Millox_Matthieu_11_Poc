package com.service.road.controller;

import com.service.road.model.Hospital;
import com.service.road.model.TravelInfo;
import com.service.road.service.DistanceService;
import com.graphhopper.util.shapes.GHPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
@CrossOrigin(origins = "http://localhost:3000")
public class LocationController {

    @Autowired
    private DistanceService distanceService;

    @GetMapping("/nearest")
    public List<Hospital> getNearestHospitals(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam String specialty,
            @RequestParam(defaultValue = "5") int limit) {
        return distanceService.findNearestHospitals(lat, lon, specialty, limit);
    }

    @GetMapping("/route")
    public List<GHPoint> getRoute(
            @RequestParam double fromLat,
            @RequestParam double fromLon,
            @RequestParam double toLat,
            @RequestParam double toLon) {
        return distanceService.getRoute(fromLat, fromLon, toLat, toLon);
    }

    @GetMapping("/travelinfo")
    public TravelInfo getTravelInfo(
            @RequestParam double fromLat,
            @RequestParam double fromLon,
            @RequestParam double toLat,
            @RequestParam double toLon) {
        return distanceService.getTravelInfo(fromLat, fromLon, toLat, toLon);
    }
}
