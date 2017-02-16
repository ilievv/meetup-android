package com.telerikacademy.meetup.ui.components.navigation_drawer;

import android.app.Activity;
import android.support.annotation.Dimension;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.AbstractBadgeableDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.telerikacademy.meetup.ui.components.navigation_drawer.base.Drawer;
import com.telerikacademy.meetup.ui.components.navigation_drawer.base.DrawerItem;

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
        throwIfUninitialized();
        IDrawerItem[] parsedDrawerItems = parseDrawerItems(Arrays.asList(drawerItems));
        drawerBuilder.addDrawerItems(parsedDrawerItems);
        return this;
    }

    @Override
    public Drawer withDrawerItems(@NonNull List<DrawerItem> drawerItems) {
        throwIfUninitialized();
        IDrawerItem[] parsedDrawerItems = parseDrawerItems(drawerItems);
        drawerBuilder.addDrawerItems(parsedDrawerItems);
        return this;
    }

    @Override
    public Drawer withSelectedItem(long identifier) {
        throwIfUninitialized();
        drawerBuilder.withSelectedItem(identifier);
        return this;
    }

    @Override
    public Drawer withDrawerWidth(@Dimension(unit = DP) int drawerWidth) {
        throwIfUninitialized();
        drawerBuilder.withDrawerWidthDp(drawerWidth);
        return this;
    }

    @Override
    public Drawer withToolbar(@NonNull Toolbar toolbar) {
        throwIfUninitialized();
        drawerBuilder.withToolbar(toolbar);
        return this;
    }

    @Override
    public Drawer withActionBarDrawerToggleAnimated(boolean hasAnimation) {
        throwIfUninitialized();
        drawerBuilder.withActionBarDrawerToggleAnimated(hasAnimation);
        return this;
    }

    @Override
    public Drawer withTranslucentStatusBar(boolean isTranslucent) {
        throwIfUninitialized();
        drawerBuilder.withTranslucentStatusBar(isTranslucent);
        return this;
    }

    @Override
    public Drawer withOnDrawerItemClickListener(final Drawer.OnDrawerItemClickListener onDrawerItemClickListener) {
        throwIfUninitialized();
        drawerBuilder.withOnDrawerItemClickListener(new com.mikepenz.materialdrawer.Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                return onDrawerItemClickListener.onClick(view, position);
            }
        });

        return this;
    }

    @Override
    public void build() {
        throwIfUninitialized();
        drawerBuilder.build();
    }

    private IDrawerItem[] parseDrawerItems(List<DrawerItem> drawerItems) {
        List<IDrawerItem> materialDrawerItems = new ArrayList<>();

        for (DrawerItem drawerItem : drawerItems) {
            Type drawerItemType = drawerItem.getDrawerItemType();

            IDrawerItem materialDrawerItem;
            AbstractBadgeableDrawerItem mainDrawerItem;

            if (drawerItemType == PrimaryDrawerItem.class) {
                mainDrawerItem = new com.mikepenz.materialdrawer.model.PrimaryDrawerItem();
            } else if (drawerItemType == SecondaryDrawerItem.class) {
                mainDrawerItem = new com.mikepenz.materialdrawer.model.SecondaryDrawerItem();
            } else if (drawerItemType == DividerDrawerItem.class) {
                materialDrawerItem = new com.mikepenz.materialdrawer.model.DividerDrawerItem()
                        .withIdentifier(drawerItem.getIdentifier());
                materialDrawerItems.add(materialDrawerItem);
                continue;
            } else {
                throw new UnsupportedOperationException(drawerItemType.toString() + " not supported.");
            }

            mainDrawerItem.withIdentifier(drawerItem.getIdentifier());
            mainDrawerItem.withName(drawerItem.getName());

            if (drawerItem.getIicon() != null) {
                mainDrawerItem.withIcon(drawerItem.getIicon());
            } else {
                mainDrawerItem.withIcon(drawerItem.getIcon());
            }

            materialDrawerItems.add(mainDrawerItem);
        }

        return materialDrawerItems.toArray(new IDrawerItem[materialDrawerItems.size()]);
    }

    private void throwIfUninitialized() {
        if (drawerBuilder == null) {
            throw new RuntimeException("Drawer is not initialized.");
        }
    }
}
