package com.telerikacademy.meetup.view.home;

import android.content.Context;
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
import com.telerikacademy.meetup.provider.base.ILocationProvider;
import com.telerikacademy.meetup.view.home.base.IHomeContentContract;
import com.telerikacademy.meetup.view.nearby_venues.NearbyVenuesActivity;

import javax.inject.Inject;

public class HomeContentFragment extends Fragment
        implements IHomeContentContract.View {

    private static final String EXTRA_VENUE_TYPE =
            HomeContentFragment.class.getCanonicalName() + ".VENUE_TYPE";
    private static final String EXTRA_CURRENT_LATITUDE =
            HomeContentFragment.class.getCanonicalName() + ".EXTRA_CURRENT_LATITUDE";
    private static final String EXTRA_CURRENT_LONGITUDE =
            HomeContentFragment.class.getCanonicalName() + ".EXTRA_CURRENT_LONGITUDE";

    @Inject
    IIntentFactory intentFactory;

    private IHomeContentContract.Presenter presenter;
    private ILocationProvider locationProvider;

    @Override
    public void setPresenter(IHomeContentContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        locationProvider = (ILocationProvider) context;
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
        Intent intent = createNearbyVenuesIntent()
                .putExtra(EXTRA_VENUE_TYPE, "restaurant");
        startActivity(intent);
    }

    @OnClick(R.id.btn_cafe)
    void showNearbyCafes() {
        Intent intent = createNearbyVenuesIntent()
                .putExtra(EXTRA_VENUE_TYPE, "cafe");
        startActivity(intent);
    }

    @OnClick(R.id.btn_pub)
    void showNearbyPubs() {
        Intent intent = createNearbyVenuesIntent()
                .putExtra(EXTRA_VENUE_TYPE, "pub");
        startActivity(intent);
    }

    @OnClick(R.id.btn_fast_food)
    void showNearbyFastFoodRestaurants() {
        Intent intent = createNearbyVenuesIntent()
                .putExtra(EXTRA_VENUE_TYPE, "fast_food");
        startActivity(intent);
    }

    @OnClick(R.id.btn_night_club)
    void showNightClubs() {
        Intent intent = createNearbyVenuesIntent()
                .putExtra(EXTRA_VENUE_TYPE, "night_club");
        startActivity(intent);
    }

    @OnClick(R.id.btn_other)
    void showOtherVenues() {
        Intent intent = createNearbyVenuesIntent()
                .putExtra(EXTRA_VENUE_TYPE, "other");
        startActivity(intent);
    }

    private Intent createNearbyVenuesIntent() {
        return intentFactory
                .createIntentToFront(NearbyVenuesActivity.class)
                .putExtra(EXTRA_CURRENT_LATITUDE, locationProvider
                        .getLocation()
                        .getLatitude())
                .putExtra(EXTRA_CURRENT_LONGITUDE, locationProvider
                        .getLocation()
                        .getLongitude());
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
