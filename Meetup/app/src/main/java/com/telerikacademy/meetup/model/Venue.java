package com.telerikacademy.meetup.model;

public class Venue implements com.telerikacademy.meetup.model.base.IVenue {

    private String id;
    private String name;
    private String address;
    private String[] types;
    private float rating;

    public Venue() {
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

    private void setId(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Venues.setId parameter cannot be null");
        }

        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Venue.setName parameter cannot be null.");
        }

        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    public String[] getTypes() {
        return types;
    }

    private void setTypes(String[] types) {
        this.types = types;
    }

    public float getRating() {
        return rating;
    }

    private void setRating(float rating) {
        this.rating = rating;
    }
}
