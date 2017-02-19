package com.telerikacademy.meetup.view.sign_in.base;

import com.telerikacademy.meetup.view.base.BasePresenter;
import com.telerikacademy.meetup.view.base.BaseView;
import com.telerikacademy.meetup.view.sign_in.SignInActivity;


public interface ISignInContentContract {
    interface View extends BaseView<ISignInContentContract.Presenter> {
    }

    interface Presenter extends BasePresenter<ISignInContentContract.View> {

        void initialize(ISignInContentContract.View view, SignInActivity activity);

    }
}
