package com.telerikacademy.meetup.data.local.realm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.telerikacademy.meetup.data.local.base.IRecentVenue;

import java.io.ByteArrayOutputStream;

import io.realm.RealmObject;

public class RealmRecentVenue extends RealmObject {

    private String name;
    private String id;
    private byte[] pictureBytesArray;

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


}
