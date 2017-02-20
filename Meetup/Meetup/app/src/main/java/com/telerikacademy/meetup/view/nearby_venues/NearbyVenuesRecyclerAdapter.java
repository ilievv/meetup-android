package com.telerikacademy.meetup.view.nearby_venues;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RatingBar;
import android.widget.TextView;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.model.Venue;
import com.telerikacademy.meetup.view.venue_details.VenueDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class NearbyVenuesRecyclerAdapter
        extends RecyclerView.Adapter<NearbyVenuesRecyclerAdapter.VenueHolder>
        implements Filterable {

    private static String VENUE_KEY = "venue";

    private List<Venue> venues;
    private List<Venue> filteredVenues;

    private VenueFilter venueFilter;

    public NearbyVenuesRecyclerAdapter(List<Venue> venues) {
        this.venues = venues;
        this.filteredVenues = new ArrayList<>(venues);
    }

    @Override
    public VenueHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_venue_item_row, parent, false);

        return new VenueHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(VenueHolder holder, int position) {
        Venue venue = this.filteredVenues.get(position);
        holder.bindVenue(venue);
    }

    @Override
    public int getItemCount() {
        return filteredVenues.size();
    }

    @Override
    public Filter getFilter() {
        if (this.venueFilter == null) {
            this.venueFilter = new VenueFilter(this, venues);
        }

        return venueFilter;
    }

    static class VenueHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Venue venue;
        private TextView venueName;
        private TextView venueTypes;
        private TextView venueAddress;
        private RatingBar venueRating;

        private VenueHolder(View itemView) {
            super(itemView);

            this.venueName = (TextView) itemView.findViewById(R.id.venue_name);
            this.venueTypes = (TextView) itemView.findViewById(R.id.venue_types);
            this.venueAddress = (TextView) itemView.findViewById(R.id.venue_address);
            this.venueRating = (RatingBar) itemView.findViewById(R.id.venue_rating);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = itemView.getContext();

            Intent showVenueIntent = new Intent(context, VenueDetailsActivity.class);
            showVenueIntent.putExtra(VENUE_KEY, this.venue);
            context.startActivity(showVenueIntent);
        }

        void bindVenue(Venue venue) {
            this.venue = venue;
            this.venueName.setText(venue.getName());
            this.venueAddress.setText(venue.getAddress());
            this.venueRating.setRating(venue.getRating());

            if (venue.getTypes() != null) {
                this.venueTypes.setText(TextUtils.join(", ", venue.getTypes()));
            }
        }
    }

    private class VenueFilter extends Filter {

        private NearbyVenuesRecyclerAdapter adapter;
        private List<Venue> originalList;
        private List<Venue> filteredList;

        private VenueFilter(NearbyVenuesRecyclerAdapter adapter, List<Venue> venues) {
            super();

            this.adapter = adapter;
            this.originalList = venues;
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();

            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final Venue venue : originalList) {
                    String venueName = venue.getName();
                    if (venueName != null &&
                            venueName.toLowerCase().contains(filterPattern)) {
                        filteredList.add(venue);
                    }
                }
            }

            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.filteredVenues.clear();
            adapter.filteredVenues.addAll((ArrayList<Venue>) results.values);
            adapter.notifyDataSetChanged();
        }
    }
}
