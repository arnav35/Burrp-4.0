package com.example.arnavdesai.burrp;

public class mLocation {

    public Double latitude,longitude;

    public mLocation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public mLocation() {
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
