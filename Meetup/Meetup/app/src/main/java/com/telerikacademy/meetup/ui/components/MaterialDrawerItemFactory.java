package com.telerikacademy.meetup.ui.components;

import android.content.Context;
import com.telerikacademy.meetup.ui.components.base.DrawerItem;
import com.telerikacademy.meetup.ui.components.base.DrawerItemFactory;

import javax.inject.Inject;

public class MaterialDrawerItemFactory extends DrawerItemFactory {

    private final Context context;

    @Inject
    public MaterialDrawerItemFactory(Context context) {
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
}
