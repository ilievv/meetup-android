package com.telerikacademy.meetup.interfaces;

import com.telerikacademy.meetup.interfaces.events.IOnConnectedListener;
import com.telerikacademy.meetup.interfaces.events.IOnConnectionFailedListener;
import com.telerikacademy.meetup.interfaces.events.IOnLocationChangeListener;

public interface ILocationProvider {

    void connect();

    void disconnect();

    void setOnConnectedListener(IOnConnectedListener onConnectedListener);

    void setOnConnectionFailedListener(IOnConnectionFailedListener onConnectionFailedListener);

    void setOnLocationChangeListener(IOnLocationChangeListener onLocationChangeListener);
}
