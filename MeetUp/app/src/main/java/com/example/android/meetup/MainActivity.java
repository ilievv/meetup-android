package com.example.android.meetup;

import android.*;
import android.Manifest;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Toolbar toolbar;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private String latitudeText;
    private String longitudeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        if (this.googleApiClient == null) {
            this.buildGoogleApiClient();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (R.id.action_settings == id) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this, "onConnected", Toast.LENGTH_SHORT);
        if(checkPermission()){
            this.lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    googleApiClient);
            if (this.lastLocation != null) {
                this.latitudeText = String.valueOf(lastLocation.getLatitude());
                this.longitudeText = String.valueOf(lastLocation.getLongitude());

                TextView latitudeTextView = (TextView) findViewById(R.id.latitude);
                latitudeTextView.setText("" + this.latitudeText);

                TextView longitudeTextView = (TextView) findViewById(R.id.longitude);
                longitudeTextView.setText("" + this.longitudeText);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "onConnectionSuspended", Toast.LENGTH_SHORT);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "onConnectionFailed", Toast.LENGTH_SHORT);
    }

    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            this.googleApiClient.connect();
        }
    }

    protected void onStop() {
        this.googleApiClient.disconnect();
        super.onStop();
    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if(result != 0) {
            return true;
        } else {
            return false;
        }
    }

    protected void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
}
