package com.telerikacademy.meetup.model;

import com.telerikacademy.meetup.model.base.IUser;

public class User implements IUser {

    private String username;
    private String id;

    public User() {
    }

    public User(String username, String id) {

    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
