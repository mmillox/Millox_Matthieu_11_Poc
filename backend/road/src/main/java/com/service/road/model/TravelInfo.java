package com.service.road.model;

public class TravelInfo {
    private double distance;
    private long time;

    public TravelInfo(double distance, long time) {
        this.distance = distance;
        this.time = time;
    }

    public double getDistance() {
        return distance;
    }

    public long getTime() {
        return time;
    }
}
