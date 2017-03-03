package com.telerikacademy.meetup.view.review;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.config.di.module.ControllerModule;
import com.telerikacademy.meetup.model.base.IVenue;
import com.telerikacademy.meetup.view.review.base.IReviewContract;
import com.telerikacademy.meetup.view.venue_details.VenueDetailsContentFragment;

import javax.inject.Inject;

public class ReviewActivity extends AppCompatActivity {

    private static final String EXTRA_VENUE =
            VenueDetailsContentFragment.class.getCanonicalName() + ".VENUE";

    private ReviewContentFragment content;
    private IVenue currentVenue;

    @Inject
    IReviewContract.Presenter presenter;
    @Inject
    FragmentManager fragmentManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        injectDependencies();
        initialize();
        setup();
    }

    private void initialize() {
        currentVenue = (IVenue) getIntent().getSerializableExtra(EXTRA_VENUE);

        content = (ReviewContentFragment) fragmentManager
                .findFragmentById(R.id.fragment_review_content);
    }

    private void setup() {
        presenter.setView(content);
        presenter.setVenue(currentVenue);
        content.setPresenter(presenter);
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
}
