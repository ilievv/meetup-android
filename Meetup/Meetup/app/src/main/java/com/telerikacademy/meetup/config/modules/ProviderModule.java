package com.telerikacademy.meetup.config.modules;

import android.content.Context;
import com.telerikacademy.meetup.providers.GoogleLocationProvider;
import com.telerikacademy.meetup.providers.base.LocationProvider;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Singleton;

@Module
public class ProviderModule {

    @Inject
    @Provides
    @Singleton
    LocationProvider provideLocationProvider(Context context) {
        return new GoogleLocationProvider(context);
    }
}
