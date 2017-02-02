package com.telerikacademy.meetup.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.telerikacademy.meetup.R;
import com.telerikacademy.meetup.interfaces.IToolbar;


public class LoginActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.fragmentManager = this.getSupportFragmentManager();
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
}
