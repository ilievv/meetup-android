package com.telerikacademy.meetup.view.venue_details;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.config.di.module.ControllerModule;
import com.telerikacademy.meetup.model.base.IVenue;
import com.telerikacademy.meetup.ui.fragments.GalleryFragment;
import com.telerikacademy.meetup.view.nearby_venues.NearbyVenuesRecyclerAdapter;
import com.telerikacademy.meetup.view.venue_details.base.IVenueDetailsContract;

import javax.inject.Inject;

public class VenueDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_CURRENT_VENUE =
            NearbyVenuesRecyclerAdapter.class.getCanonicalName() + ".VenueHolder.CURRENT_VENUE";

    @Inject
    IVenueDetailsContract.Presenter presenter;
    @Inject
    FragmentManager fragmentManager;

    private GalleryFragment galleryFragment;
    private VenueDetailsContentFragment content;

    private IVenue currentVenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_details);
        injectDependencies();
        initialize();
        setup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void initialize() {
        currentVenue = (IVenue) getIntent()
                .getSerializableExtra(EXTRA_CURRENT_VENUE);

        content = (VenueDetailsContentFragment) fragmentManager
                .findFragmentById(R.id.fragment_venue_details_content);

        galleryFragment = (GalleryFragment) fragmentManager
                .findFragmentById(R.id.fragment_venue_details_gallery);
    }

    private void setup() {
        presenter.setView(content);
        presenter.setVenue(currentVenue);
        content.setPresenter(presenter);
        content.setGallery(galleryFragment);
        setTitle(currentVenue.getName());

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        Drawable actionBarBackground = getDrawable(R.drawable.gradient_black_transparent);
        actionBar.setBackgroundDrawable(actionBarBackground);
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
