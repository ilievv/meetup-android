package com.telerikacademy.meetup.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.telerikacademy.meetup.utils.base.IPermissionHandler;

public class PermissionHandler implements IPermissionHandler {

    public boolean checkPermissions(Context context, @NonNull String... permissions) {
        for (String permission : permissions) {
            int permissionRes = ContextCompat.checkSelfPermission(context, permission);
            if (permissionRes != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    public void requestPermissions(Activity activity, @NonNull String... permissions) {
        for (String permission : permissions) {
            int permissionRes = ContextCompat.checkSelfPermission(activity, permission);
            if (permissionRes != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, 1);
            }
        }
    }
}
