package com.telerikacademy.meetup.ui.components.navigation_drawer.base;

import android.support.annotation.Dimension;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

public abstract class Drawer {

    private final static int DP = 0;

    public abstract Drawer withDrawerItems(@NonNull DrawerItem... drawerItems);

    public abstract Drawer withDrawerItems(@NonNull List<DrawerItem> drawerItems);

    public abstract Drawer withSelectedItem(long identifier);

    public abstract Drawer withDrawerWidth(@Dimension(unit = DP) int drawerWidth);

    public abstract Drawer withToolbar(@NonNull Toolbar toolbar);

    public abstract Drawer withActionBarDrawerToggleAnimated(boolean hasAnimation);

    public abstract Drawer withTranslucentStatusBar(boolean isTranslucent);

    public abstract Drawer withOnDrawerItemClickListener(OnDrawerItemClickListener onDrawerItemClickListener);

    public abstract void build();

    public interface OnDrawerItemClickListener {

        boolean onClick(View view, int position);
    }
}
