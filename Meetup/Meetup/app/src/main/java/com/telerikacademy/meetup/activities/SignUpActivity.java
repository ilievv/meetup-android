package com.telerikacademy.meetup.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.fragments.base.IToolbar;
import com.telerikacademy.meetup.models.User;
import com.telerikacademy.meetup.utils.base.IHttpRequester;
import com.telerikacademy.meetup.utils.base.IHttpResponse;
import com.telerikacademy.meetup.utils.base.IJsonParser;
import com.telerikacademy.meetup.utils.base.IUserSession;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SignUpActivity extends AppCompatActivity {
    @Inject
    public IHttpRequester httpRequester;
    @Inject
    public IJsonParser jsonParser;
    @Inject
    public IUserSession userSession;

    private FragmentManager fragmentManager;
    private EditText usernameEditText;
    private EditText passwordEditText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ((BaseApplication) getApplication()).getApplicationComponent().inject(this);

        this.fragmentManager = this.getSupportFragmentManager();
        this.usernameEditText = (EditText)findViewById(R.id.username);
        this.passwordEditText = (EditText)findViewById(R.id.password);

        this.attachSignUpButtonEvent();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        IToolbar menuInflater = (IToolbar)
                this.fragmentManager.findFragmentById(R.id.fragment_toolbar);

        if (menuInflater != null) {
            menuInflater.inflateMenu(R.menu.main, menu, getMenuInflater());
        }

        return true;
    }

    private void attachSignUpButtonEvent(){
        Button signInButton = (Button) findViewById(R.id.btn_sign_up);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpUser();
            }
        });
    }

    private void signUpUser(){
        String username = this.usernameEditText.getText().toString();
        String password = this.passwordEditText.getText().toString();

        // TODO add to resourse
        if(username.equals("") || password.equals("")) {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_LONG).show();
            return;
        }

        Map map = new HashMap<String, String>();
        map.put("username", username);
        map.put("passHash", password);

        String url = "https://telerik-meetup.herokuapp.com/auth/register";

        final Context context = this.getApplicationContext();
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
                        } catch(IllegalStateException e) {
                            Toast.makeText(context, "Username already exists", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Toast.makeText(context, "Sign up successfull!", Toast.LENGTH_LONG).show();
                        Toast.makeText(context, "You may sign in now...", Toast.LENGTH_LONG).show();
                        Intent signInIntent = new Intent(currentActivity, SignInActivity.class);
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
}
