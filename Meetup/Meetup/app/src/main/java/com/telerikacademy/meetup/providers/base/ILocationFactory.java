package com.telerikacademy.meetup.providers.base;

import com.telerikacademy.meetup.models.base.ILocation;

public interface ILocationFactory {

    ILocation createLocation(double latitude, double longitude);

    ILocation createLocation(double latitude, double longitude,
                             String locality, String thoroughfare,
                             String subThoroughfare);
}
