package com.tomek.locationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by tomek on 08.07.16.
 */
public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 2000;
    private Handler launchScreenDelayHandler;
    private static Runnable activityDelayRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDelayRunnable = () -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        };
        launchScreenDelayHandler = new Handler();
        launchScreenDelayHandler.postDelayed(activityDelayRunnable, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    protected void onStop() {
        Log.d(SplashActivity.this.getClass().getSimpleName(), "onStopped");
        launchScreenDelayHandler.removeCallbacks(activityDelayRunnable);
        finish();
        super.onStop();
    }
}
