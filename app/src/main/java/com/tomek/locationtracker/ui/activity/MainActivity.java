package com.tomek.locationtracker.ui.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.tomek.locationtracker.R;
import com.tomek.locationtracker.util.LocationHelper;
import com.tomek.locationtracker.util.SnackbarUtils;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String LAST_LOCATION_TAG = "Last location: ";
    private CoordinatorLayout coordinatorLayout;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = ((CoordinatorLayout) findViewById(R.id.coordinator_layout));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        buildGoogleApiClient();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkLocationServices();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLocationServices();
    }

    @Override
    protected void onStop() {
        if (googleApiClient.isConnected()) {
            stopLocationUpdates();
            googleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();
        Log.d(TAG, "onConnected");
        lastLocation = LocationHelper.getLastKnownLocation(googleApiClient);
        if (lastLocation != null) {
            SnackbarUtils.showShortSnackbar(
                    coordinatorLayout,
                    LAST_LOCATION_TAG + String.valueOf(lastLocation.getLatitude()) + " , " + String.valueOf(lastLocation.getLongitude()));

        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "Connection suspended");
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        String errorMessage = connectionResult.getErrorCode() + ": " + connectionResult.getErrorCode();
        Log.i(TAG, "Connection failed: " + errorMessage);
        SnackbarUtils.showLongSnackbar(coordinatorLayout, "Error " + errorMessage);
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        SnackbarUtils.showShortSnackbar(
                coordinatorLayout,
                "onLocationChanged : " + String.valueOf(location.getLatitude()) + " , " + String.valueOf(location.getLongitude())
        );
    }

    private void startLocationUpdates() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest
                .setPriority(PRIORITY_HIGH_ACCURACY)
                .setInterval(LocationHelper.UPDATE_INTERVAL)
                .setFastestInterval(LocationHelper.UPDATE_INTERVAL / 2)
                .setSmallestDisplacement(LocationHelper.DISPLACEMENT);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
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

    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
}

