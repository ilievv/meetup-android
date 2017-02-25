package com.telerikacademy.meetup.view.venue_details.base;

import android.graphics.Bitmap;
import com.telerikacademy.meetup.model.base.IVenue;
import com.telerikacademy.meetup.ui.fragments.base.IGallery;
import com.telerikacademy.meetup.view.base.BasePresenter;
import com.telerikacademy.meetup.view.base.BaseView;

public interface IVenueDetailsContract {

    interface View extends BaseView<Presenter> {

        void setGallery(IGallery gallery);

        void addPhoto(Bitmap photo);
    }

    interface Presenter extends BasePresenter<View> {

        void setVenue(IVenue venue);

        void subscribe();

        void unsubscribe();

        void loadPhotos();
    }
}
