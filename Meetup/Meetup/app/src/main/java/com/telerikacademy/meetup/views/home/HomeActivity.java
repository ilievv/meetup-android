package com.telerikacademy.meetup.views.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.ui.fragments.base.IToolbar;
import com.telerikacademy.meetup.views.nearby_venues.NearbyVenuesActivity;

public class HomeActivity extends AppCompatActivity {

    private static final String VENUE_TYPE_TAG = "VENUE_TYPE";

    @BindView(R.id.btn_update_location)
    FloatingActionButton updateLocationButton;

    private FragmentManager fragmentManager;
    private IHomeHeader homeHeader;
    private IToolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        injectDependencies();

        fragmentManager = getSupportFragmentManager();
        toolbar = (IToolbar) fragmentManager.findFragmentById(R.id.fragment_home_header);
        homeHeader = (IHomeHeader) fragmentManager.findFragmentById(R.id.fragment_home_header);
    }

    @OnClick(R.id.btn_update_location)
    void updateLocation() {
        if (homeHeader != null) {
            homeHeader.updateLocation();
        }
    }

    @OnClick(R.id.btn_restaurant)
    void showNearbyRestaurants() {
        Intent intent = new Intent(this, NearbyVenuesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(VENUE_TYPE_TAG, "restaurant");
        startActivity(intent);
    }

    @OnClick(R.id.btn_cafe)
    void showNearbyCafes() {
        Intent intent = new Intent(this, NearbyVenuesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(VENUE_TYPE_TAG, "cafe");
        startActivity(intent);
    }

    @OnClick(R.id.btn_pub)
    void showNearbyPubs() {
        Intent intent = new Intent(this, NearbyVenuesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(VENUE_TYPE_TAG, "pub");
        startActivity(intent);
    }

    @OnClick(R.id.btn_fast_food)
    void showNearbyFastFoodRestaurants() {
        Intent intent = new Intent(this, NearbyVenuesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(VENUE_TYPE_TAG, "fast_food");
        startActivity(intent);
    }

    @OnClick(R.id.btn_night_club)
    void showNightClubs() {
        Intent intent = new Intent(this, NearbyVenuesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(VENUE_TYPE_TAG, "night_club");
        startActivity(intent);
    }

    @OnClick(R.id.btn_other)
    void showOtherVenues() {
        Intent intent = new Intent(this, NearbyVenuesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(VENUE_TYPE_TAG, "other");
        startActivity(intent);
    }

    protected void onStart() {
        super.onStart();

        if (toolbar != null) {
            toolbar.setNavigationDrawer(R.layout.activity_home);
        }
    }

    private void injectDependencies() {
        ButterKnife.bind(this);
    }
}
