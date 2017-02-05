package com.telerikacademy.meetup.interfaces.events;

import com.telerikacademy.meetup.models.Location;

public interface IOnLocationChangeListener {
    void onLocationChange(Location location);
}
