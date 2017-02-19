package com.telerikacademy.meetup.view.home;

import com.telerikacademy.meetup.view.home.base.HomeHeaderContract;

public class HomeHeaderPresenter implements HomeHeaderContract.Presenter {

    private HomeHeaderContract.View view;
    private HomeActivity activity;

    @Override
    public void initialize(HomeHeaderContract.View view, HomeActivity activity) {
        setView(view);
        this.activity = activity;
    }

    @Override
    public void setView(HomeHeaderContract.View view) {
        this.view = view;
    }

    @Override
    public void load() {
    }
}
