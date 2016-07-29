package com.tomek.locationtracker.ui.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.tomek.locationtracker.model.LocationData;
import com.tomek.locationtracker.ui.recycler.LocationListAdapter;
import com.tomek.locationtracker.util.Constants;
import com.tomek.locationtracker.util.LocationHelper;
import com.tomek.locationtracker.util.SnackbarUtils;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView locationRecycler;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private List<LocationData> locationList;
    private LocationListAdapter locationAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = ((CoordinatorLayout) findViewById(R.id.coordinator_layout));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initRecyclerView();
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
        Log.d(TAG, Constants.TAG_CONNECTED);
        lastLocation = LocationHelper.getLastKnownLocation(googleApiClient);
        if (lastLocation != null) {
            SnackbarUtils.showShortSnackbar(
                    coordinatorLayout,
                    Constants.TAG_LAST_LOCATION + String.valueOf(lastLocation.getLatitude()) + " , " + String.valueOf(lastLocation.getLongitude()));
            addLocation(lastLocation);
            locationAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, Constants.TAG_CONNECTION_SUSPENDED);
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        String errorMessage = connectionResult.getErrorCode() + ": " + connectionResult.getErrorCode();
        Log.i(TAG, Constants.TAG_CONNECTION_FAILED + errorMessage);
        SnackbarUtils.showLongSnackbar(coordinatorLayout, Constants.TAG_ERROR + errorMessage);
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        SnackbarUtils.showShortSnackbar(
                coordinatorLayout,
                Constants.TAG_LOCATION_CHANGED + location.getLatitude() + " , " + location.getLongitude());
        addLocation(location);
        locationAdapter.notifyDataSetChanged();
    }

    private void startLocationUpdates() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest
                .setPriority(PRIORITY_HIGH_ACCURACY)
                .setInterval(Constants.UPDATE_INTERVAL)
                .setFastestInterval(Constants.UPDATE_INTERVAL / 2)
                .setSmallestDisplacement(Constants.DISPLACEMENT);
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

    private void addLocation(Location location) {
        String city = Constants.UNKNOWN_CITY;
        try {
            city = LocationHelper.getCityFromLocation(this, location.getLatitude(), location.getLongitude());
        } catch (IOException e) {
            String errorMessage = e.getLocalizedMessage();
            Log.e(Constants.TAG_ERROR, errorMessage);
            if (errorMessage.contains(Constants.TAG_TIMED_OUT)) {
                SnackbarUtils.showSnackbarWithAction(
                        coordinatorLayout, Constants.TAG_UNABLE_TO_GET_CITY, Constants.TAG_CONNECT, action -> startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS))
                );
            }
            e.printStackTrace();
        }
        locationList.add(0,
                new LocationData(city, location.getLatitude() + " , " + location.getLongitude(), new DateTime()
                )
        );
    }

    private void initRecyclerView() {
        locationList = new ArrayList<>();
        locationRecycler = (RecyclerView) findViewById(R.id.location_recycler);
        locationRecycler.setLayoutManager(new LinearLayoutManager(this));
        locationAdapter = new LocationListAdapter(locationList);
        locationRecycler.setAdapter(locationAdapter);
    }
}