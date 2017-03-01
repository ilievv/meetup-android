package com.telerikacademy.meetup.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.BindView;
import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.model.base.IVenue;

import java.util.List;

public class RecentVenuesAdapter extends RecyclerView.Adapter<RecentVenuesAdapter.VenueHolder> {

    private List<IVenue> venues;

    public RecentVenuesAdapter(List<IVenue> venues) {
        this.venues = venues;
    }

    @Override
    public VenueHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_recent_venue_item_row, parent, false);
        return new VenueHolder(view);
    }

    @Override
    public void onBindViewHolder(VenueHolder holder, int position) {
        IVenue venue = venues.get(position);
        holder.bindVenue(venue);
    }

    @Override
    public int getItemCount() {
        return venues.size();
    }

    static class VenueHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.iv_recent_venues_photo)
        ImageView venuePhoto;
        @BindView(R.id.tv_recent_venues_name)
        TextView venueName;
        @BindView(R.id.rb_recent_venues_rating)
        RatingBar venueRating;

        private IVenue venue;

        public VenueHolder(View itemView) {
            super(itemView);
            BaseApplication.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // TODO: Implement
        }

        void bindVenue(IVenue venue) {
            this.venue = venue;
            venuePhoto.setImageBitmap(venue.getPhoto());
            venueName.setText(venue.getName());
            venueRating.setRating(venue.getRating());
        }
    }
}
