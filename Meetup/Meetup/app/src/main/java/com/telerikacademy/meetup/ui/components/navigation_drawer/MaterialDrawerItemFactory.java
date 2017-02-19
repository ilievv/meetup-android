package com.telerikacademy.meetup.ui.components.navigation_drawer;

import android.content.Context;
import com.telerikacademy.meetup.config.di.annotation.ApplicationContext;
import com.telerikacademy.meetup.ui.components.navigation_drawer.base.DrawerItem;
import com.telerikacademy.meetup.ui.components.navigation_drawer.base.IDrawerItemFactory;

import javax.inject.Inject;

public class MaterialDrawerItemFactory implements IDrawerItemFactory {

    private final Context context;

    @Inject
    public MaterialDrawerItemFactory(@ApplicationContext Context context) {
        this.context = context;
    }

    @Override
    public DrawerItem createPrimaryDrawerItem() {
        return new PrimaryDrawerItem(context);
    }

    @Override
    public DrawerItem createSecondaryDrawerItem() {
        return new SecondaryDrawerItem(context);
    }

    @Override
    public DrawerItem createDividerDrawerItem() {
        return new DividerDrawerItem(context);
    }
}
