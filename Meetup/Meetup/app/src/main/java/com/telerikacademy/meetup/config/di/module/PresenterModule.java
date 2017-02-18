package com.telerikacademy.meetup.config.di.module;

import com.telerikacademy.meetup.config.di.qualifier.PerActivity;
import com.telerikacademy.meetup.view.home.HomeContentPresenter;
import com.telerikacademy.meetup.view.home.HomeHeaderPresenter;
import com.telerikacademy.meetup.view.home.base.HomeContentContract;
import com.telerikacademy.meetup.view.home.base.HomeHeaderContract;
import dagger.Module;
import dagger.Provides;

@Module
@PerActivity
public class PresenterModule {

    @Provides
    HomeContentContract.Presenter provideHomeContentPresenter() {
        return new HomeContentPresenter();
    }

    @Provides
    HomeHeaderContract.Presenter provideHomeHeaderPresenter() {
        return new HomeHeaderPresenter();
    }
}
