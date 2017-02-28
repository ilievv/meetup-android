package com.telerikacademy.meetup.data.local.realm;

import android.content.Context;
import android.graphics.Bitmap;
import com.telerikacademy.meetup.config.base.IApiConstants;
import com.telerikacademy.meetup.data.local.base.ILocalData;
import com.telerikacademy.meetup.data.local.base.IRecentVenue;
import com.telerikacademy.meetup.model.base.IVenue;
import com.telerikacademy.meetup.util.base.IImageUtil;
import com.telerikacademy.meetup.util.base.IUserSession;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

public class RealmLocalData implements ILocalData {

    private final IUserSession userSession;
    private final IImageUtil imageUtil;
    private final IApiConstants constants;
    private Context context;

    public RealmLocalData(Context context, IUserSession userSession, IImageUtil imageUtil, IApiConstants constants) {
        this.context = context;
        this.userSession = userSession;
        this.imageUtil = imageUtil;
        this.constants = constants;
        Realm.init(this.context);
    }

    public Single<IVenue> saveVenueToRecent(final IVenue venue, final Bitmap picture) {
        return Single.defer(new Callable<SingleSource<IVenue>>() {
            @Override
            public SingleSource<IVenue> call() throws Exception {
                final Realm realm = Realm.getDefaultInstance();

                RealmRecentVenue recentVenue = new RealmRecentVenue();

                String venueToSaveId = venue.getId();
                String name = venue.getName();
                byte[] pictureBytes = imageUtil.transformPictureToByteArray(picture);
                String username = userSession.getUsername();
                if (username == null) {
                    username = constants.defaultUsername();
                }

                Date dateViewed = new Date();
                String id = generateId(venueToSaveId, name, username);

                recentVenue.setId(id);
                recentVenue.setName(name);
                recentVenue.setPictureBytes(pictureBytes);
                recentVenue.setViewerUsername(username);
                recentVenue.setDateViewed(dateViewed);

                realm.beginTransaction();
                realm.copyToRealm(recentVenue);
                realm.commitTransaction();
                realm.close();

                return Single.just(venue);
            }
        });
    }

    public Single<List<IRecentVenue>> getRecentVenues() {
        return Single.defer(new Callable<SingleSource<List<IRecentVenue>>>() {
            @Override
            public SingleSource<List<IRecentVenue>> call() throws Exception {
                final List<IRecentVenue> resultsToDisplay = new ArrayList<>();
                final Realm realm = Realm.getDefaultInstance();

                String currentUsername = userSession.getUsername();
                if (currentUsername == null) {
                    currentUsername = constants.defaultUsername();
                }

                final RealmResults<RealmRecentVenue> results = realm.where(RealmRecentVenue.class)
                        .equalTo("viewerUsername", currentUsername)
                        .findAllSorted("dateViewed", Sort.DESCENDING)
                        .distinct("name");

                for (RealmRecentVenue r : results) {
                    IRecentVenue recentVenue = new RecentVenue(r.getName(),
                            imageUtil.transformByteArrayToPicture(r.getPictureBytes()));
                    resultsToDisplay.add(recentVenue);
                }

                realm.close();
                return Single.just(resultsToDisplay);
            }
        });
    }

    private String generateId(String venueId, String username, String venueName) {
        String helpString = venueId + username + venueName;
        char[] array = helpString.toCharArray();
        List<Character> list = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            if (array[i] != ' ') {
                list.add(array[i]);
            }
        }

        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }

        return sb.toString();
    }

    private void wipeData() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm realm = Realm.getInstance(config);

        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }
}
