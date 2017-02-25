package com.telerikacademy.meetup.view.venue_details;

import android.graphics.Bitmap;
import com.telerikacademy.meetup.model.base.IVenue;
import com.telerikacademy.meetup.provider.base.VenuePhotoProvider;
import com.telerikacademy.meetup.view.venue_details.base.IVenueDetailsContract;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.List;

public class VenueDetailsPresenter implements IVenueDetailsContract.Presenter {

    private IVenueDetailsContract.View view;
    private IVenue venue;

    private final VenuePhotoProvider venuePhotoProvider;

    @Inject
    public VenueDetailsPresenter(VenuePhotoProvider venuePhotoProvider) {
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
                .subscribe(new Observer<List<Bitmap>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<Bitmap> photos) {
                        for (final Bitmap photo : photos) {
                            view.addPhoto(photo);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
