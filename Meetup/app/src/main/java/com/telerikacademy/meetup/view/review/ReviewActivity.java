package com.telerikacademy.meetup.view.review;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.config.di.module.ControllerModule;
import com.telerikacademy.meetup.view.review.base.IReviewContract;
import com.telerikacademy.meetup.view.venue_details.VenueDetailsActivity;

import javax.inject.Inject;

public class ReviewActivity extends AppCompatActivity {

    private static final String VENUE_DETAILS_ID =
            ReviewActivity.class.getCanonicalName() + ".VENUE_DETAILS_ID";
    private static final String VENUE_DETAILS_NAME =
            ReviewActivity.class.getCanonicalName() + ".VENUE_DETAILS_NAME";
    private static final String VENUE_DETAILS_ADDRESS =
            ReviewActivity.class.getCanonicalName() + ".VENUE_DETAILS_ADDRESS";

    private String currentVenueId;
    private String currentVenueName;
    private String currentVenueAddress;
    private ReviewContentFragment content;

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
        currentVenueId = getIntent().getStringExtra(VENUE_DETAILS_ID);
        currentVenueName = getIntent().getStringExtra(VENUE_DETAILS_NAME);
        currentVenueAddress = getIntent().getStringExtra(VENUE_DETAILS_ADDRESS);

        content = (ReviewContentFragment) fragmentManager
                .findFragmentById(R.id.fragment_review_content);
    }

    private void setup() {
        presenter.setView(content);
        presenter.setVenueId(currentVenueId);
        presenter.setVenueName(currentVenueName);
        presenter.setVenueAddress(currentVenueAddress);

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
