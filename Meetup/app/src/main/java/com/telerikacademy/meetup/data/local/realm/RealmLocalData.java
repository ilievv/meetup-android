package com.telerikacademy.meetup.data.local.realm;

import android.content.Context;
import android.graphics.Bitmap;

import com.telerikacademy.meetup.config.base.IApiConstants;
import com.telerikacademy.meetup.data.local.base.ILocalData;
import com.telerikacademy.meetup.data.local.base.IRecentVenue;
import com.telerikacademy.meetup.model.base.IVenue;
import com.telerikacademy.meetup.util.base.IImageUtil;
import com.telerikacademy.meetup.util.base.IUserSession;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

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
        final Context c = this.context;

        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                RealmRecentVenue recentVenue = new RealmRecentVenue();

                String id = venueForSave.getId();
                String name = venueForSave.getName();
                byte[] pictureBytes = imageUtil.transformPictureToByteArray(pictureForSave);
                String username = userSession.getUsername();
                if(username == null) {
                    username = constants.defaultUsername();
                }

                recentVenue.setId(id);
                recentVenue.setName(name);
                recentVenue.setPictureBytes(pictureBytes);
                recentVenue.setViewerUsername(username);
                //recentVenue.setDateViewed(new Date());

                bgRealm.copyToRealm(recentVenue);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
            }
        });
    }

    @Override
    public List<IRecentVenue> getRecentVenues() {

        /*RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm realm = Realm.getInstance(config);

        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();*/

        Realm realm = Realm.getDefaultInstance();

        List<RealmRecentVenue> dbResults = realm.where(RealmRecentVenue.class).findAll();

        List<IRecentVenue> results = new ArrayList<>();
        for (RealmRecentVenue v : dbResults) {
            RecentVenue r = new RecentVenue(v.getId(), v.getName(), imageUtil.transformByteArrayToPicture(v.getPictureBytes()));
            results.add(r);
        }

        return results;
    }
}
