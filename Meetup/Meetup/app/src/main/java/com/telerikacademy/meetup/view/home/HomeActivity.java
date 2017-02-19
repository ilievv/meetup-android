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
import com.telerikacademy.meetup.config.di.module.ControllerModule;
import com.telerikacademy.meetup.ui.fragments.base.IToolbar;
import com.telerikacademy.meetup.view.home.base.IHomeContentContract;
import com.telerikacademy.meetup.view.home.base.IHomeHeaderContract;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity {

    @Inject
    IHomeContentContract.Presenter contentPresenter;
    @Inject
    IHomeHeaderContract.Presenter headerPresenter;

    @BindView(R.id.btn_update_location)
    FloatingActionButton updateLocationButton;

    private FragmentManager fragmentManager;
    private HomeContentFragment content;
    private HomeHeaderFragment header;
    private IToolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        injectDependencies();

        fragmentManager = getSupportFragmentManager();

        toolbar = (IToolbar) fragmentManager
                .findFragmentById(R.id.fragment_home_header);

        content = (HomeContentFragment) fragmentManager
                .findFragmentById(R.id.fragment_home_content);
        contentPresenter.setView(content);
        content.setPresenter(contentPresenter);

        header = (HomeHeaderFragment) fragmentManager
                .findFragmentById(R.id.fragment_home_header);
        headerPresenter.setView(header);
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
                .getControllerComponent(new ControllerModule(
                        this, getSupportFragmentManager()
                ))
                .inject(this);

        ButterKnife.bind(this);
    }
}
