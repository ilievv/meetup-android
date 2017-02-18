package com.telerikacademy.meetup.view.home.base;

import android.app.Activity;
import com.telerikacademy.meetup.view.base.BasePresenter;
import com.telerikacademy.meetup.view.base.BaseView;
import com.telerikacademy.meetup.view.home.HomeActivity;

import java.util.Map;

public interface HomeContentContract {

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {

        void setActivity(HomeActivity activity);

        void navigate(Class<? extends Activity> cls);

        void navigate(Class<? extends Activity> cls, Map<String, String> extras);

        void showNearbyRestaurants();

        void showNearbyCafes();

        void showNearbyPubs();

        void showNearbyFastFoodRestaurants();

        void showNearbyNightClubs();

        void showOtherVenues();
    }
}
