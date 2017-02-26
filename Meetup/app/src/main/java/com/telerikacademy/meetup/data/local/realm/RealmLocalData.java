package com.telerikacademy.meetup.data.local.realm;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.ImageView;

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
import io.realm.RealmConfiguration;

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

                String name = venueForSave.getName();
                byte[] pictureBytes = imageUtil.transformPictureToByteArray(pictureForSave);
                String username = userSession.getUsername();
                if(username == null) {
                    username = constants.defaultUsername();
                }

                String id = venueForSave.getId() + username;

                recentVenue.setId(id);
                recentVenue.setName(name);
                recentVenue.setPictureBytes(pictureBytes);
                recentVenue.setViewerUsername(username);
                //recentVenue.setDateViewed(new Date());

                bgRealm.copyToRealmOrUpdate(recentVenue);
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
    public void loadRecentVenues(Activity activity) {

        /*RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm realm = Realm.getInstance(config);

        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();*/

        Realm realm = Realm.getDefaultInstance();
        final Activity activityForTransaction = activity;

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                String currentUsername = userSession.getUsername();

                if(currentUsername == null){
                    currentUsername = constants.defaultUsername();
                }

                List<RealmRecentVenue> results = bgRealm.where(RealmRecentVenue.class).equalTo("viewerUsername", currentUsername).findAll();

                int size = results.size();
                int venuesCountForDisplay = size - constants.recentVenuesForDisplayCount();
                if(venuesCountForDisplay < 0) {
                    venuesCountForDisplay = 0;
                }

                int position = 0;

                for (int i = size - 1; i >= venuesCountForDisplay; i--) {
                    String name = results.get(i).getName();
                    byte[] pictureBytes = results.get(i).getPictureBytes();

                    int buttonId = activityForTransaction.getResources().getIdentifier("rv_button_" + position,
                            "id", activityForTransaction.getPackageName());
                    Button button = (Button) activityForTransaction.findViewById(buttonId);
                    button.setText(name);

                    int imageId = activityForTransaction.getResources().getIdentifier("rv_image_" + position,
                            "id", activityForTransaction.getPackageName());
                    ImageView image = (ImageView) activityForTransaction.findViewById(imageId);
                    image.setImageBitmap(imageUtil.transformByteArrayToPicture(pictureBytes));

                    position++;
                }
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
}
