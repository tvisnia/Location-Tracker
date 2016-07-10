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
    private Handler launchScreenDelayHandler;
    private ActivityDelay activityDelayRunnable;
    private static class ActivityDelay implements Runnable {
        final WeakReference<Context> ctxReference;

        public ActivityDelay(SplashActivity activity) {
            ctxReference = new WeakReference<>(activity.getApplicationContext());
        }

        @Override
        public void run() {
            Intent intent = new Intent(ctxReference.get(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctxReference.get().startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        launchScreenDelayHandler = new Handler();
        activityDelayRunnable = new ActivityDelay(this);
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
