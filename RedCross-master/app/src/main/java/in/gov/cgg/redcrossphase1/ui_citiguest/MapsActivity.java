package in.gov.cgg.redcrossphase1.ui_citiguest;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.MapAdaptor;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.eRaktkoshResponseBean;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int REQUEST_LOCATION_TURN_ON = 2000;
    private static final int PERMISSION_REQUEST_CODE = 200;
    Button switchView;
    CustomProgressDialog progressBar;
    double latitude;
    double longitude;
    MarkerOptions markerOptions;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    boolean markerClicked;
    TextView tvLocInfo;
    Button submitBtn;
    double distance = 0.0;
    private GoogleMap mMap;
    private Marker markername;
    private ArrayList<eRaktkoshResponseBean> eRaktkoshResponseBeans;
    private String fromClass;
    private int selectedThemeColor = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        progressBar = new CustomProgressDialog(MapsActivity.this);
        switchView = findViewById(R.id.switchView);
        switchView.setText("Show blood banks list");

        requestPermissions();
        try {
            selectedThemeColor = MapsActivity.this.getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            setThemes();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        //Check if Google Play Services Available or not
        try {
            if (!CheckGooglePlayServices()) {
                Toast.makeText(this, "Sorry, Google play services not available in your device", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            turnOnLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Bundle bundle = getIntent().getExtras();
            eRaktkoshResponseBeans = bundle.getParcelableArrayList("E_RAKTAKOSH_DATA");
            fromClass = bundle.getString("FROM_CLASS");
//            progressBar.show();


        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        switchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setThemes() {
        if (selectedThemeColor != -1) { //
            if (selectedThemeColor == R.color.redcroosbg_1) {

                switchView.setBackgroundColor(getResources().getColor(R.color.redcroosbg_1));
            } else if (selectedThemeColor == R.color.redcroosbg_2) {

                switchView.setBackgroundColor(getResources().getColor(R.color.redcroosbg_2));

            } else if (selectedThemeColor == R.color.redcroosbg_3) {

                switchView.setBackgroundColor(getResources().getColor(R.color.redcroosbg_3));

            } else if (selectedThemeColor == R.color.redcroosbg_4) {

                switchView.setBackgroundColor(getResources().getColor(R.color.redcroosbg_4));
            } else if (selectedThemeColor == R.color.redcroosbg_5) {

                switchView.setBackgroundColor(getResources().getColor(R.color.redcroosbg_5));
            } else if (selectedThemeColor == R.color.redcroosbg_6) {

                switchView.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));
            } else if (selectedThemeColor == R.color.redcroosbg_7) {

                switchView.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));

            } else if (selectedThemeColor == R.color.redcroosbg_8) {

                switchView.setBackgroundColor(getResources().getColor(R.color.redcroosbg_8));
            } else {

                switchView.setBackgroundColor(getResources().getColor(R.color.redcroosbg_1));

            }

        } else {

            switchView.setBackgroundColor(getResources().getColor(R.color.redcroosbg_1));

        }
    }
    private void setMarkers(final List<eRaktkoshResponseBean> eRaktkoshResponseBeans) {
        try {
            if (eRaktkoshResponseBeans.size() > 0) {

                for (int i = 0; i < eRaktkoshResponseBeans.size(); i++) {
                    double lat = 0, lng = 0;
                    final MarkerOptions markerOptions = new MarkerOptions();
                    final eRaktkoshResponseBean otData = eRaktkoshResponseBeans.get(i);
                    if (otData.getLat() != null && otData.getLong() != null) {
                        lat = Double.valueOf(otData.getLat());
                        lng = Double.valueOf(otData.getLong());

                        LatLng latLng = new LatLng(lat, lng);

                        markerOptions.position(latLng);

                        String finals = "Name: " + otData.getName() + "\n\n" +
                                "Address: " + otData.getAdd();

                        markerOptions.title(finals);

                        if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {
                            mMap.setMyLocationEnabled(true);
                        }

                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));


                        markername = mMap.addMarker(markerOptions);
                        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {

                                Uri.Builder builder = new Uri.Builder();
                                builder.scheme("https").authority("www.google.com").appendPath("maps").appendPath("dir").appendPath("").appendQueryParameter("api", "1").appendQueryParameter("destination", marker.getPosition().latitude + "," + marker.getPosition().longitude);
                                String url = builder.build().toString();
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            }
                        });
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                    }
                }

            }

//            progressBar.dismiss();


            MapAdaptor customInfoWindow = new MapAdaptor(MapsActivity.this);
            mMap.setInfoWindowAdapter(customInfoWindow);
            markername.showInfoWindow();

        } catch (Exception e) {
//            progressBar.dismiss();
            e.printStackTrace();
//            Utilities.showCustomNetworkAlert(this, getResources().getString(R.string.something), false);
        }

    }

    private void turnOnLocation() {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).build();
        googleApiClient.connect();
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        if (CheckInternet.isOnline(MapsActivity.this)) {
                            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                    .findFragmentById(R.id.map);
                            mapFragment.getMapAsync(MapsActivity.this);


                        } else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(MapsActivity.this);
                            alert.setCancelable(false);
                            alert.setMessage(getResources().getString(R.string.no_internet));
                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                            alert.show();
                        }

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(MapsActivity.this, REQUEST_LOCATION_TURN_ON);
                        } catch (IntentSender.SendIntentException e) {
                            Toast.makeText(MapsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Toast.makeText(MapsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        break;
                    default: {
                        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(i);
                        break;
                    }
                }
            }
        });

    }

    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result, 0).show();
            }
            return false;
        }
        return true;
    }

    private boolean requestPermissions() {
        boolean requestFlag = false;
        if (ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_CODE);
            requestFlag = false;
        } else {
            requestFlag = true;
        }
        return requestFlag;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
//                mMap.setMyLocationEnabled(true);

                setMarkers(eRaktkoshResponseBeans);
            }
        } else {
            buildGoogleApiClient();
//            mMap.setMyLocationEnabled(true);
        }

        CameraUpdate point = CameraUpdateFactory.newLatLng(new LatLng(17.3850, 78.4867));
        if (fromClass.equalsIgnoreCase("ALL")) {
            mMap.moveCamera(point);
        }
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("You are here");
        // markerOptions.snippet("ward_name\ncirclename\nZone");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        if (fromClass.equalsIgnoreCase("ALL")) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }


        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_LOCATION_TURN_ON) {
            if (resultCode == RESULT_OK) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            } else {
                new AlertDialog.Builder(MapsActivity.this).setCancelable(false).setTitle("Please turn on the location to continue").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        turnOnLocation();

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }).show();


            }
        }
    }

}