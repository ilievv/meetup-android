package com.telerikacademy.meetup.view.home.base;

import com.telerikacademy.meetup.view.base.BasePresenter;
import com.telerikacademy.meetup.view.base.BaseView;
import com.telerikacademy.meetup.view.home.HomeActivity;

public interface HomeHeaderContract {

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {

        void setActivity(HomeActivity activity);
    }
}
