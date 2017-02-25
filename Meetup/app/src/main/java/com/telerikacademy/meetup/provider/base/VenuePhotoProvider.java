package com.telerikacademy.meetup.provider.base;

import android.graphics.Bitmap;
import io.reactivex.Observable;

public abstract class VenuePhotoProvider {

    private IOnConnectionFailedListener onConnectionFailedListener;

    public abstract Observable<Bitmap> getPhotos(String placeId);

    public abstract void connect();

    public abstract void disconnect();

    public void setOnConnectionFailedListener(IOnConnectionFailedListener onConnectionFailedListener) {
        this.onConnectionFailedListener = onConnectionFailedListener;
    }

    protected IOnConnectionFailedListener getOnConnectionFailedListener() {
        return onConnectionFailedListener;
    }

    public interface IOnConnectionFailedListener {
        void onConnectionFailed(String errorMessage);
    }
}
