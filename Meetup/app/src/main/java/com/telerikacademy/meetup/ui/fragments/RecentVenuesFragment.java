package com.telerikacademy.meetup.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telerikacademy.meetup.R;
import android.support.v4.app.Fragment;

import com.telerikacademy.meetup.data.local.base.ILocalData;
import com.telerikacademy.meetup.ui.fragments.base.IRecentVenues;


public class RecentVenuesFragment extends Fragment implements IRecentVenues {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recent_venues, container, false);

        return view;
    }
}
