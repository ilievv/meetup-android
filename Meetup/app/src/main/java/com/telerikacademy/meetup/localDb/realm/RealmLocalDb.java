package com.telerikacademy.meetup.localDb.realm;

import com.telerikacademy.meetup.localDb.base.ILocalDb;
import com.telerikacademy.meetup.model.base.IVenue;

public class RealmLocalDb implements ILocalDb {

    @Override
    public void saveVenue(IVenue venue) {
        String id = venue.getId();
        String name = venue.getName();
    }
}
