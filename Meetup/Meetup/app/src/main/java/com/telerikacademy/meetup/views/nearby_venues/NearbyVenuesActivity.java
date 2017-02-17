package com.telerikacademy.meetup.views.nearby_venues;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.models.Venue;
import com.telerikacademy.meetup.ui.fragments.SearchHeaderFragment;
import com.telerikacademy.meetup.ui.fragments.base.IToolbar;

import java.util.ArrayList;
import java.util.List;

public class NearbyVenuesActivity extends AppCompatActivity {

    private static final String VENUE_TYPE_TAG = "VENUE_TYPE";

    @BindView(R.id.rv_venues)
    RecyclerView recyclerView;
    @BindView(R.id.et_search)
    AutoCompleteTextView searchInput;

    private IToolbar toolbar;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_venues);
        injectDependencies();

        fragmentManager = getSupportFragmentManager();
        toolbar = (IToolbar) fragmentManager.findFragmentById(R.id.fragment_toolbar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        NearbyVenuesRecyclerAdapter recyclerAdapter =
                new NearbyVenuesRecyclerAdapter(generateSampleData());

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        final SearchHeaderFragment searchFragment = (SearchHeaderFragment)
                fragmentManager.findFragmentById(R.id.fragment_search_header);

        if (searchFragment != null) {
            searchFragment.setFilter(searchInput, recyclerAdapter);
        }

/*        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fragmentManager
                            .beginTransaction()
                            .setCustomAnimations(R.anim.slide_down, R.anim.slide_out)
                            .show(searchFragment)
                            .commit();
                }

                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                boolean hasChanged = dy > 50 || dy < -50;
                boolean isVisible = searchFragment != null && searchFragment.isVisible();
                if (hasChanged && isVisible) {
                    fragmentManager
                            .beginTransaction()
                            .setCustomAnimations(R.anim.slide_down, R.anim.slide_out)
                            .hide(searchFragment)
                            .commit();
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        String type = getIntent().getStringExtra(VENUE_TYPE_TAG);
        Toast.makeText(this, type, Toast.LENGTH_SHORT).show();

        if (toolbar != null) {
            toolbar.setNavigationDrawer(R.layout.activity_nearby_venues);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        if (toolbar != null) {
            toolbar.inflateMenu(R.menu.main, menu, getMenuInflater());
        }

        return true;
    }

    private void injectDependencies() {
        ButterKnife.bind(this);
    }

    // TODO: Delete
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
