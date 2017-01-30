package com.telerikacademy.meetup.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.AutoCompleteTextView;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.fragments.SearchHeaderFragment;
import com.telerikacademy.meetup.fragments.ToolBarFragment;
import com.telerikacademy.meetup.models.Venue;
import com.telerikacademy.meetup.views.adapters.NearbyVenuesRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class NearbyVenuesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_venues);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_venues);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        NearbyVenuesRecyclerAdapter recyclerAdapter = new NearbyVenuesRecyclerAdapter(generateSampleData());

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        SearchHeaderFragment searchFragment = (SearchHeaderFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_search_header);

        AutoCompleteTextView searchInput = (AutoCompleteTextView) findViewById(R.id.et_search);

        if (searchFragment != null) {
            searchFragment.setFilter(searchInput, recyclerAdapter);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        ToolBarFragment toolBarFragment = (ToolBarFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_tool_bar);

        if (toolBarFragment != null) {
            toolBarFragment.inflateOptionsMenu(menu, getMenuInflater());
        }

        return true;
    }

    // TODO: Delete
    private List<Venue> generateSampleData() {
        List<Venue> venues = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Venue venue = new Venue(Integer.toString(i),
                    "Pri Ilyo #" + i, "zh.k. Lyulin " + i + 1, null, 0);
            venues.add(venue);
        }
        Venue someVen = new Venue("123", "Gosho", "Kostinbrod", null, 0);
        venues.add(someVen);

        return venues;
    }
}
