package com.tomek.locationtracker.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by tomek on 21.07.16.
 * <p>
 * An util class for an easy access to Android Snackbar component used to provide a graphical
 * feedback about operation
 **/
public class SnackbarUtils {

    public static void showLongSnackbar(View view, CharSequence message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void showShortSnackbar(View view, CharSequence message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }
}
