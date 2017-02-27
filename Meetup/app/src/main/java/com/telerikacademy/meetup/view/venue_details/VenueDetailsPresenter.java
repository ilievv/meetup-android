package com.telerikacademy.meetup.view.venue_details;

import android.graphics.Bitmap;
import com.telerikacademy.meetup.model.base.IVenue;
import com.telerikacademy.meetup.model.base.IVenueDetail;
import com.telerikacademy.meetup.provider.base.IVenueDetailsProvider;
import com.telerikacademy.meetup.view.venue_details.base.IVenueDetailsContract;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import javax.inject.Inject;

public class VenueDetailsPresenter implements IVenueDetailsContract.Presenter {

    private IVenueDetailsContract.View view;
    private IVenue venue;

    private final IVenueDetailsProvider venueDetailsProvider;

    @Inject
    public VenueDetailsPresenter(IVenueDetailsProvider venueDetailsProvider) {
        this.venueDetailsProvider = venueDetailsProvider;
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
        venueDetailsProvider.connect();
    }

    @Override
    public void unsubscribe() {
        venueDetailsProvider.disconnect();
    }

    @Override
    public void loadData() {
        if (venue == null) {
            return;
        }

        venueDetailsProvider
                .getById(venue.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<IVenueDetail>() {
                    @Override
                    public void accept(IVenueDetail venue) throws Exception {
                        view.setTitle(venue.getName());
                    }
                });
    }

    @Override
    public void loadPhotos() {
        if (venue == null) {
            return;
        }

        venueDetailsProvider
                .getPhotos(venue.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {

                    private static final int ITEMS_PER_REQUEST = 1;

                    private Subscription subscription;
                    private boolean hasPhoto = false;

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        this.subscription = subscription;
                        subscription.request(ITEMS_PER_REQUEST);
                    }

                    @Override
                    public void onNext(Bitmap photo) {
                        view.addPhoto(photo);
                        view.stopLoading();
                        hasPhoto = true;
                        subscription.request(ITEMS_PER_REQUEST);
                    }

                    @Override
                    public void onError(Throwable t) {
                        view.setDefaultPhoto();
                        view.stopLoading();
                        view.showErrorMessage();
                    }

                    @Override
                    public void onComplete() {
                        if (!hasPhoto) {
                            view.setDefaultPhoto();
                        }

                        view.stopLoading();
                    }
                });
    }
}
