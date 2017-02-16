package com.telerikacademy.meetup.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.ui.components.navigation_drawer.base.Drawer;
import com.telerikacademy.meetup.ui.components.navigation_drawer.base.DrawerItem;
import com.telerikacademy.meetup.ui.components.navigation_drawer.base.IDrawerItemFactory;
import com.telerikacademy.meetup.ui.fragments.base.IToolbar;
import com.telerikacademy.meetup.utils.base.IUserSession;
import com.telerikacademy.meetup.views.home.HomeActivity;
import com.telerikacademy.meetup.views.nearby_venues.NearbyVenuesActivity;
import com.telerikacademy.meetup.views.sign_in.SignInActivity;
import com.telerikacademy.meetup.views.sign_up.SignUpActivity;

import javax.inject.Inject;

public class ToolbarFragment extends Fragment
        implements IToolbar {

    @Inject
    IUserSession userSession;
    @Inject
    Drawer navigationDrawer;
    @Inject
    IDrawerItemFactory drawerItemFactory;

    private Toolbar toolbar;
    private ActionBar actionBar;
    private AppCompatActivity currentActivity;

    public ToolbarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_toolbar, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        injectDependencies();

        if (!(getActivity() instanceof AppCompatActivity)) {
            throw new ClassCastException(
                    "Activity must be of type AppCompatActivity in order to support custom Toolbar.");
        }

        currentActivity = (AppCompatActivity) getActivity();
        toolbar = (Toolbar) currentActivity.findViewById(R.id.toolbar);
        currentActivity.setSupportActionBar(toolbar);
        actionBar = currentActivity.getSupportActionBar();
    }

    public void setNavigationOnClickListener() {
        setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(currentActivity);
            }
        });
    }

    public void setNavigationOnClickListener(View.OnClickListener clickListener) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(clickListener);
    }

    public void inflateMenu(@MenuRes int menuRes, Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        currentActivity.getMenuInflater().inflate(menuRes, menu);
    }

    public void setNavigationDrawer(@LayoutRes long selectedItemId) {
        createDrawerBuilder();

        final Intent homeIntent = new Intent(currentActivity, HomeActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        final Intent nearbyVenuesIntent = new Intent(currentActivity, NearbyVenuesActivity.class);
        nearbyVenuesIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        if (userSession.isUserLoggedIn()) {
            DrawerItem itemSignOut = drawerItemFactory.createPrimaryDrawerItem()
                    .withName("Sign out")
                    .withIcon(FontAwesome.Icon.faw_sign_out);

            navigationDrawer
                    .withDrawerItems(itemSignOut)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onClick(View view, int position) {
                            switch (position) {
                                case 0:
                                    startActivity(homeIntent);
                                    break;
                                case 1:
                                    startActivity(nearbyVenuesIntent);
                                    break;
                                case 3:
                                    userSession.clearSession();
                                    startActivity(homeIntent);
                                    break;
                            }

                            return false;
                        }
                    });
        } else {
            DrawerItem itemSignIn = drawerItemFactory.createPrimaryDrawerItem()
                    .withIdentifier(R.layout.activity_sign_in)
                    .withName("Sign in")
                    .withIcon(FontAwesome.Icon.faw_sign_in);

            DrawerItem itemSignUp = drawerItemFactory.createPrimaryDrawerItem()
                    .withIdentifier(R.layout.activity_sign_up)
                    .withName("Sign up")
                    .withIcon(GoogleMaterial.Icon.gmd_person_add);

            final Intent signInIntent = new Intent(currentActivity, SignInActivity.class);
            signInIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            final Intent signUpIntent = new Intent(currentActivity, SignUpActivity.class);
            signUpIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            navigationDrawer
                    .withDrawerItems(
                            itemSignIn,
                            itemSignUp
                    )
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onClick(View view, int position) {
                            switch (position) {
                                case 0:
                                    startActivity(homeIntent);
                                    break;
                                case 1:
                                    startActivity(nearbyVenuesIntent);
                                    break;
                                case 3:
                                    startActivity(signInIntent);
                                    break;
                                case 4:
                                    startActivity(signUpIntent);
                                    break;
                            }

                            return false;
                        }
                    });
        }

        navigationDrawer.withSelectedItem(selectedItemId).build();
    }

    private void createDrawerBuilder() {
        DrawerItem itemHome = drawerItemFactory.createPrimaryDrawerItem()
                .withIdentifier(R.layout.activity_home)
                .withName("Home")
                .withIcon(GoogleMaterial.Icon.gmd_home);

        DrawerItem itemNearbyVenues = drawerItemFactory.createPrimaryDrawerItem()
                .withIdentifier(R.layout.activity_nearby_venues)
                .withName("Explore")
                .withIcon(GoogleMaterial.Icon.gmd_explore);

        navigationDrawer
                .initialize(currentActivity)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withTranslucentStatusBar(false)
                .withDrawerWidth(270)
                .withDrawerItems(
                        itemHome,
                        itemNearbyVenues,
                        drawerItemFactory.createDividerDrawerItem()
                );
    }

    private void injectDependencies() {
        ((BaseApplication) getActivity()
                .getApplication())
                .getApplicationComponent()
                .inject(this);
    }
}
