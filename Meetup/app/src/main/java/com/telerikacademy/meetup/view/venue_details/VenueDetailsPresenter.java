package com.telerikacademy.meetup.view.venue_details;

import android.graphics.Bitmap;
import com.telerikacademy.meetup.model.base.IVenue;
import com.telerikacademy.meetup.provider.base.IVenuePhotoProvider;
import com.telerikacademy.meetup.view.venue_details.base.IVenueDetailsContract;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;

public class VenueDetailsPresenter implements IVenueDetailsContract.Presenter {

    private IVenueDetailsContract.View view;
    private IVenue venue;

    private final IVenuePhotoProvider venuePhotoProvider;

    @Inject
    public VenueDetailsPresenter(IVenuePhotoProvider venuePhotoProvider) {
        this.venuePhotoProvider = venuePhotoProvider;
    }

    @Override
    public void setView(IVenueDetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void setVenue(IVenue venue) {
        this.venue = venue;
    }

    @Override
    public void subscribe() {
        venuePhotoProvider.connect();
    }

    @Override
    public void unsubscribe() {
        venuePhotoProvider.disconnect();
    }

    @Override
    public void loadPhotos() {
        if (venue == null) {
            return;
        }

        venuePhotoProvider
                .getPhotos(venue.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        int a = 5;
                    }

                    @Override
                    public void onNext(Bitmap value) {
                        view.addPhoto(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        int b = 5;
                    }

                    @Override
                    public void onComplete() {
                        int c = 5;
                    }
                });
    }
}
