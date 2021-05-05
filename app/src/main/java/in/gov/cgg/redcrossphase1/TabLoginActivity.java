package in.gov.cgg.redcrossphase1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import in.gov.cgg.redcrossphase1.databinding.ActivityTabloginBinding;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.CitiGuestMainActivity;
import in.gov.cgg.redcrossphase1.ui_citiguest.CitizenLoginFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.ui_citiguest.LocActivity;
import in.gov.cgg.redcrossphase1.ui_citiguest.NewRegisterActivity;
import in.gov.cgg.redcrossphase1.ui_officer.fragments.OfficerLoginFragment;

public class TabLoginActivity extends LocActivity implements View.OnClickListener {
    public static final String MyPREFERENCES = "MyPrefs";
    private final int[] tabIcons = {
            R.drawable.group,
            R.drawable.officer1,
    };
    String checkfrag = "c";
    int selectedThemeColor = -1;
    private ActivityTabloginBinding binding;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private JsonObject gsonObject;
    private String uname, pswd;
    private boolean isCheked;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        binding = DataBindingUtil.setContentView(TabLoginActivity.this, R.layout.activity_tablogin);

        SharedPreferences prefs = this.getSharedPreferences(
                MyPREFERENCES, Context.MODE_PRIVATE);

        binding.tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        GlobalDeclaration.profilePic = "";

        try {
            selectedThemeColor = this.getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF), MODE_PRIVATE).
                    getInt(getResources().getString(R.string.theme_color1), -1);
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
                    //binding.tabs.setBackground(getResources().getDrawable(R.drawable.tab_background3));

                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    binding.rlmain.setBackground(getResources().getDrawable(R.drawable.redcross4_bg));
                    //  binding.tabs.setBackground(getResources().getDrawable(R.drawable.tab_background4));


                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    binding.rlmain.setBackground(getResources().getDrawable(R.drawable.redcross5_bg));
                    //binding.tabs.setBackground(getResources().getDrawable(R.drawable.tab_background5));


                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    binding.rlmain.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                    // binding.tabs.setBackground(getResources().getDrawable(R.drawable.tab_background6));


                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    binding.rlmain.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
                    // binding.tabs.setBackground(getResources().getDrawable(R.drawable.tab_background7));


                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    binding.rlmain.setBackground(getResources().getDrawable(R.drawable.redcross8_bg));
                    // binding.tabs.setBackground(getResources().getDrawable(R.drawable.tab_background8));


                } else {
                    binding.rlmain.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                    binding.tvSignIn.setTextColor(getResources().getColor(R.color.colorPrimary));
                    binding.tvRegister.setTextColor(getResources().getColor(R.color.colorPrimary));
                    binding.tvCntinueguest.setTextColor(getResources().getColor(R.color.colorPrimary));
                    //  binding.tabs.setBackground(getResources().getDrawable(R.drawable.tab_background6));

                }
            } else {
                binding.rlmain.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                binding.tvSignIn.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvRegister.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvCntinueguest.setTextColor(getResources().getColor(R.color.colorPrimary));
                // binding.tabs.setBackground(getResources().getDrawable(R.drawable.tab_background6));

            }
        } catch (Exception e) {
            e.printStackTrace();
            binding.rlmain.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
            binding.tvSignIn.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvRegister.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.tvCntinueguest.setTextColor(getResources().getColor(R.color.colorPrimary));
            // binding.tabs.setBackground(getResources().getDrawable(R.drawable.tab_background6));

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
                    //tabLayout.setTabIconTint(ColorStateList.valueOf(getResources().getColor(R.color.black)));

                    try {
                        selectedThemeColor = getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF), MODE_PRIVATE).
                                getInt(getResources().getString(R.string.theme_color1), -1);
                        if (selectedThemeColor != -1) {

                            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(selectedThemeColor));

                            if (selectedThemeColor == R.color.redcroosbg_1) {
                                tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                                        getResources().getColor(selectedThemeColor));

                                binding.rltab.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected1));

                            } else if (selectedThemeColor == R.color.redcroosbg_2) {
                                tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                                        getResources().getColor(selectedThemeColor));
                                binding.rltab.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected2));


                            } else if (selectedThemeColor == R.color.redcroosbg_3) {

                                tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                                        getResources().getColor(selectedThemeColor));
                                binding.rltab.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected3));

                            } else if (selectedThemeColor == R.color.redcroosbg_4) {

                                tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                                        getResources().getColor(selectedThemeColor));
                                binding.rltab.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected4));


                            } else if (selectedThemeColor == R.color.redcroosbg_5) {


                                tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                                        getResources().getColor(selectedThemeColor));
                                binding.rltab.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected5));

                            } else if (selectedThemeColor == R.color.redcroosbg_6) {

                                tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                                        getResources().getColor(selectedThemeColor));
                                binding.rltab.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected6));


                            } else if (selectedThemeColor == R.color.redcroosbg_7) {

                                tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                                        getResources().getColor(selectedThemeColor));
                                binding.rltab.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected7));


                            } else if (selectedThemeColor == R.color.redcroosbg_8) {


                                tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                                        getResources().getColor(selectedThemeColor));
                                binding.rltab.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected8));


                            } else {
                                tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                                        getResources().getColor(R.color.redcroosbg_6));

                                binding.rltab.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected6));
                                tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.redcroosbg_6));


                            }
                        } else {

                            tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                                    getResources().getColor(R.color.redcroosbg_6));
                            binding.rltab.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected6));
                            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.redcroosbg_6));


                        }
                    } catch (Exception e) {
                        e.printStackTrace();


                        tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                                getResources().getColor(R.color.redcroosbg_6));
                        binding.rltab.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected6));
                        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.redcroosbg_6));

                    }


                    checkfrag = "c";

                    ViewGroup.MarginLayoutParams cardViewMarginParams = (ViewGroup.MarginLayoutParams) binding.cvMain.getLayoutParams();
                    cardViewMarginParams.setMargins(15, 5, 15, 30);
                    binding.cvMain.requestLayout();
                } else {
                    //tabLayout.setTabIconTint(ColorStateList.valueOf(getResources().getColor(R.color.black)));

                    binding.llRegister.setVisibility(View.GONE);

                    try {
                        selectedThemeColor = getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF), MODE_PRIVATE).
                                getInt(getResources().getString(R.string.theme_color1), -1);
                        if (selectedThemeColor != -1) {

                            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(selectedThemeColor));

                            if (selectedThemeColor == R.color.redcroosbg_1) {
                                tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                                        getResources().getColor(selectedThemeColor));

                                binding.rltab.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected1));

                            } else if (selectedThemeColor == R.color.redcroosbg_2) {
                                tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                                        getResources().getColor(selectedThemeColor));
                                binding.rltab.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected2));


                            } else if (selectedThemeColor == R.color.redcroosbg_3) {

                                tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                                        getResources().getColor(selectedThemeColor));
                                binding.rltab.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected3));

                            } else if (selectedThemeColor == R.color.redcroosbg_4) {

                                tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                                        getResources().getColor(selectedThemeColor));
                                binding.rltab.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected4));


                            } else if (selectedThemeColor == R.color.redcroosbg_5) {


                                tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                                        getResources().getColor(selectedThemeColor));
                                binding.rltab.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected5));

                            } else if (selectedThemeColor == R.color.redcroosbg_6) {

                                tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                                        getResources().getColor(selectedThemeColor));
                                binding.rltab.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected6));


                            } else if (selectedThemeColor == R.color.redcroosbg_7) {

                                tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                                        getResources().getColor(selectedThemeColor));
                                binding.rltab.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected7));


                            } else if (selectedThemeColor == R.color.redcroosbg_8) {


                                tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                                        getResources().getColor(selectedThemeColor));
                                binding.rltab.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected8));


                            } else {
                                tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                                        getResources().getColor(R.color.redcroosbg_6));

                                binding.rltab.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected6));

                            }
                        } else {

                            tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                                    getResources().getColor(R.color.redcroosbg_6));
                            binding.rltab.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected6));


                        }
                    } catch (Exception e) {
                        e.printStackTrace();


                        tabLayout.setTabTextColors(getResources().getColor(R.color.black),
                                getResources().getColor(R.color.redcroosbg_6));
                        binding.rltab.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected6));
                    }
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
                startActivity(new Intent(TabLoginActivity.this, NewRegisterActivity.class));
                break;
            case R.id.tv_cntinueguest:
                startActivity(new Intent(TabLoginActivity.this, CitiGuestMainActivity.class));
                GlobalDeclaration.guest = "y";
                GlobalDeclaration.profilePic = "";
                break;


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {


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
