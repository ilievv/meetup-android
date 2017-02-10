package com.telerikacademy.meetup.config.modules;

import com.telerikacademy.meetup.utils.base.IPermissionHandler;
import com.telerikacademy.meetup.utils.PermissionHandler;
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
}
