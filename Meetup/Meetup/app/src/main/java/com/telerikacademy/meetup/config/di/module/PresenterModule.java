package com.telerikacademy.meetup.config.di.module;

import com.telerikacademy.meetup.config.di.qualifier.PerActivity;
import com.telerikacademy.meetup.provider.base.LocationProvider;
import com.telerikacademy.meetup.view.home.HomeContentPresenter;
import com.telerikacademy.meetup.view.home.HomeHeaderPresenter;
import com.telerikacademy.meetup.view.home.base.IHomeContentContract;
import com.telerikacademy.meetup.view.home.base.IHomeHeaderContract;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;

@Module
@PerActivity
public class PresenterModule {

    @Provides
    IHomeContentContract.Presenter provideHomeContentPresenter() {
        return new HomeContentPresenter();
    }

    @Inject
    @Provides
    IHomeHeaderContract.Presenter provideHomeHeaderPresenter(LocationProvider locationProvider) {
        return new HomeHeaderPresenter(locationProvider);
    }
}
