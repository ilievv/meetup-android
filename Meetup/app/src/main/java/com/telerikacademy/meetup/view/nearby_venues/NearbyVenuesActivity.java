package com.telerikacademy.meetup.view.nearby_venues;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.config.di.module.ControllerModule;
import com.telerikacademy.meetup.model.base.IVenue;
import com.telerikacademy.meetup.network.base.IVenueData;
import com.telerikacademy.meetup.ui.components.dialog.base.Dialog;
import com.telerikacademy.meetup.ui.components.dialog.base.IDialogFactory;
import com.telerikacademy.meetup.ui.fragments.ToolbarFragment;
import com.telerikacademy.meetup.ui.fragments.base.ISearchBar;
import com.telerikacademy.meetup.view.home.HomeContentFragment;
import com.telerikacademy.meetup.view.nearby_venues.base.INearbyVenuesContract;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class NearbyVenuesActivity extends AppCompatActivity {

    private static final String EXTRA_VENUE_TYPE =
            HomeContentFragment.class.getCanonicalName() + ".VENUE_TYPE";
    private static final String EXTRA_CURRENT_LATITUDE =
            HomeContentFragment.class.getCanonicalName() + ".EXTRA_CURRENT_LATITUDE";
    private static final String EXTRA_CURRENT_LONGITUDE =
            HomeContentFragment.class.getCanonicalName() + ".EXTRA_CURRENT_LONGITUDE";

    @Inject
    INearbyVenuesContract.Presenter presenter;
    @Inject
    FragmentManager fragmentManager;
    @Inject
    IDialogFactory dialogFactory;
    @Inject
    IVenueData venueData;

    private NearbyVenuesRecyclerAdapter recyclerAdapter;
    private NearbyVenuesContentFragment content;
    private ToolbarFragment toolbar;
    private Dialog progressDialog;
    private ISearchBar searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_venues);
        injectDependencies();

        toolbar = (ToolbarFragment) fragmentManager
                .findFragmentById(R.id.fragment_nearby_venues_toolbar);

        content = (NearbyVenuesContentFragment) fragmentManager.
                findFragmentById(R.id.fragment_nearby_venues_content);

        searchBar = (ISearchBar) fragmentManager
                .findFragmentById(R.id.fragment_nearby_venues_search_header);

        progressDialog = dialogFactory
                .createDialog()
                .withContent(R.string.dialog_loading_content)
                .withProgress();

        double latitude = getIntent().getDoubleExtra(EXTRA_CURRENT_LATITUDE, 0);
        double longitude = getIntent().getDoubleExtra(EXTRA_CURRENT_LONGITUDE, 0);
        String venueType = getIntent().getStringExtra(EXTRA_VENUE_TYPE);

        setup();
        showNearbyVenues(latitude, longitude, 50, venueType);
    }

    @Override
    protected void onStart() {
        super.onStart();
        toolbar.setNavigationDrawer(R.layout.activity_nearby_venues);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        toolbar.inflateMenu(R.menu.main, menu, getMenuInflater());
        return true;
    }

    private void setup() {
        content.setPresenter(presenter);
        presenter.setView(content);

        recyclerAdapter = new NearbyVenuesRecyclerAdapter(new ArrayList<IVenue>());
        content.setAdapter(recyclerAdapter);
        searchBar.setFilter(recyclerAdapter);
    }

    private void showNearbyVenues(double latitude, double longitude, int radius, String venueType) {
        venueData.getNearby(latitude, longitude, radius)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<IVenue>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        progressDialog.show();
                    }

                    @Override
                    public void onNext(List<IVenue> value) {
                        recyclerAdapter.swapData(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        progressDialog.hide();
                    }
                });
    }

    private void injectDependencies() {
        BaseApplication
                .from(this)
                .getComponent()
                .getControllerComponent(new ControllerModule(
                        this, getSupportFragmentManager()
                ))
                .inject(this);
    }
}
