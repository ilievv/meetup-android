package com.telerikacademy.meetup.interfaces;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

public interface IPermissionHandler {

    boolean checkPermissions(Context context, @NonNull String... permissions);

    void requestPermissions(Activity activity, @NonNull String... permissions);
}
