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
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.fragments.base.IToolbar;
import com.telerikacademy.meetup.models.Location;
import com.telerikacademy.meetup.providers.base.ILocationProvider;
import com.telerikacademy.meetup.providers.events.IOnConnectedListener;
import com.telerikacademy.meetup.providers.events.IOnConnectionFailedListener;
import com.telerikacademy.meetup.providers.events.IOnLocationChangeListener;
import com.telerikacademy.meetup.utils.base.IPermissionHandler;
import com.telerikacademy.meetup.utils.base.IUserSession;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private static final String VENUE_TYPE_TAG = "VENUE_TYPE";

    @Inject
    IPermissionHandler permissionHandler;
    @Inject
    ILocationProvider locationProvider;
    @Inject
    IUserSession userSession;

    @BindView(R.id.tv_location_title)
    TextView currentLocationTitle;
    @BindView(R.id.tv_location_subtitle)
    TextView currentLocationSubtitle;
    @BindView(R.id.btn_update_location)
    FloatingActionButton updateLocationButton;

    private FragmentManager fragmentManager;
    private Location currentLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        injectDependencies();

        fragmentManager = getSupportFragmentManager();

        locationProvider.setOnLocationChangeListener(new IOnLocationChangeListener() {
            @Override
            public void onLocationChange(Location location) {
                currentLocation = location;
            }
        });
        locationProvider.setOnConnectedListener(new IOnConnectedListener() {
            @Override
            public void onConnected(Location location) {
                currentLocation = location;
                setTextViewTitle(currentLocation);
            }
        });
        locationProvider.setOnConnectionFailedListener(new IOnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(String errorMessage) {
                setTextViewTitle(currentLocation);
                Log.e(TAG, errorMessage);
            }
        });
    }

    @OnClick(R.id.btn_update_location)
    void updateLocation() {
        requestPermissions();
        showEnableLocationDialog();

        if (checkPermissions() &&
                !locationProvider.isConnected() &&
                !locationProvider.isConnecting()) {

            locationProvider.connect();
        }

        setTextViewTitle(currentLocation);
    }

    @OnClick(R.id.btn_restaurant)
    void showNearbyRestaurants() {
        Intent intent = new Intent(this, NearbyVenuesActivity.class);
        intent.putExtra(VENUE_TYPE_TAG, "restaurant");
        startActivity(intent);
    }

    @OnClick(R.id.btn_cafe)
    void showNearbyCafes() {
        Intent intent = new Intent(this, NearbyVenuesActivity.class);
        intent.putExtra(VENUE_TYPE_TAG, "cafe");
    }

    @OnClick(R.id.btn_pub)
    void showNearbyPubs() {
        Intent intent = new Intent(this, NearbyVenuesActivity.class);
        intent.putExtra(VENUE_TYPE_TAG, "pub");
        startActivity(intent);
    }

    @OnClick(R.id.btn_fast_food)
    void showNearbyFastFoodRestaurants() {
        Intent intent = new Intent(this, NearbyVenuesActivity.class);
        intent.putExtra(VENUE_TYPE_TAG, "fast_food");
        startActivity(intent);
    }

    @OnClick(R.id.btn_favourites)
    void showFavourites() {
        Intent intent = new Intent(this, NearbyVenuesActivity.class);
        intent.putExtra(VENUE_TYPE_TAG, "favourites");
        startActivity(intent);
    }

    @OnClick(R.id.btn_other)
    void showOtherVenues() {
        Intent intent = new Intent(this, NearbyVenuesActivity.class);
        intent.putExtra(VENUE_TYPE_TAG, "other");
    }

    protected void onStart() {
        super.onStart();

        IToolbar toolbar = (IToolbar)
                fragmentManager.findFragmentById(R.id.fragment_toolbar);
        toolbar.setNavigationDrawer();

        requestPermissions();
        showEnableLocationDialog();

        locationProvider.connect();
    }

    protected void onStop() {
        super.onStop();
        locationProvider.disconnect();
    }

    protected boolean checkPermissions() {
        return permissionHandler.checkPermissions(this,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    protected void requestPermissions() {
        permissionHandler.requestPermissions(this,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    protected void showEnableLocationDialog() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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

    private void setTextViewTitle(Location location) {
        final String LOCATION_NOT_FOUND = "Unknown location";

        if (location == null) {
            currentLocationTitle.setText(LOCATION_NOT_FOUND);
            return;
        }

        String locality = location.getLocality();
        String thoroughfare = location.getThoroughfare();
        String subThoroughfare = location.getSubThoroughfare();

        if (locality.isEmpty() && thoroughfare.isEmpty()) {
            currentLocationTitle.setText(LOCATION_NOT_FOUND);
        } else if (locality.isEmpty()) {
            currentLocationTitle.setText(thoroughfare);
            currentLocationSubtitle.setText(subThoroughfare);
        } else {
            currentLocationTitle.setText(locality);

            String subtitle;
            if (!thoroughfare.isEmpty()) {
                subtitle = thoroughfare;

                if (!subThoroughfare.isEmpty()) {
                    subtitle = String.format("%s, %s", thoroughfare, subThoroughfare);
                }
            } else {
                subtitle = subThoroughfare;
            }

            currentLocationSubtitle.setText(subtitle);
        }
    }

    private void injectDependencies() {
        ((BaseApplication) getApplication())
                .getApplicationComponent()
                .inject(this);
        ButterKnife.bind(this);
    }
}
