package com.telerikacademy.meetup;

import com.telerikacademy.meetup.activities.HomeActivity;
import com.telerikacademy.meetup.config.modules.AndroidModule;
import com.telerikacademy.meetup.config.modules.NetworkModule;
import com.telerikacademy.meetup.config.modules.ProviderModule;
import com.telerikacademy.meetup.config.modules.UtilModule;
import com.telerikacademy.meetup.providers.GoogleLocationProvider;
import dagger.Component;
import okhttp3.OkHttpClient;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        AndroidModule.class,
        UtilModule.class,
        ProviderModule.class,
        NetworkModule.class})
public interface ApplicationComponent {

    void inject(HomeActivity homeActivity);

    void inject(GoogleLocationProvider locationProvider);

    void inject(OkHttpClient okHttpClient);
}
