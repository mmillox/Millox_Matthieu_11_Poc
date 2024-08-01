package com.service.road.service;

import com.service.road.model.Hospital;
import com.service.road.model.HospitalDistance;
import com.service.road.model.TravelInfo;
import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.util.shapes.GHPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DistanceService {

    @Autowired
    private GraphHopper graphHopper;

    @Autowired
    private HospitalService hospitalService;

    public List<Hospital> findNearestHospitals(double lat, double lon, String specialty, int limit) {
        List<Hospital> hospitals = hospitalService.getHospitalsBySpecialty(specialty);
        List<HospitalDistance> distances = new ArrayList<>();

        for (Hospital hospital : hospitals) {
            GHRequest req = new GHRequest(lat, lon, hospital.getLatitude(), hospital.getLongitude())
                    .setProfile("car");
            GHResponse rsp = graphHopper.route(req);

            if (!rsp.hasErrors()) {
                ResponsePath path = rsp.getBest();
                distances.add(new HospitalDistance(hospital, path.getDistance(), path.getTime()));
            }
        }

        return distances.stream()
                .sorted(Comparator.comparingDouble(HospitalDistance::getDistance))
                .limit(limit)
                .map(HospitalDistance::getHospital)
                .collect(Collectors.toList());
    }

    public List<GHPoint> getRoute(double fromLat, double fromLon, double toLat, double toLon) {
        GHRequest req = new GHRequest(fromLat, fromLon, toLat, toLon)
                .setProfile("car");
        GHResponse rsp = graphHopper.route(req);

        List<GHPoint> points = new ArrayList<>();
        if (!rsp.hasErrors()) {
            ResponsePath path = rsp.getBest();
            path.getPoints().forEach(p -> points.add(new GHPoint(p.lat, p.lon)));
        }
        return points;
    }

    public TravelInfo getTravelInfo(double fromLat, double fromLon, double toLat, double toLon) {
        GHRequest req = new GHRequest(fromLat, fromLon, toLat, toLon)
                .setProfile("car");
        GHResponse rsp = graphHopper.route(req);

        if (!rsp.hasErrors()) {
            ResponsePath path = rsp.getBest();
            return new TravelInfo(path.getDistance(), path.getTime());
        }
        return null;
    }
}
