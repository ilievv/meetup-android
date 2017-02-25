package com.telerikacademy.meetup.data.local.realm;

import android.graphics.Bitmap;

import com.telerikacademy.meetup.data.local.base.IRecentVenue;

public class RecentVenue implements IRecentVenue {

    private String id;
    private String name;
    private Bitmap picture;

    public RecentVenue(String id, String name, Bitmap picture){
        this.setId(id);
        this.setName(name);
        this.setPicture(picture);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Bitmap getPicture() {
        return this.picture;
    }

    @Override
    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }
}
