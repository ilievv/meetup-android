package com.telerikacademy.meetup.view.home.base;

import com.telerikacademy.meetup.provider.base.ILocationAware;
import com.telerikacademy.meetup.ui.fragment.base.IToolbar;
import com.telerikacademy.meetup.view.base.BasePresenter;
import com.telerikacademy.meetup.view.base.BaseView;

public interface IHomeHeaderContract {

    interface View extends BaseView<Presenter>, IToolbar {

        void setTitle(String title);

        void setSubtitle(String subtitle);

        boolean checkPermissions();

        void requestPermissions();

        void showEnableLocationDialog();

        void updateLocation();
    }

    interface Presenter extends BasePresenter<View>, ILocationAware {

        void subscribe();

        void unsubscribe();

        void update();
    }
}
