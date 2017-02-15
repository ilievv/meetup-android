package com.telerikacademy.meetup.ui.components;

import android.app.Activity;
import android.support.annotation.Dimension;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.telerikacademy.meetup.ui.components.base.Drawer;
import com.telerikacademy.meetup.ui.components.base.DrawerItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaterialDrawer extends Drawer {

    private final static int DP = 0;

    private DrawerBuilder drawerBuilder;

    public Drawer initialize(Activity activity) {
        drawerBuilder = new DrawerBuilder(activity);
        return this;
    }

    @Override
    public Drawer withDrawerItems(@NonNull DrawerItem... drawerItems) {
        List<IDrawerItem> parsedDrawerItems = parseDrawerItems(Arrays.asList(drawerItems));
        drawerBuilder.withDrawerItems(parsedDrawerItems);
        return this;
    }

    @Override
    public Drawer withDrawerItems(@NonNull List<DrawerItem> drawerItems) {
        List<IDrawerItem> parsedDrawerItems = parseDrawerItems(drawerItems);
        drawerBuilder.withDrawerItems(parsedDrawerItems);
        return this;
    }

    @Override
    public Drawer withSelectedItem(long identifier) {
        drawerBuilder.withSelectedItem(identifier);
        return this;
    }

    @Override
    public Drawer withDrawerWidth(@Dimension(unit = DP) int drawerWidth) {
        drawerBuilder.withDrawerWidthDp(drawerWidth);
        return this;
    }

    @Override
    public Drawer withToolbar(@NonNull Toolbar toolbar) {
        drawerBuilder.withToolbar(toolbar);
        return this;
    }

    @Override
    public Drawer withActionBarDrawerToggleAnimated(boolean hasAnimation) {
        drawerBuilder.withActionBarDrawerToggleAnimated(hasAnimation);
        return this;
    }

    @Override
    public Drawer withTranslucentStatusBar(boolean isTranslucent) {
        drawerBuilder.withTranslucentStatusBar(isTranslucent);
        return this;
    }

    @Override
    public void build() {
        drawerBuilder.build();
    }

    private List<IDrawerItem> parseDrawerItems(List<DrawerItem> drawerItems) {
        List<IDrawerItem> materialDrawerItems = new ArrayList<>();

        for (DrawerItem drawerItem : drawerItems) {
            Type drawerItemType = drawerItem.getDrawerItemType();

            IDrawerItem materialDrawerItem = null;
            if (drawerItemType == PrimaryDrawerItem.class) {
                materialDrawerItem = new com.mikepenz.materialdrawer.model.PrimaryDrawerItem()
                        .withIdentifier(drawerItem.getIdentifier())
                        .withName(drawerItem.getName())
                        .withIcon(drawerItem.getIcon());

            } else if (drawerItemType == SecondaryDrawerItem.class) {
                materialDrawerItem = new com.mikepenz.materialdrawer.model.SecondaryDrawerItem()
                        .withIdentifier(drawerItem.getIdentifier())
                        .withName(drawerItem.getName())
                        .withIcon(drawerItem.getIcon());
            }

            materialDrawerItems.add(materialDrawerItem);
        }

        return materialDrawerItems;
    }
}
