package com.telerikacademy.meetup;

import com.telerikacademy.meetup.activities.HomeActivity;
import com.telerikacademy.meetup.config.modules.AndroidModule;
import com.telerikacademy.meetup.config.modules.ProvidersModule;
import com.telerikacademy.meetup.config.modules.UtilsModule;
import com.telerikacademy.meetup.providers.GoogleLocationProvider;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        AndroidModule.class,
        UtilsModule.class,
        ProvidersModule.class})
public interface ApplicationComponent {

    void inject(HomeActivity homeActivity);

    void inject(GoogleLocationProvider locationProvider);
}
