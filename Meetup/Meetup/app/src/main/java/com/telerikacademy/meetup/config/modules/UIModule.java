package com.telerikacademy.meetup.config.modules;

import android.content.Context;
import com.telerikacademy.meetup.ui.components.dialog.MaterialDialog;
import com.telerikacademy.meetup.ui.components.dialog.base.Dialog;
import com.telerikacademy.meetup.ui.components.navigation_drawer.MaterialDrawer;
import com.telerikacademy.meetup.ui.components.navigation_drawer.MaterialDrawerItemFactory;
import com.telerikacademy.meetup.ui.components.navigation_drawer.base.Drawer;
import com.telerikacademy.meetup.ui.components.navigation_drawer.base.IDrawerItemFactory;
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
    IDrawerItemFactory provideDrawerItemFactory(Context context) {
        return new MaterialDrawerItemFactory(context);
    }

    @Inject
    @Provides
    @Singleton
    Dialog provideDialog(Context context) {
        return new MaterialDialog(context);
    }
}
