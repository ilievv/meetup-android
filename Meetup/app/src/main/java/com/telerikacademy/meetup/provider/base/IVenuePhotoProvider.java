package com.telerikacademy.meetup.provider.base;

import android.graphics.Bitmap;
import io.reactivex.Observable;

public interface IVenuePhotoProvider {

    Observable<Bitmap> getPhotos(String placeId);

    void connect();

    void disconnect();

    void setOnConnectionFailedListener(IOnConnectionFailedListener onConnectionFailedListener);

    interface IOnConnectionFailedListener {
        void onConnectionFailed(String errorMessage);
    }
}
