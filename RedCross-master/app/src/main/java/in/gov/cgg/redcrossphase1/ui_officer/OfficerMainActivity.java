package in.gov.cgg.redcrossphase1.ui_officer;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.TabLoginActivity;
import in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport.AllDistrictsFragment;
import in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport.AllMandalsFragment;
import in.gov.cgg.redcrossphase1.ui_officer.daywisereportcont.DaywiseFragment;
import in.gov.cgg.redcrossphase1.ui_officer.home_distrcit.OfficerHomeFragment;

public class OfficerMainActivity extends AppCompatActivity {


    public static final String MyPREFERENCES = "MyPrefs";
    private static final float END_SCALE = 0.7f;
    Spinner spinYear;
    private View contentView;
    private TabHost tabHost;
    private AppBarConfiguration mAppBarConfiguration;
    private int pos;
    private TextView tv_name;
    FloatingActionButton fabmain;
    private Menu mMenu;
    private NavController navController;
    int selectedThemeColor = -1;
    NavigationView navigationView;
    Toolbar toolbar;
    View header;
    RelativeLayout layout_main;
    DrawerLayout drawerLayout;
    private LinearLayout ll_nav_header;
    private Fragment selectedFragment;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    private ImageView iv_color1_selected, iv_color2_selected, iv_color3_selected, iv_color4_selected, iv_color5_selected, iv_color6_selected, iv_color7_selected, iv_color8_selected;

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

        setSupportActionBar(toolbar);
        Menu nav_Menu = navigationView.getMenu();
        GlobalDeclaration.FARG_TAG = OfficerHomeFragment.class.getSimpleName();
        //default fragment
        selectedFragment = new OfficerHomeFragment();
        callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);


        try {
            selectedThemeColor = this.getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                toolbar.setBackgroundResource(selectedThemeColor);
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    ll_nav_header.setBackgroundResource(R.drawable.redcross1_bg);
                    navigationView.setBackgroundResource(R.drawable.redcross1_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    ll_nav_header.setBackgroundResource(R.drawable.redcross2_bg);
                    navigationView.setBackgroundResource(R.drawable.redcross2_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    ll_nav_header.setBackgroundResource(R.drawable.redcross3_bg);
                    navigationView.setBackgroundResource(R.drawable.redcross3_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    ll_nav_header.setBackgroundResource(R.drawable.redcross4_bg);
                    navigationView.setBackgroundResource(R.drawable.redcross4_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    ll_nav_header.setBackgroundResource(R.drawable.redcross5_bg);
                    navigationView.setBackgroundResource(R.drawable.redcross5_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    ll_nav_header.setBackgroundResource(R.drawable.redcross6_bg);
                    navigationView.setBackgroundResource(R.drawable.redcross6_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    ll_nav_header.setBackgroundResource(R.drawable.redcross7_bg);
                    navigationView.setBackgroundResource(R.drawable.redcross7_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    ll_nav_header.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
                    navigationView.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
                } else {
                    ll_nav_header.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
                    navigationView.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
                    selectedThemeColor = R.color.colorPrimary;
                    toolbar.setBackgroundResource(selectedThemeColor);
                    sharedPreferenceMethod(selectedThemeColor);
                }
            } else {
                ll_nav_header.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
                navigationView.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
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
            tv_name.setText("Welcome to" + GlobalDeclaration.designation + "\n" + GlobalDeclaration.username);
        } else {
            tv_name.setText("Welcome to" + GlobalDeclaration.username);

        }
     /*   drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                                           @Override
                                           public void onDrawerSlide(View drawerView, float slideOffset) {
                                               //labelView.setVisibility(slideOffset > 0 ? View.VISIBLE : View.GONE);

                                               // Scale the View based on current slide offset
                                               final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                                               final float offsetScale = 1 - diffScaledOffset;
                                               contentView.setScaleX(offsetScale);
                                               contentView.setScaleY(offsetScale);

                                               // Translate the View, accounting for the scaled width
                                               final float xOffset = drawerView.getWidth() * slideOffset;
                                               final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                                               final float xTranslation = xOffset - xOffsetDiff;
                                               contentView.setTranslationX(xTranslation);
                                           }

                                           @Override
                                           public void onDrawerClosed(View drawerView) {
//                                               labelView.setVisibility(View.GONE);
                                           }
                                       }*/
        // );
    }

    private void sharedPreferenceMethod(int selectedThemeColor) {
        settings = getSharedPreferences("THEMECOLOR_PREF", Activity.MODE_PRIVATE);
        editor = settings.edit();
        editor.putInt("theme_color", selectedThemeColor);
        editor.commit();
    }

    // creating sharred preferences
    public void storesInsharedPref(int selectedThemeColor) {
        sharedPreferenceMethod(selectedThemeColor);

        // CustomRelativeLayout.changeStatusBarColor(this);
        //refersh of same fragment
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_officer);
        if (currentFragment instanceof OfficerHomeFragment) {
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
        getMenuInflater().inflate(R.menu.activity_backpress, menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                if (menuItem.getItemId() == R.id.nav_ofctheme) {
//                    showThemePicker();
//
//                }else
                if (menuItem.getItemId() == R.id.nav_daywise) {
                    menu.findItem(R.id.logout).setIcon(R.drawable.ic_home_white_48dp);
                    GlobalDeclaration.FARG_TAG = DaywiseFragment.class.getSimpleName();
                    selectedFragment = new DaywiseFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            startActivity(new Intent(OfficerMainActivity.this, OfficerMainActivity.class));
                            return true;
                        }
                    });
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_district) {
                    menu.findItem(R.id.logout).setIcon(R.drawable.ic_power_settings_new_black_24dp);
                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            callHomeAlert();
                            return true;
                        }
                    });
                    GlobalDeclaration.FARG_TAG = OfficerHomeFragment.class.getSimpleName();
                    selectedFragment = new OfficerHomeFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_alldistricts) {
                    menu.findItem(R.id.logout).setIcon(R.drawable.ic_home_white_48dp);
                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            startActivity(new Intent(OfficerMainActivity.this, OfficerMainActivity.class));
                            return true;
                        }
                    });
                    GlobalDeclaration.FARG_TAG = AllDistrictsFragment.class.getSimpleName();
                    selectedFragment = new AllDistrictsFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_tc) {
                    menu.findItem(R.id.logout).setIcon(R.drawable.ic_home_white_48dp);
                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            startActivity(new Intent(OfficerMainActivity.this, OfficerMainActivity.class));
                            return true;
                        }
                    });
                    GlobalDeclaration.FARG_TAG = TCFragment.class.getSimpleName();
                    selectedFragment = new TCFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_privacy) {
                    menu.findItem(R.id.logout).setIcon(R.drawable.ic_home_white_48dp);
                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            startActivity(new Intent(OfficerMainActivity.this, OfficerMainActivity.class));
                            return true;
                        }
                    });
                    GlobalDeclaration.FARG_TAG = PrivacyPolicyFragment.class.getSimpleName();
                    selectedFragment = new PrivacyPolicyFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_allmandals) {
                    menu.findItem(R.id.logout).setIcon(R.drawable.ic_home_white_48dp);
                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            startActivity(new Intent(OfficerMainActivity.this, OfficerMainActivity.class));
                            return true;
                        }
                    });
                    GlobalDeclaration.FARG_TAG = AllMandalsFragment.class.getSimpleName();
                    selectedFragment = new AllMandalsFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
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
//                    GlobalDeclaration.FARG_TAG = OfficerHomeFragment.class.getSimpleName();
//                    //default fragment
//                    selectedFragment = new OfficerHomeFragment();
//                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            startActivity(new Intent(OfficerMainActivity.this, OfficerMainActivity.class));
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
                } else if (destination.getId() == R.id.nav_alldistricts) {
                    menu.findItem(R.id.logout).setIcon(R.drawable.ic_home_white_48dp);
                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            startActivity(new Intent(OfficerMainActivity.this, OfficerMainActivity.class));
                            return true;
                        }
                    });
                } else if (destination.getId() == R.id.nav_tc) {
                    menu.findItem(R.id.logout).setIcon(R.drawable.ic_home_white_48dp);
                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            startActivity(new Intent(OfficerMainActivity.this, OfficerMainActivity.class));
                            return true;
                        }
                    });
                } else if (destination.getId() == R.id.nav_privacy) {
                    menu.findItem(R.id.logout).setIcon(R.drawable.ic_home_white_48dp);
                    menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            startActivity(new Intent(OfficerMainActivity.this, OfficerMainActivity.class));
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

    //call this method for selection of themes in menu
    private void showThemePicker() {
        selectedThemeColor = -1;
        final Dialog dialog = new Dialog(OfficerMainActivity.this);
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

        iv_color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedThemeColor = R.color.redcroosbg_1;
                if (selectedThemeColor != -1) {
                    layout_main.setBackgroundResource(selectedThemeColor);
                    navigationView.setBackground(getResources().getDrawable(R.drawable.redcross1_bg));
                    toolbar.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    ll_nav_header.setBackgroundResource(R.drawable.redcross1_bg);
                    dialog.dismiss();
                    storesInsharedPref(selectedThemeColor);


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
                    navigationView.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
                    toolbar.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    dialog.dismiss();
                    storesInsharedPref(selectedThemeColor);

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
                    navigationView.setBackground(getResources().getDrawable(R.drawable.redcross3_bg));
                    toolbar.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    dialog.dismiss();
                    storesInsharedPref(selectedThemeColor);

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
                    navigationView.setBackground(getResources().getDrawable(R.drawable.redcross4_bg));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.redcroosbg_4));
                    dialog.dismiss();
                    storesInsharedPref(selectedThemeColor);

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
                    navigationView.setBackground(getResources().getDrawable(R.drawable.redcross5_bg));
                    toolbar.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    dialog.dismiss();
                    storesInsharedPref(selectedThemeColor);

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
                    navigationView.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                    toolbar.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    dialog.dismiss();
                    storesInsharedPref(selectedThemeColor);

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
                    navigationView.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
                    toolbar.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    dialog.dismiss();
                    storesInsharedPref(selectedThemeColor);

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
                    navigationView.setBackground(getResources().getDrawable(R.drawable.redcross_splashscreen_bg));
                    toolbar.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    dialog.dismiss();
                    storesInsharedPref(selectedThemeColor);

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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(OfficerMainActivity.this);
        final AlertDialog alertDialog1 = alertDialog.create();


        alertDialog.setMessage("Do you want to logout ?");
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //alertDialog1.dismiss();
                alertDialog1.dismiss();
            }
        });
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

//                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//                editor.putString("un", "");
//                editor.putString("pw", "");
//                editor.putBoolean("is", false);
//                editor.apply();
                startActivity(new Intent(OfficerMainActivity.this, TabLoginActivity.class));
                finish();
            }
        });

//        alertDialog.setNeutralButton("JUST LOGOUT", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                //alertDialog1.dismiss();
//                startActivity(new Intent(OfficerMainActivity.this, TabLoginActivity.class));
//                finish();
//            }
//        });

        alertDialog.show();
    }


    private void callAllAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OfficerMainActivity.this);

        alertDialogBuilder.setTitle("");
        alertDialogBuilder.setMessage("Do you want to?");
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // go to a new activity of the app
                Intent positveActivity = new Intent(OfficerMainActivity.this,
                        TabLoginActivity.class);
                startActivity(positveActivity);
                finish();
            }
        });
        // set negative button: No message
        alertDialogBuilder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // cancel the alert box and put a Toast to the user
//                        dialog.cancel();
////                        Toast.makeText(getApplicationContext(), "You chose a negative answer",
////                                Toast.LENGTH_LONG).show();
                finishAffinity();
            }
        });
        // set neutral button: Exit the app message
        alertDialogBuilder.setNeutralButton("Go Home", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // exit the app and go to the HOME
                dialog.dismiss();
                Intent positveActivity = new Intent(getApplicationContext(),
                        OfficerMainActivity.class);
                startActivity(positveActivity);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();

    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_officer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}

