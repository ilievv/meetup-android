package com.telerikacademy.meetup.view.home;

import com.telerikacademy.meetup.view.home.base.IHomeContentContract;

import javax.inject.Inject;

public class HomeContentPresenter implements IHomeContentContract.Presenter {


    private IHomeContentContract.View view;

    @Inject
    public HomeContentPresenter() {

    }

    @Override
    public void setView(IHomeContentContract.View view) {
        this.view = view;
    }

}
