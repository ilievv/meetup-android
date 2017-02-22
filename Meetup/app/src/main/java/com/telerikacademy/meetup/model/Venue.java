package com.telerikacademy.meetup.model;

import com.telerikacademy.meetup.model.base.IVenue;

public class Venue implements IVenue {

    private String id;
    private String name;
    private String address;
    private String[] types;
    private float rating;

    public Venue(String id, String name) {
        setId(id);
        setName(name);
    }

    public Venue(String id, String name, String address, String[] types, float rating) {
        setId(id);
        setName(name);
        setAddress(address);
        setTypes(types);
        setRating(rating);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Venues.setId parameter cannot be null");
        }

        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Venue.setName parameter cannot be null.");
        }

        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
