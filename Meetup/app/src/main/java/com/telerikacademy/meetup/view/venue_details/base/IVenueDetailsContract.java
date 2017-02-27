package com.telerikacademy.meetup.view.venue_details.base;

import android.graphics.Bitmap;
import android.net.Uri;
import com.telerikacademy.meetup.model.base.IVenue;
import com.telerikacademy.meetup.ui.fragment.base.IGallery;
import com.telerikacademy.meetup.view.base.BasePresenter;
import com.telerikacademy.meetup.view.base.BaseView;

public interface IVenueDetailsContract {

    interface View extends BaseView<Presenter> {

        void setTitle(CharSequence title);

        void setGallery(IGallery gallery);

        void addPhoto(Bitmap photo);

        void setDefaultPhoto();

        void startLoading();

        void stopLoading();

        void showErrorMessage();

        void startNavigation(Uri uri);
    }

    interface Presenter extends BasePresenter<View> {

        void setVenue(IVenue venue);

        void subscribe();

        void unsubscribe();

        void loadData();

        void loadPhotos();

        void onNavigationButtonClick();
    }
}
