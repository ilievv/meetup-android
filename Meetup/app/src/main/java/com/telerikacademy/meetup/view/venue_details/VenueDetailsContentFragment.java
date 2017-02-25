package com.telerikacademy.meetup.view.venue_details;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.ui.fragments.base.IGallery;
import com.telerikacademy.meetup.view.venue_details.base.IVenueDetailsContract;

public class VenueDetailsContentFragment extends Fragment
        implements IVenueDetailsContract.View {

    private IVenueDetailsContract.Presenter presenter;
    private IGallery gallery;

    public VenueDetailsContentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_venue_details_content, container, false);
        BaseApplication.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.loadPhotos();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribe();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    public void setPresenter(IVenueDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setGallery(IGallery gallery) {
        this.gallery = gallery;
    }

    @Override
    public void addPhoto(final Bitmap photo) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gallery.addPhoto(photo);
            }
        });
    }
}
