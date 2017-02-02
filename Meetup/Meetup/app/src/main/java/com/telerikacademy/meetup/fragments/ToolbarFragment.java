package com.telerikacademy.meetup.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.activities.NearbyVenuesActivity;
import com.telerikacademy.meetup.activities.VenueDetailsActivity;
import com.telerikacademy.meetup.interfaces.IMenuInflater;
import com.telerikacademy.meetup.models.Venue;

public class ToolbarFragment extends Fragment
        implements IMenuInflater {

    private Toolbar toolbar;
    private ActionBar actionBar;
    private AppCompatActivity currentActivity;

    public ToolbarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tool_bar, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!(getActivity() instanceof AppCompatActivity)) {
            throw new ClassCastException("Activity must be of type AppCompatActivity in order to support custom Toolbar.");
        }

        this.currentActivity = (AppCompatActivity) getActivity();
        this.toolbar = (Toolbar) this.currentActivity.findViewById(R.id.tool_bar);
        this.currentActivity.setSupportActionBar(toolbar);
        this.actionBar = this.currentActivity.getSupportActionBar();
        this.setDrawer();
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

    private void setDrawer(){
        PrimaryDrawerItem itemLogin = new PrimaryDrawerItem().withIdentifier(1).withName("Login");
        PrimaryDrawerItem itemRegister = new PrimaryDrawerItem().withIdentifier(2).withName("Register");
        final Activity currentActivity = this.currentActivity;
        Drawer result = new DrawerBuilder()
                .withActivity(this.currentActivity)
                .withToolbar(this.toolbar)
                .addDrawerItems(
                        itemLogin,
                        new DividerDrawerItem(),
                        itemRegister
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch(position) {
                            case 1:
                                Intent a = new Intent(currentActivity, NearbyVenuesActivity.class);
                                startActivity(a);
                                break;
                            case 2:
                                Intent b = new Intent(currentActivity, VenueDetailsActivity.class);
                                startActivity(b);
                                break;
                            default:
                        }

                        return true;
                    }
                })
                .build();
    }
}
