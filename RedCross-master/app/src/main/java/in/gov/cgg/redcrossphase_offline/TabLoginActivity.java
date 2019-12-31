package in.gov.cgg.redcrossphase_offline;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import in.gov.cgg.redcrossphase_offline.databinding.ActivityTabloginBinding;
import in.gov.cgg.redcrossphase_offline.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.CitiGuestMainActivity;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.CitizenLoginFragment;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.RegisterActivity;
import in.gov.cgg.redcrossphase_offline.ui_officer.fragments.OfficerLoginFragment;

public class TabLoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String MyPREFERENCES = "MyPrefs";
    String checkfrag = "c";
    private ActivityTabloginBinding binding;
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
    int selectedThemeColor = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(TabLoginActivity.this, R.layout.activity_tablogin);

        SharedPreferences prefs = this.getSharedPreferences(
                MyPREFERENCES, Context.MODE_PRIVATE);


        try {
            selectedThemeColor = this.getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).
                    getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                binding.tvSignIn.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvRegister.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvCntinueguest.setTextColor(getResources().getColor(selectedThemeColor));

                if (selectedThemeColor == R.color.redcroosbg_1) {
                    binding.rlmain.setBackground(getResources().getDrawable(R.drawable.redcross1_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    binding.rlmain.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    binding.rlmain.setBackground(getResources().getDrawable(R.drawable.redcross3_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    binding.rlmain.setBackground(getResources().getDrawable(R.drawable.redcross4_bg));

                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    binding.rlmain.setBackground(getResources().getDrawable(R.drawable.redcross5_bg));

                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    binding.rlmain.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));

                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    binding.rlmain.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));

                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    binding.rlmain.setBackground(getResources().getDrawable(R.drawable.redcross8_bg));

                } else {
                    binding.rlmain.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
                    binding.tvSignIn.setTextColor(getResources().getColor(R.color.colorPrimary));
                    binding.tvRegister.setTextColor(getResources().getColor(R.color.colorPrimary));
                    binding.tvCntinueguest.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } else {
                binding.rlmain.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
                binding.tvSignIn.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvRegister.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvCntinueguest.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        } catch (Exception e) {
            e.printStackTrace();
            binding.rlmain.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
            binding.tvSignIn.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvRegister.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvCntinueguest.setTextColor(getResources().getColor(R.color.colorPrimary));
        }


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
                    tabLayout.setTabIconTint(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    checkfrag = "c";

                    ViewGroup.MarginLayoutParams cardViewMarginParams = (ViewGroup.MarginLayoutParams) binding.cvMain.getLayoutParams();
                    cardViewMarginParams.setMargins(15, 5, 15, 30);
                    binding.cvMain.requestLayout();
                } else {
                    binding.llRegister.setVisibility(View.GONE);

                    checkfrag = "o";
                    ViewGroup.MarginLayoutParams cardViewMarginParams = (ViewGroup.MarginLayoutParams) binding.cvMain.getLayoutParams();
                    cardViewMarginParams.setMargins(15, 5, 15, 0);
                    binding.cvMain.requestLayout();

                }
            }
        });


        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        binding.tvRegister.setOnClickListener(this);
        //binding.btnSubmit.setOnClickListener(this);
        binding.tvCntinueguest.setOnClickListener(this);


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
                break;
            case R.id.tv_cntinueguest:
                startActivity(new Intent(TabLoginActivity.this, CitiGuestMainActivity.class));
                GlobalDeclaration.guest = "y";
                break;


        }
    }



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
