package com.telerikacademy.meetup.provider.base;

import com.telerikacademy.meetup.model.base.ILocation;

public abstract class LocationProvider {

    public abstract void connect();

    public abstract void disconnect();

    public abstract boolean isConnected();

    public abstract boolean isConnecting();

    public abstract void setOnConnectedListener(IOnConnectedListener onConnectedListener);

    public abstract void setOnConnectionFailedListener(IOnConnectionFailedListener onConnectionFailedListener);

    public abstract void setOnLocationChangeListener(IOnLocationChangeListener onLocationChangeListener);

    public interface IOnConnectedListener {
        void onConnected(ILocation location);
    }

    public interface IOnConnectionFailedListener {
        void onConnectionFailed(String errorMessage);
    }

    public interface IOnLocationChangeListener {
        void onLocationChange(ILocation location);
    }
}
