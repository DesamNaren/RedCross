package in.gov.cgg.redcrossphase1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

public class SplashActivity extends Activity {

    public static final String MyPREFERENCES = "MyPrefs";
    private String uname, pswd;
    private boolean isCheked;
    RelativeLayout mainsplash;
    private int selectedThemeColor = -1;
    PrefManager prefManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences prefs = this.getSharedPreferences(
                MyPREFERENCES, Context.MODE_PRIVATE);
        prefManager = new PrefManager(SplashActivity.this);
        mainsplash = findViewById(R.id.mainsplash);

        uname = prefs.getString("un", "");
        pswd = prefs.getString("pw", "");


        try {
            selectedThemeColor = this.getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).
                    getInt("theme_color", -1);
            if (selectedThemeColor != -1) {

                if (selectedThemeColor == R.color.redcroosbg_1) {
                    mainsplash.setBackground(getResources().getDrawable(R.drawable.redcross1_splash));
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    mainsplash.setBackground(getResources().getDrawable(R.drawable.redcross2_splash));
                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    mainsplash.setBackground(getResources().getDrawable(R.drawable.redcross3_splash));
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    mainsplash.setBackground(getResources().getDrawable(R.drawable.redcross4_splash));

                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    mainsplash.setBackground(getResources().getDrawable(R.drawable.splash9));

                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    mainsplash.setBackground(getResources().getDrawable(R.drawable.redcross6_splash));

                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    mainsplash.setBackground(getResources().getDrawable(R.drawable.redcross7_splash));

                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    mainsplash.setBackground(getResources().getDrawable(R.drawable.redcross8_splash));

                } else {
                    mainsplash.setBackground(getResources().getDrawable(R.drawable.redcross2_splash));
                }
            } else {
                mainsplash.setBackground(getResources().getDrawable(R.drawable.redcross2_splash));

            }
        } catch (Exception e) {
            e.printStackTrace();
            mainsplash.setBackground(getResources().getDrawable(R.drawable.redcross2_splash));

        }


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
        }, 2000);


    }


}
