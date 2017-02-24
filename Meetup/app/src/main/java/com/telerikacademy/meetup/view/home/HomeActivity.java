package com.telerikacademy.meetup.view.home;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.OnClick;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;
import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.config.di.module.ControllerModule;
import com.telerikacademy.meetup.model.base.ILocation;
import com.telerikacademy.meetup.provider.base.ILocationProvider;
import com.telerikacademy.meetup.view.home.base.IHomeContentContract;
import com.telerikacademy.meetup.view.home.base.IHomeHeaderContract;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class HomeActivity extends AppCompatActivity
        implements ILocationProvider {

    @Inject
    IHomeContentContract.Presenter contentPresenter;
    @Inject
    IHomeHeaderContract.Presenter headerPresenter;
    @Inject
    FragmentManager fragmentManager;

    private HomeContentFragment content;
    private HomeHeaderFragment header;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        injectDependencies();

        content = (HomeContentFragment) fragmentManager
                .findFragmentById(R.id.fragment_home_content);

        header = (HomeHeaderFragment) fragmentManager
                .findFragmentById(R.id.fragment_home_header);

        setup();

        loadPhotoTest()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<List<Bitmap>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(final List<Bitmap> value) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (Bitmap photo : value) {
                                    ImageView iv = new ImageView(HomeActivity.this);
                                    iv.setImageBitmap(photo);
                                    LinearLayout homeWrapper = (LinearLayout) HomeActivity
                                            .this.findViewById(R.id.home_wrapper);
                                    homeWrapper.addView(iv);
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    // TODO: Delete
    private Observable<List<Bitmap>> loadPhotoTest() {
        final Activity activity = this;

        return Observable.defer(new Callable<ObservableSource<List<Bitmap>>>() {
            @Override
            public ObservableSource<List<Bitmap>> call() throws Exception {
                final String placeId = "ChIJ0b_NyW6FqkARJXGz7W-es9k";

                GoogleApiClient googleApiClient = new GoogleApiClient.Builder(activity)
                        .addApi(Places.GEO_DATA_API)
                        .addApi(Places.PLACE_DETECTION_API)
                        .enableAutoManage((FragmentActivity) activity, new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            }
                        })
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
    public void onStart() {
        super.onStart();
        header.setNavigationDrawer(R.layout.activity_home);
    }

    @Override
    public ILocation getLocation() {
        ILocation currentLocation = headerPresenter.getLocation();
        return currentLocation;
    }

    @OnClick(R.id.btn_update_location)
    void updateLocation() {
        header.updateLocation();
    }

    private void setup() {
        contentPresenter.setView(content);
        content.setPresenter(contentPresenter);

        headerPresenter.setView(header);
        header.setPresenter(headerPresenter);
    }

    private void injectDependencies() {
        BaseApplication
                .bind(this)
                .from(this)
                .getComponent()
                .getControllerComponent(new ControllerModule(
                        this, getSupportFragmentManager()
                ))
                .inject(this);
    }
}
