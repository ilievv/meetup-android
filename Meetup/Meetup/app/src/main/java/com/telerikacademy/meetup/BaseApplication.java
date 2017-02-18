package com.telerikacademy.meetup;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import com.telerikacademy.meetup.config.di.component.ApplicationComponent;
import com.telerikacademy.meetup.config.di.component.DaggerApplicationComponent;
import com.telerikacademy.meetup.config.di.module.ApplicationModule;

public class BaseApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }

    public static BaseApplication from(@NonNull Context context) {
        return (BaseApplication) context.getApplicationContext();
    }
}
