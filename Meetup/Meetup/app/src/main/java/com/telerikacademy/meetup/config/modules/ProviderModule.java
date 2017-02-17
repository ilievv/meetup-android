package com.telerikacademy.meetup.config.modules;

import android.content.Context;
import com.telerikacademy.meetup.providers.GoogleLocationProvider;
import com.telerikacademy.meetup.providers.LocationFactory;
import com.telerikacademy.meetup.providers.RecyclerDecorationFactory;
import com.telerikacademy.meetup.providers.base.ILocationFactory;
import com.telerikacademy.meetup.providers.base.IRecyclerDecorationFactory;
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
    LocationProvider provideLocationProvider(Context context, ILocationFactory locationFactory) {
        return new GoogleLocationProvider(context, locationFactory);
    }

    @Provides
    @Singleton
    ILocationFactory provideLocationFactory() {
        return new LocationFactory();
    }

    @Inject
    @Provides
    @Singleton
    IRecyclerDecorationFactory provideRecyclerDecorationFactory(Context context) {
        return new RecyclerDecorationFactory(context);
    }
}
