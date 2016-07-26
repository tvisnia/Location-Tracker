package com.tomek.locationtracker.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by tomek on 21.07.16.
 * <p>
 * An util class for easy access to Android components used to provide a graphical
 * feedback about operation using Snackbar
 **/
public class FeedbackHelper {

    public static void showLongSnackbar(View view, CharSequence message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void showShortSnackbar(View view, CharSequence message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }
}
