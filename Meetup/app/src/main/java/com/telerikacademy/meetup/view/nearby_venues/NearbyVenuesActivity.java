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
import com.telerikacademy.meetup.ui.fragments.ToolbarFragment;
import com.telerikacademy.meetup.ui.fragments.base.ISearchBar;
import com.telerikacademy.meetup.view.nearby_venues.base.INearbyVenuesContract;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.List;

public class NearbyVenuesActivity extends AppCompatActivity {

    @Inject
    INearbyVenuesContract.Presenter presenter;
    @Inject
    FragmentManager fragmentManager;
    @Inject
    IVenueData venueData;

    private NearbyVenuesContentFragment content;
    private ToolbarFragment toolbar;
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
        content.setPresenter(presenter);
        presenter.setView(content);

        searchBar = (ISearchBar) fragmentManager
                .findFragmentById(R.id.fragment_nearby_venues_search_header);

        venueData.getNearby(42.692923, 23.320057, 50)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<IVenue>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // start loading screen
                    }

                    @Override
                    public void onNext(List<IVenue> value) {
                        // stop loading screen
                        NearbyVenuesRecyclerAdapter recyclerAdapter = new NearbyVenuesRecyclerAdapter(value);
                        content.setAdapter(recyclerAdapter);
                        searchBar.setFilter(recyclerAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
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
