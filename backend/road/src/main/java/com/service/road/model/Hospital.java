package com.service.road.model;

public class Hospital {
    private String name;
    private String specialtyGroup;
    private String specialty;
    private double longitude;
    private double latitude;    

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    };

    public String getSpecialtyGroup() {
        return this.specialtyGroup;
    }

    public void setSpecialtyGroup(String specialtyGroup) {
        this.specialtyGroup = specialtyGroup;
    }

    public String getSpecialty() {
        return this.specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
