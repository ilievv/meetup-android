package com.telerikacademy.meetup.config.modules;

import android.content.Context;
import com.telerikacademy.meetup.services.GoogleLocationProvider;
import com.telerikacademy.meetup.services.base.LocationProvider;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Singleton;

@Module
public class ServiceModule {

    @Inject
    @Provides
    @Singleton
    LocationProvider provideLocationProvider(Context context) {
        return new GoogleLocationProvider(context);
    }
}
