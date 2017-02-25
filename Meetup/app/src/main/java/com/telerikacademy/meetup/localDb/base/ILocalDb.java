package com.telerikacademy.meetup.localDb.base;

import com.telerikacademy.meetup.model.base.IVenue;

public interface ILocalDb {
    void saveVenue(IVenue venue);
}

