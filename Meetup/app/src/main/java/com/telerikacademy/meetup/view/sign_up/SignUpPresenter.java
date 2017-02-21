package com.telerikacademy.meetup.view.sign_up;

import com.telerikacademy.meetup.config.base.IApiConstants;
import com.telerikacademy.meetup.model.User;
import com.telerikacademy.meetup.util.base.*;
import com.telerikacademy.meetup.view.sign_up.base.ISignUpContract;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class SignUpPresenter implements ISignUpContract.Presenter {

    private ISignUpContract.View view;
    private IApiConstants apiConstants;
    private IHttpRequester httpRequester;
    private IJsonParser jsonParser;
    private IUserSession userSession;
    private IValidator validator;
    private IHashProvider hashProvider;

    @Inject
    public SignUpPresenter(IApiConstants apiConstants, IHttpRequester httpRequester,
                           IJsonParser jsonParser, IUserSession userSession,
                           IValidator validator, IHashProvider hashProvider) {

        this.apiConstants = apiConstants;
        this.httpRequester = httpRequester;
        this.jsonParser = jsonParser;
        this.userSession = userSession;
        this.validator = validator;
        this.hashProvider = hashProvider;
    }

    public void load() {
    }

    @Override
    public void setView(ISignUpContract.View view) {
        this.view = view;
    }

    @Override
    public void signUp(String username, String password) {
        if (!validator.isUsernameValid(username)) {
            view.setUsernameError();
            return;
        }

        if (!validator.isPasswordValid(password)) {
            view.setPasswordError();
            return;
        }

        String passHash = this.hashProvider.hashPassword(password);

        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("passHash", passHash);

        httpRequester.post(apiConstants.signUpUrl(), map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IHttpResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(IHttpResponse value) {
                        String responseBody = value.getBody();
                        String userJsonObject;
                        User resultUser;

                        try {
                            userJsonObject = jsonParser.toJsonFromResponseBody(responseBody);
                            resultUser = jsonParser.fromJson(userJsonObject, User.class);
                        } catch (IllegalStateException e) {
                            view.setPasswordError();
                            return;
                        }

                        view.notifySuccessful();
                        view.redirectToSignIn();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
