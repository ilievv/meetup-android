package com.telerikacademy.meetup.ui.components.navigation_drawer.base;

public interface IDrawerItemFactory {

    DrawerItem createPrimaryDrawerItem();

    DrawerItem createSecondaryDrawerItem();

    DrawerItem createDividerDrawerItem();
}
