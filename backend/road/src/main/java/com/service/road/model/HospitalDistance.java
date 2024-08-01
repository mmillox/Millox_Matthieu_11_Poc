package com.service.road.model;

public class HospitalDistance {
    private Hospital hospital;
    private double distance;
    private long time;

    public HospitalDistance(Hospital hospital, double distance, long time) {
        this.hospital = hospital;
        this.distance = distance;
        this.time = time;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public double getDistance() {
        return distance;
    }

    public long getTime() {
        return time;
    }
}
