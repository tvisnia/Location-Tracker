package com.tomek.locationtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by tomek on 08.07.16.
 */
public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 2000;
    private final Handler launchScreenDelayHandler = new Handler();
    private DelayRunnable activityDelayRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDelayRunnable = new DelayRunnable(this);
        launchScreenDelayHandler.postDelayed(activityDelayRunnable, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    protected void onStop() {
        Log.d(SplashActivity.this.getClass().getSimpleName(), "onStopped");
        launchScreenDelayHandler.removeCallbacks(activityDelayRunnable);
        finish();
        super.onStop();
    }

    private static class DelayRunnable implements Runnable {
        private final WeakReference<Context> contextReference;

        public DelayRunnable(SplashActivity activity) {
            contextReference = new WeakReference<>(activity.getApplicationContext());
        }

        @Override
        public void run() {
            Context applicationContext = contextReference.get();
            if (applicationContext != null) {
                Intent intent = new Intent(applicationContext, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                applicationContext.startActivity(intent);
            }
        }
    }
}
