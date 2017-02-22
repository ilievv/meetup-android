package com.telerikacademy.meetup.network;

import com.telerikacademy.meetup.config.base.IGoogleApiConstants;
import com.telerikacademy.meetup.model.Venue;
import com.telerikacademy.meetup.network.base.IVenueData;
import com.telerikacademy.meetup.util.base.IHttpRequester;
import com.telerikacademy.meetup.util.base.IHttpResponse;
import com.telerikacademy.meetup.util.base.IJsonParser;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class VenueData implements IVenueData {

    private final IGoogleApiConstants googleApiConstants;
    private final IHttpRequester httpRequester;
    private final IJsonParser jsonParser;

    @Inject
    public VenueData(IGoogleApiConstants googleApiConstants, IHttpRequester httpRequester,
                     IJsonParser jsonParser) {

        this.googleApiConstants = googleApiConstants;
        this.httpRequester = httpRequester;
        this.jsonParser = jsonParser;
    }

    @Override
    public List<com.telerikacademy.meetup.model.base.IVenue> getSampleData() {
        List<com.telerikacademy.meetup.model.base.IVenue> venues = new ArrayList<>();

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

    @Override
    public Observable<List<com.telerikacademy.meetup.model.base.IVenue>> getNearby(double latitude, double longitude, int radius) {
        String nearbySearchUrl = googleApiConstants.nearbySearchUrl(latitude, longitude, radius);

        return httpRequester
                .get(nearbySearchUrl)
                .map(new Function<IHttpResponse, List<com.telerikacademy.meetup.model.base.IVenue>>() {
                    @Override
                    public List<com.telerikacademy.meetup.model.base.IVenue> apply(IHttpResponse iHttpResponse) throws Exception {
                        String body = iHttpResponse.getBody();
//                        String latitude = jsonParser.getDirectMember(body, "results", "geometry", "location", "lat");
                        List<com.telerikacademy.meetup.model.base.IVenue> venues = new ArrayList<>();
                        return venues;
                    }
                });
    }
}
