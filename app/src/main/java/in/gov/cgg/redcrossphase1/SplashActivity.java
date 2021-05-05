package in.gov.cgg.redcrossphase1;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.JsonObject;

import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends Activity {

    public static final String MyPREFERENCES = "MyPrefs";
    RelativeLayout mainsplash;
    PrefManager prefManager;
    CustomProgressDialog progressDialog;
    private String uname, pswd;
    private boolean isCheked;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fabric.with(this, new Crashlytics());
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_splash);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        progressDialog = new CustomProgressDialog(SplashActivity.this);

        SharedPreferences prefs = this.getSharedPreferences(
                MyPREFERENCES, Context.MODE_PRIVATE);
        prefManager = new PrefManager(SplashActivity.this);
        mainsplash = findViewById(R.id.mainsplash);

        uname = prefs.getString("un", "");
        pswd = prefs.getString("pw", "");

        callversioncheckdata();

//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                if (prefManager.isFirstTimeLaunch()) {
//                    startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
//                    finish();
//                } else {
//                    startActivity(new Intent(SplashActivity.this, TabLoginActivity.class));
//                    finish();
//                }
//
//
//            }
//        }, 3000);

    }

    private void callversioncheckdata() {


        progressDialog.show();
        ApiInterface apiServiceSession = ApiClient.getClient().create(ApiInterface.class);

        Call<JsonObject> call = apiServiceSession.getCurrentVersionNumber();
        Log.e("apiServiceSession_url: ", "" + call.request().url());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                    JsonObject body = response.body();
                    if (body != null) {
                        String versionId = body.get("versionId").toString().replace(("\""), "");
                        String statusMessage = body.get("statusMessage").toString().replace(("\""), "");
                        String status = body.get("status").toString().replace(("\""), "");

                        Log.e("checkkk", versionId + "...." + statusMessage + "...." + status);

                        if (status.equalsIgnoreCase("200")) {
                            if (versionId.equalsIgnoreCase(getVersionInfo())) {

                                if (CheckInternet.isOnline(SplashActivity.this)) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (prefManager.isFirstTimeLaunch()) {
                                                startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
                                                finish();
                                            } else {
                                                startActivity(new Intent(SplashActivity.this, TabLoginActivity.class));
                                                finish();
                                            }
                                        }
                                    }, 3000);
                                } else {
                                    Toast.makeText(SplashActivity.this, getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();

                                }

                            } else {
                                showResponseAlert(getResources().getString(R.string.newVersion), true);
                            }
                        } else {
                            showResponseAlert(statusMessage, false);
                        }
                    } else {
                        showResponseAlert(getResources().getString(R.string.server_not_responding), false);
                    }
                } catch (Exception e) {
                    Toast.makeText(SplashActivity.this, getResources().getString(R.string.somethingwentwrong) + e.toString(), Toast.LENGTH_SHORT).show();
                    Log.e("Error", e.toString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                progressDialog.dismiss();
                Log.e("Exp", t.toString());

            }
        });

    }

    private void showResponseAlert(String message, final Boolean flag) {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (flag) {

                            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=in.gov.cgg.redcrossphase1&hl=en"));
                            startActivity(browserIntent);

                        }
                    }
                });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private String getVersionInfo() {
        String versionName = "";
        int versionCode = -1;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }


}


