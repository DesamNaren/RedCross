package in.gov.cgg.redcrossphase1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.textfield.TextInputEditText;

import in.gov.cgg.redcrossphase1.databinding.RegisBinding;
import in.gov.cgg.redcrossphase1.ui_citiguest.EnterMPinActivity;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;

public class RegisterActivity extends AppCompatActivity {


    RegisBinding binding;
    TextInputEditText et_name, et_email, et_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(RegisterActivity.this, R.layout.regis);

        et_name = binding.nameEditText;
        et_email = binding.emailEditText;
        et_mobile = binding.mobileEditText;

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isOnline(RegisterActivity.this)) {
                    if (validate()) {
                        startActivity(new Intent(RegisterActivity.this, EnterMPinActivity.class));
                        Toast.makeText(RegisterActivity.this, "Registered successfullly", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });

    }

    private boolean validate() {
        if (et_name.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(RegisterActivity.this, "Please enter username", Toast.LENGTH_LONG).show();
            return false;
        }

        if (et_email.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(RegisterActivity.this, "Please enter email", Toast.LENGTH_LONG).show();
            return false;
        }
        if (et_mobile.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(RegisterActivity.this, "Please enter mobile number", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
