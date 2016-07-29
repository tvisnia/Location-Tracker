package com.tomek.locationtracker.util;

/**
 * Created by tomek on 29.07.16.
 </p>
 */
public class Constants {

    //Log & messaging
    public static final String TAG_CONNECTED = "onConnected";
    public static final String TAG_CONNECTION_FAILED = "Connection failed .";
    public static final String TAG_CONNECTION_SUSPENDED = "Connection suspended .";
    public static final String TAG_TIMED_OUT = "Timed out waiting for response from server";
    public static final String TAG_ERROR = "Error occured";
    public static final String TAG_CONNECT = "TAG_CONNECT";
    public static final String UNKNOWN_CITY = "Unknown city";
    public static final String LOCATION_AWAITING = "Waiting for location";

    public static final String TAG_LAST_LOCATION = "Last location: ";
    public static final String TAG_LOCATION_CHANGED = "Location has changed : ";
    public static final String TAG_UNABLE_TO_GET_CITY = "Unable to get city";

    //Location values
    public static final float DISPLACEMENT = 25;       // minimum radius of displacement (in meters) to notify onLocationChanged;
    public static final long UPDATE_INTERVAL = 20000; // Update location every 20000 miliseconds

    //Other
    public static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";
}
