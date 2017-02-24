package com.telerikacademy.meetup.provider.base;

import android.graphics.Bitmap;
import io.reactivex.Observable;

import java.util.List;

public abstract class VenuePhotoProvider {

    private IOnConnectionFailedListener onConnectionFailedListener;

    public abstract Observable<List<Bitmap>> getPhotos(String placeId);

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
