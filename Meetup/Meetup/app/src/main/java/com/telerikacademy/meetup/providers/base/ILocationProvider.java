package com.telerikacademy.meetup.providers.base;

import com.telerikacademy.meetup.providers.events.IOnConnectedListener;
import com.telerikacademy.meetup.providers.events.IOnConnectionFailedListener;
import com.telerikacademy.meetup.providers.events.IOnLocationChangeListener;

public interface ILocationProvider {

    void connect();

    void disconnect();

    boolean isConnected();

    boolean isConnecting();

    void setOnConnectedListener(IOnConnectedListener onConnectedListener);

    void setOnConnectionFailedListener(IOnConnectionFailedListener onConnectionFailedListener);

    void setOnLocationChangeListener(IOnLocationChangeListener onLocationChangeListener);
}
