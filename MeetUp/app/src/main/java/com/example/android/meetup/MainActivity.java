package com.example.android.meetup;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.*;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private final int PLACE_PICKER_REQUEST = 1;

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

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            Log.e("Error: ", e.getMessage());

        }

        Button button = (Button) findViewById(R.id.button_test);
        button.setOnClickListener((View view) -> Toast.makeText(MainActivity.this, "Testing arrow func", Toast.LENGTH_SHORT).show());

        Log.v("Check", "In onCreate");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                Toast.makeText(this, "Place is: " + place.getName(), Toast.LENGTH_SHORT).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return R.id.action_settings == id || super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this, "onConnected", Toast.LENGTH_SHORT).show();

        if (checkPermission()) {
            this.lastLocation = LocationServices.FusedLocationApi
                    .getLastLocation(googleApiClient);

            if (this.lastLocation != null) {
                this.latitudeText = String.valueOf(lastLocation.getLatitude());
                this.longitudeText = String.valueOf(lastLocation.getLongitude());

                TextView latitudeTextView = (TextView) findViewById(R.id.latitude);
                latitudeTextView.setText(this.latitudeText);

                TextView longitudeTextView = (TextView) findViewById(R.id.longitude);
                longitudeTextView.setText(this.longitudeText);
            }
        }

        GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        Log.v("Check", "In onConnected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "onConnectionSuspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("Connection failed: ", Integer.toString(connectionResult.getErrorCode()));
        Log.e("Connection failed: ", connectionResult.getErrorMessage());

        Toast.makeText(this, "onConnectionFailed", Toast.LENGTH_SHORT).show();
    }

    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            this.googleApiClient.connect();
        }

        Log.v("Check", "In OnStart");
    }

    protected void onStop() {
        this.googleApiClient.disconnect();
        super.onStop();
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        return result != 0;
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }
}
