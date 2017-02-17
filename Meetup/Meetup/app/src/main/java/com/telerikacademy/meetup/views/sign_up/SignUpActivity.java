package com.telerikacademy.meetup.views.sign_up;

import android.app.Activity;
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
import com.telerikacademy.meetup.ui.fragments.base.IToolbar;
import com.telerikacademy.meetup.models.User;
import com.telerikacademy.meetup.utils.base.IHttpRequester;
import com.telerikacademy.meetup.utils.base.IHttpResponse;
import com.telerikacademy.meetup.utils.base.IJsonParser;
import com.telerikacademy.meetup.utils.base.IUserSession;
import com.telerikacademy.meetup.views.sign_in.SignInActivity;
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

    @BindView(R.id.username)
    EditText usernameEditText;
    @BindView(R.id.password)
    EditText passwordEditText;

    private FragmentManager fragmentManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
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

    @OnClick(R.id.btn_sign_up)
    void signUpUser() {
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

        final String url = "https://telerik-meetup.herokuapp.com/auth/register";

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
                        String responseBody = value.getBody();
                        String userJsonObject;
                        User resultUser;

                        try {
                            userJsonObject = jsonParser.toJsonFromResponseBody(responseBody);
                            resultUser = jsonParser.fromJson(userJsonObject, User.class);
                        } catch (IllegalStateException e) {
                            Toast.makeText(currentActivity, "Username already exists", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Toast.makeText(currentActivity, "Sign up successfull!", Toast.LENGTH_LONG).show();
                        Toast.makeText(currentActivity, "You may sign in now...", Toast.LENGTH_LONG).show();

                        Intent signInIntent = new Intent(currentActivity, SignInActivity.class);
                        signInIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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
        Intent signInIntent = new Intent(this, SignInActivity.class);
        signInIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(signInIntent);
    }

    private void injectDependencies() {
        ((BaseApplication) getApplication())
                .getApplicationComponent()
                .inject(this);
        ButterKnife.bind(this);
    }
}
