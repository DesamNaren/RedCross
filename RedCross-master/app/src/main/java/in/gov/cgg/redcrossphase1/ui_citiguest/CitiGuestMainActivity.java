package in.gov.cgg.redcrossphase1.ui_citiguest;

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
import android.widget.Spinner;
import android.widget.TabHost;

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

import com.google.android.material.navigation.NavigationView;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.TabLoginActivity;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.CitiNewTCFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.CitiPrivacyPolicyFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.ContactusFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.HistoryFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.LocateBloodbanksFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.MissionFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.SevenFundamentalFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.VisionFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.WhoWeAreFragment;
import in.gov.cgg.redcrossphase1.ui_officer.home_distrcit.OfficerHomeFragment;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class CitiGuestMainActivity extends AppCompatActivity {

    private static final float END_SCALE = 0.7f;
    Spinner spinYear;
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private View contentView;
    private TabHost tabHost;
    private int pos;
    View header;
    int selectedThemeColor = -1;
    SharedPreferences settings;
    private Fragment selectedFragment;
    SharedPreferences.Editor editor;
    LinearLayout ll_sevenPrinciples;
    private LinearLayout ll_nav_header;
    private ImageView iv_color1_selected, iv_color2_selected, iv_color3_selected, iv_color4_selected, iv_color5_selected, iv_color6_selected, iv_color7_selected, iv_color8_selected;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizendashboard);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        contentView = findViewById(R.id.content);
        header = navigationView.getHeaderView(0);
        ll_nav_header = header.findViewById(R.id.ll_dashboard_header);
        Menu nav_Menu = navigationView.getMenu();



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_History, R.id.nav_Principles, R.id.nav_whoweare, R.id.nav_Vision, R.id.nav_mission
                , R.id.nav_Structure, R.id.nav_Contact, R.id.nav_lifetimemember, R.id.nav_blooddonorreg, R.id.nav_hnreg,
                R.id.nav_onlinedonations, R.id.nav_training, R.id.nav_hn, R.id.nav_doateblood, R.id.nav_locatebloodbanks
                , R.id.nav_privacy, R.id.nav_tc)
                .setDrawerLayout(drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        GlobalDeclaration.FARG_TAG = SevenFundamentalFragment.class.getSimpleName();
        //default fragment
        selectedFragment = new SevenFundamentalFragment();
        callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.nav_Principles) {
                    GlobalDeclaration.FARG_TAG = SevenFundamentalFragment.class.getSimpleName();
                    selectedFragment = new SevenFundamentalFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_whoweare) {
                    GlobalDeclaration.FARG_TAG = WhoWeAreFragment.class.getSimpleName();
                    selectedFragment = new WhoWeAreFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_History) {
                    GlobalDeclaration.FARG_TAG = HistoryFragment.class.getSimpleName();
                    selectedFragment = new HistoryFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_Vision) {
                    GlobalDeclaration.FARG_TAG = VisionFragment.class.getSimpleName();
                    selectedFragment = new VisionFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_mission) {
                    GlobalDeclaration.FARG_TAG = MissionFragment.class.getSimpleName();
                    selectedFragment = new MissionFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_Contact) {
                    GlobalDeclaration.FARG_TAG = ContactusFragment.class.getSimpleName();
                    selectedFragment = new ContactusFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_lifetimemember) {
                    showChangeLangDialog();
                } else if (menuItem.getItemId() == R.id.nav_locatebloodbanks) {
                    GlobalDeclaration.FARG_TAG = LocateBloodbanksFragment.class.getSimpleName();
                    selectedFragment = new LocateBloodbanksFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_blooddonorreg) {
                    showChangeLangDialog();
                } else if (menuItem.getItemId() == R.id.nav_hn) {
                    showChangeLangDialog();
                } else if (menuItem.getItemId() == R.id.nav_onlinedonations) {
                    showChangeLangDialog();
                } else if (menuItem.getItemId() == R.id.nav_training) {
                    showChangeLangDialog();
                } else if (menuItem.getItemId() == R.id.nav_hnreg) {
                    showChangeLangDialog();
                } else if (menuItem.getItemId() == R.id.nav_doateblood) {
                    showChangeLangDialog();
                } else if (menuItem.getItemId() == R.id.nav_privacy) {
                    GlobalDeclaration.FARG_TAG = CitiPrivacyPolicyFragment.class.getSimpleName();
                    selectedFragment = new CitiPrivacyPolicyFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_tc) {
                    GlobalDeclaration.FARG_TAG = CitiNewTCFragment.class.getSimpleName();
                    selectedFragment = new CitiNewTCFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_continueasGuestthemes) {
                    showThemePicker();
                }


                return true;
            }
        });
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {


                if (destination.getId() == R.id.nav_onlinedonations) {
                    showChangeLangDialog();
                } else if (destination.getId() == R.id.nav_training) {
                    showChangeLangDialog();
                } else if (destination.getId() == R.id.nav_lifetimemember) {
                    showChangeLangDialog();
                } else if (destination.getId() == R.id.nav_hnreg) {
                    showChangeLangDialog();
                } else if (destination.getId() == R.id.nav_blooddonorreg) {
                    showChangeLangDialog();
                } else if (destination.getId() == R.id.nav_hn) {
                    showChangeLangDialog();
                } else if (destination.getId() == R.id.nav_doateblood) {
                    showChangeLangDialog();
                }



            }
        });


        //Adding animation to drawer

        drawerLayout.setScrimColor(Color.TRANSPARENT);

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


    }

    void callFragment(Fragment fragment, String name) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment, name);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    private void showThemePicker() {
        selectedThemeColor = -1;
        final Dialog dialog = new Dialog(CitiGuestMainActivity.this);
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
                    //  ll.setBackgroundResource(selectedThemeColor);
                    navigationView.setBackground(getResources().getDrawable(R.drawable.redcross1_bg));
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
                    //  layout_main.setBackgroundColor(selectedThemeColor);
                    ll_nav_header.setBackgroundResource(R.drawable.redcross2_bg);
                    navigationView.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
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
                    //   layout_main.setBackgroundResource(selectedThemeColor);
                    ll_nav_header.setBackgroundResource(R.drawable.redcross3_bg);
                    navigationView.setBackground(getResources().getDrawable(R.drawable.redcross3_bg));
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
                    //    layout_main.setBackgroundResource(selectedThemeColor);
                    ll_nav_header.setBackgroundResource(R.drawable.redcross4_bg);
                    navigationView.setBackground(getResources().getDrawable(R.drawable.redcross4_bg));
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
                    //  layout_main.setBackgroundResource(selectedThemeColor);
                    ll_nav_header.setBackgroundResource(R.drawable.redcross5_bg);
                    navigationView.setBackground(getResources().getDrawable(R.drawable.redcross5_bg));
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
                    //  layout_main.setBackgroundResource(selectedThemeColor);
                    ll_nav_header.setBackgroundResource(R.drawable.redcross6_bg);
                    navigationView.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
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
                    //  layout_main.setBackgroundResource(selectedThemeColor);
                    ll_nav_header.setBackgroundResource(R.drawable.redcross7_bg);
                    navigationView.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
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
                    //  layout_main.setBackgroundResource(selectedThemeColor);
                    ll_nav_header.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
                    navigationView.setBackground(getResources().getDrawable(R.drawable.redcross_splashscreen_bg));
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

    private void sharedPreferenceMethod(int selectedThemeColor) {
        settings = getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE);
        editor = settings.edit();
        editor.putInt("theme_color", selectedThemeColor);
        editor.commit();
    }

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

    private void setFragment(String fargTag) {

        if (fargTag.equalsIgnoreCase("SevenFundamentalFragment")) {
            selectedFragment = new SevenFundamentalFragment();
        } else if (fargTag.equalsIgnoreCase("WhoWeAreFragment")) {
            selectedFragment = new WhoWeAreFragment();
        } else if (fargTag.equalsIgnoreCase("HistoryFragment")) {
            selectedFragment = new HistoryFragment();
        } else if (fargTag.equalsIgnoreCase("VisionFragment")) {
            selectedFragment = new VisionFragment();
        } else if (fargTag.equalsIgnoreCase("MissionFragment")) {
            selectedFragment = new MissionFragment();
        } else if (fargTag.equalsIgnoreCase("ContactusFragment")) {
            selectedFragment = new ContactusFragment();
        } else if (fargTag.equalsIgnoreCase("LocateBloodbanksFragment")) {
            selectedFragment = new LocateBloodbanksFragment();
        } else if (fargTag.equalsIgnoreCase("CitiNewTCFragment")) {
            selectedFragment = new CitiNewTCFragment();
        } else if (fargTag.equalsIgnoreCase("CitiPrivacyPolicyFragment")) {
            selectedFragment = new CitiPrivacyPolicyFragment();
        } else {
            GlobalDeclaration.FARG_TAG = SevenFundamentalFragment.class.getSimpleName();
            selectedFragment = new SevenFundamentalFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_backpress, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.logout:
                // search action
                onClickExit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void showChangeLangDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CitiGuestMainActivity.this);

        alertDialogBuilder.setMessage("Availed soon");
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = alertDialogBuilder.create();

        // show alert
        alertDialog.show();
    }

    //    private void sexitDailouge() {
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CitiGuestMainActivity.this);
//
//        alertDialogBuilder.setMessage("Do you want to exit ?");
//        // set positive button: Yes message
//        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.dismiss();
//            }
//        });
//        final AlertDialog alertDialog = alertDialogBuilder.create();
//
//        // show alert
//        alertDialog.show();
//    }
    public void onClickExit() {
        final PrettyDialog dialog = new PrettyDialog(this);
        dialog
                .setTitle("")
                .setMessage("Do you want to Logout?")
                .setIcon(R.drawable.pdlg_icon_info, R.color.pdlg_color_blue, null)
                .addButton("OK", R.color.pdlg_color_white, R.color.pdlg_color_green, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        dialog.dismiss();
                        startActivity(new Intent(CitiGuestMainActivity.this, TabLoginActivity.class));
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
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
