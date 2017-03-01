package com.telerikacademy.meetup.view.venue_details;

import android.graphics.Bitmap;
import android.net.Uri;
import com.telerikacademy.meetup.model.base.IVenue;
import com.telerikacademy.meetup.model.base.IVenueDetail;
import com.telerikacademy.meetup.network.local.base.ILocalData;
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
    private IVenueDetail venue;
    private String venueId;

    private final IVenueDetailsProvider venueDetailsProvider;
    private final ILocalData localData;

    @Inject
    public VenueDetailsPresenter(IVenueDetailsProvider venueDetailsProvider, ILocalData localData) {
        this.venueDetailsProvider = venueDetailsProvider;
        this.localData = localData;
    }

    @Override
    public void setView(IVenueDetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void setVenueId(String venueId) {
        this.venueId = venueId;
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
        if (venueId == null || venueId.isEmpty()) {
            return;
        }

        view.startContentLoadingIndicator();

        venueDetailsProvider
                .getById(venueId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<IVenueDetail>() {
                    @Override
                    public void accept(IVenueDetail venue) throws Exception {
                        VenueDetailsPresenter.this.venue = venue;
                        view.setTitle(venue.getName());
                        view.setRating(venue.getRating());
                        if (venue.getTypes().length > 0) {
                            view.setType(venue.getTypes()[0]);
                        }
                        view.stopContentLoadingIndicator();
                    }
                });
    }

    @Override
    public void loadPhotos() {
        if (venueId == null || venueId.isEmpty()) {
            return;
        }

        venueDetailsProvider
                .getPhotos(venueId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {

                    private static final int ITEMS_PER_REQUEST = 1;

                    private Subscription subscription;
                    private boolean isFirst = true;
                    private boolean hasPhoto = false;

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        this.subscription = subscription;
                        subscription.request(ITEMS_PER_REQUEST);
                    }

                    @Override
                    public void onNext(Bitmap photo) {
                        Bitmap modifiedPhoto = photo.copy(Bitmap.Config.RGB_565, true);
                        photo.recycle();

                        view.addPhoto(modifiedPhoto);

                        if (isFirst) {
                            isFirst = false;
                            saveToRecent(venue, modifiedPhoto);
                            view.stopLoading();
                            view.startGalleryLoadingIndicator();
                            hasPhoto = true;
                        }

                        subscription.request(ITEMS_PER_REQUEST);
                    }

                    @Override
                    public void onError(Throwable t) {
                        view.setDefaultPhoto();
                        view.stopLoading();
                        view.stopGalleryLoadingIndicator();
                        view.showGalleryIndicator();
                        view.showErrorMessage();
                    }

                    @Override
                    public void onComplete() {
                        if (!hasPhoto) {
                            view.setDefaultPhoto();
                            saveToRecent(venue, null);
                        }

                        view.stopLoading();
                        view.stopGalleryLoadingIndicator();
                        view.showGalleryIndicator();
                    }
                });
    }

    @Override
    public void onNavigationButtonClick() {
        if (venue == null || venue.getLatitude() == -1 || venue.getLongitude() == -1) {
            return;
        }

        String uriString = String.format("google.navigation:q=%s,%s",
                venue.getLatitude(), venue.getLongitude());
        view.startNavigation(Uri.parse(uriString));
    }

    private void saveToRecent(IVenue venue, Bitmap photo) {
        localData.saveVenueToRecent(venue, photo)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
