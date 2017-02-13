package com.telerikacademy.meetup.fragments;

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
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.activities.HomeActivity;
import com.telerikacademy.meetup.activities.NearbyVenuesActivity;
import com.telerikacademy.meetup.activities.SignInActivity;
import com.telerikacademy.meetup.activities.SignUpActivity;
import com.telerikacademy.meetup.fragments.base.IToolbar;
import com.telerikacademy.meetup.utils.base.IUserSession;

import javax.inject.Inject;

public class ToolbarFragment extends Fragment
        implements IToolbar {

    @Inject
    IUserSession userSession;

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
        DrawerBuilder builder = createDrawerBuilder(selectedItemId);

        if (userSession.isUserLoggedIn()) {
            PrimaryDrawerItem itemSignOut = new PrimaryDrawerItem()
                    .withIdentifier(0)
                    .withName("Sign out")
                    .withIcon(FontAwesome.Icon.faw_sign_out);

            builder.addDrawerItems(itemSignOut)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            switch (position) {
                                case 0:
                                    userSession.clearSession();
                                    Intent homeIntent = new Intent(currentActivity, HomeActivity.class);
                                    startActivity(homeIntent);
                                    break;
                            }

                            return false;
                        }
                    });
        } else {
            PrimaryDrawerItem itemSignIn = new PrimaryDrawerItem()
                    .withIdentifier(R.layout.activity_sign_in)
                    .withName("Sign in")
                    .withIcon(FontAwesome.Icon.faw_sign_in);

            PrimaryDrawerItem itemSignUp = new PrimaryDrawerItem()
                    .withIdentifier(R.layout.activity_sign_up)
                    .withName("Sign up")
                    .withIcon(GoogleMaterial.Icon.gmd_person_add);

            builder.addDrawerItems(
                    itemSignIn,
                    itemSignUp
            ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                @Override
                public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                    switch (position) {
                        case 0:
                            Intent homeIntent = new Intent(currentActivity, HomeActivity.class);
                            startActivity(homeIntent);
                            break;
                        case 1:
                            Intent nearbyVenuesIntent = new Intent(currentActivity, NearbyVenuesActivity.class);
                            startActivity(nearbyVenuesIntent);
                            break;
                        case 3:
                            Intent signInIntent = new Intent(currentActivity, SignInActivity.class);
                            startActivity(signInIntent);
                            break;
                        case 4:
                            Intent signUpIntent = new Intent(currentActivity, SignUpActivity.class);
                            startActivity(signUpIntent);
                            break;
                    }

                    return false;
                }
            });
        }

        builder.build();
    }

    private DrawerBuilder createDrawerBuilder(long selectedItemId) {
        PrimaryDrawerItem itemHome = new PrimaryDrawerItem()
                .withIdentifier(R.layout.activity_home)
                .withName("Home")
                .withIcon(GoogleMaterial.Icon.gmd_home);

        PrimaryDrawerItem itemNearbyVenues = new PrimaryDrawerItem()
                .withIdentifier(R.layout.activity_nearby_venues)
                .withName("Explore")
                .withIcon(GoogleMaterial.Icon.gmd_explore);

        DrawerBuilder builder = new DrawerBuilder(this.currentActivity)
                .withToolbar(this.toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withTranslucentStatusBar(false)
                .withDrawerWidthDp(270)
                .withSelectedItem(selectedItemId)
                .addDrawerItems(
                        itemHome,
                        itemNearbyVenues,
                        new DividerDrawerItem());

        return builder;
    }

    private void injectDependencies() {
        ((BaseApplication) getActivity()
                .getApplication())
                .getApplicationComponent()
                .inject(this);
    }
}
