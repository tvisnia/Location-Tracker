package com.tomek.locationtracker.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
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
import com.tomek.locationtracker.service.FetchLocationAddressService;
import com.tomek.locationtracker.ui.recycler.LocationListAdapter;
import com.tomek.locationtracker.util.Constants;
import com.tomek.locationtracker.util.LocationHelper;
import com.tomek.locationtracker.util.SnackbarUtils;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView locationRecycler;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private LocationListAdapter locationAdapter;
    private ResultReceiver cityResultReceiver;
    private MaterialDialog checkLocationDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = ((CoordinatorLayout) findViewById(R.id.coordinator_layout));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initRecyclerView();
        buildCheckLocationDialog();
        buildGoogleApiClient();
        cityResultReceiver = new CityResultReceiver(new Handler());
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkLocationAvailability();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLocationAvailability();
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
            startReverseGeocodingService(lastLocation);
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
        startReverseGeocodingService(location);
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

    private void startReverseGeocodingService(Location location) {
        Intent intent = new Intent(this, FetchLocationAddressService.class);
        intent.putExtra(Constants.KEY_RECEIVER, cityResultReceiver);
        intent.putExtra(Constants.KEY_LOCATION_DATA_EXTRA, location);
        startService(intent);
    }

    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void initRecyclerView() {
        locationRecycler = (RecyclerView) findViewById(R.id.location_recycler);
        locationRecycler.setLayoutManager(new LinearLayoutManager(this));
        locationAdapter = new LocationListAdapter();
        locationRecycler.setAdapter(locationAdapter);
    }

    @SuppressLint("ParcelCreator")
    class CityResultReceiver extends ResultReceiver {
        public CityResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            String city = resultData.getString(Constants.KEY_RESULT_DATA);
            if (resultCode == Constants.FAILURE_RESULT) {
                SnackbarUtils.showSnackbarWithAction(coordinatorLayout, Constants.UNABLE_TO_GET_CITY, Constants.CHECKOUT_CONNECTION, v -> startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS)));
            }
            Log.d(TAG, lastLocation + city + locationRecycler);
            if (lastLocation != null)
                locationAdapter.addNewItem(lastLocation, city, locationRecycler);
        }
    }

    private void displayLocationSettings() {
        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

    private void checkLocationAvailability() {
        if (!LocationHelper.isLocationEnabled(this) && !checkLocationDialog.isShowing()) {
            checkLocationDialog.show();
        }
    }

    private void buildCheckLocationDialog() {
        MaterialDialog.Builder checkLocationDialogBuilder = new MaterialDialog.Builder(this);
        checkLocationDialogBuilder
                .title(R.string.dialog_title)
                .content(R.string.dialog_content)
                .positiveText(R.string.agree)
                .negativeText(R.string.cancel)
                .autoDismiss(false)
                .cancelable(false)
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
                        dialog.dismiss();
                        displayLocationSettings();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                        SnackbarUtils.showSnackbarWithAction(coordinatorLayout, Constants.LOCATION_OFF, Constants.TURN_ON, v -> displayLocationSettings());
                    }
                });
        checkLocationDialog = checkLocationDialogBuilder.build();
    }
}