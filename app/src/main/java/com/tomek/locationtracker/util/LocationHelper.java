package com.tomek.locationtracker.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by tomek on 16.07.16.
 */
public class LocationHelper {
    private static final int MAX_ADRESSES = 1;

    public static boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static Location getLastKnownLocation(GoogleApiClient googleApiClient) {
        return LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
    }

    public static String getCityFromLocation(Context context, double latitude, double longitude) throws IOException {
        String result = "";
        Geocoder geo = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = geo.getFromLocation(latitude, longitude, MAX_ADRESSES);
        if (addresses.isEmpty()) {
            result = Constants.LOCATION_AWAITING;
        } else if (addresses.size() > 0) {
            result = addresses.get(0).getLocality();
        }
        return result;
    }
}