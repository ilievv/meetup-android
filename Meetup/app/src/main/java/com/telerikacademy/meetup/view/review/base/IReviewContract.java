package com.telerikacademy.meetup.view.review.base;

import com.telerikacademy.meetup.view.base.BasePresenter;
import com.telerikacademy.meetup.view.base.BaseView;


public interface IReviewContract {
    interface View extends BaseView<IReviewContract.Presenter> {

        void postComment();
    }

    interface Presenter extends BasePresenter<IReviewContract.View> {

        void postComment();

        void setVenueId(String venueId);

        void setVenueName(String venueName);

        void setVenueAddress(String venueAddress);
    }
}
