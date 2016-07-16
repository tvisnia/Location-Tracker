package com.tomek.locationtracker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.afollestad.materialdialogs.MaterialDialog;
import com.tomek.locationtracker.R;
import com.tomek.locationtracker.util.LocationHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkLocationServices();
    }

    private void checkLocationServices() {
        if (!LocationHelper.isLocationEnabled(this)) {
            new MaterialDialog.Builder(this)
                    .title(R.string.dialog_title)
                    .content(R.string.dialog_content)
                    .positiveText(R.string.agree)
                    .negativeText(R.string.disagree)
                    .keyListener((dialog, keyCode, event) -> {
                                if (keyCode == KeyEvent.KEYCODE_BACK) {
                                    dialog.dismiss();
                                    finish();
                                    return true;
                                } else return false;
                            }
                    )
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            Intent locationSettingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(locationSettingsIntent);
                            dialog.dismiss();
                        }

                        @Override
                        public void onNegative(MaterialDialog dialog) {
                            finish();
                        }
                    })
                    .autoDismiss(false)
                    .build()
                    .show();
        }
    }
}
