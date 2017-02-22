package com.telerikacademy.meetup.config;

import com.telerikacademy.meetup.config.base.IGoogleApiConstants;

public class GoogleApiDevelopmentConstants implements IGoogleApiConstants {

    private static final String googleApiUrl = "https://maps.googleapis.com/maps/api";
    private static final String googleApiKey = "AIzaSyCJX1sMGpv4p9Mpww85unBMQHrIr9VM2NM";

    private static final String nearbySearchUrl = googleApiUrl + "/place/nearbysearch/json";

    @Override
    public String nearbySearchUrl(double latitude, double longitude, int radius, String venueType) {
        String url = String.format("%s?location=%s,%s&radius=%d&type=%s&key=%s",
                nearbySearchUrl, latitude, longitude, radius, venueType, googleApiKey);

        return url;
    }

    @Override
    public String nearbySearchUrl(double latitude, double longitude, int radius) {
        String url = String.format("%s?location=%s,%s&radius=%d&key=%s",
                nearbySearchUrl, latitude, longitude, radius, googleApiKey);

        return url;
    }
}
