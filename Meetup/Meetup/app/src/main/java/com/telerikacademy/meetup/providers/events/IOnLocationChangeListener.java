package com.telerikacademy.meetup.providers.events;

import com.telerikacademy.meetup.models.Location;

public interface IOnLocationChangeListener {
    void onLocationChange(Location location);
}
