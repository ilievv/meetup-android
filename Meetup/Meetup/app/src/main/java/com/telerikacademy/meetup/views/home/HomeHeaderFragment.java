package com.telerikacademy.meetup.views.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.models.base.ILocation;
import com.telerikacademy.meetup.providers.base.LocationProvider;
import com.telerikacademy.meetup.ui.components.dialog.base.Dialog;
import com.telerikacademy.meetup.ui.components.dialog.base.IDialogFactory;
import com.telerikacademy.meetup.ui.fragments.ToolbarFragment;
import com.telerikacademy.meetup.utils.base.IPermissionHandler;
import com.telerikacademy.meetup.views.home.base.IHomeHeader;

import javax.inject.Inject;

public class HomeHeaderFragment extends ToolbarFragment
        implements IHomeHeader {

    private static final String TAG = HomeHeaderFragment.class.getSimpleName();

    @Inject
    LocationProvider locationProvider;
    @Inject
    IPermissionHandler permissionHandler;
    @Inject
    IDialogFactory dialogFactory;

    @BindView(R.id.tv_location_title)
    TextView locationTitle;
    @BindView(R.id.tv_location_subtitle)
    TextView locationSubtitle;

    private ILocation currentLocation;

    public HomeHeaderFragment() {
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
        dialogFactory.initialize(getActivity());

        locationProvider.setOnLocationChangeListener(new LocationProvider.IOnLocationChangeListener() {
            @Override
            public void onLocationChange(ILocation location) {
                currentLocation = location;
            }
        });
        locationProvider.setOnConnectedListener(new LocationProvider.IOnConnectedListener() {
            @Override
            public void onConnected(ILocation location) {
                currentLocation = location;
                setTextViewTitle(currentLocation);
            }
        });
        locationProvider.setOnConnectionFailedListener(new LocationProvider.IOnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(String errorMessage) {
                setTextViewTitle(currentLocation);
                Log.e(TAG, errorMessage);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        locationProvider.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        locationProvider.disconnect();
    }

    public void updateLocation() {
        requestPermissions();
        showEnableLocationDialog();

        if (checkPermissions() &&
                !locationProvider.isConnected() &&
                !locationProvider.isConnecting()) {

            locationProvider.connect();
        }

        setTextViewTitle(currentLocation);
    }

    protected boolean checkPermissions() {
        return permissionHandler.checkPermissions(getActivity(),
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    protected void requestPermissions() {
        permissionHandler.requestPermissions(getActivity(),
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    protected void showEnableLocationDialog() {
        LocationManager locationManager = (LocationManager) getActivity()
                .getSystemService(Context.LOCATION_SERVICE);

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

    private void setTextViewTitle(ILocation location) {
        final String LOCATION_NOT_FOUND = "Unknown location";

        if (location == null) {
            locationTitle.setText(LOCATION_NOT_FOUND);
            return;
        }

        String locality = location.getLocality();
        String thoroughfare = location.getThoroughfare();
        String subThoroughfare = location.getSubThoroughfare();

        locality = locality == null ? "" : locality;
        thoroughfare = thoroughfare == null ? "" : thoroughfare;
        subThoroughfare = subThoroughfare == null ? "" : subThoroughfare;

        if (locality.isEmpty() && thoroughfare.isEmpty()) {
            locationTitle.setText(LOCATION_NOT_FOUND);
        } else if (locality.isEmpty()) {
            locationTitle.setText(thoroughfare);
            locationSubtitle.setText(subThoroughfare);
        } else {
            locationTitle.setText(locality);

            String subtitle;
            if (!thoroughfare.isEmpty()) {
                subtitle = thoroughfare;

                if (!subThoroughfare.isEmpty()) {
                    subtitle = String.format("%s, %s", thoroughfare, subThoroughfare);
                }
            } else {
                subtitle = subThoroughfare;
            }

            locationSubtitle.setText(subtitle);
        }
    }

    private void injectDependencies() {
        ((BaseApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);
    }
}
