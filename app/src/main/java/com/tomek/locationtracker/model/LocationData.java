package com.tomek.locationtracker.model;

import com.tomek.locationtracker.util.Constants;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Location model object
 *
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
     * Geographical longitude and lattitude concatenated as a String object.
     */
    public final String coordinates;
    /**
     * The name of city derived from reverse geocoding service.
     */
    public final String city;
    /**
     * The date and time of given location.
     */
    public final DateTime time;

    /**
     * Constructor.
     *
     * @param city        value for {@link #city}
     * @param coordinates value for {@link #coordinates}
     * @param dateTime    value for {@link #time}
     */
    public LocationData(String city, String coordinates, DateTime dateTime) {
        this.city = city;
        this.coordinates = coordinates;
        this.time = dateTime;
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
