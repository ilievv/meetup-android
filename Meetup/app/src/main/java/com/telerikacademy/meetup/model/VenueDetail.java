package com.telerikacademy.meetup.model;

import android.net.Uri;
import com.telerikacademy.meetup.model.base.IVenueDetail;

public class VenueDetail extends Venue
        implements IVenueDetail {

    private String phoneNumber;
    private Uri websiteUri;

    public VenueDetail(String id, String name) {
        super(id, name);
    }

    public VenueDetail(String id, String name, String address, String[] types,
                       float rating, String phoneNumber, Uri websiteUri) {

        super(id, name, address, types, rating);
        setPhoneNumber(phoneNumber);
        setWebsiteUri(websiteUri);
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Uri getWebsiteUri() {
        return websiteUri;
    }

    @Override
    public void setWebsiteUri(Uri websiteUri) {
        this.websiteUri = websiteUri;
    }
}
