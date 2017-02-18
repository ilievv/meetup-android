package com.telerikacademy.meetup;

import android.app.Application;
import com.telerikacademy.meetup.config.di.components.ApplicationComponent;
import com.telerikacademy.meetup.config.di.components.DaggerApplicationComponent;
import com.telerikacademy.meetup.config.di.modules.AndroidModule;

public class BaseApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(getApplicationContext()))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
