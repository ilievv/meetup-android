package com.telerikacademy.meetup.config.modules;

import com.telerikacademy.meetup.interfaces.IPermissionHandler;
import com.telerikacademy.meetup.utils.PermissionHandler;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class HomeActivityModule {

    @Provides
    @Singleton
    IPermissionHandler providePermissionHandler() {
        return new PermissionHandler();
    }
}
