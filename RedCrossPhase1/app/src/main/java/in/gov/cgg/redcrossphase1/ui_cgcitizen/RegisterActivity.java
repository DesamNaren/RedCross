package in.gov.cgg.redcrossphase1.ui_cgcitizen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.RegisBinding;

public class RegisterActivity extends AppCompatActivity {


    RegisBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //

        binding = DataBindingUtil.setContentView(RegisterActivity.this, R.layout.regis);

    }
}
