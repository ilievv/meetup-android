package com.telerikacademy.meetup.views.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.views.nearby_venues.NearbyVenuesActivity;

public class HomeContentFragment extends Fragment {

    private static final String VENUE_TYPE_TAG = "VENUE_TYPE";

    public HomeContentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_content, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_restaurant)
    void showNearbyRestaurants() {
        Intent intent = new Intent(getActivity(), NearbyVenuesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(VENUE_TYPE_TAG, "restaurant");
        startActivity(intent);
    }

    @OnClick(R.id.btn_cafe)
    void showNearbyCafes() {
        Intent intent = new Intent(getActivity(), NearbyVenuesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(VENUE_TYPE_TAG, "cafe");
        startActivity(intent);
    }

    @OnClick(R.id.btn_pub)
    void showNearbyPubs() {
        Intent intent = new Intent(getActivity(), NearbyVenuesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(VENUE_TYPE_TAG, "pub");
        startActivity(intent);
    }

    @OnClick(R.id.btn_fast_food)
    void showNearbyFastFoodRestaurants() {
        Intent intent = new Intent(getActivity(), NearbyVenuesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(VENUE_TYPE_TAG, "fast_food");
        startActivity(intent);
    }

    @OnClick(R.id.btn_night_club)
    void showNightClubs() {
        Intent intent = new Intent(getActivity(), NearbyVenuesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(VENUE_TYPE_TAG, "night_club");
        startActivity(intent);
    }

    @OnClick(R.id.btn_other)
    void showOtherVenues() {
        Intent intent = new Intent(getActivity(), NearbyVenuesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(VENUE_TYPE_TAG, "other");
        startActivity(intent);
    }
}
