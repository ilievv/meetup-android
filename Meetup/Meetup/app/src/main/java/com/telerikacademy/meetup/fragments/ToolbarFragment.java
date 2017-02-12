package com.telerikacademy.meetup.fragments;

import android.content.Intent;
import android.os.Bundle;
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
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.activities.HomeActivity;
import com.telerikacademy.meetup.activities.SignInActivity;
import com.telerikacademy.meetup.activities.SignUpActivity;
import com.telerikacademy.meetup.fragments.base.IToolbar;
import com.telerikacademy.meetup.utils.base.IUserSession;

public class ToolbarFragment extends Fragment
        implements IToolbar {

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

        if (!(getActivity() instanceof AppCompatActivity)) {
            throw new ClassCastException("Activity must be of type AppCompatActivity in order to support custom Toolbar.");
        }

        this.currentActivity = (AppCompatActivity) getActivity();
        this.toolbar = (Toolbar) this.currentActivity.findViewById(R.id.toolbar);
        this.currentActivity.setSupportActionBar(toolbar);
        this.actionBar = this.currentActivity.getSupportActionBar();
    }

    public void setNavigationOnClickListener() {
        this.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(currentActivity);
            }
        });
    }

    public void setNavigationOnClickListener(View.OnClickListener clickListener) {
        this.actionBar.setDisplayHomeAsUpEnabled(true);
        this.actionBar.setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(clickListener);
    }

    public void inflateMenu(@MenuRes int menuRes, Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();

        this.currentActivity.getMenuInflater().inflate(menuRes, menu);
    }

    public void setNavigationDrawer(IUserSession userSession) {
        if (userSession.isUserLoggedIn()) {
            this.buildDrawerForLoggedUser(userSession);
        } else {
            this.buildDrawerForNotLoggedUser();
        }
    }

    private void buildDrawerForLoggedUser(final IUserSession userSession) {
        PrimaryDrawerItem itemSignOut = new PrimaryDrawerItem()
                .withIdentifier(0)
                .withName("Sign out")
                .withIcon(FontAwesome.Icon.faw_sign_in);

        new DrawerBuilder(this.currentActivity)
                .withToolbar(this.toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withTranslucentStatusBar(false)
                .withDrawerWidthDp(260)
                .addDrawerItems(
                        itemSignOut
                )
                .withSelectedItem(0)
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

                        return true;
                    }
                })
                .build();
    }

    private void buildDrawerForNotLoggedUser() {
        PrimaryDrawerItem itemSignIn = new PrimaryDrawerItem()
                .withIdentifier(0)
                .withName("Sign in")
                .withIcon(FontAwesome.Icon.faw_sign_in);

        PrimaryDrawerItem itemSignUp = new PrimaryDrawerItem()
                .withIdentifier(1)
                .withName("Sign up")
                .withIcon(GoogleMaterial.Icon.gmd_person_add);

        new DrawerBuilder(this.currentActivity)
                .withToolbar(this.toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withTranslucentStatusBar(false)
                .withDrawerWidthDp(260)
                .addDrawerItems(
                        itemSignIn,
                        itemSignUp
                )
                .withSelectedItem(0)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 0:
                                Intent signInIntent = new Intent(currentActivity, SignInActivity.class);
                                startActivity(signInIntent);
                                break;
                            case 1:
                                Intent signUpIntent = new Intent(currentActivity, SignUpActivity.class);
                                startActivity(signUpIntent);
                                break;
                        }

                        return true;
                    }
                })
                .build();
    }
}
