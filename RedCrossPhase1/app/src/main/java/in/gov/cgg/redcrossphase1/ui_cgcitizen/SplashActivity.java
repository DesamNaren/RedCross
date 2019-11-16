package in.gov.cgg.redcrossphase1.ui_cgcitizen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import in.gov.cgg.redcrossphase1.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //showAlert();
                startActivity(new Intent(SplashActivity.this, TabLoginActivity.class));
                finish();
            }
        }, 2000);
    }
}
