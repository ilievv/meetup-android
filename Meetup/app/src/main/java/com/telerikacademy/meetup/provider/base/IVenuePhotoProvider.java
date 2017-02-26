package com.telerikacademy.meetup.provider.base;

import android.graphics.Bitmap;
import io.reactivex.Flowable;

public interface IVenuePhotoProvider {

    Flowable<Bitmap> getPhotos(final String placeId);

    void connect();

    void disconnect();

    void setOnConnectionFailedListener(IOnConnectionFailedListener onConnectionFailedListener);

    interface IOnConnectionFailedListener {
        void onConnectionFailed(String errorMessage);
    }
}
