package com.telerikacademy.meetup.view.home;

import android.content.Context;

import com.telerikacademy.meetup.data.local.base.ILocalData;
import com.telerikacademy.meetup.data.local.base.IRecentVenue;
import com.telerikacademy.meetup.view.home.base.IHomeContentContract;

import java.util.List;

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
