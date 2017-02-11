package com.telerikacademy.meetup.models;

public class Location {

    private final String STRING_EMPTY = "";

    private final double latitude;
    private final double longitude;

    private String locality;
    private String thoroughfare;
    private String subThoroughfare;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.locality = STRING_EMPTY;
        this.thoroughfare = STRING_EMPTY;
        this.subThoroughfare = STRING_EMPTY;
    }

    public Location(double latitude, double longitude, String locality,
                    String thoroughfare, String subThoroughfare) {

        this(latitude, longitude);
        this.locality = locality;
        this.thoroughfare = thoroughfare;
        this.subThoroughfare = subThoroughfare;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getLocality() {
        return locality;
    }

    public String getThoroughfare() {
        return thoroughfare;
    }

    public String getSubThoroughfare() {
        return subThoroughfare;
    }

    @Override
    public String toString() {
        String fullAddress = STRING_EMPTY;

        if (this.getThoroughfare() != null) {
            fullAddress += this.thoroughfare;
        }

        if (this.getSubThoroughfare() != null) {
            fullAddress += " " + this.getSubThoroughfare();
        }

        if (this.getLocality() != null) {
            fullAddress += ", " + this.getLocality();
        }

        return fullAddress;
    }
}
