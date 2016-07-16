package com.tomek.locationtracker.util;

import android.content.Context;
import android.location.LocationManager;

/**
 * Created by tomek on 16.07.16.
 */
public class LocationHelper {

    public static boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
