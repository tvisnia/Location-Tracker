package com.tomek.locationtracker.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by tomek on 21.07.16.
 * <p>
 * An util class for easy access to Android components used to provide a graphical
 * feedback about operation (Toast & Snackbar)
 **/
public class FeedbackHelper {

    public static void showLongSnackbar(View view, CharSequence message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void showShortSnackbar(View view, CharSequence message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void showLongToast(Context context, CharSequence message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(Context context, CharSequence message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
