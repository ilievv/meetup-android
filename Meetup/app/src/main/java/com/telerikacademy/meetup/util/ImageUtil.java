package com.telerikacademy.meetup.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.telerikacademy.meetup.util.base.IImageUtil;

import java.io.ByteArrayOutputStream;

public class ImageUtil implements IImageUtil {

    @Override
    public byte[] parseToByteArray(Bitmap picture) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }

    @Override
    public Bitmap transformByteArrayToPicture(byte[] array) {
        Bitmap picture = BitmapFactory.decodeByteArray(array, 0, array.length);
        return picture;
    }
}
