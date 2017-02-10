package com.telerikacademy.meetup.config.components;

import com.telerikacademy.meetup.activities.HomeActivity;
import com.telerikacademy.meetup.config.modules.HomeActivityModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {HomeActivityModule.class})
public interface IDaggerComponent {

    void inject(HomeActivity homeActivity);
}
