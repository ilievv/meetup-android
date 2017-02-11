package com.telerikacademy.meetup.utils.base;

public interface IUserSession {

    String getUsername();

    void setUsername(String username);

    String getToken();

    void setToken(String token);

    boolean isUserLoggedIn();

    void clearSession();
}
