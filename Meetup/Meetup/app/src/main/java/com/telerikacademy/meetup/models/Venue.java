package com.telerikacademy.meetup.models;

import java.io.Serializable;

public class Venue implements Serializable {

    private String id;
    private String name;
    private String address;
    private String[] types;
    private float rating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
