package com.tomek.locationtracker.util;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

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

    public static void showSnackbarWithAction(View view, CharSequence message, CharSequence actionText, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(actionText, listener)
                .setActionTextColor(Color.RED);
        ((TextView) snackbar.getView()
                .findViewById(android.support.design.R.id.snackbar_text)
        )
                .setTextColor(Color.YELLOW);
        snackbar.show();
    }
}
