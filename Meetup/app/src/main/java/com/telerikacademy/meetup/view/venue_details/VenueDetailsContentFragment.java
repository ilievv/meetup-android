package com.telerikacademy.meetup.view.venue_details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.view.venue_details.base.IVenueDetailsContract;

public class VenueDetailsContentFragment extends Fragment
        implements IVenueDetailsContract.View {

    private IVenueDetailsContract.Presenter presenter;

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
    public void setPresenter(IVenueDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
