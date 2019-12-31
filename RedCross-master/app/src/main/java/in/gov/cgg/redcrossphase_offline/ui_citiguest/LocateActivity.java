package in.gov.cgg.redcrossphase_offline.ui_citiguest;

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
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import in.gov.cgg.redcrossphase_offline.R;
import in.gov.cgg.redcrossphase_offline.retrofit.ApiClient;
import in.gov.cgg.redcrossphase_offline.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Adaptors.LocateAdapter;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Adaptors.LocateMapAdaptor;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans.locate.Additionscenter;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans.locate.LocateResponse;
import in.gov.cgg.redcrossphase_offline.utils.CheckInternet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocateActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int REQUEST_LOCATION_TURN_ON = 2000;
    public CustomProgressDialog progressDialog;
    double latitude;
    double longitude;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    ImageView back_button_map;
    BottomSheetDialog bottomSheetDialog;
    BottomSheetBehavior bottomSheetBehavior;
    View bottomSheetView;
    LinearLayout ll_near_meal, ll_near_toilet, ll_near_sports;
    JsonObject gsonObject;
    DrawerLayout drawer;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView tv_title;
    private GoogleMap mMap;
    private int PROXIMITY_RADIUS = 10000;
    private Marker markername;
    private ImageView bt_open_menu;
    private boolean isNavListLoaded = false;
    private List<Additionscenter> additionscenters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_nearme_maps);
        additionscenters = new ArrayList<>();
        showBottom();
        initUI();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        //Check if Google Play Services Available or not
        if (!CheckGooglePlayServices()) {
            finish();
        } else {
        }


        turnOnLocation();


    }

    private void initUI() {
        tv_title = findViewById(R.id.tv_title);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.menuList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LocateActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        drawer = findViewById(R.id.drawer_layout);


        getNearMeList();

//        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                if (!isNavListLoaded) {
//                    drawer.openDrawer(Gravity.RIGHT);
//
//                }
//
//            }
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//
//            }
//
//            @Override
//            public void onDrawerStateChanged(int newState) {
//
//
//            }
//        });


        progressDialog = CustomProgressDialog.getInstance();
        bt_open_menu = findViewById(R.id.bt_open_menu);
        bt_open_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavigationMenu();
            }
        });

        back_button_map = findViewById(R.id.back_button_map);
        back_button_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LocateActivity.this.finish();

            }
        });

    }

    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        CameraUpdate point = CameraUpdateFactory.newLatLng(new LatLng(17.3850, 78.4867));
        mMap.moveCamera(point);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));


        ll_near_meal = bottomSheetView.findViewById(R.id.ll_near_meal);
        ll_near_toilet = bottomSheetView.findViewById(R.id.ll_near_toilet);
        ll_near_sports = bottomSheetView.findViewById(R.id.ll_near_sports);

        ll_near_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                mMap.clear();
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }


            }
        });

        ll_near_toilet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                mMap.clear();
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }
            }
        });

        ll_near_sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                mMap.clear();
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }
//                callSportsService(latitude, longitude);
            }
        });

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

    private String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + getResources().getString(R.string.google_maps_key));
        return (googlePlacesUrl.toString());
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

        //Place current location marker
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        // Toast.makeText(LocateActivity.this, "Your Current Location", Toast.LENGTH_LONG).show();


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

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
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

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            //   shareIntent();
        }
        if (requestCode == REQUEST_LOCATION_TURN_ON) {
            if (resultCode == RESULT_OK) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            } else {
                new AlertDialog.Builder(LocateActivity.this).setCancelable(false).setTitle("Please turn on the location to continue").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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


    public void showBottomSheet(View v) {
        //showBottom();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();
    }

    private void showBottom() {
        bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_nearme_map, null);
        bottomSheetDialog = new BottomSheetDialog(LocateActivity.this);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        //textPrompt2.setText("COLLAPSED");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        // textPrompt2.setText("DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        // textPrompt2.setText("EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        //textPrompt2.setText("HIDDEN");
                        bottomSheetDialog.dismiss();
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        // textPrompt2.setText("SETTLING");
                        break;
                    default:
                        // textPrompt2.setText("unknown...");
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        bottomSheetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                //textPrompt1.setText("OnShow");
            }
        });

        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                //textPrompt1.setText("OnDismiss");
            }
        });


    }


    /*private void callSportsService(double latitude, double longitude) {
        JsonObject gsonObject = null;
        progressDialog.showProgress(LocateActivity.this);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            JSONObject object = new JSONObject();
            object.put("userid", "cgg@ghmc");
            object.put("password", "ghmc@cgg@2018");
            object.put("latitude", latitude);
            object.put("longitude", longitude);
            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<LocateResponse> call = apiInterface.getSportComplexes(gsonObject);
        call.enqueue(new Callback<LocateResponse>() {
            @Override
            public void onResponse(Call<LocateResponse> call, Response<LocateResponse> response) {
                progressDialog.hideProgress();
                try {
                    if (response.body() != null && response.body().size() > 0) {
                        final LocateResponse list = (LocateResponse) response.body();

                        for (int i = 0; i < list.size(); i++) {
                            MarkerOptions markerOptions = new MarkerOptions();
                            SportsBean googlePlace = list.get(i);

                            double lat = Double.parseDouble(googlePlace.getLatitude());
                            double lng = Double.parseDouble(googlePlace.getLongitude());

                            String placeName = googlePlace.getCirclenoName();
                            String vicinity = googlePlace.getWardnoName();
                            LatLng latLng = new LatLng(lat, lng);

                            markerOptions.position(latLng);


                            String finals = " Ward: " + googlePlace.getWardnoName() + "\n" + " Circle: " + googlePlace.getCirclenoName() + "\n Address: " + googlePlace.getAddress() + "\n Caretaker: " + googlePlace.getCareTaker();

                            markerOptions.title(finals);
                            if (ContextCompat.checkSelfPermission(LocateActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                                    == PackageManager.PERMISSION_GRANTED) {


                                mMap.setMyLocationEnabled(true);
                            }

                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                            markername = mMap.addMarker(markerOptions);
                            final int finalI = i;
                            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                @Override
                                public void onInfoWindowClick(Marker marker) {

                                    Uri.Builder builder = new Uri.Builder();
                                    builder.scheme("https")
                                            .authority("www.google.com").appendPath("maps").appendPath("dir").appendPath("").appendQueryParameter("api", "1")
                                            .appendQueryParameter("destination", marker.getPosition().latitude + "," + marker.getPosition().longitude);
                                    String url = builder.build().toString();
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    startActivity(i);

                                }
                            });
                            //move map camera
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));


                        }

                        LocateMapAdaptor customInfoWindow = new LocateMapAdaptor(LocateActivity.this);
                        mMap.setInfoWindowAdapter((GoogleMap.InfoWindowAdapter) customInfoWindow);

                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                markername.showInfoWindow();

                                return false;
                            }
                        });
                    } else {
                        Toast.makeText(LocateActivity.this, "Server Not Responding", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (Exception e) {
                    // Toast.makeText(LocateActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LocateResponse> call, Throwable t) {
                progressDialog.hideProgress();
//                ShowRetroAlert.show(LocateActivity.this);
            }
        });

    }*/


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

                        if (CheckInternet.isOnline(LocateActivity.this)) {
                            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                    .findFragmentById(R.id.map);
                            mapFragment.getMapAsync(LocateActivity.this);

                        } else {

                            AlertDialog.Builder alert = new AlertDialog.Builder(LocateActivity.this);
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
                            status.startResolutionForResult(LocateActivity.this, REQUEST_LOCATION_TURN_ON);
                        } catch (IntentSender.SendIntentException e) {
                            Toast.makeText(LocateActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Toast.makeText(LocateActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
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

    private void openNavigationMenu() {
        if (CheckInternet.isOnline(LocateActivity.this)) {

            if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                drawer.closeDrawer(Gravity.RIGHT);
            } else {
                drawer.openDrawer(Gravity.RIGHT);
            }
            if (!isNavListLoaded) {
                getNearMeList();
            }

        } else {
            Toast.makeText(LocateActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }


    }

    private void getNearMeList() {
        progressBar.setVisibility(View.VISIBLE);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LocateResponse> call = apiInterface.getAdditionsCentersService();
        call.enqueue(new Callback<LocateResponse>() {
            @Override
            public void onResponse(Call<LocateResponse> call, Response<LocateResponse> response) {
                progressBar.setVisibility(View.GONE);

                if (response.body() != null && response.body().getAdditionscenters() != null
                        && response.body().getAdditionscenters().size() > 0) {
                    additionscenters = response.body().getAdditionscenters();

                    if (additionscenters.size() > 0) {
                        List<String> addNmaes = new ArrayList<>();
                        for (int z = 0; z < additionscenters.size(); z++) {
                            if (!additionscenters.get(z).getType().contains("SERV")) {
                                addNmaes.add(additionscenters.get(z).getType());
                            }
                        }

                        LinkedHashSet<String> hashSet = new LinkedHashSet<String>(addNmaes);
                        ArrayList<String> listWithoutDuplicates = new ArrayList<>(hashSet);
                        LocateAdapter adapter = new LocateAdapter(LocateActivity.this, listWithoutDuplicates);
                        recyclerView.setAdapter(adapter);
                        isNavListLoaded = true;
                        drawer.openDrawer(Gravity.RIGHT);


                    }

                }
            }

            @Override
            public void onFailure(Call<LocateResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public void onClickCalled(String id) {
        tv_title.setText(id);
        mMap.clear();
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        }
        getNearMeData(latitude, longitude, id);
    }


    private void getNearMeData(double latitude, double longitude, String type) {

        for (int i = 0; i < additionscenters.size(); i++) {
            if (additionscenters.get(i).getType().trim().equalsIgnoreCase(type.trim())) {

                if (additionscenters.get(i).getLat() != null && additionscenters.get(i).getLon() != null) {
                    double lat = Double.parseDouble(additionscenters.get(i).getLat());
                    double lng = Double.parseDouble(additionscenters.get(i).getLon());

                    MarkerOptions markerOptions = new MarkerOptions();
                    Additionscenter googlePlace = additionscenters.get(i);


                    LatLng latLng = new LatLng(lat, lng);

                    markerOptions.position(latLng);
                    String finals = googlePlace.getType() + "\n" + googlePlace.getAddress() + "\n" + googlePlace.getName() + "\n" + googlePlace.getMobileno();

                    markerOptions.title(finals);
                    if (ContextCompat.checkSelfPermission(LocateActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {


                        mMap.setMyLocationEnabled(true);
                    }

                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_parking));

                    markername = mMap.addMarker(markerOptions);
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {


                            if (marker.getTitle().contains("\n")) {
                                final String[] strings = marker.getTitle().split("\n");
                                if (strings[3] != null && !strings[3].equals("0") && strings[3].length() >= 10) {
                                    ShowContactAlert(strings[3].trim());
                                } else {
                                    Uri.Builder builder = new Uri.Builder();
                                    builder.scheme("https").authority("www.google.com").appendPath("maps").appendPath("dir").appendPath("").appendQueryParameter("api", "1").appendQueryParameter("destination", marker.getPosition().latitude + "," + marker.getPosition().longitude);
                                    String url = builder.build().toString();
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    startActivity(i);
                                }

                            }

                        }
                    });
                    //move map camera
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                    mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                }
            }

        }


        LocateMapAdaptor customInfoWindow = new LocateMapAdaptor(LocateActivity.this);
        mMap.setInfoWindowAdapter(customInfoWindow);


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                markername.showInfoWindow();

                return false;
            }
        });
    }

    private void ShowContactAlert(String mobNo) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL,
                    Uri.fromParts("tel", mobNo, null));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

