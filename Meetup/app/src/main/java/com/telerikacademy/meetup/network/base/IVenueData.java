package com.telerikacademy.meetup.network.base;

import com.telerikacademy.meetup.model.base.IVenue;
import io.reactivex.Observable;

import java.util.List;

public interface IVenueData {

    Observable<List<IVenue>> getNearby(double latitude, double longitude, int radius);

    Observable<List<IVenue>> getNearby(double latitude, double longitude, int radius, String type);
}
