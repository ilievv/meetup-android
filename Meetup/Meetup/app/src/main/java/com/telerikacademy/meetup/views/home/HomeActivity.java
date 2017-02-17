package com.telerikacademy.meetup.views.home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.ui.fragments.base.IToolbar;
import com.telerikacademy.meetup.views.home.base.IHomeHeader;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.btn_update_location)
    FloatingActionButton updateLocationButton;

    private FragmentManager fragmentManager;
    private IHomeHeader homeHeader;
    private IToolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        fragmentManager = getSupportFragmentManager();
        toolbar = (IToolbar) fragmentManager
                .findFragmentById(R.id.fragment_home_header);
        homeHeader = (IHomeHeader) fragmentManager
                .findFragmentById(R.id.fragment_home_header);
    }

    @OnClick(R.id.btn_update_location)
    void updateLocation() {
        homeHeader.updateLocation();
    }

    protected void onStart() {
        super.onStart();
        toolbar.setNavigationDrawer(R.layout.activity_home);
    }
}
