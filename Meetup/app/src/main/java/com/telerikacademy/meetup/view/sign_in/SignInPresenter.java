package com.telerikacademy.meetup.view.sign_in;

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

    private IHttpRequester httpRequester;
    private IJsonParser jsonParser;
    private IUserSession userSession;
    private IHashProvider hashProvider;

    @Inject
    public SignInPresenter(IHttpRequester httpRequester, IJsonParser jsonParser,
                           IUserSession userSession, IHashProvider hashProvider) {

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
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("passHash", passHash);

        // TODO: Extract into shared pref
        String url = "https://telerik-meetup.herokuapp.com/auth/login";

        httpRequester.post(url, map)
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
                        try {
                            userJsonObject = jsonParser.toJsonFromResponseBody(responseBody);
                            resultUser = jsonParser.fromJson(userJsonObject, User.class);
                        } catch (IllegalStateException e) {
                            view.setUsernameError();
                            view.setPasswordError();
                            return;
                        }

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
