package com.telerikacademy.meetup.view.home;

import android.app.Activity;
import android.content.Intent;
import com.telerikacademy.meetup.view.home.base.HomeContentContract;
import com.telerikacademy.meetup.view.nearby_venues.NearbyVenuesActivity;

import java.util.HashMap;
import java.util.Map;

public class HomeContentPresenter implements HomeContentContract.Presenter {

    private static final String VENUE_TYPE_TAG = "VENUE_TYPE";

    private HomeContentContract.View view;
    private HomeActivity activity;

    @Override
    public void initialize(HomeContentContract.View view, HomeActivity activity) {
        setView(view);
        this.activity = activity;
    }

    @Override
    public void setView(HomeContentContract.View view) {
        this.view = view;
    }

    @Override
    public void load() {
    }

    @Override
    public void navigate(Class<? extends Activity> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
    }

    @Override
    public void navigate(Class<? extends Activity> cls, Map<String, String> extras) {
        Intent intent = new Intent(activity, cls);
        for (Map.Entry<String, String> pair : extras.entrySet()) {
            intent.putExtra(pair.getKey(), pair.getValue());
        }
        activity.startActivity(intent);
    }

    public void showNearbyRestaurants() {
        Map<String, String> extras = new HashMap<>();
        extras.put(VENUE_TYPE_TAG, "restaurant");
        navigate(NearbyVenuesActivity.class, extras);
    }

    public void showNearbyCafes() {
        Map<String, String> extras = new HashMap<>();
        extras.put(VENUE_TYPE_TAG, "cafe");
        navigate(NearbyVenuesActivity.class, extras);
    }

    public void showNearbyPubs() {
        Map<String, String> extras = new HashMap<>();
        extras.put(VENUE_TYPE_TAG, "pub");
        navigate(NearbyVenuesActivity.class, extras);
    }

    public void showNearbyFastFoodRestaurants() {
        Map<String, String> extras = new HashMap<>();
        extras.put(VENUE_TYPE_TAG, "fast_food");
        navigate(NearbyVenuesActivity.class, extras);
    }

    public void showNearbyNightClubs() {
        Map<String, String> extras = new HashMap<>();
        extras.put(VENUE_TYPE_TAG, "night_club");
        navigate(NearbyVenuesActivity.class, extras);
    }

    public void showOtherVenues() {
        Map<String, String> extras = new HashMap<>();
        navigate(NearbyVenuesActivity.class, extras);
    }
}
