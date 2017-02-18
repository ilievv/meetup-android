package com.telerikacademy.meetup.config.di.module;

import android.content.Context;
import com.telerikacademy.meetup.config.di.qualifier.ApplicationContext;
import com.telerikacademy.meetup.util.GsonParser;
import com.telerikacademy.meetup.util.PermissionHandler;
import com.telerikacademy.meetup.util.UserSession;
import com.telerikacademy.meetup.util.base.IJsonParser;
import com.telerikacademy.meetup.util.base.IPermissionHandler;
import com.telerikacademy.meetup.util.base.IUserSession;
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
    IUserSession provideUserSession(@ApplicationContext Context context) {
        return new UserSession(context);
    }

    @Provides
    @Singleton
    IJsonParser provideJsonParser() {
        return new GsonParser();
    }
}
