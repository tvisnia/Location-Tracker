package com.tomek.locationtracker.model;

import android.location.Location;

import com.tomek.locationtracker.util.Constants;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Location model object
 * <p>
 * <P> Contains attributes of received location.
 *
 * @author Tomasz Wisniewski
 */
public class LocationData {

    /**
     * The formatter parsing given DateTime object to a String with a DATE_TIME_FORMAT pattern.
     */
    private static final DateTimeFormatter dateAndTime = DateTimeFormat.forPattern(Constants.DATE_TIME_FORMAT);

    /**
     * Location object.
     */
    public final Location location;
    /**
     * The name of city derived from reverse geocoding service.
     */
    public final String city;
    /**
     * The date and time of given location.
     */
    public final DateTime time;

    /**
     * Geographical longitude and latitude
     */
    public final Coordinate coordinate;

    /**
     * Constructor.
     *
     * @param city       value for {@link #city}
     * @param location   value for {@link #location}
     * @param dateTime   value for {@link #time}
     * @param coordinate value for {@link #coordinate}
     */
    public LocationData(String city, Location location, DateTime dateTime, Coordinate coordinate) {
        this.city = city;
        this.location = location;
        this.time = dateTime;
        this.coordinate = coordinate;
    }

    /**
     * Gets date and time from a DateTime field
     *
     * @return date and time parsed to a String object
     */
    public String getDateAndTime() {
        return dateAndTime.print(time);
    }
}
