package com.telerikacademy.meetup.view.review;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.config.di.module.ControllerModule;
import com.telerikacademy.meetup.model.base.IVenue;
import com.telerikacademy.meetup.ui.fragment.base.IToolbar;
import com.telerikacademy.meetup.view.review.base.IReviewContract;
import com.telerikacademy.meetup.view.venue_details.VenueDetailsContentFragment;

import javax.inject.Inject;

public class ReviewActivity extends AppCompatActivity {

    private static final String EXTRA_VENUE =
            VenueDetailsContentFragment.class.getCanonicalName() + ".VENUE";

    @Inject
    IReviewContract.Presenter presenter;
    @Inject
    FragmentManager fragmentManager;

    private IToolbar toolbar;
    private ReviewContentFragment content;
    private IVenue currentVenue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        injectDependencies();
        initialize();
        setup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.setBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void initialize() {
        currentVenue = (IVenue) getIntent().getSerializableExtra(EXTRA_VENUE);

        toolbar = (IToolbar) fragmentManager
                .findFragmentById(R.id.fragment_review_toolbar);

        content = (ReviewContentFragment) fragmentManager
                .findFragmentById(R.id.fragment_review_content);
    }

    private void setup() {
        setTitle(currentVenue.getName());
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
