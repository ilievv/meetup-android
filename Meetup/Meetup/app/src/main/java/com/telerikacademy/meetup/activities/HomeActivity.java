package com.telerikacademy.meetup.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.interfaces.IMenuInflater;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private FragmentManager fragmentManager;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    private TextView currLocationTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.fragmentManager = getSupportFragmentManager();
        this.currLocationTextView = (TextView) findViewById(R.id.tv_location);

        if (this.googleApiClient == null) {
            this.buildGoogleApiClient();
        }

        this.locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1000);

        this.requestPermissions();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (this.googleApiClient != null &&
                this.checkPermission()) {

            Location location = LocationServices.FusedLocationApi
                    .getLastLocation(googleApiClient);

            this.handleLocation(location);

            LocationServices.FusedLocationApi
                    .requestLocationUpdates(this.googleApiClient, locationRequest, this);
        }

        GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.handleLocation(location);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        IMenuInflater menuInflater = (IMenuInflater)
                this.fragmentManager.findFragmentById(R.id.fragment_tool_bar);

        if (menuInflater != null) {
            menuInflater.inflateMenu(R.menu.main, menu, getMenuInflater());
        }

        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("Connection failed: ", Integer.toString(connectionResult.getErrorCode()));
        Log.e("Connection failed: ", connectionResult.getErrorMessage());

        Toast.makeText(this, "Make sure you are connected to the internet.", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            this.googleApiClient.connect();
        }
    }

    protected void onStop() {
        super.onStop();
        this.googleApiClient.disconnect();
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    private boolean checkPermission() {
        return checkPermission(Manifest.permission.INTERNET) &&
                checkPermission(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private boolean checkPermission(String permission) {
        int result = ContextCompat.checkSelfPermission(this, permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        int accessFineLocationResult = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (accessFineLocationResult != 0) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        int internetLocationResult = ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET);
        if (internetLocationResult != 0) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET}, 2);
        }
    }

    private void handleLocation(Location location) {
        if (location == null) {
            return;
        }

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = new ArrayList<>();

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses == null || addresses.size() <= 0) {
            return;
        }

        Address address = addresses.get(0);
        List<String> addressComponents = new ArrayList<>();

        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
            addressComponents.add(address.getAddressLine(i));
        }

        String primaryAddress = address.getThoroughfare();
        String secondaryAddress = TextUtils.join(", ", addressComponents);

        if (primaryAddress != null) {
            if (!address.getSubThoroughfare().isEmpty()) {
                primaryAddress += " " + address.getSubThoroughfare();
            }

            if (!address.getLocality().isEmpty()) {
                primaryAddress += ", " + address.getLocality();
            }

            this.currLocationTextView.setText(primaryAddress);
        } else if (secondaryAddress != null) {
            this.currLocationTextView.setText(secondaryAddress);
        } else {
            this.currLocationTextView.setText(R.string.unknown_location);
        }
    }

    public void secretIntent(View view) {
        Intent intent = new Intent(this, NearbyVenuesActivity.class);
        this.startActivity(intent);
    }
}
