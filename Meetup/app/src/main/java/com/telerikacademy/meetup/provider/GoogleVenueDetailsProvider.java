package com.telerikacademy.meetup.provider;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.*;
import com.telerikacademy.meetup.model.base.IVenueDetail;
import com.telerikacademy.meetup.provider.base.IVenueFactory;
import com.telerikacademy.meetup.provider.base.VenueDetailsProvider;
import io.reactivex.*;

import javax.inject.Inject;
import java.util.concurrent.Callable;

public class GoogleVenueDetailsProvider extends VenueDetailsProvider
        implements GoogleApiClient.OnConnectionFailedListener {

    private final IVenueFactory venueFactory;
    private GoogleApiClient googleApiClient;

    @Inject
    public GoogleVenueDetailsProvider(Context context, IVenueFactory venueFactory) {
        this.venueFactory = venueFactory;
        buildGoogleApiClient(context);
    }

    @Override
    public void connect() {
        googleApiClient.connect();
    }

    @Override
    public void disconnect() {
        googleApiClient.disconnect();
    }

    public Flowable<Bitmap> getPhotos(@NonNull final String placeId) {
        return Flowable.create(new FlowableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(FlowableEmitter<Bitmap> emitter) throws Exception {
                try {
                    PlacePhotoMetadataResult res = Places.GeoDataApi
                            .getPlacePhotos(googleApiClient, placeId)
                            .await();
                    PlacePhotoMetadataBuffer buffer = res.getPhotoMetadata();

                    for (PlacePhotoMetadata photoMetadata : buffer) {
                        Bitmap photo = photoMetadata
                                .getPhoto(googleApiClient)
                                .await()
                                .getBitmap();

                        if (photo != null) {
                            emitter.onNext(photo);
                        }
                    }

                    buffer.release();
                } catch (Exception ex) {
                    emitter.onError(ex);
                } finally {
                    emitter.onComplete();
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

    public Observable<IVenueDetail> getById(@NonNull final String placeId) {
        return Observable.defer(new Callable<ObservableSource<? extends IVenueDetail>>() {
            @Override
            public ObservableSource<? extends IVenueDetail> call() throws Exception {
                PlaceBuffer places = Places.GeoDataApi
                        .getPlaceById(googleApiClient, placeId)
                        .await();

                if (places.getCount() > 0) {
                    Place place = places.get(0);
                    return Observable.just(parsePlace(place));
                }

                return null;
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (getOnConnectionFailedListener() != null) {
            String errorMessage = connectionResult.getErrorCode() + " " + connectionResult.getErrorMessage();
            getOnConnectionFailedListener().onConnectionFailed(errorMessage);
        }
    }

    protected synchronized void buildGoogleApiClient(Context context) {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
    }

    private IVenueDetail parsePlace(Place place) {
        IVenueDetail venue = venueFactory.createVenueDetail(place.getId(), place.getName().toString());
        venue.setPhoneNumber(place.getPhoneNumber().toString());
        venue.setAddress(place.getAddress().toString());
        venue.setWebsiteUri(place.getWebsiteUri());
        venue.setRating(place.getRating());
        return venue;
    }
}
