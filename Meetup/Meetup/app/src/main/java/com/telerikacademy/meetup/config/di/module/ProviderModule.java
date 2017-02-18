package com.telerikacademy.meetup.config.di.module;

import android.content.Context;
import com.telerikacademy.meetup.config.di.qualifier.ApplicationContext;
import com.telerikacademy.meetup.provider.GoogleLocationProvider;
import com.telerikacademy.meetup.provider.LocationFactory;
import com.telerikacademy.meetup.provider.RecyclerDecorationFactory;
import com.telerikacademy.meetup.provider.base.ILocationFactory;
import com.telerikacademy.meetup.provider.base.IRecyclerDecorationFactory;
import com.telerikacademy.meetup.provider.base.LocationProvider;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Singleton;

@Module
public class ProviderModule {

    @Inject
    @Provides
    @Singleton
    LocationProvider provideLocationProvider(@ApplicationContext Context context,
                                             ILocationFactory locationFactory) {

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
    IRecyclerDecorationFactory provideRecyclerDecorationFactory(@ApplicationContext Context context) {
        return new RecyclerDecorationFactory(context);
    }
}
