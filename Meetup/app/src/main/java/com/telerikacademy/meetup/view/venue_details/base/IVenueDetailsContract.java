package com.telerikacademy.meetup.view.venue_details.base;

import android.graphics.Bitmap;
import com.telerikacademy.meetup.view.base.BasePresenter;
import com.telerikacademy.meetup.view.base.BaseView;

public interface IVenueDetailsContract {

    interface View extends BaseView<Presenter> {

        void addPhoto(Bitmap photo);
    }

    interface Presenter extends BasePresenter<View> {

        void subscribe();

        void unsubscribe();

        void loadPhotos(String venueId);
    }
}
