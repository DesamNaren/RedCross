package in.gov.cgg.redcrossphase1.ui_officer_new;

import android.app.Dialog;
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

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.TabLoginActivity;
import in.gov.cgg.redcrossphase1.ui_officer.NewTCFragment;
import in.gov.cgg.redcrossphase1.ui_officer.PhotoUploadActivity;
import in.gov.cgg.redcrossphase1.ui_officer.PrivacyPolicyFragment;
import in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport.AllMandalsFragment;
import in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport.AllVillageFragment;
import in.gov.cgg.redcrossphase1.ui_officer.daywisereportcont.DaywiseFragment;
import in.gov.cgg.redcrossphase1.ui_officer.drilldown.GetDrilldownFragment;
import in.gov.cgg.redcrossphase1.ui_officer.govtpvt.GovtPvtFragment;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class NewOfficerMainActivity extends AppCompatActivity implements IHomeListener {


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
        setContentView(R.layout.activity_officer_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        header = navigationView.getHeaderView(0);
        layout_main = findViewById(R.id.layout_main);
        ll_nav_header = header.findViewById(R.id.ll_dashboard_header);
        View headerView = navigationView.getHeaderView(0);
        contentView = findViewById(R.id.content);
        TextView tv_name = headerView.findViewById(R.id.textView_name);

//        viewPager_home = findViewById(R.id.viewpager_home);
//
        setSupportActionBar(toolbar);
        toolbar.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));

        Menu nav_Menu = navigationView.getMenu();
//
//        setupViewPager(viewPager_home);
//        tabLayout = findViewById(R.id.tabs_home);
//        tabLayout.setupWithViewPager(viewPager_home);
        //setupTabIcons();

        GlobalDeclaration.FARG_TAG = NewOfficerHomeFragment.class.getSimpleName();
        //default fragmentl
        selectedFragment = new NewOfficerHomeFragment();
        callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);


//        try {
//            selectedThemeColor = this.getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
//            if (selectedThemeColor != -1) {
//                toolbar.setBackgroundResource(selectedThemeColor);
//                if (selectedThemeColor == R.color.redcroosbg_1) {
//                    ll_nav_header.setBackgroundResource(R.drawable.redcross1_bg);
//                    //navigationView.setBackgroundResource(R.drawable.redcross1_bg);
//                } else if (selectedThemeColor == R.color.redcroosbg_2) {
//                    ll_nav_header.setBackgroundResource(R.drawable.redcross2_bg);
//                    //navigationView.setBackgroundResource(R.drawable.redcross2_bg);
//                } else if (selectedThemeColor == R.color.redcroosbg_3) {
//                    ll_nav_header.setBackgroundResource(R.drawable.redcross3_bg);
//                    //navigationView.setBackgroundResource(R.drawable.redcross3_bg);
//                } else if (selectedThemeColor == R.color.redcroosbg_4) {
//                    ll_nav_header.setBackgroundResource(R.drawable.redcross4_bg);
//                    //navigationView.setBackgroundResource(R.drawable.redcross4_bg);
//                } else if (selectedThemeColor == R.color.redcroosbg_5) {
//                    ll_nav_header.setBackgroundResource(R.drawable.redcross5_bg);
//                    //navigationView.setBackgroundResource(R.drawable.redcross5_bg);
//                } else if (selectedThemeColor == R.color.redcroosbg_6) {
//                    ll_nav_header.setBackgroundResource(R.drawable.redcross6_bg);
//                    // navigationView.setBackgroundResource(R.drawable.redcross6_bg);
//                } else if (selectedThemeColor == R.color.redcroosbg_7) {
//                    ll_nav_header.setBackgroundResource(R.drawable.redcross7_bg);
//                    //navigationView.setBackgroundResource(R.drawable.redcross7_bg);
//                } else if (selectedThemeColor == R.color.redcroosbg_8) {
//                    ll_nav_header.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
//                    //navigationView.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
//                } else {
//                    ll_nav_header.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
//                    //    navigationView.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
//                    selectedThemeColor = R.color.colorPrimary;
//                    toolbar.setBackgroundResource(selectedThemeColor);
//                    sharedPreferenceMethod(selectedThemeColor);
//                }
//            } else {
//                ll_nav_header.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
//                //   navigationView.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
//                selectedThemeColor = R.color.colorPrimary;
//                //toolbar.setBackgroundResource(selectedThemeColor);
//                sharedPreferenceMethod(selectedThemeColor);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }

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


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_age, R.id.nav_gender, R.id.nav_district,
                R.id.nav_blood, R.id.nav_top5, R.id.nav_govtpvt, R.id.nav_alldistricts, R.id.nav_tc, R.id.nav_privacy,
                R.id.nav_drill, R.id.nav_daywise, R.id.nav_ofctheme)
                .setDrawerLayout(drawerLayout)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_officer);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        if (GlobalDeclaration.username != null && GlobalDeclaration.designation != null) {
            tv_name.setText("Welcome to " + GlobalDeclaration.designation + "\n" + GlobalDeclaration.username);
        } else {
            tv_name.setText("Welcome to " + GlobalDeclaration.username);

        }

    }


//    private void setupTabIcons() {
//        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
//        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
//    }

//    private void sharedPreferenceMethod(int selectedThemeColor) {
//        settings = getSharedPreferences("THEMECOLOR_PREF", Activity.MODE_PRIVATE);
//        editor = settings.edit();
//        editor.putInt("theme_color", selectedThemeColor);
//        editor.commit();
//    }

    // creating sharred preferences
    public void storesInsharedPref(int selectedThemeColor) {
        // sharedPreferenceMethod(selectedThemeColor);

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
//                if (menuItem.getItemId() == R.id.nav_ofctheme) {
//                    showThemePicker();
//
//                }else
                if (menuItem.getItemId() == R.id.nav_daywise) {
                    // menu.findItem(R.id.logout).setIcon(R.drawable.ic_home_white_48dp);
                    GlobalDeclaration.FARG_TAG = DaywiseFragment.class.getSimpleName();
                    selectedFragment = new DaywiseFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
//                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                        @Override
//                        public boolean onMenuItemClick(MenuItem item) {
//                            startActivity(new Intent(OfficerMainActivity.this, OfficerMainActivity.class));
//                            return true;
//                        }
//                    });
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_district) {
         /*           menu.findItem(R.id.logout).setIcon(R.drawable.ic_power_settings_new_black_24dp);
                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            callHomeAlert();
                            return true;
                        }
                    });*/
                    GlobalDeclaration.FARG_TAG = NewOfficerHomeFragment.class.getSimpleName();
                    selectedFragment = new NewOfficerHomeFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_govtpvt) {
         /*           menu.findItem(R.id.logout).setIcon(R.drawable.ic_power_settings_new_black_24dp);
                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            callHomeAlert();
                            return true;
                        }
                    });*/
                    GlobalDeclaration.FARG_TAG = GovtPvtFragment.class.getSimpleName();
                    selectedFragment = new GovtPvtFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_age) {
         /*           menu.findItem(R.id.logout).setIcon(R.drawable.ic_power_settings_new_black_24dp);
                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            callHomeAlert();
                            return true;
                        }
                    });*/
                    GlobalDeclaration.FARG_TAG = PhotoUploadActivity.class.getSimpleName();
                    selectedFragment = new PhotoUploadActivity();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_alldistricts) {
                   /* menu.findItem(R.id.logout).setIcon(R.drawable.ic_home_white_48dp);
                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            startActivity(new Intent(OfficerMainActivity.this, OfficerMainActivity.class));
                            return true;
                        }
                    });*/
                    GlobalDeclaration.FARG_TAG = NewAllDistrictsFragment.class.getSimpleName();
                    selectedFragment = new NewAllDistrictsFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_tc) {
                    //menu.findItem(R.id.logout).setIcon(R.drawable.ic_home_white_48dp);
//                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                        @Override
//                        public boolean onMenuItemClick(MenuItem item) {
//                            startActivity(new Intent(OfficerMainActivity.this, OfficerMainActivity.class));
//                            return true;
//                        }
//                    });
                    GlobalDeclaration.FARG_TAG = NewTCFragment.class.getSimpleName();
                    selectedFragment = new NewTCFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_privacy) {
                    // menu.findItem(R.id.logout).setIcon(R.drawable.ic_home_white_48dp);
//                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                        @Override
//                        public boolean onMenuItemClick(MenuItem item) {
//                            startActivity(new Intent(OfficerMainActivity.this, OfficerMainActivity.class));
//                            return true;
//                        }
//                    });
                    GlobalDeclaration.FARG_TAG = PrivacyPolicyFragment.class.getSimpleName();
                    selectedFragment = new PrivacyPolicyFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_allmandals) {
                   /* menu.findItem(R.id.logout).setIcon(R.drawable.ic_home_white_48dp);
                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            startActivity(new Intent(OfficerMainActivity.this, OfficerMainActivity.class));
                            return true;
                        }
                    });*/
                    GlobalDeclaration.FARG_TAG = AllMandalsFragment.class.getSimpleName();
                    selectedFragment = new AllMandalsFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_logoutmenu) {

                    startActivity(new Intent(NewOfficerMainActivity.this, TabLoginActivity.class));
                    finish();
                    return true;
                } else if (menuItem.getItemId() == R.id.nav_ofctheme) {
                    showThemePicker();
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
//                    GlobalDeclaration.FARG_TAG = NewOfficerHomeFragment.class.getSimpleName();
//                    //default fragment
//                    selectedFragment = new NewOfficerHomeFragment();
//                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
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

        if (fargTag.equalsIgnoreCase("DaywiseFragment")) {
            selectedFragment = new DaywiseFragment();
        } else if (fargTag.equalsIgnoreCase("NewOfficerHomeFragment")) {
            selectedFragment = new NewOfficerHomeFragment();
        } else if (fargTag.equalsIgnoreCase("NewAllDistrictsFragment")) {
            selectedFragment = new NewAllDistrictsFragment();
        } else if (fargTag.equalsIgnoreCase("PrivacyPolicyFragment")) {
            selectedFragment = new PrivacyPolicyFragment();
        } else if (fargTag.equalsIgnoreCase("AllMandalsFragment")) {
            selectedFragment = new AllMandalsFragment();
        } else if (fargTag.equalsIgnoreCase("AllVillageFragment")) {
            selectedFragment = new AllVillageFragment();
        } else if (fargTag.equalsIgnoreCase("GetDrilldownFragment")) {
            selectedFragment = new GetDrilldownFragment();

        } else if (fargTag.equalsIgnoreCase("NewTCFragment")) {
            selectedFragment = new NewTCFragment();
        } else {
            GlobalDeclaration.FARG_TAG = NewOfficerHomeFragment.class.getSimpleName();
            selectedFragment = new NewOfficerHomeFragment();
        }

    }

    //call this method for selection of themes in menu
    private void showThemePicker() {
        selectedThemeColor = -1;
        final Dialog dialog = new Dialog(NewOfficerMainActivity.this);
        dialog.setTitle("Select Theme Color");
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

//        iv_color1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                selectedThemeColor = R.color.redcroosbg_1;
//                if (selectedThemeColor != -1) {
//                    layout_main.setBackgroundResource(selectedThemeColor);
//                    //  navigationView.setBackground(getResources().getDrawable(R.drawable.redcross1_bg));
//                    toolbar.setBackgroundColor(getResources().getColor(selectedThemeColor));
//                    ll_nav_header.setBackgroundResource(R.drawable.redcross1_bg);
//                    dialog.dismiss();
//                    storesInsharedPref(selectedThemeColor);
//                    setFragment(GlobalDeclaration.FARG_TAG);
//                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
//            }
//        });
//        iv_color2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                selectedThemeColor = R.color.redcroosbg_2;
//                if (selectedThemeColor != -1) {
//                    //  int[] colorsIds = getResources().getIntArray(R.array.theme_colors_id);
//                    layout_main.setBackgroundColor(selectedThemeColor);
//                    ll_nav_header.setBackgroundResource(R.drawable.redcross2_bg);
//                    // navigationView.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
//                    toolbar.setBackgroundColor(getResources().getColor(selectedThemeColor));
//                    dialog.dismiss();
//                    storesInsharedPref(selectedThemeColor);
//                    setFragment(GlobalDeclaration.FARG_TAG);
//                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
//            }
//        });
//        iv_color3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                selectedThemeColor = R.color.redcroosbg_3;
//                if (selectedThemeColor != -1) {
//                    layout_main.setBackgroundResource(selectedThemeColor);
//                    ll_nav_header.setBackgroundResource(R.drawable.redcross3_bg);
//                    //  navigationView.setBackground(getResources().getDrawable(R.drawable.redcross3_bg));
//                    toolbar.setBackgroundColor(getResources().getColor(selectedThemeColor));
//                    dialog.dismiss();
//                    storesInsharedPref(selectedThemeColor);
//                    setFragment(GlobalDeclaration.FARG_TAG);
//                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
//            }
//        });
//        iv_color4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                selectedThemeColor = R.color.redcroosbg_4;
//                if (selectedThemeColor != -1) {
//                    layout_main.setBackgroundResource(selectedThemeColor);
//                    ll_nav_header.setBackgroundResource(R.drawable.redcross4_bg);
//                    // navigationView.setBackground(getResources().getDrawable(R.drawable.redcross4_bg));
//                    toolbar.setBackgroundColor(getResources().getColor(R.color.redcroosbg_4));
//                    dialog.dismiss();
//                    storesInsharedPref(selectedThemeColor);
//                    setFragment(GlobalDeclaration.FARG_TAG);
//                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
//            }
//        });
//        iv_color5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                selectedThemeColor = R.color.redcroosbg_5;
//                if (selectedThemeColor != -1) {
//                    layout_main.setBackgroundResource(selectedThemeColor);
//                    ll_nav_header.setBackgroundResource(R.drawable.redcross5_bg);
//                    // navigationView.setBackground(getResources().getDrawable(R.drawable.redcross5_bg));
//                    toolbar.setBackgroundColor(getResources().getColor(selectedThemeColor));
//                    dialog.dismiss();
//                    storesInsharedPref(selectedThemeColor);
//                    setFragment(GlobalDeclaration.FARG_TAG);
//                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
//            }
//        });
//        iv_color6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                selectedThemeColor = R.color.redcroosbg_6;
//                if (selectedThemeColor != -1) {
//                    layout_main.setBackgroundResource(selectedThemeColor);
//                    ll_nav_header.setBackgroundResource(R.drawable.redcross6_bg);
//                    //   navigationView.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
//                    toolbar.setBackgroundColor(getResources().getColor(selectedThemeColor));
//                    dialog.dismiss();
//                    storesInsharedPref(selectedThemeColor);
//                    setFragment(GlobalDeclaration.FARG_TAG);
//                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
//            }
//        });
//        iv_color7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                selectedThemeColor = R.color.redcroosbg_7;
//                if (selectedThemeColor != -1) {
//                    layout_main.setBackgroundResource(selectedThemeColor);
//                    ll_nav_header.setBackgroundResource(R.drawable.redcross7_bg);
//                    //  navigationView.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
//                    toolbar.setBackgroundColor(getResources().getColor(selectedThemeColor));
//                    dialog.dismiss();
//                    storesInsharedPref(selectedThemeColor);
//                    setFragment(GlobalDeclaration.FARG_TAG);
//                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
//            }
//        });
//        iv_color8.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                selectedThemeColor = R.color.redcroosbg_8;
//                if (selectedThemeColor != -1) {
//                    layout_main.setBackgroundResource(selectedThemeColor);
//                    ll_nav_header.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
//                    //  navigationView.setBackground(getResources().getDrawable(R.drawable.redcross_splashscreen_bg));
//                    toolbar.setBackgroundColor(getResources().getColor(selectedThemeColor));
//                    dialog.dismiss();
//                    storesInsharedPref(selectedThemeColor);
//                    setFragment(GlobalDeclaration.FARG_TAG);
//                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
//            }
//        });
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

        final PrettyDialog dialog = new PrettyDialog(this);
        dialog
                .setTitle("Red cross")
                .setMessage("Do you want to Logout?")
                .setIcon(R.drawable.pdlg_icon_info, R.color.pdlg_color_blue, null)
                .addButton("OK", R.color.pdlg_color_white, R.color.pdlg_color_green, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        dialog.dismiss();
                        startActivity(new Intent(NewOfficerMainActivity.this, TabLoginActivity.class));
                        finish();
                    }
                })
                .addButton("Cancel", R.color.pdlg_color_white, R.color.pdlg_color_red, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        dialog.dismiss();
                        // Toast.makeText(OfficerMainActivity.this, "Cancel selected", Toast.LENGTH_SHORT).show();
                    }
                });

        dialog.show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_officer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public void callDistrcitFragment(String type) {
        GlobalDeclaration.FARG_TAG = NewOfficerHomeFragment.class.getSimpleName();
        selectedFragment = new NewOfficerHomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("D_TYPE", type);
        selectedFragment.setArguments(bundle);
        setFragment(GlobalDeclaration.FARG_TAG);
        callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
    }
}


