package com.telerikacademy.meetup.provider;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;
import com.telerikacademy.meetup.provider.base.VenuePhotoProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import javax.inject.Inject;

public class GoogleVenuePhotoProvider extends VenuePhotoProvider
        implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;

    @Inject
    public GoogleVenuePhotoProvider(Context context) {
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

    @Override
    public Observable<Bitmap> getPhotos(final String placeId) {
        return Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> emitter) throws Exception {
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

                        emitter.onNext(photo);
                    }

                    buffer.release();
                } catch (Exception ex) {
                    emitter.onError(ex);
                } finally {
                    emitter.onComplete();
                }
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
}
