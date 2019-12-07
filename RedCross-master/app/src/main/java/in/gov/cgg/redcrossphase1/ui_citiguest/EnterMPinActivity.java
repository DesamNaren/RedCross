package in.gov.cgg.redcrossphase1.ui_citiguest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.TabLoginActivity;
import in.gov.cgg.redcrossphase1.ui_officer_new.NewOfficerMainActivity;

public class EnterMPinActivity extends Activity {


    PinView pinView;
    TextView tv_forgot, tv_wrong, tv_log, tv_new;
    String mpin, pmpin;
    Button validate_mpin_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entermpin);

        pinView = findViewById(R.id.pin);
        tv_forgot = findViewById(R.id.tv_forgot);
        tv_log = findViewById(R.id.text_log);
        tv_new = findViewById(R.id.tv_new);
        tv_wrong = findViewById(R.id.tv_wrong);
        validate_mpin_btn = findViewById(R.id.validate_mpin_btn);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // final String mpin = "1234";
        //final String mobile = preferences.getString("mobile", "");

//        String first = mobile.substring(0, 3);
//        String last = mobile.substring(7, 10);

        //  String newmobile = first + "*****" + last;

//        tv_log.setText("Logged in with " + newmobile);
//        tv_new.setText("Not you?");
        //Log.e("newmobile", newmobile);


        pinView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_wrong.setVisibility(View.GONE);
            }
        });


        tv_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pinView.setText("");
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EnterMPinActivity.this);
                builder1.setMessage("Proceed to submit ticket");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            @SuppressLint("NewApi")
                            public void onClick(DialogInterface dialog, int id) {

                                //startActivity(new Intent(EnterMPinActivity.this, RaiseTicketActivity.class));

                            }
                        });
                builder1.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            @SuppressLint("NewApi")
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });

        validate_mpin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields(v)) {
                    pmpin = pinView.getText().toString();
                    if (pmpin.equalsIgnoreCase("1234")) {
                        tv_wrong.setVisibility(View.GONE);
                        startActivity(new Intent(EnterMPinActivity.this, NewOfficerMainActivity.class));

                    } else {

                        pinView.setText("");

//                        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                        hideKeyboardFrom(v);
                        tv_wrong.setVisibility(View.VISIBLE);
                        tv_wrong.requestFocus();
                    }
                }
            }
        });

        tv_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EnterMPinActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("mobile", "");
                editor.putString("mpin", "");
                editor.apply();
                startActivity(new Intent(EnterMPinActivity.this, TabLoginActivity.class));

            }
        });

//        tv_forgot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder1 = new AlertDialog.Builder(EnterMPinActivity.this);
//                builder1.setMessage("Press OK to Set MPIN");
//                builder1.setCancelable(false);
//
//                builder1.setPositiveButton(
//                        "OK",
//                        new DialogInterface.OnClickListener() {
//                            @SuppressLint("NewApi")
//                            public void onClick(DialogInterface dialog, int id) {
//                                startActivity(new Intent(EnterMPinActivity.this, RaiseTicketActivity.class));
//                            }
//                        });
//                AlertDialog alert11 = builder1.create();
//                alert11.show();
//            }
//        });

    }


    private boolean validateFields(View v) {
        if (pinView.getText().toString().trim().length() == 0 || pinView.getText().toString().trim().length() < 4) {
            Toast.makeText(EnterMPinActivity.this, "Please Enter Valid MPIN", Toast.LENGTH_SHORT).show();
//
//            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

            hideKeyboardFrom(v);
            return false;
        }
        return true;
    }

    public void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}