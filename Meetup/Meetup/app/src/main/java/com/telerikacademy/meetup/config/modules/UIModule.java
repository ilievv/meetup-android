package com.telerikacademy.meetup.config.modules;

import android.content.Context;
import com.telerikacademy.meetup.ui.components.MaterialDrawer;
import com.telerikacademy.meetup.ui.components.MaterialDrawerItemFactory;
import com.telerikacademy.meetup.ui.components.base.Drawer;
import com.telerikacademy.meetup.ui.components.base.DrawerItemFactory;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Singleton;

@Module
public class UIModule {

    @Provides
    @Singleton
    Drawer provideNavigationDrawer() {
        return new MaterialDrawer();
    }

    @Inject
    @Provides
    @Singleton
    DrawerItemFactory provideDrawerItemFactory(Context context) {
        return new MaterialDrawerItemFactory(context);
    }
}
