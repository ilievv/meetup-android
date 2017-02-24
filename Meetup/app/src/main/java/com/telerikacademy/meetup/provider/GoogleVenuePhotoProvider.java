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
import io.reactivex.ObservableSource;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

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
    public Observable<List<Bitmap>> getPhotos(final String placeId) {
        return Observable.defer(new Callable<ObservableSource<List<Bitmap>>>() {
            @Override
            public ObservableSource<List<Bitmap>> call() throws Exception {
                PlacePhotoMetadataResult res = Places.GeoDataApi.getPlacePhotos(googleApiClient, placeId).await();
                PlacePhotoMetadataBuffer buffer = res.getPhotoMetadata();

                List<Bitmap> photos = new ArrayList<>();
                for (PlacePhotoMetadata photoMetadata : buffer) {
                    photos.add(photoMetadata.getPhoto(googleApiClient).await().getBitmap());
                }

                buffer.release();
                return Observable.just(photos);
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
