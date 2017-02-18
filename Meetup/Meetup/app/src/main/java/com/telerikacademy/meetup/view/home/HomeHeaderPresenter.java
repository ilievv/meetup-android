package com.telerikacademy.meetup.view.home;

import com.telerikacademy.meetup.view.home.base.HomeHeaderContract;

public class HomeHeaderPresenter implements HomeHeaderContract.Presenter {

    private HomeActivity activity;

    @Override
    public void setActivity(HomeActivity activity) {
        this.activity = activity;
    }

    @Override
    public void load() {
    }
}
