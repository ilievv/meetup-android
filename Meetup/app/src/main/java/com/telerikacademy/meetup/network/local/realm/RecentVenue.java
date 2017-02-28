package com.telerikacademy.meetup.network.local.realm;

import android.graphics.Bitmap;
import com.telerikacademy.meetup.network.local.base.IRecentVenue;

public class RecentVenue implements IRecentVenue {

    private final String name;
    private final Bitmap picture;

    public RecentVenue(String name, Bitmap picture) {
        this.name = name;
        this.picture = picture;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Bitmap getPicture() {
        return this.picture;
    }
}
