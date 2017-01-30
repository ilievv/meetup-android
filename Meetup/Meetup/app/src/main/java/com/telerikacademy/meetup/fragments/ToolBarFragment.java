package com.telerikacademy.meetup.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;

import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.interfaces.IMenuInflater;

public class ToolBarFragment extends Fragment
        implements IMenuInflater {

    private AppCompatActivity currentActivity;

    public ToolBarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tool_bar, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.currentActivity = (AppCompatActivity) getActivity();

        Toolbar toolbar = (Toolbar) this.currentActivity.findViewById(R.id.tool_bar);
        this.currentActivity.setSupportActionBar(toolbar);
    }

    public void inflateMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();

        this.currentActivity.getMenuInflater().inflate(R.menu.main, menu);
    }
}
