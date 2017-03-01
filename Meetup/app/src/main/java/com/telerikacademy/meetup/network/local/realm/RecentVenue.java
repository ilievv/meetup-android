package com.telerikacademy.meetup.network.local.realm;

import android.graphics.Bitmap;
import com.telerikacademy.meetup.network.local.base.IRecentVenue;

public class RecentVenue implements IRecentVenue {

    private final String id;
    private final String name;
    private final Bitmap picture;

    public RecentVenue(String id, String name, Bitmap picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Bitmap getPhoto() {
        return picture;
    }
}
