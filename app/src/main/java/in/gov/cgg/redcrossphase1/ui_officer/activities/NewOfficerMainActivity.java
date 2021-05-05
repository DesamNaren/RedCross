package in.gov.cgg.redcrossphase1.ui_officer.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.TabLoginActivity;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.ui_officer.fragments.AgewiseFragment;
import in.gov.cgg.redcrossphase1.ui_officer.fragments.AllMandalsFragment;
import in.gov.cgg.redcrossphase1.ui_officer.fragments.AllVillageFragment;
import in.gov.cgg.redcrossphase1.ui_officer.fragments.BloodwiseFragment;
import in.gov.cgg.redcrossphase1.ui_officer.fragments.DaywiseFragment;
import in.gov.cgg.redcrossphase1.ui_officer.fragments.EnrolledMemberEditFragment;
import in.gov.cgg.redcrossphase1.ui_officer.fragments.GetDrilldownFragment;
import in.gov.cgg.redcrossphase1.ui_officer.fragments.GetMembershipDrilldown;
import in.gov.cgg.redcrossphase1.ui_officer.fragments.GovtPvtFragment;
import in.gov.cgg.redcrossphase1.ui_officer.fragments.InstitutionCountsFrgament;
import in.gov.cgg.redcrossphase1.ui_officer.fragments.MemberDistrictFragment;
import in.gov.cgg.redcrossphase1.ui_officer.fragments.NewAllDistrictsFragment;
import in.gov.cgg.redcrossphase1.ui_officer.fragments.NewOfficerHomeFragment;
import in.gov.cgg.redcrossphase1.ui_officer.fragments.NewTCFragment;
import in.gov.cgg.redcrossphase1.ui_officer.fragments.OfficerChangePassword;
import in.gov.cgg.redcrossphase1.ui_officer.fragments.OnlyGenderwiseFragment;
import in.gov.cgg.redcrossphase1.ui_officer.fragments.PrivacyPolicyFragment;

public class NewOfficerMainActivity extends AppCompatActivity {


    int selectedThemeColor = -1;
    NavigationView navigationView;
    Toolbar toolbar;
    View header;
    RelativeLayout layout_main;
    DrawerLayout drawerLayout;
    SharedPreferences settings;
    ViewPager viewPager_home;
    SharedPreferences.Editor editor;
    private View contentView;
    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;
    private LinearLayout ll_nav_header;
    private Fragment selectedFragment;
    private ImageView iv_color1_selected, iv_color2_selected, iv_color3_selected, iv_color4_selected, iv_color5_selected, iv_color6_selected, iv_color7_selected, iv_color8_selected;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        setContentView(R.layout.activity_officer_main);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        header = navigationView.getHeaderView(0);
        layout_main = findViewById(R.id.layout_main);
        ll_nav_header = header.findViewById(R.id.ll_dashboard_headerof);
        View headerView = navigationView.getHeaderView(0);
        contentView = findViewById(R.id.content);
        Menu nav_Menu = navigationView.getMenu();

        TextView tv_name = headerView.findViewById(R.id.textView_name);

        setSupportActionBar(toolbar);


        //default fragmentl
        GlobalDeclaration.FARG_TAG = NewOfficerHomeFragment.class.getSimpleName();
        selectedFragment = new NewOfficerHomeFragment();
        callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);

        try {
            selectedThemeColor = this.getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF), MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);
            if (selectedThemeColor != -1) {
                toolbar.setBackgroundResource(selectedThemeColor);
                ll_nav_header.setBackgroundColor(selectedThemeColor);
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    ll_nav_header.setBackground(getResources().getDrawable(R.drawable.redcross1_bg));
                    //navigationView.setBackgroundResource(R.drawable.redcross1_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    ll_nav_header.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
                    //navigationView.setBackgroundResource(R.drawable.redcross2_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    ll_nav_header.setBackground(getResources().getDrawable(R.drawable.redcross3_bg));
                    //navigationView.setBackgroundResource(R.drawable.redcross3_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    ll_nav_header.setBackground(getResources().getDrawable(R.drawable.redcross4_bg));
                    //navigationView.setBackgroundResource(R.drawable.redcross4_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    ll_nav_header.setBackground(getResources().getDrawable(R.drawable.redcross5_bg));
                    //navigationView.setBackgroundResource(R.drawable.redcross5_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    ll_nav_header.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                    // navigationView.setBackgroundResource(R.drawable.redcross6_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    ll_nav_header.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
                    //navigationView.setBackgroundResource(R.drawable.redcross7_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    ll_nav_header.setBackground(getResources().getDrawable(R.drawable.redcross8_bg));
                    //navigationView.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
                } else {
                    ll_nav_header.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                    //    navigationView.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
                    selectedThemeColor = R.color.colorPrimary;
                    toolbar.setBackgroundResource(selectedThemeColor);
                    sharedPreferenceMethod(selectedThemeColor);
                }
            } else {
                ll_nav_header.setBackgroundResource(R.drawable.redcross6_bg);
                //   navigationView.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
                selectedThemeColor = R.color.colorPrimary;
                toolbar.setBackgroundResource(selectedThemeColor);
                sharedPreferenceMethod(selectedThemeColor);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        if (GlobalDeclaration.role != null) {
            if (!GlobalDeclaration.role.contains("D")) {
                nav_Menu.findItem(R.id.nav_allmandals).setVisible(false);
                nav_Menu.findItem(R.id.nav_alldistricts).setVisible(true);
                drawerLayout.setScrimColor(Color.TRANSPARENT);

            } else {
                nav_Menu.findItem(R.id.nav_allmandals).setVisible(true);
                nav_Menu.findItem(R.id.nav_alldistricts).setVisible(false);
                drawerLayout.setScrimColor(Color.TRANSPARENT);
            }
        }


        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_gender, R.id.nav_district,
                R.id.nav_blood, R.id.nav_govtpvt, R.id.nav_alldistricts, R.id.nav_tc, R.id.nav_privacy,
                R.id.nav_drill, R.id.nav_daywise, R.id.nav_ofctheme, R.id.nav_institutecount)
                .setDrawerLayout(drawerLayout)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_officer);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        if (GlobalDeclaration.username != null && GlobalDeclaration.designation != null) {
            tv_name.setText("Welcome " + GlobalDeclaration.designation + "\n" + GlobalDeclaration.username);
        } else {
            tv_name.setText("Welcome " + GlobalDeclaration.username);

        }

    }

    private void sharedPreferenceMethod(int selectedThemeColor) {
        settings = getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF), Activity.MODE_PRIVATE);
        editor = settings.edit();
        editor.putInt(getResources().getString(R.string.theme_color1), selectedThemeColor);
        editor.commit();
    }

    // creating sharred preferences
    public void storesInsharedPref(int selectedThemeColor) {
        sharedPreferenceMethod(selectedThemeColor);

        // CustomRelativeLayout.changeStatusBarColor(this);
        //refersh of same fragment
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_officer);
        if (currentFragment instanceof NewOfficerHomeFragment) {
            FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
            fragTransaction.detach(currentFragment);
            fragTransaction.attach(currentFragment);
            fragTransaction.commit();
        }


    }

    void callFragment(Fragment fragment, String name) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment_officer, fragment, name);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_backpress, menu);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.nav_daywise) {
                    GlobalDeclaration.FARG_TAG = DaywiseFragment.class.getSimpleName();
                    selectedFragment = new DaywiseFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_district) {
                    GlobalDeclaration.FARG_TAG = NewOfficerHomeFragment.class.getSimpleName();
                    selectedFragment = new NewOfficerHomeFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_govtpvt) {
                    GlobalDeclaration.FARG_TAG = GovtPvtFragment.class.getSimpleName();
                    selectedFragment = new GovtPvtFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_alldistricts) {
                    GlobalDeclaration.FARG_TAG = NewAllDistrictsFragment.class.getSimpleName();
                    selectedFragment = new NewAllDistrictsFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_tc) {
                    GlobalDeclaration.FARG_TAG = NewTCFragment.class.getSimpleName();
                    selectedFragment = new NewTCFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_privacy) {
                    GlobalDeclaration.FARG_TAG = PrivacyPolicyFragment.class.getSimpleName();
                    selectedFragment = new PrivacyPolicyFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_allmandals) {
                    GlobalDeclaration.FARG_TAG = AllMandalsFragment.class.getSimpleName();
                    selectedFragment = new AllMandalsFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_institutecount) {
                    GlobalDeclaration.FARG_TAG = InstitutionCountsFrgament.class.getSimpleName();
                    selectedFragment = new InstitutionCountsFrgament();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_logoutmenu) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(NewOfficerMainActivity.this);
                    builder1.setMessage(getResources().getString(R.string.logoutmsg));
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                    startActivity(new Intent(NewOfficerMainActivity.this, TabLoginActivity.class));

                                    finish();
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.cancel),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                    return true;
                } else if (menuItem.getItemId() == R.id.nav_ofctheme) {
                    showThemePicker();
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_changepassword) {
                    GlobalDeclaration.FARG_TAG = OfficerChangePassword.class.getSimpleName();
                    selectedFragment = new OfficerChangePassword();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }

                return true;
            }
        });
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.nav_daywise) {
                    menu.findItem(R.id.logout).setIcon(R.drawable.ic_home_white_48dp);
                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            startActivity(new Intent(NewOfficerMainActivity.this, NewOfficerMainActivity.class));
                            return true;
                        }
                    });

                } else if (destination.getId() == R.id.nav_institutecount) {
                    menu.findItem(R.id.logout).setIcon(R.drawable.ic_home_white_48dp);
                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            startActivity(new Intent(NewOfficerMainActivity.this, NewOfficerMainActivity.class));
                            return true;
                        }
                    });

                } else if (destination.getId() == R.id.nav_district) {
                    menu.findItem(R.id.logout).setIcon(R.drawable.ic_power_settings_new_black_24dp);
                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            callHomeAlert();
                            return true;
                        }
                    });
                } else if (destination.getId() == R.id.nav_govtpvt) {
                    menu.findItem(R.id.logout).setIcon(R.drawable.ic_power_settings_new_black_24dp);
                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            callHomeAlert();
                            return true;
                        }
                    });
                } else if (destination.getId() == R.id.nav_alldistricts) {
                    menu.findItem(R.id.logout).setIcon(R.drawable.ic_home_white_48dp);
                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            callHomeAlert();
                            return true;
                        }
                    });
                } else if (destination.getId() == R.id.nav_tc) {
                    menu.findItem(R.id.logout).setIcon(R.drawable.ic_home_white_48dp);
                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            callHomeAlert();
                            return true;
                        }
                    });
                } else if (destination.getId() == R.id.nav_privacy) {
                    menu.findItem(R.id.logout).setIcon(R.drawable.ic_home_white_48dp);
                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            startActivity(new Intent(NewOfficerMainActivity.this, NewOfficerMainActivity.class));
                            return true;
                        }
                    });
                } else if (destination.getId() == R.id.nav_ofctheme) {
                    showThemePicker();
                }

            }
        });
        return true;
    }

    private void setFragment(String fargTag) {

        if (fargTag.equalsIgnoreCase(getResources().getString(R.string.DaywiseFragment))) {
            selectedFragment = new DaywiseFragment();
        } else if (fargTag.equalsIgnoreCase(getResources().getString(R.string.NewOfficerHomeFragment))) {
            selectedFragment = new NewOfficerHomeFragment();
        } else if (fargTag.equalsIgnoreCase(getResources().getString(R.string.NewAllDistrictsFragment))) {
            selectedFragment = new NewAllDistrictsFragment();
        } else if (fargTag.equalsIgnoreCase(getResources().getString(R.string.PrivacyPolicyFragment))) {
            selectedFragment = new PrivacyPolicyFragment();
        } else if (fargTag.equalsIgnoreCase(getResources().getString(R.string.AllMandalsFragment))) {
            selectedFragment = new AllMandalsFragment();
        } else if (fargTag.equalsIgnoreCase(getResources().getString(R.string.AllVillageFragment))) {
            selectedFragment = new AllVillageFragment();
        } else if (fargTag.equalsIgnoreCase(getResources().getString(R.string.GetDrilldownFragment))) {
            selectedFragment = new GetDrilldownFragment();
        } else if (fargTag.equalsIgnoreCase(getResources().getString(R.string.NewTCFragment))) {
            selectedFragment = new NewTCFragment();
        } else if (fargTag.equalsIgnoreCase(getResources().getString(R.string.InstitutionCountsFrgament))) {
            selectedFragment = new InstitutionCountsFrgament();
        } else if (fargTag.equalsIgnoreCase(getResources().getString(R.string.EnrolledMemberEditFragment))) {
            selectedFragment = new EnrolledMemberEditFragment();
        } else if (fargTag.equalsIgnoreCase(getResources().getString(R.string.MemberDistrictFragment))) {
            selectedFragment = new MemberDistrictFragment();
        } else if (fargTag.equalsIgnoreCase(getResources().getString(R.string.AgewiseFragment))) {
            selectedFragment = new AgewiseFragment();
        } else if (fargTag.equalsIgnoreCase(getResources().getString(R.string.BloodwiseFragment))) {
            selectedFragment = new BloodwiseFragment();
        } else if (fargTag.equalsIgnoreCase(getResources().getString(R.string.GetMembershipDrilldown))) {
            selectedFragment = new GetMembershipDrilldown();
        } else if (fargTag.equalsIgnoreCase(getResources().getString(R.string.OnlyGenderwiseFragment))) {
            selectedFragment = new OnlyGenderwiseFragment();
        } else if (fargTag.equalsIgnoreCase(getResources().getString(R.string.OfficerChangePassword))) {
            selectedFragment = new OfficerChangePassword();
        } else {
            GlobalDeclaration.FARG_TAG = NewOfficerHomeFragment.class.getSimpleName();
            selectedFragment = new NewOfficerHomeFragment();
        }

    }


    //call this method for selection of themes in menu
    private void showThemePicker() {
        selectedThemeColor = -1;
        final Dialog dialog = new Dialog(NewOfficerMainActivity.this);
        dialog.setTitle(getResources().getString(R.string.theme_color));
        dialog.setContentView(R.layout.fragment_themes);

        ImageView iv_color1 = dialog.findViewById(R.id.color1);
        ImageView iv_color2 = dialog.findViewById(R.id.color2);
        ImageView iv_color3 = dialog.findViewById(R.id.color3);
        ImageView iv_color4 = dialog.findViewById(R.id.color4);
        ImageView iv_color5 = dialog.findViewById(R.id.color5);
        ImageView iv_color6 = dialog.findViewById(R.id.color6);
        ImageView iv_color7 = dialog.findViewById(R.id.color7);
        ImageView iv_color8 = dialog.findViewById(R.id.color8);

        iv_color1_selected = dialog.findViewById(R.id.color1_selected);
        iv_color2_selected = dialog.findViewById(R.id.color2_selected);
        iv_color3_selected = dialog.findViewById(R.id.color3_selected);
        iv_color4_selected = dialog.findViewById(R.id.color4_selected);
        iv_color5_selected = dialog.findViewById(R.id.color5_selected);
        iv_color6_selected = dialog.findViewById(R.id.color6_selected);
        iv_color7_selected = dialog.findViewById(R.id.color7_selected);
        iv_color8_selected = dialog.findViewById(R.id.color8_selected);
        Button bt_cancel_color = dialog.findViewById(R.id.bt_cancel_color);

        bt_cancel_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        iv_color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedThemeColor = R.color.redcroosbg_1;
                if (selectedThemeColor != -1) {
                    layout_main.setBackgroundResource(selectedThemeColor);
                    //  navigationView.setBackground(getResources().getDrawable(R.drawable.redcross1_bg));
                    toolbar.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    ll_nav_header.setBackgroundResource(R.drawable.redcross1_bg);
                    dialog.dismiss();
                    storesInsharedPref(selectedThemeColor);
                    setFragment(GlobalDeclaration.FARG_TAG);
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });
        iv_color2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedThemeColor = R.color.redcroosbg_2;
                if (selectedThemeColor != -1) {
                    //  int[] colorsIds = getResources().getIntArray(R.array.theme_colors_id);
                    layout_main.setBackgroundColor(selectedThemeColor);
                    ll_nav_header.setBackgroundResource(R.drawable.redcross2_bg);
                    // navigationView.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
                    toolbar.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    dialog.dismiss();
                    storesInsharedPref(selectedThemeColor);
                    setFragment(GlobalDeclaration.FARG_TAG);
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });
        iv_color3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedThemeColor = R.color.redcroosbg_3;
                if (selectedThemeColor != -1) {
                    layout_main.setBackgroundResource(selectedThemeColor);
                    ll_nav_header.setBackgroundResource(R.drawable.redcross3_bg);
                    //  navigationView.setBackground(getResources().getDrawable(R.drawable.redcross3_bg));
                    toolbar.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    dialog.dismiss();
                    storesInsharedPref(selectedThemeColor);
                    setFragment(GlobalDeclaration.FARG_TAG);
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });
        iv_color4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedThemeColor = R.color.redcroosbg_4;
                if (selectedThemeColor != -1) {
                    layout_main.setBackgroundResource(selectedThemeColor);
                    ll_nav_header.setBackgroundResource(R.drawable.redcross4_bg);
                    // navigationView.setBackground(getResources().getDrawable(R.drawable.redcross4_bg));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.redcroosbg_4));
                    dialog.dismiss();
                    storesInsharedPref(selectedThemeColor);
                    setFragment(GlobalDeclaration.FARG_TAG);
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });
        iv_color5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedThemeColor = R.color.redcroosbg_5;
                if (selectedThemeColor != -1) {
                    layout_main.setBackgroundResource(selectedThemeColor);
                    ll_nav_header.setBackgroundResource(R.drawable.redcross5_bg);
                    // navigationView.setBackground(getResources().getDrawable(R.drawable.redcross5_bg));
                    toolbar.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    dialog.dismiss();
                    storesInsharedPref(selectedThemeColor);
                    setFragment(GlobalDeclaration.FARG_TAG);
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });
        iv_color6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedThemeColor = R.color.redcroosbg_6;
                if (selectedThemeColor != -1) {
                    layout_main.setBackgroundResource(selectedThemeColor);
                    ll_nav_header.setBackgroundResource(R.drawable.redcross6_bg);
                    //   navigationView.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                    toolbar.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    dialog.dismiss();
                    storesInsharedPref(selectedThemeColor);
                    setFragment(GlobalDeclaration.FARG_TAG);
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });
        iv_color7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedThemeColor = R.color.redcroosbg_7;
                if (selectedThemeColor != -1) {
                    layout_main.setBackgroundResource(selectedThemeColor);
                    ll_nav_header.setBackgroundResource(R.drawable.redcross7_bg);
                    //  navigationView.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
                    toolbar.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    dialog.dismiss();
                    storesInsharedPref(selectedThemeColor);
                    setFragment(GlobalDeclaration.FARG_TAG);
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });
        iv_color8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedThemeColor = R.color.redcroosbg_8;
                if (selectedThemeColor != -1) {
                    layout_main.setBackgroundResource(selectedThemeColor);
                    ll_nav_header.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
                    //  navigationView.setBackground(getResources().getDrawable(R.drawable.redcross_splashscreen_bg));
                    toolbar.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    dialog.dismiss();
                    storesInsharedPref(selectedThemeColor);
                    setFragment(GlobalDeclaration.FARG_TAG);
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {

        if (GlobalDeclaration.home) {
            callHomeAlert();
        } else {
//            mMenu.findItem(R.id.logout).setIcon(R.drawable.home_white);
        }
    }

    private void callHomeAlert() {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(NewOfficerMainActivity.this);
        builder1.setMessage("Do you want to Logout?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        startActivity(new Intent(NewOfficerMainActivity.this, TabLoginActivity.class));

                        finish();
                    }
                });

        builder1.setNegativeButton(
                getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_officer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}


