package com.telerikacademy.meetup.views.nearby_venues;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.models.Venue;
import com.telerikacademy.meetup.ui.fragments.base.ISearchBar;
import com.telerikacademy.meetup.ui.fragments.base.IToolbar;

import java.util.ArrayList;
import java.util.List;

public class NearbyVenuesActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private NearbyVenuesContentFragment content;

    private IToolbar toolbar;
    private ISearchBar searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_venues);

        fragmentManager = getSupportFragmentManager();

        toolbar = (IToolbar) fragmentManager
                .findFragmentById(R.id.fragment_home_header);

        NearbyVenuesRecyclerAdapter recyclerAdapter =
                new NearbyVenuesRecyclerAdapter(generateSampleData());

        content = (NearbyVenuesContentFragment) fragmentManager.
                findFragmentById(R.id.fragment_nearby_venues_content);
        content.setAdapter(recyclerAdapter);

        searchBar = (ISearchBar) fragmentManager
                .findFragmentById(R.id.fragment_search_header);
        searchBar.setFilter(recyclerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        toolbar.setNavigationDrawer(R.layout.activity_nearby_venues);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        toolbar.inflateMenu(R.menu.main, menu, getMenuInflater());
        return true;
    }

    private List<Venue> generateSampleData() {
        List<Venue> venues = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Venue venue = new Venue(Integer.toString(i),
                    "Pri Ilyo #" + i, "zh.k. Lyulin " + i + 1, new String[]{"food"}, i % 5f);
            venues.add(venue);
        }
        Venue someVen = new Venue("123", "Gosho",
                "Kostinbrod", new String[]{"restaurant", "bar", "club", "food"}, 3.2f);
        venues.add(someVen);

        return venues;
    }
}
