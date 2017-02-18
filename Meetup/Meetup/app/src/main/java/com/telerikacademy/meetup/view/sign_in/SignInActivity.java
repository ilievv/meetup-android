package com.telerikacademy.meetup.view.sign_in;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.model.User;
import com.telerikacademy.meetup.ui.fragments.base.IToolbar;
import com.telerikacademy.meetup.util.base.IHttpRequester;
import com.telerikacademy.meetup.util.base.IHttpResponse;
import com.telerikacademy.meetup.util.base.IJsonParser;
import com.telerikacademy.meetup.util.base.IUserSession;
import com.telerikacademy.meetup.view.home.HomeActivity;
import com.telerikacademy.meetup.view.sign_up.SignUpActivity;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    @Inject
    IHttpRequester httpRequester;
    @Inject
    IJsonParser jsonParser;
    @Inject
    IUserSession userSession;

    @BindView(R.id.username)
    EditText usernameEditText;
    @BindView(R.id.password)
    EditText passwordEditText;

    private FragmentManager fragmentManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        injectDependencies();

        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        IToolbar menuInflater = (IToolbar)
                fragmentManager.findFragmentById(R.id.fragment_home_header);

        if (menuInflater != null) {
            menuInflater.inflateMenu(R.menu.main, menu, getMenuInflater());
        }

        return true;
    }

    @OnClick(R.id.btn_sign_in)
    void signInUser() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // TODO add to resourse
        if (username.equals("") || password.equals("")) {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_LONG).show();
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("passHash", password);

        String url = "https://telerik-meetup.herokuapp.com/auth/login";

        final Context context = getApplicationContext();
        final Activity currentActivity = this;
        final IUserSession userSession = this.userSession;
        final IJsonParser jsonParser = this.jsonParser;

        this.httpRequester.post(url, map)
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
                            Toast.makeText(context, "Invalid username or password", Toast.LENGTH_LONG).show();
                            return;
                        }

                        userSession.setUsername(resultUser.getUsername());
                        userSession.setId(resultUser.getId());
                        Toast.makeText(context, "You are now signed in as " + resultUser.getUsername(), Toast.LENGTH_LONG).show();
                        Intent homeIntent = new Intent(currentActivity, HomeActivity.class);
                        startActivity(homeIntent);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    @OnClick(R.id.link_signup)
    void redirectToSignUp() {
        Intent signUpIntent = new Intent(this, SignUpActivity.class);
        signUpIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(signUpIntent);
    }

    private void injectDependencies() {
        BaseApplication
                .from(this)
                .getComponent()
                .inject(this);

        ButterKnife.bind(this);
    }
}
