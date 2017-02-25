package com.telerikacademy.meetup.ui.components.navigation_drawer.base;

public interface IDrawerItemFactory {

    IDrawerItem createPrimaryDrawerItem();

    IDrawerItem createSecondaryDrawerItem();

    IDrawerItem createDividerDrawerItem();
}
