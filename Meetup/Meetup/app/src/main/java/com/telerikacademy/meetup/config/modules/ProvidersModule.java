package com.telerikacademy.meetup.config.modules;

import android.content.Context;
import com.telerikacademy.meetup.interfaces.ILocationProvider;
import com.telerikacademy.meetup.providers.GoogleLocationProvider;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Singleton;

@Module
public class ProvidersModule {

    @Inject
    @Provides
    @Singleton
    ILocationProvider provideLocationProvider(Context context) {
        return new GoogleLocationProvider(context);
    }
}
