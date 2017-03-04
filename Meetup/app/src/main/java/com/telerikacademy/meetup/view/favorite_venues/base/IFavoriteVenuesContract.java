package com.telerikacademy.meetup.view.favorite_venues.base;

import com.telerikacademy.meetup.view.base.BasePresenter;
import com.telerikacademy.meetup.view.base.BaseView;

public interface IFavoriteVenuesContract {

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter<View> {
    }
}
