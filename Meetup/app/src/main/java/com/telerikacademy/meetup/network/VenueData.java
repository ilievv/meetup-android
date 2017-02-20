package com.telerikacademy.meetup.network;

import com.telerikacademy.meetup.model.Venue;
import com.telerikacademy.meetup.network.base.IVenueData;

import java.util.ArrayList;
import java.util.List;

public class VenueData implements IVenueData {

    @Override
    public List<Venue> getSampleData() {
        List<Venue> venues = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Venue venue = new Venue(Integer.toString(i),
                    "Pri Ilyo #" + i, "zh.k. Lyulin " + i + 1, new String[]{"food"}, i % 5f);
            venues.add(venue);
        }
        Venue someVen = new Venue("123", "Gosho",
                "Kostinbrod", new String[]{"restaurant", "bar", "club", "food"}, 3.2f);
        venues.add(someVen);

        return venues;
    }
}
