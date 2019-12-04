package in.gov.cgg.redcrossphase1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.gov.cgg.redcrossphase1.databinding.ActivityTabloginBinding;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.ui_citiguest.CitiGuestMainActivity;
import in.gov.cgg.redcrossphase1.ui_citiguest.CitizenLoginFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.RegisterActivity;
import in.gov.cgg.redcrossphase1.ui_officer.OfficerLoginFragment;
import in.gov.cgg.redcrossphase1.ui_officer.OfficerMainActivity;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabLoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String MyPREFERENCES = "MyPrefs";
    String checkfrag = "c";
    private ActivityTabloginBinding binding;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private JsonObject gsonObject;
    private String uname, pswd;
    private boolean isCheked;

    private int[] tabIcons = {
            R.drawable.citizen,
            R.drawable.officer1,
    };
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(TabLoginActivity.this, R.layout.activity_tablogin);

        SharedPreferences prefs = this.getSharedPreferences(
                MyPREFERENCES, Context.MODE_PRIVATE);

//        uname = prefs.getString("un", "");
//        pswd = prefs.getString("pw", "");
//        isCheked = prefs.getBoolean("is", false);
//        if (isCheked) {
//            GlobalDeclaration.sharedUname = uname;
//            GlobalDeclaration.sharedUPswd = pswd;
//            GlobalDeclaration.ischecked = isCheked;
//        }

        progressDialog = new ProgressDialog(TabLoginActivity.this);
        progressDialog.setMessage("Please wait");
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onPageSelected(int position) {
                if (position == 0) {
                    binding.llRegister.setVisibility(View.VISIBLE);
                    //  binding.llRememberme.setVisibility(View.GONE);
                    tabLayout.setTabIconTint(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    checkfrag = "c";

//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                            ViewGroup.LayoutParams.WRAP_CONTENT);
//                    params.setMargins(10, 5, 10, 50);
//                    binding.llCard.setLayoutParams(params);
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
//                    params.setMargins(30, 10, 30, 50);
//                    binding.cvMain.setLayoutParams(params);
                    ViewGroup.MarginLayoutParams cardViewMarginParams = (ViewGroup.MarginLayoutParams) binding.cvMain.getLayoutParams();
                    cardViewMarginParams.setMargins(15, 5, 15, 30);
                    binding.cvMain.requestLayout();
                } else {
                    binding.llRegister.setVisibility(View.GONE);
                    //binding.llRememberme.setVisibility(View.VISIBLE);

                    checkfrag = "o";
                    ViewGroup.MarginLayoutParams cardViewMarginParams = (ViewGroup.MarginLayoutParams) binding.cvMain.getLayoutParams();
                    cardViewMarginParams.setMargins(15, 5, 15, 0);
                    binding.cvMain.requestLayout();

//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                            ViewGroup.LayoutParams.MATCH_PARENT);
//                    params.setMargins(30, 10, 30, 10);
//                    binding.viewpager.setLayoutParams(params);
                }
            }
        });


        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        binding.tvRegister.setOnClickListener(this);
        //binding.btnSubmit.setOnClickListener(this);
        binding.tvCntinueguest.setOnClickListener(this);

        //set cardview height
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
//        params.setMargins(30, 10, 30, 10);
//        binding.viewpager.setLayoutParams(params);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new CitizenLoginFragment(), "CITIZEN");
        adapter.addFrag(new OfficerLoginFragment(), "OFFICER");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                startActivity(new Intent(TabLoginActivity.this, RegisterActivity.class));
                Toast.makeText(this, "Registrations will be availed soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_cntinueguest:
                startActivity(new Intent(TabLoginActivity.this, CitiGuestMainActivity.class));
                GlobalDeclaration.guest = "y";
                break;

//            case R.id.btn_submit:
//                checkLoginType();
//                break;
        }
    }

    private void checkLoginType() {

        if (checkfrag.equalsIgnoreCase("c")) {
            startActivity(new Intent(TabLoginActivity.this, CitiGuestMainActivity.class));
            finish();

        } else if (checkfrag.equalsIgnoreCase("o")) {
            if (CheckInternet.isOnline(TabLoginActivity.this)) {

                // if (validate()) {
                callLoginRequest();

                // }
            } else {
                Toast.makeText(TabLoginActivity.this, "Please check your internet connection", Toast.LENGTH_LONG).show();

            }
        } else {

        }


    }

    private void callLoginRequest() {

        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        JSONObject object = new JSONObject();
        try {
            object.put("userName", "RCADLB");
            object.put("passwd", "RCADLB");
            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(object.toString());
            Log.e("sent_json", object.toString());

            Call<JsonObject> call = apiInterface.callLogin(gsonObject);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    progressDialog.dismiss();
                    if (response.body() != null) {

                        //Log.d("Login", "onResponse: "+response.body().getAsString());
                        String message = response.body().get("message").toString();
                        String status = response.body().get("status").toString();


                        if (status.equals("200")) {

                            String role = response.body().get("role").toString();
                            String districtId = response.body().get("districtId").toString();
                            String userID = response.body().get("userID").toString();
                            GlobalDeclaration.role = role;
                            GlobalDeclaration.districtId = districtId;
                            GlobalDeclaration.userID = userID;


                            Toast.makeText(TabLoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(TabLoginActivity.this, OfficerMainActivity.class));
                            finish();
                        } else if (status.equals("100")) {
                            Toast.makeText(TabLoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            // etName.setText("");
                            //etPwd.setText("");
                        } else {

                        }
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(TabLoginActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

/*
    private boolean validate() {
        if (etName.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(LoginActivity.this, "Please enter valid username", Toast.LENGTH_LONG).show();
            return false;
        }

        if (etPwd.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
*/

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
