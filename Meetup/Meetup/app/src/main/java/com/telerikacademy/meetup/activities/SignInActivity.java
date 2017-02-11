package com.telerikacademy.meetup.activities;

import android.database.Observable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.telerikacademy.meetup.BaseApplication;
import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.fragments.base.IToolbar;
import com.telerikacademy.meetup.network.base.IHttpRequester;
import com.telerikacademy.meetup.network.base.IHttpResponse;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;


public class SignInActivity extends AppCompatActivity {

    @Inject
    public IHttpRequester httpRequester;

    private FragmentManager fragmentManager;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView textView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ((BaseApplication) getApplication()).getApplicationComponent().inject(this);

        this.fragmentManager = this.getSupportFragmentManager();
        this.usernameEditText = (EditText)findViewById(R.id.username);
        this.passwordEditText = (EditText)findViewById(R.id.password);
        this.textView = (TextView)findViewById(R.id.text_login);
        this.attachSignInButtonEvent();
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

    private void attachSignInButtonEvent(){
        Button signInButton = (Button) findViewById(R.id.btn_sign_in);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInUser();
            }
        });

    }


    private void signInUser(){
        String username = this.usernameEditText.getText().toString();
        String password = this.passwordEditText.getText().toString();

        // TODO add to resourse
        if(username == null || password == null) {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_LONG).show();
            return;
        }

        //String username1 = "georgivelikov";
        //String password1 = "123456";
        final TextView tv = this.textView;
        String url = "https://telerik-meetup.herokuapp.com/auth/login";
        //String localUrl = "http://10.0.2.2:8080/auth/login";
        //String body = String.format("{ \"username\": %s, \"password\": %s }", username1, password1);
        this.httpRequester.post(url, username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IHttpResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(IHttpResponse value) {
                        tv.setText(value.getBody().toString());
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
