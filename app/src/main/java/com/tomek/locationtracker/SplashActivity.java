package com.tomek.locationtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by tomek on 08.07.16.
 */
public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 2000;
    private static Context context;

    private static class MyHandler extends Handler {
        private final WeakReference<SplashActivity> mActivity;

        public MyHandler(SplashActivity activity) {
            mActivity = new WeakReference<SplashActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SplashActivity activity = mActivity.get();
            if (activity != null) {
                Log.d(activity.getClass().getSimpleName(), activity.getString(R.string.on_handled));
            }
        }
    }

    private final MyHandler mHandler = new MyHandler(SplashActivity.this);
    private static final Runnable activityDelayRunnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        mHandler.postDelayed(activityDelayRunnable, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void onBackPressed() {
        mHandler.removeCallbacks(activityDelayRunnable);
        finish();
    }

    @Override
    protected void onStop() {
        Log.d(SplashActivity.this.getClass().getSimpleName(), getString(R.string.onStop));
        mHandler.removeCallbacks(activityDelayRunnable);
        finish();
        super.onStop();
    }
}
