package com.tomek.locationtracker.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class LocationHelper {

    public static boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public static Location getLastKnownLocation(GoogleApiClient googleApiClient) {
        return LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
    }
}