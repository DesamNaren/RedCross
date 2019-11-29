package in.gov.cgg.redcrossphase1.ui_citiguest;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.TabLoginActivity;

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
    private Fragment selectedFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizendashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        contentView = findViewById(R.id.content);
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


                /*menu.findItem(R.id.logout).setIcon(R.drawable.ic_home_white_48dp);
                menu.findItem(R.id.logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        startActivity(new Intent(OfficerMainActivity.this, OfficerMainActivity.class));
                        return true;
                    }
                });*/

            }
        });


        //Adding animation to drawer

        drawerLayout.setScrimColor(Color.TRANSPARENT);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CitiGuestMainActivity.this);
        final AlertDialog alertDialog1 = alertDialog.create();


        alertDialog.setMessage("Do you want to exit ?");
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


                startActivity(new Intent(CitiGuestMainActivity.this, TabLoginActivity.class));
                finish();
            }
        });
        alertDialog.show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
