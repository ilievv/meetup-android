package com.telerikacademy.meetup.view.review.base;

import com.telerikacademy.meetup.model.base.IVenue;
import com.telerikacademy.meetup.view.base.BasePresenter;
import com.telerikacademy.meetup.view.base.BaseView;


public interface IReviewContract {
    interface View extends BaseView<IReviewContract.Presenter> {

        void postComment();
    }

    interface Presenter extends BasePresenter<IReviewContract.View> {

        void postComment(IVenue venue);
    }
}
