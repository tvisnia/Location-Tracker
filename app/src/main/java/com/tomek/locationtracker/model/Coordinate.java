package com.tomek.locationtracker.model;

import android.location.Location;

import com.tomek.locationtracker.util.Constants;

/**
 * Created by tomek on 06.08.16.
 */
public final class Coordinate {

public final Location location;

    public Coordinate(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return location.getLatitude() + Constants.STRING_SEPARATOR + location.getLongitude();
    }
}