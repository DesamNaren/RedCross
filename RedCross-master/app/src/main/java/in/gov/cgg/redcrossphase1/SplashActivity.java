package in.gov.cgg.redcrossphase1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

public class SplashActivity extends Activity {

    public static final String MyPREFERENCES = "MyPrefs";
    private String uname, pswd;
    private boolean isCheked;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences prefs = this.getSharedPreferences(
                MyPREFERENCES, Context.MODE_PRIVATE);

        uname = prefs.getString("un", "");
        pswd = prefs.getString("pw", "");
        isCheked = prefs.getBoolean("is", false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //showAlert();

                if (isCheked) {
                    GlobalDeclaration.sharedUname = uname;
                    GlobalDeclaration.sharedUPswd = pswd;
                    startActivity(new Intent(SplashActivity.this, TabLoginActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, TabLoginActivity.class));
                    finish();
                }

            }
        }, 2000);
    }
}
