package com.tomek.locationtracker.util;

/**
 * Created by tomek on 29.07.16.
 * </p>
 */
public class Constants {

    //Logcat
    public static final String TAG_CONNECTED = "onConnected";
    public static final String TAG_ERROR = "Error occured";
    public static final String TAG_CONNECTION_FAILED = "Connection failed .";
    public static final String TAG_CONNECTION_SUSPENDED = "Connection suspended .";

    //Snackbar
    public static final String TAG_LAST_LOCATION = "Last location: ";
    public static final String TAG_LOCATION_CHANGED = "Location has changed : ";
    public static final String UNABLE_TO_GET_CITY = "Unable to get locality";
    public static final String CHECKOUT_CONNECTION = "Check connection";

    //Service messaging & results
    public static final String SERVICE_NOT_AVAILABLE = "Service not available";
    public static final String INVALID_COORDINATES_USED = "Invalid coordinates used";
    public static final String NO_ADDRESSES_FOUND = "No addresses found";
    public static final String ADDRESS_FOUND = "Address found";
    public static final String UNKNOWN_CITY = "Unknown city";
    public static final int FAILURE_RESULT = 0;
    public static final int SUCCESS_RESULT = 1;

    //Keys for an Intent Service
    public static final String KEY_LOCATION_DATA_EXTRA = "com.tomek.locationtracker.location";
    public static final String KEY_RESULT_DATA = "com.tomek.locationtracker.result";
    public static final String KEY_RECEIVER = "com.tomek.locationtracker.resultreceiver";

    //Location values
    public static final float DISPLACEMENT = 25;       // minimum radius of displacement (in meters) to notify onLocationChanged;
    public static final long UPDATE_INTERVAL = 20000; // Update location every 20000
    public static final double DEFAULT_CORDINATE = 0;
    public static final int MAX_ADRESSES = 1;

    //Other
    public static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";
}
