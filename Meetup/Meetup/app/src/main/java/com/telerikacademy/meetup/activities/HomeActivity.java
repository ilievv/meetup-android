package com.telerikacademy.meetup.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.config.components.DaggerIDaggerComponent;
import com.telerikacademy.meetup.config.components.IDaggerComponent;
import com.telerikacademy.meetup.interfaces.ILocationProvider;
import com.telerikacademy.meetup.interfaces.IPermissionHandler;
import com.telerikacademy.meetup.interfaces.IToolbar;
import com.telerikacademy.meetup.providers.events.IOnConnectedListener;
import com.telerikacademy.meetup.providers.events.IOnConnectionFailedListener;
import com.telerikacademy.meetup.providers.events.IOnLocationChangeListener;
import com.telerikacademy.meetup.models.Location;
import com.telerikacademy.meetup.providers.GoogleLocationProvider;
import com.telerikacademy.meetup.utils.PermissionHandler;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();

    @Inject
    public IPermissionHandler permissionHandler;
    private ILocationProvider locationProvider;

    private FragmentManager fragmentManager;
    private TextView currentLocationTextView;
    private FloatingActionButton updateLocationButton;

    private Location currentLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        IDaggerComponent daggerComponent = DaggerIDaggerComponent.builder().build();
        daggerComponent.inject(this);

        this.fragmentManager = this.getSupportFragmentManager();
        this.currentLocationTextView = (TextView) findViewById(R.id.tv_location);
        this.updateLocationButton = (FloatingActionButton) findViewById(R.id.btn_update_location);

        this.updateLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
                showEnableLocationDialog();

                if (checkPermissions() &&
                        !locationProvider.isConnected() &&
                        !locationProvider.isConnecting()) {

                    locationProvider.connect();
                }

                if (currentLocation != null) {
                    currentLocationTextView.setText(currentLocation.getAddress());
                }
            }
        });

        // TODO: Inject
        this.locationProvider = new GoogleLocationProvider(this);
        locationProvider.setOnLocationChangeListener(new IOnLocationChangeListener() {
            @Override
            public void onLocationChange(Location location) {
                currentLocation = location;
            }
        });
        locationProvider.setOnConnectedListener(new IOnConnectedListener() {
            @Override
            public void onConnected(Location location) {
                if (location == null) {
                    requestPermissions();
                    showEnableLocationDialog();
                } else {
                    currentLocationTextView.setText(location.getAddress());
                }
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

        IToolbar toolbar = (IToolbar)
                this.fragmentManager.findFragmentById(R.id.fragment_toolbar);
        toolbar.setNavigationDrawer();
    }

    protected void onStop() {
        super.onStop();
        this.locationProvider.disconnect();
    }

    protected boolean checkPermissions() {
        return this.permissionHandler.checkPermissions(this,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    protected void requestPermissions() {
        this.permissionHandler.requestPermissions(this,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    protected void showEnableLocationDialog() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            new MaterialDialog.Builder(this)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .title("Enable location service?")
                    .positiveText("Yes")
                    .negativeText("No")
                    .iconRes(R.drawable.ic_location_gps)
                    .show();
        }
    }

    // TODO: Remove
    public void secretIntent(View view) {
        Intent intent = new Intent(this, NearbyVenuesActivity.class);
        this.startActivity(intent);
    }
}
