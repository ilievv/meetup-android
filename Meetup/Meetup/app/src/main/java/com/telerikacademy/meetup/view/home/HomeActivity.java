package com.telerikacademy.meetup.view.home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.ui.fragments.base.IToolbar;
import com.telerikacademy.meetup.view.home.base.HomeContentContract;
import com.telerikacademy.meetup.view.home.base.HomeHeaderContract;
import com.telerikacademy.meetup.view.home.base.IHomeHeader;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity {

    @Inject
    HomeContentContract.Presenter contentPresenter;
    @Inject
    HomeHeaderContract.Presenter headerPresenter;

    @BindView(R.id.btn_update_location)
    FloatingActionButton updateLocationButton;

    private FragmentManager fragmentManager;
    private HomeContentFragment content;
    private IHomeHeader header;
    private IToolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        injectDependencies();

        fragmentManager = getSupportFragmentManager();

        content = (HomeContentFragment) fragmentManager
                .findFragmentById(R.id.fragment_home_content);
        contentPresenter.setActivity(this);
        content.setPresenter(contentPresenter);

        toolbar = (IToolbar) fragmentManager
                .findFragmentById(R.id.fragment_home_header);

        header = (IHomeHeader) fragmentManager
                .findFragmentById(R.id.fragment_home_header);
        headerPresenter.setActivity(this);
        header.setPresenter(headerPresenter);
    }

    @OnClick(R.id.btn_update_location)
    void updateLocation() {
        header.updateLocation();
    }

    protected void onStart() {
        super.onStart();
        toolbar.setNavigationDrawer(R.layout.activity_home);
    }

    private void injectDependencies() {
        BaseApplication
                .from(this)
                .getComponent()
                .inject(this);

        ButterKnife.bind(this);
    }
}
