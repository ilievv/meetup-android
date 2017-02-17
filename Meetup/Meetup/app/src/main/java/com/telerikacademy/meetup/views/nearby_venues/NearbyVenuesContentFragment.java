package com.telerikacademy.meetup.views.nearby_venues;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import butterknife.BindView;
import com.telerikacademy.meetup.R;

public class NearbyVenuesContentFragment extends Fragment {

    @BindView(R.id.rv_venues)
    RecyclerView recyclerView;
    @BindView(R.id.et_search)
    AutoCompleteTextView searchInput;

    public NearbyVenuesContentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_nearby_venues_content, container, false);
    }
}
