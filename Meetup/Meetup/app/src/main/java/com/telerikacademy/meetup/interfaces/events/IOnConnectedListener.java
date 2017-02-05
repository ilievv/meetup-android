package com.telerikacademy.meetup.interfaces.events;

import com.telerikacademy.meetup.models.Location;

public interface IOnConnectedListener {
    void onConnected(Location location);
}
