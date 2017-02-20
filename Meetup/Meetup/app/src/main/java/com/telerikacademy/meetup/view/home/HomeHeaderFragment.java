package com.telerikacademy.meetup.view.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.config.di.module.ControllerModule;
import com.telerikacademy.meetup.ui.components.dialog.base.Dialog;
import com.telerikacademy.meetup.ui.components.dialog.base.IDialogFactory;
import com.telerikacademy.meetup.ui.fragments.ToolbarFragment;
import com.telerikacademy.meetup.util.base.IPermissionHandler;
import com.telerikacademy.meetup.view.home.base.IHomeHeaderContract;

import javax.inject.Inject;

public class HomeHeaderFragment extends ToolbarFragment
        implements IHomeHeaderContract.View {

    @Inject
    IPermissionHandler permissionHandler;
    @Inject
    IDialogFactory dialogFactory;

    @BindView(R.id.tv_location_title)
    TextView locationTitle;
    @BindView(R.id.tv_location_subtitle)
    TextView locationSubtitle;

    private IHomeHeaderContract.Presenter presenter;
    private LocationManager locationManager;

    public HomeHeaderFragment() {
    }

    @Override
    public void setPresenter(IHomeHeaderContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_header, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        injectDependencies();

        locationManager = (LocationManager) getActivity()
                .getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribe();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    public void setTitle(String title) {
        locationTitle.setText(title);
    }

    @Override
    public void setSubtitle(String subtitle) {
        locationSubtitle.setText(subtitle);
    }

    public void updateLocation() {
        presenter.update();
    }

    @Override
    public boolean checkPermissions() {
        return permissionHandler.checkPermissions(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    public void requestPermissions() {
        permissionHandler.requestPermissions(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    public void showEnableLocationDialog() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            dialogFactory
                    .createDialog()
                    .withTitle(R.string.enable_location_dialog_title)
                    .withPositiveButton(R.string.enable_location_dialog_positive, new Dialog.OnOptionButtonClick() {
                        @Override
                        public void onClick() {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .withNegativeButton(R.string.enable_location_dialog_negative, null)
                    .withIcon(R.drawable.ic_location_gps)
                    .show();
        }
    }

    private void injectDependencies() {
        BaseApplication
                .from(getContext())
                .getComponent()
                .getControllerComponent(new ControllerModule(
                        getActivity(), getFragmentManager()
                ))
                .inject(this);
    }
}
