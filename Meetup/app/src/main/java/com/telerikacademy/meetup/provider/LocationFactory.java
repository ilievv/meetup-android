package com.telerikacademy.meetup.provider;

import com.telerikacademy.meetup.model.Location;
import com.telerikacademy.meetup.model.base.ILocation;
import com.telerikacademy.meetup.provider.base.ILocationFactory;

public class LocationFactory implements ILocationFactory {

    @Override
    public ILocation createLocation(double latitude, double longitude) {
        Location location = new Location();
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }

    @Override
    public ILocation createLocation(double latitude, double longitude,
                                    String locality, String thoroughfare,
                                    String subThoroughfare) {

        Location location = new Location();
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setLocality(locality);
        location.setThoroughfare(thoroughfare);
        location.setSubThoroughfare(subThoroughfare);
        return location;
    }
}
