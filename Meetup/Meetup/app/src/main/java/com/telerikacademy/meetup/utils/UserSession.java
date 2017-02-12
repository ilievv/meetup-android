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
    public String getId() {
        String id = this.prefs.getString("id", null);
        return id;
    }

    @Override
    public void setId(String id) {
        this.prefs.edit().putString("id", id).commit();
    }

    public boolean isUserLoggedIn() {
        String username = this.getUsername();
        return username != null;
    }

    public void clearSession() {
        this.setUsername(null);
        this.setId(null);
    }
}
