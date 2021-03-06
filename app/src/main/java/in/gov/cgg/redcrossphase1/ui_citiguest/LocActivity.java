package in.gov.cgg.redcrossphase1.ui_citiguest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.gov.cgg.redcrossphase1.BuildConfig;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.LocBaseFragment;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class LocActivity extends AppCompatActivity {

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    private static final int REQUEST_CHECK_SETTINGS = 100;
    private static final String TAG = LocBaseFragment.class.getSimpleName();
    public LocationCallback mLocationCallback;
    public Location mCurrentLocation;
    boolean flag = false;
    String[] permissions = new String[]{
            ACCESS_FINE_LOCATION, CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE};
    // location last updated time
    private String mLastUpdateTime;
    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates = false;

    private static void openSettings(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callPermissions();
    }

    public boolean callPermissions() {

        Dexter.withActivity(this)
                .withPermissions(permissions)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            flag = true;
                            mRequestingLocationUpdates = true;
                            init();
                            startLocationUpdates();
                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            flag = false;
                            showSettingsAlert(getResources().getString(R.string.grant_all_new));
//                            openSettings(LocActivity.this);
                        } else if (report.getDeniedPermissionResponses().size() > 0) {
                            customPerAlert();
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).check();
        return flag;
    }

    private void customPerAlert() {
        try {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(LocActivity.this);
            builder1.setMessage(getResources().getString(R.string.grant_all));
            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    getResources().getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            callPermissions();
                        }
                    });

            builder1.setNegativeButton(
                    getResources().getString(R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            LocActivity.this.finish();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
//            final PrettyDialog dialog = new PrettyDialog(LocActivity.this);
//            dialog.setTitle(getResources().getString(R.string.app_name))
//                    .setMessage(getString(R.string.grant_all))
//                    .setIcon(R.drawable.pdlg_icon_info, R.color.pdlg_color_blue, null)
//
//                    .addButton(getResources().getString(R.string.ok), R.color.pdlg_color_white, R.color.pdlg_color_green, new PrettyDialogCallback() {
//                        @Override
//                        public void onClick() {
//                            dialog.dismiss();
//                            callPermissions();
//                        }
//                    })
//                    .addButton("Cancel", R.color.pdlg_color_white, R.color.pdlg_color_red, new PrettyDialogCallback() {
//                        @Override
//                        public void onClick() {
//                            dialog.dismiss();
//                            startActivity(new Intent(LocActivity.this, CitiGuestMainActivity.class));
//                            LocActivity.this.finish();
//                        }
//                    }).setCancelable(false);
//
//            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showSettingsAlert(String string) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LocActivity.this);
        builder.setMessage(string);
        builder.setCancelable(false);


        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openSettings(LocActivity.this);
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LocActivity.this.finish();
            }
        });

        builder.create();
        AlertDialog alert11 = builder.create();
        alert11.show();
    }

    private void showPermissionsAlert(String string) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LocActivity.this);
        builder.setMessage(string);
        builder.setCancelable(false);


        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callPermissions();
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LocActivity.this.finish();
            }
        });

        builder.create();
    }


    private void init() {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(LocActivity.this);
        mSettingsClient = LocationServices.getSettingsClient(LocActivity.this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                updateLocationUI();
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    public void startLocationUpdates() {

        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(LocActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");
                        if (checkPermissions()) {
                            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        }
                    }
                })
                .addOnFailureListener(LocActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        int statusCode = ((ApiException) e).getStatusCode();

                        switch (statusCode) {

                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    ResolvableApiException resolvableApiException = (ResolvableApiException) e;

                                    resolvableApiException.startResolutionForResult(LocActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException e1) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;

                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = getResources().getString(R.string.loc_sett);

                                Log.e(TAG, errorMessage);

                                Toast.makeText(LocActivity.this, errorMessage, Toast.LENGTH_LONG).show();

                                break;
                        }

                        updateLocationUI();
                    }
                });


    }

    public void stopLocationUpdates() {

        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(LocActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(LocActivity.this, getResources().getString(R.string.loc_stopped), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case REQUEST_CHECK_SETTINGS:

                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        startLocationUpdates();
                        break;

                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;

                        customPerAlert();
                        break;
                }

                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        }
        updateLocationUI();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mRequestingLocationUpdates) {
            // pausing location updates
            stopLocationUpdates();
        }
    }


    public void updateLocationUI() {

        if (mCurrentLocation != null) {
//            Toast.makeText(this, " Lat:" + mCurrentLocation.getLatitude() +","
//                    + "Lng:" + mCurrentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
        }

    }

    private boolean checkPermissions() {
        int locationPermission = ContextCompat.checkSelfPermission(LocActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int locationPermission2 = ContextCompat.checkSelfPermission(LocActivity.this,
                CAMERA);
        int locationPermission3 = ContextCompat.checkSelfPermission(LocActivity.this,
                WRITE_EXTERNAL_STORAGE);
        int locationPermission4 = ContextCompat.checkSelfPermission(LocActivity.this,
                READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (locationPermission2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(CAMERA);
        }
        if (locationPermission3 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(WRITE_EXTERNAL_STORAGE);
        }
        if (locationPermission4 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(READ_EXTERNAL_STORAGE);
        }

        return listPermissionsNeeded.isEmpty();
    }
}
