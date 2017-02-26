package com.telerikacademy.meetup.data.local.realm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.telerikacademy.meetup.data.local.base.ILocalData;
import com.telerikacademy.meetup.data.local.base.IRecentVenue;
import com.telerikacademy.meetup.model.base.IVenue;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmLocalData implements ILocalData {

    private Context context;

    public RealmLocalData(Context context){
        this.context = context;
        //Realm.init(this.context);
    }

    @Override
    public void saveVenue(IVenue venue, Bitmap picture) {
        String id = venue.getId();
        String name = venue.getName();
        //byte[] pictureBytes = this.transformPictureToByteArray(picture);

        //Realm realm = Realm.getDefaultInstance();
        //realm.close();
        /*realm.beginTransaction();
        RealmRecentVenue recentVenue = realm.createObject(RealmRecentVenue.class);
        recentVenue.setName(name);
        recentVenue.setId(id);
        recentVenue.setPictureBytes(pictureBytes);
        realm.commitTransaction();*/
    }

    @Override
    public List<IRecentVenue> getRecentVenues() {
        /*Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();

        List<RealmRecentVenue> dbResults = realm.where(RealmRecentVenue.class).findAll();
        realm.close();
        List<IRecentVenue> results = new ArrayList<>();
        for (RealmRecentVenue v : dbResults) {
            RecentVenue r = new RecentVenue(v.getId(), v.getName(), this.transformByteArrayToPicture(v.getPictureBytes()));
            results.add(r);
        } */
        List<IRecentVenue> results = new ArrayList<>();
        return results;
    }

    private byte[] transformPictureToByteArray(Bitmap picture){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }

    private Bitmap transformByteArrayToPicture(byte[] array){
        Bitmap picture = BitmapFactory.decodeByteArray(array, 0, array.length);

        return picture;
    }
}
