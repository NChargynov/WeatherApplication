package com.geektech.weatherapplication.ui.service.maps;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionUtils {

    private static String[] PERMISSION =
            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION};

    public static int REQUEST_CODE = 101;

    public static boolean isPermissionLocationGranted(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, PERMISSION[0]) ==
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, PERMISSION[1]) ==
                        PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        requestPermission(activity);
        return false;
    }

    private static void requestPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, PERMISSION, REQUEST_CODE);
    }
}
