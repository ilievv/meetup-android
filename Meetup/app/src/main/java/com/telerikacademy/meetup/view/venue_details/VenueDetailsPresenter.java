package com.telerikacademy.meetup.view.venue_details;

import com.telerikacademy.meetup.view.nearby_venues.base.INearbyVenuesContract;

public class VenueDetailsPresenter implements INearbyVenuesContract.Presenter {

    private INearbyVenuesContract.View view;

    public void load() {
    }

    @Override
    public void setView(INearbyVenuesContract.View view) {
        this.view = view;
    }
}
