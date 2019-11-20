package in.gov.cgg.redcrossphase1.ui_officer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.TabLoginActivity;

public class OfficerMainActivity extends AppCompatActivity {


    public static final String MyPREFERENCES = "MyPrefs";
    private static final float END_SCALE = 0.7f;
    Spinner spinYear;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private View contentView;
    private TabHost tabHost;
    private AppBarConfiguration mAppBarConfiguration;
    private int pos;
    private TextView tv_name;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officer_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
//        tv_name = findViewById(R.id.textView_name);

        View headerView = navigationView.getHeaderView(0);
        TextView tv_name = headerView.findViewById(R.id.textView_name);

        contentView = findViewById(R.id.content);

        setSupportActionBar(toolbar);


        if (GlobalDeclaration.role != null) {
            if (GlobalDeclaration.role.contains("G")) {
                navigationView = findViewById(R.id.nav_view);
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_govtpvt).setVisible(true);
                drawerLayout.setScrimColor(Color.TRANSPARENT);
            } else {
                navigationView = findViewById(R.id.nav_view);
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_govtpvt).setVisible(false);
                drawerLayout.setScrimColor(Color.TRANSPARENT);
            }
        }


        // actionBarDrawerToggle.syncState();

//
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerLayout.setDrawerListener(toggle);
////        drawer.addDrawerListener(toggle);
//        toggle.syncState();

        //navigationView.setNavigationItemSelectedListener(this);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_age, R.id.nav_gender, R.id.nav_district,
                R.id.nav_blood)
                .setDrawerLayout(drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_officer);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

//        navigationView.setCheckedItem(R.id.home);
//        Fragment fragment = new OfficerHomeFragment();
//        displaySelectedFragment(fragment);

//         toolbar.setTitle("Home");
//        toolbar.setNavigationIcon(new DrawerArrowDrawable(this));
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                                                 @Override
//                                                 public void onClick(View v) {
//                                                     if (drawerLayout.isDrawerOpen(navigationView)) {
//                                                         drawerLayout.closeDrawer(navigationView);
//                                                     } else {
//                                                         drawerLayout.openDrawer(navigationView);
//                                                     }
//                                                 }
//                                             }
//        );
        if (GlobalDeclaration.username != null) {
            if (GlobalDeclaration.role.contains("D")) {
                tv_name.setText("Welcome to Chairman" + "\n" + GlobalDeclaration.username);
            } else if (GlobalDeclaration.role.contains("S")) {
                tv_name.setText("Welcome to Secretary" + "\n" + GlobalDeclaration.username);

            } else if (GlobalDeclaration.role.contains("G")) {
                tv_name.setText("Welcome to Governer");
            } else {

            }
        }


        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
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
                                       }
        );


    }

//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//        Fragment fragment = null;
//        if (CheckInternet.isOnline(OfficerMainActivity.this)) {
//            if (id == R.id.nav_home) {
//                fragment = new OfficerHomeFragment();
//                displaySelectedFragment(fragment);
//            } else if (id == R.id.nav_agewise) {
//                fragment = new AgewiseFragment();
//                displaySelectedFragment(fragment);
//            }else if (id == R.id.nav_bloodwise) {
//                fragment = new BloodwiseFragment();
//                displaySelectedFragment(fragment);
//            }else if (id == R.id.nav_genderwise) {
//                fragment = new GovtPvtFragment();
//                displaySelectedFragment(fragment);
//            }
//        } else {
//            Toast.makeText(OfficerMainActivity.this, "Please Check Internet Connection", Toast.LENGTH_LONG).show();
//
//        }
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//
//    private void displaySelectedFragment(Fragment fragment) {
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.frame, fragment);
//        fragmentTransaction.commit();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_backpress, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(OfficerMainActivity.this);
        final AlertDialog alertDialog1 = alertDialog.create();


        alertDialog.setMessage("Do you want to logout?");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(OfficerMainActivity.this, TabLoginActivity.class));
                finish();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //alertDialog1.dismiss();
                alertDialog1.dismiss();
            }
        });

        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
//                fragment = new ViewOrderFragment();
//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.frame, fragment);
//                fragmentTransaction.commit();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(OfficerMainActivity.this);
                final AlertDialog alertDialog1 = alertDialog.create();


                alertDialog.setMessage("Do you want to logout and clear login?");
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

                        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("un", "");
                        editor.putString("pw", "");
                        editor.putBoolean("is", false);
                        editor.commit();
                        startActivity(new Intent(OfficerMainActivity.this, TabLoginActivity.class));
                        finish();
                    }
                });

                alertDialog.setNeutralButton("JUST LOGOUT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //alertDialog1.dismiss();
                        startActivity(new Intent(OfficerMainActivity.this, TabLoginActivity.class));
                        finish();
                    }
                });

                alertDialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_officer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}

