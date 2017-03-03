package com.telerikacademy.meetup.view.review;

import com.telerikacademy.meetup.model.base.IVenue;
import com.telerikacademy.meetup.model.base.IVenueDetail;
import com.telerikacademy.meetup.view.review.base.IReviewContract;
import com.telerikacademy.meetup.view.venue_details.base.IVenueDetailsContract;

public class ReviewPresenter implements IReviewContract.Presenter {
    private IReviewContract.View view;

    private String venueId;
    private String venueName;
    private String venueAddress;

    @Override
    public void postComment() {

    }

    @Override
    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    @Override
    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    @Override
    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    @Override
    public void setView(IReviewContract.View view) {
        this.view = view;
    }
}
