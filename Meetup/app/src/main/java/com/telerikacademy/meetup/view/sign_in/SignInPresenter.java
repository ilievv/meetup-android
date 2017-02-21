package com.telerikacademy.meetup.view.sign_in;

import com.telerikacademy.meetup.config.base.IApiConstants;
import com.telerikacademy.meetup.model.User;
import com.telerikacademy.meetup.util.base.*;
import com.telerikacademy.meetup.view.sign_in.base.ISignInContract;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class SignInPresenter implements ISignInContract.Presenter {

    private static final String STRING_EMPTY = "";

    private IApiConstants apiConstants;
    private IHttpRequester httpRequester;
    private IJsonParser jsonParser;
    private IUserSession userSession;
    private IHashProvider hashProvider;

    @Inject
    public SignInPresenter(IApiConstants apiConstants, IHttpRequester httpRequester,
                           IJsonParser jsonParser, IUserSession userSession,
                           IHashProvider hashProvider) {

        this.apiConstants = apiConstants;
        this.httpRequester = httpRequester;
        this.jsonParser = jsonParser;
        this.userSession = userSession;
        this.hashProvider = hashProvider;
    }

    private ISignInContract.View view;

    public void load() {
    }

    @Override
    public void setView(ISignInContract.View view) {
        this.view = view;
    }

    @Override
    public void signIn(String username, String password) {
        if (username.equals(STRING_EMPTY)) {
            view.setUsernameError();
            return;
        }

        if (password.equals(STRING_EMPTY)) {
            view.setPasswordError();
            return;
        }

        String passHash = hashProvider.hashPassword(password);
        Map<String, String> userCredentials = new HashMap<>();
        userCredentials.put("username", username);
        userCredentials.put("passHash", passHash);

        httpRequester.post(apiConstants.signInUrl(), userCredentials)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IHttpResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(IHttpResponse value) {
                        String responseBody = value.getBody().toString();
                        String userJsonObject;
                        User resultUser;

                        userJsonObject = jsonParser.getDirectMember(responseBody, "result");
                        resultUser = jsonParser.fromJson(userJsonObject, User.class);

                        userSession.setUsername(resultUser.getUsername());
                        userSession.setId(resultUser.getId());

                        view.notifySuccessful();
                        view.redirectToHome();
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
