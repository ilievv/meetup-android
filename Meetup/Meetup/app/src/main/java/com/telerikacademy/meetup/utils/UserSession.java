package com.telerikacademy.meetup.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.telerikacademy.meetup.utils.base.IUserSession;

public class UserSession implements IUserSession {

    private SharedPreferences prefs;

    public UserSession(Context context) {
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public String getUsername() {
        String username = this.prefs.getString("username", null);
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.prefs.edit().putString("username", username).commit();
    }

    @Override
    public String getToken() {
        String token = this.prefs.getString("token", null);
        return token;
    }

    @Override
    public void setToken(String token) {
        this.prefs.edit().putString("token", token).commit();
    }

    public boolean isUserLoggedIn() {
        String username = this.getUsername();
        return username != null;
    }

    public void clearSession() {
        this.setUsername(null);
        this.setToken(null);
    }
}
