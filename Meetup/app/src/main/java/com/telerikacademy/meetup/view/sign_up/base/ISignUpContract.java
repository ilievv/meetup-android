package com.telerikacademy.meetup.view.sign_up.base;

import com.telerikacademy.meetup.view.base.BasePresenter;
import com.telerikacademy.meetup.view.base.BaseView;

public interface ISignUpContract {

    interface View extends BaseView<Presenter> {

        void setUsernameError();

        void setPasswordError();

        void notifySuccessful();

        void redirectToSignIn();

        void startLoading();

        void stopLoading();
    }

    interface Presenter extends BasePresenter<View> {

        void signUp(String username, String password);
    }
}
