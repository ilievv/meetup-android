package com.telerikacademy.meetup.config.di.modules;

import android.content.Context;
import com.telerikacademy.meetup.utils.GsonParser;
import com.telerikacademy.meetup.utils.PermissionHandler;
import com.telerikacademy.meetup.utils.UserSession;
import com.telerikacademy.meetup.utils.base.IJsonParser;
import com.telerikacademy.meetup.utils.base.IPermissionHandler;
import com.telerikacademy.meetup.utils.base.IUserSession;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class UtilModule {

    @Provides
    @Singleton
    IPermissionHandler providePermissionHandler() {
        return new PermissionHandler();
    }

    @Provides
    @Singleton
    IUserSession provideUserSession(Context context) {
        return new UserSession(context);
    }

    @Provides
    @Singleton
    IJsonParser provideJsonParser() {
        return new GsonParser();
    }
}
