package com.tomek.locationtracker.utils;

import android.os.Build;

/**
 * Created by tomek on 15.07.16.
 */
public class ApiChecker {
    private static final int API_LEVEL = Build.VERSION.SDK_INT;

    public static boolean kitkatOrNewer() {
        return API_LEVEL >= Build.VERSION_CODES.KITKAT;
    }
}
