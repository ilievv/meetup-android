package com.telerikacademy.meetup;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
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

    public static Intent createIntent(Context packageContext, Class<? extends Activity> cls) {
        Intent intent = new Intent(packageContext, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }
}
