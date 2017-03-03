package com.telerikacademy.meetup.view.review.base;

import com.telerikacademy.meetup.view.base.BasePresenter;
import com.telerikacademy.meetup.view.base.BaseView;


public interface IReviewContract {
    interface View extends BaseView<IReviewContract.Presenter> {

        void redirectToVenueDetails(String venueId);

        void startLoading();

        void stopLoading();

        void notifyError(String errorMessage);

        void notifySuccessful(String successMsg);
    }

    interface Presenter extends BasePresenter<IReviewContract.View> {

        void postComment(String text);

        void setVenueId(String venueId);

        void setVenueName(String venueName);

        void setVenueAddress(String venueAddress);
    }
}
