package com.telerikacademy.meetup.data.local.base;

import android.graphics.Bitmap;

import com.telerikacademy.meetup.data.local.realm.RealmRecentVenue;
import com.telerikacademy.meetup.model.base.IVenue;

import java.util.List;

public interface ILocalData {
    void saveVenue(IVenue venue, Bitmap picture);

    List<IRecentVenue> getRecentVenues();
}

