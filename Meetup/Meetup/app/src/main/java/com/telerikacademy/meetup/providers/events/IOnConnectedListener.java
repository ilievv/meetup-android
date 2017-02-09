package com.telerikacademy.meetup.providers.events;

import com.telerikacademy.meetup.models.Location;

public interface IOnConnectedListener {
    void onConnected(Location location);
}
