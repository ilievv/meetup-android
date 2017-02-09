package com.telerikacademy.meetup.interfaces;

import android.support.annotation.NonNull;

public interface IPermissionHandler {

    boolean checkPermissions(@NonNull String... permissions);

    void requestPermissions(@NonNull String... permissions);
}
