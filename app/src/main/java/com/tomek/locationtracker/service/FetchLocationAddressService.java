package com.tomek.locationtracker.service;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.tomek.locationtracker.util.Constants;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FetchLocationAddressService extends IntentService {

    public static final String TAG = FetchLocationAddressService.class.getSimpleName();
    private ResultReceiver serviceDataReceiver;

    public FetchLocationAddressService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String errorMessage = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        Location location = intent.getParcelableExtra(Constants.KEY_LOCATION_DATA_EXTRA);
        serviceDataReceiver = intent.getParcelableExtra(Constants.KEY_RECEIVER);
        List<Address> addresses = null;
        if (location != null) {
            try {
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), Constants.MAX_ADRESSES);
            } catch (IOException ioException) {
                errorMessage = Constants.SERVICE_NOT_AVAILABLE;
                Log.e(TAG, errorMessage, ioException);
            } catch (IllegalArgumentException illegalArgumentException) {
                errorMessage = Constants.INVALID_COORDINATES_USED;
                Log.e(TAG, errorMessage, illegalArgumentException);
            }
        }

        if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = Constants.NO_ADDRESSES_FOUND;
                Log.e(TAG, errorMessage);
            }
            deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage);
        } else {
            String city = addresses.get(0).getLocality();
            Log.i(TAG, Constants.ADDRESS_FOUND);
            deliverResultToReceiver(Constants.SUCCESS_RESULT, city);
        }
    }

    private void deliverResultToReceiver(int resultCode, String message) {
        String result = Constants.UNKNOWN_CITY;
        Bundle bundle = new Bundle();
        if (!message.equals(Constants.SERVICE_NOT_AVAILABLE)) {
            result = message;
        }
        bundle.putString(Constants.KEY_RESULT_DATA, result);
        serviceDataReceiver.send(resultCode, bundle);
    }
}