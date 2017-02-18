package com.telerikacademy.meetup.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.view.home.base.HomeContentContract;

public class HomeContentFragment extends Fragment
        implements HomeContentContract.View {

    private HomeContentContract.Presenter presenter;

    @Override
    public void setPresenter(HomeContentContract.Presenter presenter) {
        this.presenter = presenter;
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
        presenter.showNearbyRestaurants();
    }

    @OnClick(R.id.btn_cafe)
    void showNearbyCafes() {
        presenter.showNearbyCafes();
    }

    @OnClick(R.id.btn_pub)
    void showNearbyPubs() {
        presenter.showNearbyPubs();
    }

    @OnClick(R.id.btn_fast_food)
    void showNearbyFastFoodRestaurants() {
        presenter.showNearbyFastFoodRestaurants();
    }

    @OnClick(R.id.btn_night_club)
    void showNightClubs() {
        presenter.showNearbyNightClubs();
    }

    @OnClick(R.id.btn_other)
    void showOtherVenues() {
        presenter.showOtherVenues();
    }
}
