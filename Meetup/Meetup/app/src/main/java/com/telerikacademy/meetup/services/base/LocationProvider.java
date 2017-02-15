package com.telerikacademy.meetup.services.base;

import com.telerikacademy.meetup.models.Location;

public abstract class LocationProvider {

    public abstract void connect();

    public abstract void disconnect();

    public abstract boolean isConnected();

    public abstract boolean isConnecting();

    public abstract void setOnConnectedListener(IOnConnectedListener onConnectedListener);

    public abstract void setOnConnectionFailedListener(IOnConnectionFailedListener onConnectionFailedListener);

    public abstract void setOnLocationChangeListener(IOnLocationChangeListener onLocationChangeListener);

    public interface IOnConnectedListener {
        void onConnected(Location location);
    }

    public interface IOnConnectionFailedListener {
        void onConnectionFailed(String errorMessage);
    }

    public interface IOnLocationChangeListener {
        void onLocationChange(Location location);
    }
}
