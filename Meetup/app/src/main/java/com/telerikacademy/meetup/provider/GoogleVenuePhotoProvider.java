package com.telerikacademy.meetup.provider;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private final Activity activity;

    @Inject
    public GoogleVenuePhotoProvider(Activity activity) {
        this.activity = activity;
    }

    @Override
    public Observable<List<Bitmap>> getPhotos(String placeId) {
        final GoogleVenuePhotoProvider that = this;

        return Observable.defer(new Callable<ObservableSource<List<Bitmap>>>() {
            @Override
            public ObservableSource<List<Bitmap>> call() throws Exception {
                final String placeId = "ChIJ0b_NyW6FqkARJXGz7W-es9k";

                GoogleApiClient googleApiClient = new GoogleApiClient.Builder(activity)
                        .addApi(Places.GEO_DATA_API)
                        .addApi(Places.PLACE_DETECTION_API)
                        .addConnectionCallbacks(that)
                        .addOnConnectionFailedListener(that)
                        .build();

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
    public void onConnected(@Nullable Bundle bundle) {
        if (getOnConnectedListener() != null) {
            getOnConnectedListener().onConnected(bundle);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (getOnConnectionFailedListener() != null) {
            String errorMessage = connectionResult.getErrorCode() + " " + connectionResult.getErrorMessage();
            getOnConnectionFailedListener().onConnectionFailed(errorMessage);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }
}
