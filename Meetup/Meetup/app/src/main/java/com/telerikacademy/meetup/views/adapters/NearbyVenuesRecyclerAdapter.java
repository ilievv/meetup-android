package com.telerikacademy.meetup.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.activities.NearbyVenuesActivity;
import com.telerikacademy.meetup.models.Venue;

import java.io.Serializable;
import java.util.List;

public class NearbyVenuesRecyclerAdapter extends RecyclerView.Adapter<NearbyVenuesRecyclerAdapter.VenueHolder> {

    private static String VENUE_KEY = "Venue";

    private List<Venue> venues;

    public NearbyVenuesRecyclerAdapter(List<Venue> venues) {
        this.venues = venues;
    }

    @Override
    public VenueHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_venue_item_row, parent, false);

        return new VenueHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(VenueHolder holder, int position) {
        Venue venue = this.venues.get(position);
        holder.bindVenue(venue);
    }

    @Override
    public int getItemCount() {
        return venues.size();
    }

    static class VenueHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Venue venue;
        private TextView venueName;
        private TextView venueAddress;

        private VenueHolder(View itemView) {
            super(itemView);

            this.venueName = (TextView) itemView.findViewById(R.id.venue_name);
            this.venueAddress = (TextView) itemView.findViewById(R.id.venue_address);

            itemView.setOnClickListener(this);
        }

        // TODO: Fix accordingly
        @Override
        public void onClick(View v) {
            Context context = itemView.getContext();

            Intent showVenueIntent = new Intent(context, NearbyVenuesActivity.class);
            showVenueIntent.putExtra(VENUE_KEY, this.venue);
            context.startActivity(showVenueIntent);
        }

        void bindVenue(Venue venue) {
            this.venue = venue;
            this.venueName.setText(venue.getName());
            this.venueAddress.setText(venue.getAddress());
        }
    }
}
