package com.tomek.locationtracker.model;

import com.tomek.locationtracker.util.Constants;

/**
 * Created by tomek on 06.08.16.
 */
public final class Coordinate {

    public final double latitude;
    public final double longitude;

    public Coordinate(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return longitude + Constants.STRING_SEPARATOR + latitude;
    }
}