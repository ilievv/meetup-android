package com.telerikacademy.meetup.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.models.Venue;
import com.telerikacademy.meetup.views.adapters.NearbyVenuesRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class NearbyVenuesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_venues);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        List<Venue> venues = new ArrayList<>();
        Venue venue1 = new Venue();
        venue1.setName("Pri iliyo");
        venue1.setAddress("Lyulin");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.venues_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        NearbyVenuesRecyclerAdapter recyclerAdapter = new NearbyVenuesRecyclerAdapter(venues);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        menu.clear();
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }
}
