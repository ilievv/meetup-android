package com.telerikacademy.meetup.providers;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.telerikacademy.meetup.providers.base.ILocationProvider;
import com.telerikacademy.meetup.providers.events.IOnConnectedListener;
import com.telerikacademy.meetup.providers.events.IOnConnectionFailedListener;
import com.telerikacademy.meetup.providers.events.IOnLocationChangeListener;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GoogleLocationProvider
        implements ILocationProvider, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final Context context;

    private IOnConnectedListener onConnectedListener;
    private IOnConnectionFailedListener onConnectionFailedListener;
    private IOnLocationChangeListener onLocationChangeListener;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    @Inject
    public GoogleLocationProvider(Context context) {
        this.context = context;

        this.buildGoogleApiClient();
        this.createLocationRequest();
    }

    public void connect() {
        this.googleApiClient.connect();
    }

    public void disconnect() {
        this.googleApiClient.disconnect();
    }

    public boolean isConnected() {
        return this.googleApiClient.isConnected();
    }

    public boolean isConnecting() {
        return this.googleApiClient.isConnecting();
    }

    public void setOnConnectedListener(IOnConnectedListener onConnectedListener) {
        this.onConnectedListener = onConnectedListener;
    }

    public void setOnConnectionFailedListener(IOnConnectionFailedListener onConnectionFailedListener) {
        this.onConnectionFailedListener = onConnectionFailedListener;
    }

    public void setOnLocationChangeListener(IOnLocationChangeListener onLocationChangeListener) {
        this.onLocationChangeListener = onLocationChangeListener;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            Location location = LocationServices.FusedLocationApi
                    .getLastLocation(googleApiClient);

            LocationServices.FusedLocationApi
                    .requestLocationUpdates(this.googleApiClient, locationRequest, this);

            if (this.onConnectedListener != null) {
                this.onConnectedListener.onConnected(this.parseLocation(location));
            }

            GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this.context);

        } catch (SecurityException ex) {
            if (this.onConnectionFailedListener != null) {
                this.onConnectionFailedListener.onConnectionFailed(ex.getMessage());
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (this.onConnectionFailedListener != null) {
            this.onConnectionFailedListener.onConnectionFailed(connectionResult.getErrorMessage());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (this.onLocationChangeListener != null) {
            this.onLocationChangeListener.onLocationChange(this.parseLocation(location));
        }
    }

    protected synchronized void buildGoogleApiClient() {
        if (this.googleApiClient == null) {
            this.googleApiClient = new GoogleApiClient.Builder(this.context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    protected synchronized void createLocationRequest() {
        if (this.locationRequest == null) {
            this.locationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(10000)
                    .setFastestInterval(10000);
        }
    }

    private com.telerikacademy.meetup.models.Location parseLocation(Location location) {
        if (location == null) {
            return null;
        }

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        Geocoder geocoder = new Geocoder(this.context, Locale.getDefault());
        List<Address> addresses = new ArrayList<>();

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);

            String locality = address.getLocality();
            String thoroughfare = address.getThoroughfare();
            String subThoroughfare = address.getSubThoroughfare();

            // TODO: Create through location factory
            return new com.telerikacademy.meetup.models.Location(latitude, longitude,
                    locality, thoroughfare, subThoroughfare);
        }

        return null;
    }
}
