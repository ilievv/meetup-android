package com.telerikacademy.meetup.config.di.module;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import com.telerikacademy.meetup.config.di.qualifier.ControllerScope;
import com.telerikacademy.meetup.provider.base.LocationProvider;
import com.telerikacademy.meetup.ui.components.navigation_drawer.MaterialDrawer;
import com.telerikacademy.meetup.ui.components.navigation_drawer.base.Drawer;
import com.telerikacademy.meetup.view.home.HomeContentPresenter;
import com.telerikacademy.meetup.view.home.HomeHeaderPresenter;
import com.telerikacademy.meetup.view.home.base.IHomeContentContract;
import com.telerikacademy.meetup.view.home.base.IHomeHeaderContract;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;

@Module
public class ControllerModule {

    private final Activity activity;
    private final FragmentManager fragmentManager;

    public ControllerModule(Activity activity, FragmentManager fragmentManager) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @Provides
    @ControllerScope
    IHomeContentContract.Presenter provideHomeContentPresenter() {
        return new HomeContentPresenter();
    }

    @Inject
    @Provides
    @ControllerScope
    IHomeHeaderContract.Presenter provideHomeHeaderPresenter(LocationProvider locationProvider) {
        return new HomeHeaderPresenter(locationProvider);
    }

    @Provides
    @ControllerScope
    Drawer provideNavigationDrawer() {
        return new MaterialDrawer();
    }
}
