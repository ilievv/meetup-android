package com.telerikacademy.meetup.network;

import com.telerikacademy.meetup.config.base.IGoogleApiConstants;
import com.telerikacademy.meetup.model.base.IVenue;
import com.telerikacademy.meetup.model.gson.nearby_search.Venue;
import com.telerikacademy.meetup.network.base.IVenueData;
import com.telerikacademy.meetup.provider.base.IVenueFactory;
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
    private final IVenueFactory venueFactory;

    @Inject
    public VenueData(IGoogleApiConstants googleApiConstants, IHttpRequester httpRequester,
                     IJsonParser jsonParser, IVenueFactory venueFactory) {

        this.googleApiConstants = googleApiConstants;
        this.httpRequester = httpRequester;
        this.jsonParser = jsonParser;
        this.venueFactory = venueFactory;
    }

    @Override
    public List<IVenue> getSampleData() {
        List<IVenue> venues = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            com.telerikacademy.meetup.model.Venue venue = new com.telerikacademy.meetup.model.Venue(Integer.toString(i),
                    "Pri Ilyo #" + i, "zh.k. Lyulin " + i + 1, new String[]{"food"}, i % 5f);
            venues.add(venue);
        }
        com.telerikacademy.meetup.model.Venue someVen = new com.telerikacademy.meetup.model.Venue("123", "Gosho",
                "Kostinbrod", new String[]{"restaurant", "bar", "club", "food"}, 3.2f);
        venues.add(someVen);

        return venues;
    }

    @Override
    public Observable<List<IVenue>> getNearby(double latitude, double longitude, int radius) {
        String nearbySearchUrl = googleApiConstants.nearbySearchUrl(latitude, longitude, radius);

        return httpRequester
                .get(nearbySearchUrl)
                .map(new Function<IHttpResponse, List<com.telerikacademy.meetup.model.base.IVenue>>() {
                    @Override
                    public List<IVenue> apply(IHttpResponse iHttpResponse) throws Exception {
                        String responseBody = iHttpResponse.getBody();

                        List<Venue> venues = jsonParser.getDirectArray(responseBody, "results", Venue.class);
                        List<IVenue> parsedVenues = new ArrayList<>();

                        for (Venue venue : venues) {
                            IVenue parsedVenue = venueFactory.createVenue(
                                    venue.getId(),
                                    venue.getName(),
                                    venue.getAddress(),
                                    venue.getTypes(),
                                    venue.getRating()
                            );

                            parsedVenues.add(parsedVenue);
                        }

                        return parsedVenues;
                    }
                });
    }
}
