package com.telerikacademy.meetup.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.telerikacademy.meetup.R;

public class HomeToolbarFragment extends ToolbarFragment {

    public HomeToolbarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home_toolbar, container, false);
    }
}
