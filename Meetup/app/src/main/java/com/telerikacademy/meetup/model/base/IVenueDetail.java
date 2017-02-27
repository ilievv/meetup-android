package com.telerikacademy.meetup.model.base;

import android.net.Uri;

public interface IVenueDetail extends IVenue {

    String getPhoneNumber();

    void setPhoneNumber(String phoneNumber);

    Uri getWebsiteUri();

    void setWebsiteUri(Uri websiteUri);
}
