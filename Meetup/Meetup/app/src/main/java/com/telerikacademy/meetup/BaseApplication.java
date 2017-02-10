package com.telerikacademy.meetup;

import android.app.Application;
import com.telerikacademy.meetup.config.modules.AndroidModule;

public class BaseApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        this.applicationComponent = DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(getApplicationContext()))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
