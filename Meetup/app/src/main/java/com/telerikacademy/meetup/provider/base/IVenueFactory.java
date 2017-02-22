package com.telerikacademy.meetup.provider.base;

import com.telerikacademy.meetup.model.base.IVenue;

public interface IVenueFactory {

    IVenue createVenue(String id, String name);

    IVenue createVenue(String id, String name, String address, String[] types, float rating);
}
