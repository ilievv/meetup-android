package com.telerikacademy.meetup.provider.base;

import android.graphics.Bitmap;
import android.os.Bundle;
import io.reactivex.Observable;

import java.util.List;

public abstract class VenuePhotoProvider {

    private IOnConnectedListener onConnectedListener;
    private IOnConnectionFailedListener onConnectionFailedListener;

    public abstract Observable<List<Bitmap>> getPhotos(String placeId);

    public void setOnConnectedListener(IOnConnectedListener onConnectedListener) {
        this.onConnectedListener = onConnectedListener;
    }

    public void setOnConnectionFailedListener(IOnConnectionFailedListener onConnectionFailedListener) {
        this.onConnectionFailedListener = onConnectionFailedListener;
    }

    protected IOnConnectedListener getOnConnectedListener() {
        return onConnectedListener;
    }

    protected IOnConnectionFailedListener getOnConnectionFailedListener() {
        return onConnectionFailedListener;
    }

    public interface IOnConnectedListener {
        void onConnected(Bundle bundle);
    }

    public interface IOnConnectionFailedListener {
        void onConnectionFailed(String errorMessage);
    }
}
