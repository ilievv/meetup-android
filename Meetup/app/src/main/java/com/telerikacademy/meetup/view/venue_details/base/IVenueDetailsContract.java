package com.telerikacademy.meetup.view.venue_details.base;

import com.telerikacademy.meetup.view.base.BasePresenter;
import com.telerikacademy.meetup.view.base.BaseView;

public interface IVenueDetailsContract {

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter<View> {
    }
}
