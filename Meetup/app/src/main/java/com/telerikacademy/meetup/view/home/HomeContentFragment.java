package com.telerikacademy.meetup.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.OnClick;
import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.config.di.module.ControllerModule;
import com.telerikacademy.meetup.provider.base.IIntentFactory;
import com.telerikacademy.meetup.view.home.base.IHomeContentContract;
import com.telerikacademy.meetup.view.nearby_venues.NearbyVenuesActivity;

import javax.inject.Inject;

public class HomeContentFragment extends Fragment
        implements IHomeContentContract.View {

    private static final String VENUE_TYPE_TAG = "VENUE_TYPE";

    @Inject
    IIntentFactory intentFactory;

    private IHomeContentContract.Presenter presenter;

    @Override
    public void setPresenter(IHomeContentContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_content, container, false);
        BaseApplication.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        injectDependencies();
    }

    @OnClick(R.id.btn_restaurant)
    void showNearbyRestaurants() {
        Intent intent = intentFactory
                .createIntentToFront(NearbyVenuesActivity.class)
                .putExtra(VENUE_TYPE_TAG, "restaurant");
        startActivity(intent);
    }

    @OnClick(R.id.btn_cafe)
    void showNearbyCafes() {
        Intent intent = intentFactory
                .createIntentToFront(NearbyVenuesActivity.class)
                .putExtra(VENUE_TYPE_TAG, "cafe");
        startActivity(intent);
    }

    @OnClick(R.id.btn_pub)
    void showNearbyPubs() {
        Intent intent = intentFactory
                .createIntentToFront(NearbyVenuesActivity.class)
                .putExtra(VENUE_TYPE_TAG, "pub");
        startActivity(intent);
    }

    @OnClick(R.id.btn_fast_food)
    void showNearbyFastFoodRestaurants() {
        Intent intent = intentFactory
                .createIntentToFront(NearbyVenuesActivity.class)
                .putExtra(VENUE_TYPE_TAG, "fast_food");
        startActivity(intent);
    }

    @OnClick(R.id.btn_night_club)
    void showNightClubs() {
        Intent intent = intentFactory
                .createIntentToFront(NearbyVenuesActivity.class)
                .putExtra(VENUE_TYPE_TAG, "night_club");
        startActivity(intent);
    }

    @OnClick(R.id.btn_other)
    void showOtherVenues() {
        Intent intent = intentFactory
                .createIntentToFront(NearbyVenuesActivity.class)
                .putExtra(VENUE_TYPE_TAG, "other");
        startActivity(intent);
    }

    private void injectDependencies() {
        BaseApplication
                .from(getContext())
                .getComponent()
                .getControllerComponent(new ControllerModule(
                        getActivity(), getFragmentManager()
                ))
                .inject(this);
    }
}
