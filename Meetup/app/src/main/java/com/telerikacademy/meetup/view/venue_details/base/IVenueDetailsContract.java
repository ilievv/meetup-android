package com.telerikacademy.meetup.view.venue_details.base;

import android.graphics.Bitmap;
import android.net.Uri;

import com.telerikacademy.meetup.model.base.IVenue;
import com.telerikacademy.meetup.model.base.IVenueDetail;
import com.telerikacademy.meetup.ui.fragment.base.IGallery;
import com.telerikacademy.meetup.view.base.BasePresenter;
import com.telerikacademy.meetup.view.base.BaseView;

public interface IVenueDetailsContract {

    interface View extends BaseView<Presenter> {

        void setTitle(CharSequence title);

        void setGallery(IGallery gallery);

        void addPhoto(Bitmap photo);

        void setRating(float rating);

        void setType(String type);

        void setDefaultPhoto();

        void startLoading();

        void stopLoading();

        void startGalleryLoadingIndicator();

        void stopGalleryLoadingIndicator();

        void startContentLoadingIndicator();

        void stopContentLoadingIndicator();

        void showGalleryIndicator();

        void startNavigation(Uri uri);

        void showErrorMessage();

        void startDialer(String phoneNumber);

        void startWebsite(Uri websiteUri);

        void redirectToReview(IVenueDetail venueDetail);
    }

    interface Presenter extends BasePresenter<View> {

        void setVenueId(String venueId);

        void subscribe();

        void unsubscribe();

        void loadData();

        void loadPhotos();

        void onNavigationButtonClick();

        void onCallButtonClick();

        void onWebsiteButtonClick();

        void onReviewButtonClick();
    }
}
