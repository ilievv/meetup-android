package com.telerikacademy.meetup.util.base;

import android.graphics.Bitmap;

/**
 * Created by georgivelikov on 26-Feb-17.
 */

public interface IImageUtil {
    byte[] parseToByteArray(Bitmap picture);

    Bitmap transformByteArrayToPicture(byte[] array);
}
