package com.telerikacademy.meetup.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.interfaces.ILocationProvider;
import com.telerikacademy.meetup.interfaces.IToolbar;
import com.telerikacademy.meetup.interfaces.events.IOnConnectedListener;
import com.telerikacademy.meetup.interfaces.events.IOnConnectionFailedListener;
import com.telerikacademy.meetup.interfaces.events.IOnLocationChangeListener;
import com.telerikacademy.meetup.models.Location;
import com.telerikacademy.meetup.providers.GoogleLocationProvider;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();

    private ILocationProvider locationProvider;

    private FragmentManager fragmentManager;
    private TextView currLocationTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.fragmentManager = this.getSupportFragmentManager();
        this.currLocationTextView = (TextView) findViewById(R.id.tv_location);

        // TODO: Inject
        this.locationProvider = new GoogleLocationProvider(this);
        locationProvider.setOnLocationChangeListener(new IOnLocationChangeListener() {
            @Override
            public void onLocationChange(Location location) {
                currLocationTextView.setText(location.getAddress());
            }
        });
        locationProvider.setOnConnectedListener(new IOnConnectedListener() {
            @Override
            public void onConnected(Location location) {
                currLocationTextView.setText(location.getAddress());
            }
        });
        locationProvider.setOnConnectionFailedListener(new IOnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(String errorMessage) {
                Log.e(TAG, errorMessage);
            }
        });
    }

    protected void onStart() {
        super.onStart();

        IToolbar menuInflater = (IToolbar)
                this.fragmentManager.findFragmentById(R.id.fragment_toolbar);
        menuInflater.setNavigationDrawer();

        // TODO: Move to button on click event
        if (!checkPermission()) {
            this.requestPermissions();
        }

        if (checkPermission()) {
            this.locationProvider.connect();
        }
    }

    protected void onStop() {
        super.onStop();
        this.locationProvider.disconnect();
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

    public void secretIntent(View view) {
        Intent intent = new Intent(this, NearbyVenuesActivity.class);
        this.startActivity(intent);
    }
}
