package com.telerikacademy.meetup.models.base;

import java.io.Serializable;

public interface IVenue extends Serializable {

    String getId();

    String getName();

    String getAddress();

    String[] getTypes();

    float getRating();
}
