package com.telerikacademy.meetup.data.local.realm;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.telerikacademy.meetup.config.base.IApiConstants;
import com.telerikacademy.meetup.data.local.base.ILocalData;
import com.telerikacademy.meetup.data.local.base.IRecentVenue;
import com.telerikacademy.meetup.model.base.IVenue;
import com.telerikacademy.meetup.util.base.IImageUtil;
import com.telerikacademy.meetup.util.base.IUserSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmLocalData implements ILocalData {

    private final IUserSession userSession;
    private final IImageUtil imageUtil;
    private final IApiConstants constants;
    private Context context;

    public RealmLocalData(Context context, IUserSession userSession, IImageUtil imageUtil, IApiConstants constants){
        this.context = context;
        this.userSession = userSession;
        this.imageUtil = imageUtil;
        this.constants = constants;
        Realm.init(this.context);
    }

    @Override
    public void saveVenue(final IVenue venue, final Bitmap picture) {
        final IVenue venueForSave = venue;
        final Bitmap pictureForSave = picture;

        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {

            private String generateId(String venueId, String username, String venueName) {
                String helpString = venueId + username + venueName;
                char[] array = helpString.toCharArray();
                List<Character> list = new ArrayList<>();

                for(int i = 0; i < array.length; i++){
                    if(array[i] != ' '){
                        list.add(array[i]);
                    }
                }

                Collections.shuffle(list);

                StringBuilder sb = new StringBuilder();

                for(int i = 0; i < list.size(); i++){
                    sb.append(list.get(i));
                }

                return sb.toString();
            }

            @Override
            public void execute(Realm bgRealm) {
                RealmRecentVenue recentVenue = new RealmRecentVenue();

                String venueForSaveId = venueForSave.getId();
                String name = venueForSave.getName();
                byte[] pictureBytes = imageUtil.transformPictureToByteArray(pictureForSave);
                String username = userSession.getUsername();
                if(username == null) {
                    username = constants.defaultUsername();
                }

                Date dateViewed = new Date();
                String id = this.generateId(venueForSaveId, name, username);

                recentVenue.setId(id);
                recentVenue.setName(name);
                recentVenue.setPictureBytes(pictureBytes);
                recentVenue.setViewerUsername(username);
                recentVenue.setDateViewed(dateViewed);

                bgRealm.copyToRealm(recentVenue);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                realm.close();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
            }
        });
    }

    @Override
    public List<IRecentVenue> loadRecentVenues() {

        /*RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm realm = Realm.getInstance(config);

        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();*/

        final List<IRecentVenue> resultsForDisplay = new ArrayList<>();
        final Realm realm = Realm.getDefaultInstance();

        String currentUsername = userSession.getUsername();
        if(currentUsername == null){
            currentUsername = constants.defaultUsername();
        }

        final RealmResults<RealmRecentVenue> results = realm.where(RealmRecentVenue.class)
                .equalTo("viewerUsername", currentUsername)
                .findAllSorted("dateViewed", Sort.DESCENDING)
                .distinct("name");

        for(RealmRecentVenue r : results) {
            IRecentVenue recentVenue =
                    new RecentVenue(r.getName(), imageUtil.transformByteArrayToPicture(r.getPictureBytes()));
            resultsForDisplay.add(recentVenue);
        }

        return resultsForDisplay;
    }
}
