package com.telerikacademy.meetup.view.venue_details;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.config.di.module.ControllerModule;
import com.telerikacademy.meetup.model.base.IVenue;
import com.telerikacademy.meetup.provider.base.IIntentFactory;
import com.telerikacademy.meetup.ui.component.dialog.base.IDialog;
import com.telerikacademy.meetup.ui.component.dialog.base.IDialogFactory;
import com.telerikacademy.meetup.ui.fragment.base.IGallery;
import com.telerikacademy.meetup.view.review.ReviewActivity;
import com.telerikacademy.meetup.view.venue_details.base.IVenueDetailsContract;
import com.wang.avi.AVLoadingIndicatorView;

import javax.inject.Inject;

public class VenueDetailsContentFragment extends Fragment
        implements IVenueDetailsContract.View {

    private static final String EXTRA_VENUE =
            VenueDetailsContentFragment.class.getCanonicalName() + ".VENUE";

    private static final String PACKAGE_GOOGLE_MAPS = "com.google.android.apps.maps";

    @Inject
    IDialogFactory dialogFactory;

    @Inject
    IIntentFactory intentFactory;

    @BindView(R.id.venue_details_content_loading_indicator)
    AVLoadingIndicatorView contentLoadingIndicator;
    @BindView(R.id.tv_venue_details_title)
    TextView titleTextView;
    @BindView(R.id.tv_venue_details_rating)
    TextView ratingTextView;
    @BindView(R.id.rb_venue_details_rating)
    RatingBar ratingBar;
    @BindView(R.id.tv_venue_details_type)
    TextView typeTextView;

    private IVenueDetailsContract.Presenter presenter;
    private IDialog progressDialog;
    private IGallery gallery;
    private TabLayout galleryIndicator;
    private AVLoadingIndicatorView galleryLoadingIndicator;

    public VenueDetailsContentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_venue_details_content, container, false);
        BaseApplication.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        injectDependencies();

        progressDialog = dialogFactory
                .createDialog()
                .withContent(R.string.dialog_loading_content)
                .withProgress();

        startLoading();
        presenter.loadData();
        presenter.loadPhotos();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isNetworkAvailable()) {
            presenter.subscribe();
        } else {
            stopLoading();
            showErrorMessage();
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isNetworkAvailable()) {
            presenter.subscribe();
        } else {
            stopLoading();
            showErrorMessage();
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void setPresenter(IVenueDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setTitle(CharSequence title) {
        titleTextView.setText(title);
    }

    @Override
    public void setGallery(IGallery gallery) {
        this.gallery = gallery;
    }

    @Override
    public synchronized void addPhoto(final Bitmap photo) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gallery.addPhoto(photo);
                }
            });
        }
    }

    @Override
    public void setRating(float rating) {
        ratingTextView.setText(Float.toString(rating));
        ratingBar.setVisibility(View.VISIBLE);
        ratingBar.setRating(rating);
    }

    @Override
    public void setType(String type) {
        typeTextView.setText(type);
    }

    @Override
    public void setDefaultPhoto() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bitmap photo = BitmapFactory.decodeResource(getResources(),
                        R.drawable.no_image_available);
                gallery.addPhoto(photo);
            }
        });
    }

    @Override
    public void startLoading() {
        progressDialog.show();
    }

    @Override
    public void stopLoading() {
        progressDialog.hide();
    }

    @Override
    public void startGalleryLoadingIndicator() {
        galleryLoadingIndicator.smoothToShow();
    }

    @Override
    public void stopGalleryLoadingIndicator() {
        galleryLoadingIndicator.smoothToHide();
    }

    @Override
    public void startContentLoadingIndicator() {
        contentLoadingIndicator.smoothToShow();
    }

    @Override
    public void stopContentLoadingIndicator() {
        contentLoadingIndicator.smoothToHide();
    }

    @Override
    public void showGalleryIndicator() {
        galleryIndicator.setVisibility(View.VISIBLE);
        if (getContext() != null) {
            Animation expandIn = AnimationUtils
                    .loadAnimation(getContext(), R.anim.expand_in);
            galleryIndicator.startAnimation(expandIn);
        }
    }

    @Override
    public void startNavigation(Uri uri) {
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
        mapIntent.setPackage(PACKAGE_GOOGLE_MAPS);
        startActivity(mapIntent);
    }

    @Override
    public void showErrorMessage() {
        if (getContext() != null) {
            Toast.makeText(getContext(), "An error has occured", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void startDialer(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            Toast.makeText(getContext(), "Phone number has not been provided", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent dialerIntent = new Intent(Intent.ACTION_DIAL);
        String formattedPhoneNumber = String.format("tel:%s", phoneNumber.trim());
        dialerIntent.setData(Uri.parse(formattedPhoneNumber));
        startActivity(dialerIntent);
    }

    @Override
    public void startWebsite(Uri websiteUri) {
        if (websiteUri == null) {
            Toast.makeText(getContext(), "Website has not been provided", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(websiteUri);
        startActivity(browserIntent);
    }

    @Override
    public void redirectToReview(IVenue venue) {
        Intent reviewIntent = intentFactory
                .createIntentToFront(ReviewActivity.class)
                .putExtra(EXTRA_VENUE, venue);
        startActivity(reviewIntent);
    }

    @OnClick(R.id.btn_venue_details_call)
    void onCallButtonClick() {
        presenter.onCallButtonClick();
    }

    @OnClick(R.id.btn_venue_details_save)
    void onSaveButtonClick() {
        // TODO: Implement
    }

    @OnClick(R.id.btn_venue_details_review)
    void onReviewButtonClick() {
        presenter.onReviewButtonClick();
    }

    @OnClick(R.id.btn_venue_details_website)
    void onWebsiteButtonClick() {
        presenter.onWebsiteButtonClick();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private void injectDependencies() {
        BaseApplication
                .from(getContext())
                .getComponent()
                .getControllerComponent(new ControllerModule(
                        getActivity(), getFragmentManager()
                ))
                .inject(this);

        galleryIndicator = (TabLayout) getActivity()
                .findViewById(R.id.gallery_indicator);

        galleryLoadingIndicator = (AVLoadingIndicatorView) getActivity()
                .findViewById(R.id.gallery_loading_indicator);
    }
}
