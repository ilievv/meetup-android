package com.telerikacademy.meetup.data.local.base;

import android.graphics.Bitmap;

public interface IRecentVenue {

    String getName();

    void setName(String name);

    String getId();

    void setId(String id);

    Bitmap getPicture();

    void setPicture(Bitmap picture);
}
