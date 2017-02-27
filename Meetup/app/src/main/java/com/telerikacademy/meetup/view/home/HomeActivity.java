package com.telerikacademy.meetup.view.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.OnClick;

import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.config.di.module.ControllerModule;
import com.telerikacademy.meetup.data.local.base.IRecentVenue;
import com.telerikacademy.meetup.model.base.ILocation;
import com.telerikacademy.meetup.provider.base.ILocationAware;
import com.telerikacademy.meetup.ui.fragment.RecentVenuesFragment;
import com.telerikacademy.meetup.ui.fragment.SearchFragment;
import com.telerikacademy.meetup.view.home.base.IHomeContentContract;
import com.telerikacademy.meetup.view.home.base.IHomeHeaderContract;

import java.util.List;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity
        implements ILocationAware {

    @Inject
    IHomeContentContract.Presenter contentPresenter;
    @Inject
    IHomeHeaderContract.Presenter headerPresenter;
    @Inject
    FragmentManager fragmentManager;

    private HomeContentFragment content;
    private HomeHeaderFragment header;
    private RecentVenuesFragment recentVenuesFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        injectDependencies();
        initialize();
        setup();
    }

    @Override
    public void onStart() {
        super.onStart();
        header.setNavigationDrawer(R.layout.activity_home);

        showRecentVenues();
    }

    @Override
    public ILocation getLocation() {
        ILocation currentLocation = headerPresenter.getLocation();
        return currentLocation;
    }

    @OnClick(R.id.btn_update_location)
    void updateLocation() {
        header.updateLocation();
    }

    private void initialize() {
        content = (HomeContentFragment) fragmentManager
                .findFragmentById(R.id.fragment_home_content);

        header = (HomeHeaderFragment) fragmentManager
                .findFragmentById(R.id.fragment_home_header);

        recentVenuesFragment = (RecentVenuesFragment) fragmentManager
                .findFragmentById(R.id.fragment_recent_venues);
    }

    private void setup() {
        contentPresenter.setView(content);
        content.setPresenter(contentPresenter);

        headerPresenter.setView(header);
        header.setPresenter(headerPresenter);
    }

    private void injectDependencies() {
        BaseApplication
                .bind(this)
                .from(this)
                .getComponent()
                .getControllerComponent(new ControllerModule(
                        this, getSupportFragmentManager()
                ))
                .inject(this);
    }

    private synchronized void showRecentVenues(){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
             recentVenuesFragment.showRecentVenues();
            }
        });
    }
}


