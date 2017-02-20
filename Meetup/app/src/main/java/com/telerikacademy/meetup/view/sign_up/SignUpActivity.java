package com.telerikacademy.meetup.view.sign_up;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.config.di.module.ControllerModule;
import com.telerikacademy.meetup.model.User;
import com.telerikacademy.meetup.provider.base.IIntentFactory;
import com.telerikacademy.meetup.ui.fragments.base.IToolbar;
import com.telerikacademy.meetup.util.base.*;
import com.telerikacademy.meetup.view.sign_in.SignInActivity;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    @Inject
    IHttpRequester httpRequester;
    @Inject
    IJsonParser jsonParser;
    @Inject
    IUserSession userSession;
    @Inject
    IValidator validator;
    @Inject
    IHashProvider hashProvider;
    @Inject
    IIntentFactory intentFactory;
    @Inject
    FragmentManager fragmentManager;

    @BindView(R.id.username)
    EditText usernameEditText;
    @BindView(R.id.password)
    EditText passwordEditText;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        injectDependencies();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        IToolbar toolbar = (IToolbar)
                fragmentManager.findFragmentById(R.id.fragment_home_header);

        if (toolbar != null) {
            toolbar.inflateMenu(R.menu.main, menu, getMenuInflater());
        }

        return true;
    }

    @OnClick(R.id.btn_sign_up)
    void signUpUser() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (!validator.isUsernameValid(username)) {
            this.usernameEditText.setError(this.getString(R.string.short_username));
            return;
        }

        if (!validator.isPasswordValid(password)) {
            this.passwordEditText.setError(this.getString(R.string.short_password));
            return;
        }

        String passHash = this.hashProvider.providePasswordHash(password);

        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("passHash", passHash);

        final String url = this.getString(R.string.auth_register_post);

        final Activity currentActivity = this;
        final IUserSession userSession = this.userSession;
        final IJsonParser jsonParser = this.jsonParser;
        final EditText usernameEditText = this.usernameEditText;

        this.httpRequester.post(url, map)
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
                            usernameEditText.setError(getString(R.string.existing_username));
                            return;
                        }

                        Toast.makeText(currentActivity, getString(R.string.sign_up_successfull), Toast.LENGTH_LONG).show();
                        Toast.makeText(currentActivity, getString(R.string.sign_in_redirect), Toast.LENGTH_LONG).show();

                        Intent signInIntent = intentFactory.createIntentToFront(SignInActivity.class);
                        startActivity(signInIntent);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    @OnClick(R.id.link_signin)
    void redirectToSignIn() {
        Intent signInIntent = intentFactory.createIntentToFront(SignInActivity.class);
        startActivity(signInIntent);
    }

    private void injectDependencies() {
        BaseApplication
                .bind(this)
                .from(this)
                .getComponent()
                .getControllerComponent(new ControllerModule(
                        this, getSupportFragmentManager()
                ))
                .inject(this);
    }
}
