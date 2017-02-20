package com.telerikacademy.meetup.view.sign_in.base;

import com.telerikacademy.meetup.view.base.BasePresenter;
import com.telerikacademy.meetup.view.base.BaseView;

public interface ISignInContract {

    interface View extends BaseView<Presenter> {

        void setUsernameError();

        void setPasswordError();

        void notifySuccessful();

        void redirectToHome();
    }

    interface Presenter extends BasePresenter<View> {

        void signIn(String username, String password);
    }
}
