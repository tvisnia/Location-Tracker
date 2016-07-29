package com.tomek.locationtracker.model;

import com.tomek.locationtracker.util.Constants;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by tomek on 28.07.16.
 * <p>
 * A model class containing information about given location:
 * <p>
 * city - name of city derived from coordinates (requires internet connection )
 * coordinates - geographical longitude and lattitude
 * dateTime - date and time of last got location
 **/
public class LocationData {

    public LocationData(String city, String coordinates, DateTime dateTime) {
        this.city = city;
        this.coordinates = coordinates;
        this.time = dateTime;
    }


    public String coordinates;
    public String city;
    public DateTime time;
    DateTimeFormatter dateAndTime = DateTimeFormat.forPattern(Constants.DATE_TIME_FORMAT);

    public String getDateAndTime() {
        return dateAndTime.print(time);
    }
}
