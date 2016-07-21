package com.tomek.locationtracker.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by tomek on 16.07.16.
 */
public class LocationHelper {


    public static final float DISPLACEMENT = 25;       // minimum radius of displacement (in meters) to notify onLocationChanged;
    public static final long UPDATE_INTERVAL = 20000; // Update location every 20000 miliseconds

    public static boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static Location getLastKnownLocation(GoogleApiClient googleApiClient) {
        return LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
    }

    public static void updateLastLocation(Location previousLocation, Location currentLocation) {
        previousLocation.setLatitude(currentLocation.getLatitude());
        previousLocation.setLongitude(currentLocation.getLongitude());
    }
}
