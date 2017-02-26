package com.telerikacademy.meetup.data.local.realm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.telerikacademy.meetup.data.local.base.IRecentVenue;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmRecentVenue extends RealmObject {

    @PrimaryKey
    private String id;
    private String name;
    private byte[] pictureBytesArray;
    private String viewerUsername;
    //private Date dateViewed;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getPictureBytes() {
        return this.pictureBytesArray;
    }

    public void setPictureBytes(byte[] pictureBytes) {
        this.pictureBytesArray = pictureBytes;
    }

    public String getViewerUsername(){
        return viewerUsername;
    }

    public void setViewerUsername(String username) {
        this.viewerUsername = username;
    }

    /*public Date getDateViewed() { return this.dateViewed; }

    public void setDateViewed(Date date) { this.dateViewed = date; }*/
}
