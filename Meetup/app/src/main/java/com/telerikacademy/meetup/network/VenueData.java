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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VenueData implements IVenueData {

    private final IGoogleApiConstants googleApiConstants;
    private final IHttpRequester httpRequester;
    private final IJsonParser jsonParser;
    private final IVenueFactory venueFactory;
    private final Set<String> blacklistedTypes;

    @Inject
    public VenueData(IGoogleApiConstants googleApiConstants, IHttpRequester httpRequester,
                     IJsonParser jsonParser, IVenueFactory venueFactory) {

        this.googleApiConstants = googleApiConstants;
        this.httpRequester = httpRequester;
        this.jsonParser = jsonParser;
        this.venueFactory = venueFactory;
        blacklistedTypes = new HashSet<>();
        populateBlacklist();
    }

    @Override
    public Observable<List<IVenue>> getNearby(double latitude, double longitude, int radius) {
        String nearbySearchUrl = googleApiConstants.nearbySearchUrl(latitude, longitude, radius);

        return httpRequester
                .get(nearbySearchUrl)
                .map(new Function<IHttpResponse, List<IVenue>>() {
                    @Override
                    public List<IVenue> apply(IHttpResponse iHttpResponse) throws Exception {
                        String responseBody = iHttpResponse.getBody();
                        List<Venue> venues = jsonParser
                                .getDirectArray(responseBody, "results", Venue.class);

                        return parseVenues(venues);
                    }
                });
    }

    @Override
    public Observable<List<IVenue>> getNearby(double latitude, double longitude, int radius, String type) {
        if (type == null || type.isEmpty()) {
            return getNearby(latitude, longitude, radius);
        }

        String nearbySearchUrl = googleApiConstants.nearbySearchUrl(latitude, longitude, radius, type);

        return httpRequester
                .get(nearbySearchUrl)
                .map(new Function<IHttpResponse, List<IVenue>>() {
                    @Override
                    public List<IVenue> apply(IHttpResponse iHttpResponse) throws Exception {
                        String responseBody = iHttpResponse.getBody();
                        List<Venue> venues = jsonParser
                                .getDirectArray(responseBody, "results", Venue.class);

                        return parseVenues(venues);
                    }
                });
    }

    private List<IVenue> parseVenues(List<Venue> venues) {
        List<IVenue> parsedVenues = new ArrayList<>();

        for (final Venue venue : venues) {
            IVenue parsedVenue = venueFactory.createVenue(
                    venue.getId(),
                    venue.getName(),
                    venue.getAddress(),
                    parseVenueTypes(venue.getTypes()),
                    venue.getRating()
            );

            parsedVenues.add(parsedVenue);
        }

        return parsedVenues;
    }

    private String[] parseVenueTypes(String[] venueTypes) {
        final char OLD_SEPARATOR = '_';
        final char NEW_SEPARATOR = ' ';
        final int MAX_VENUE_TYPES = 3;

        final int venueTypesCount = Math.min(venueTypes.length, MAX_VENUE_TYPES);
        List<String> parsedVenueTypes = new ArrayList<>();

        for (int i = 0; i < venueTypesCount; i++) {
            String venueType = venueTypes[i];
            if (blacklistedTypes.contains(venueType)) {
                continue;
            }

            venueType = venueType.replace(OLD_SEPARATOR, NEW_SEPARATOR);
            parsedVenueTypes.add(venueType);
        }

        return parsedVenueTypes.toArray(new String[parsedVenueTypes.size()]);
    }

    private void populateBlacklist() {
        blacklistedTypes.add("point_of_interest");
        blacklistedTypes.add("establishment");
    }
}
