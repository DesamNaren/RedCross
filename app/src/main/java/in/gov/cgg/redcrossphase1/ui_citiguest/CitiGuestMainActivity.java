package in.gov.cgg.redcrossphase1.ui_citiguest;


import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.TabLoginActivity;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.AbstractMembership;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.BloodDonorInstructionFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.BlooddonorRegistrationFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.CapacityBuildingsFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.CitiNewTCFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.CitizendashboardFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.DonateBloodMainFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.DonateMoneyFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.DownloadCertificate;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.HomeNurseInfoFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.HomeNurseRequest;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.HomeNursingRegistrationFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.HomenursingFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.MemberFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.MembershipFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.MembershipRegFormFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.MyDonationsFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.MyMembershipFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.NewBBInfoFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.NewBecomeDonorInfoFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.NewContactusFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.RequestNurseInfoFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.ServicesFragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.Upcoming_fragment;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.WhoWeAreFragment;
import in.gov.cgg.redcrossphase1.ui_officer.fragments.NewOfficerHomeFragment;
import in.gov.cgg.redcrossphase1.ui_officer.fragments.PrivacyPolicyFragment;

public class CitiGuestMainActivity extends LocBaseActivity {
    private static final float END_SCALE = 0.7f;
    private final BroadcastReceiver mGpsSwitchStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
                    callPermissions();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    Spinner spinYear;
    View header;
    int selectedThemeColor = -1;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    TextView tv_name1, tv_name2;
    CircleImageView imageView_user;
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private View contentView;
    private TabHost tabHost;
    private int pos;
    private Fragment selectedFragment;
    private LinearLayout ll_nav_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        setContentView(R.layout.activity_citizendashboard);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        contentView = findViewById(R.id.content);
        header = navigationView.getHeaderView(0);
        ll_nav_header = header.findViewById(R.id.ll_dashboard_header);
        tv_name1 = header.findViewById(R.id.tv_name1);
        tv_name2 = header.findViewById(R.id.tv_name2);
        imageView_user = header.findViewById(R.id.imageView_user);
        Menu nav_Menu = navigationView.getMenu();


        if (GlobalDeclaration.guest != null) {
            if (GlobalDeclaration.guest.equalsIgnoreCase("y")) {
                nav_Menu.findItem(R.id.nav_logout).setVisible(false);
                nav_Menu.findItem(R.id.nav_mydonations).setVisible(false);
                nav_Menu.findItem(R.id.nav_mymembreship).setVisible(false);
            } else {
                nav_Menu.findItem(R.id.nav_logout).setVisible(true);
                nav_Menu.findItem(R.id.nav_mydonations).setVisible(true);
                nav_Menu.findItem(R.id.nav_mymembreship).setVisible(true);

            }
        } else {
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);

        }

        try {

            if (GlobalDeclaration.guest != null) {
                if (GlobalDeclaration.guest.equalsIgnoreCase("y")) {
                    tv_name1.setText(getResources().getString(R.string.welcomeguest));
                    tv_name2.setText("");
                } else if (GlobalDeclaration.loginresponse != null) {
                    tv_name1.setText(GlobalDeclaration.loginresponse.getName());
                    tv_name2.setText(GlobalDeclaration.loginresponse.getEmailId());
                } else if (GlobalDeclaration.unamefromReg != null) {
                    tv_name1.setText(getResources().getString(R.string.welcome) + GlobalDeclaration.unamefromReg);
                    tv_name2.setText("");
                } else {
                    tv_name1.setText(getResources().getString(R.string.welcomeguest));
                    tv_name2.setText("");
                }
            } else if (GlobalDeclaration.loginresponse != null) {
                tv_name1.setText(GlobalDeclaration.loginresponse.getName());
                tv_name2.setText(GlobalDeclaration.loginresponse.getEmailId());
            } else if (GlobalDeclaration.unamefromReg != null) {
                tv_name1.setText(getResources().getString(R.string.welcome) + GlobalDeclaration.unamefromReg);
                tv_name2.setText("");
            } else {
                tv_name1.setText(getResources().getString(R.string.welcomeguest));
                tv_name2.setText("");

            }
        } catch (Exception e) {

        }

        if (!GlobalDeclaration.profilePic.isEmpty()) {
            Glide.with(CitiGuestMainActivity.this).load(GlobalDeclaration.profilePic).into(imageView_user);
        }


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_History, R.id.nav_Principles, R.id.nav_whoweare, R.id.nav_Vision, R.id.nav_mission
                , R.id.nav_Structure, R.id.nav_Contact, R.id.nav_lifetimemember, R.id.nav_blooddonorreg, R.id.nav_hnreg
                , R.id.nav_Structure, R.id.nav_Contact, R.id.nav_lifetimemember, R.id.nav_blooddonorreg, R.id.nav_hnreg,
                R.id.nav_onlinedonations, R.id.nav_training, R.id.nav_hn, R.id.nav_doateblood, R.id.nav_locatebloodbanks
                , R.id.nav_privacy, R.id.nav_tc, R.id.nav_info, R.id.nav_dashboard, R.id.nav_about)
                .setDrawerLayout(drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        GlobalDeclaration.FARG_TAG = CitizendashboardFragment.class.getSimpleName();
        //default fragment
        selectedFragment = new CitizendashboardFragment();
        callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.nav_Dashboard) {
                    GlobalDeclaration.FARG_TAG = CitizendashboardFragment.class.getSimpleName();
                    selectedFragment = new CitizendashboardFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_Principles) {
                    GlobalDeclaration.FARG_TAG = WhoWeAreFragment.class.getSimpleName();
                    selectedFragment = new WhoWeAreFragment();
                    GlobalDeclaration.tabposition = 5;
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_about) {
                    GlobalDeclaration.FARG_TAG = WhoWeAreFragment.class.getSimpleName();
                    selectedFragment = new WhoWeAreFragment();
                    GlobalDeclaration.tabposition = 1;
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_History) {
                    GlobalDeclaration.tabposition = 2;
                    GlobalDeclaration.FARG_TAG = WhoWeAreFragment.class.getSimpleName();
                    selectedFragment = new WhoWeAreFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_Vision) {
                    GlobalDeclaration.tabposition = 3;
                    GlobalDeclaration.FARG_TAG = WhoWeAreFragment.class.getSimpleName();
                    selectedFragment = new WhoWeAreFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_mission) {
                    GlobalDeclaration.tabposition = 4;
                    GlobalDeclaration.FARG_TAG = WhoWeAreFragment.class.getSimpleName();
                    selectedFragment = new WhoWeAreFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_abstract_member) {
                    GlobalDeclaration.FARG_TAG = AbstractMembership.class.getSimpleName();
                    selectedFragment = new AbstractMembership();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_how_to_become_a_member) {
                    GlobalDeclaration.FARG_TAG = MemberFragment.class.getSimpleName();
                    selectedFragment = new MemberFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_become_a_member) {
                    GlobalDeclaration.FARG_TAG = MembershipFragment.class.getSimpleName();
                    selectedFragment = new MembershipFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_download_certificate) {
                    GlobalDeclaration.FARG_TAG = DownloadCertificate.class.getSimpleName();
                    selectedFragment = new DownloadCertificate();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_current_trainings) {
                    GlobalDeclaration.FARG_TAG = CapacityBuildingsFragment.class.getSimpleName();
                    selectedFragment = new CapacityBuildingsFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_upcoming_trainings) {
                    GlobalDeclaration.FARG_TAG = Upcoming_fragment.class.getSimpleName();
                    selectedFragment = new Upcoming_fragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_blood_banks) {
                    GlobalDeclaration.FARG_TAG = NewBBInfoFragment.class.getSimpleName();
                    selectedFragment = new NewBBInfoFragment();
                    GlobalDeclaration.bloodposition = 0;
                    //callFragment(selectedFragment, GlobalDeclaration.FARG_TAG, "BLOOD_BANK");
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG, getResources().getString(R.string.blood_bank));

                    drawerLayout.closeDrawer(GravityCompat.START);

                } else if (menuItem.getItemId() == R.id.nav_blood_donors) {
                    GlobalDeclaration.FARG_TAG = NewBBInfoFragment.class.getSimpleName();
                    selectedFragment = new NewBBInfoFragment();
                    GlobalDeclaration.bloodposition = 1;
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG, getResources().getString(R.string.blood_donor));
                    drawerLayout.closeDrawer(GravityCompat.START);

                } else if (menuItem.getItemId() == R.id.nav_donor_registration) {
                    GlobalDeclaration.FARG_TAG = DonateBloodMainFragment.class.getSimpleName();
                    selectedFragment = new DonateBloodMainFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_home_nursing_request) {
                    GlobalDeclaration.FARG_TAG = HomeNurseRequest.class.getSimpleName();
                    selectedFragment = new HomeNurseRequest();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_home_nursing_become) {
                    GlobalDeclaration.FARG_TAG = HomeNurseInfoFragment.class.getSimpleName();
                    selectedFragment = new HomeNurseInfoFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_home_nursing_register) {
//                    GlobalDeclaration.FARG_TAG = HomeNursingRegistrationFragment.class.getSimpleName();
//                    selectedFragment = new HomeNursingRegistrationFragment();
//                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
//                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_locate_service) {
                    GlobalDeclaration.FARG_TAG = HomeNursingRegistrationFragment.class.getSimpleName();
                    Intent i = new Intent(CitiGuestMainActivity.this, LocateActivity.class);
                    startActivity(i);
                } else if (menuItem.getItemId() == R.id.nav_serv) {
                    GlobalDeclaration.FARG_TAG = ServicesFragment.class.getSimpleName();
                    selectedFragment = new ServicesFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_statecoordinates) {
                    GlobalDeclaration.FARG_TAG = NewContactusFragment.class.getSimpleName();
                    GlobalDeclaration.contactposition = 0;
                    selectedFragment = new NewContactusFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG, getResources().getString(R.string.from_state));
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_districtcontactus) {
                    GlobalDeclaration.FARG_TAG = NewContactusFragment.class.getSimpleName();
                    GlobalDeclaration.contactposition = 1;
                    selectedFragment = new NewContactusFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG, getResources().getString(R.string.from_dist));
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_info) {
                    GlobalDeclaration.FARG_TAG = InfoFragment.class.getSimpleName();
                    selectedFragment = new InfoFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_continueasGuestthemes) {
                    showThemePicker();
                } else if (menuItem.getItemId() == R.id.nav_privacy) {
                    GlobalDeclaration.FARG_TAG = PrivacyPolicyFragment.class.getSimpleName();
                    selectedFragment = new PrivacyPolicyFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);


                } else if (menuItem.getItemId() == R.id.nav_privacy) {
                    GlobalDeclaration.FARG_TAG = RequestNurseInfoFragment.class.getSimpleName();
                    selectedFragment = new RequestNurseInfoFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);


                } else if (menuItem.getItemId() == R.id.nav_tc) {
                    GlobalDeclaration.FARG_TAG = CitiNewTCFragment.class.getSimpleName();
                    selectedFragment = new CitiNewTCFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_onlinedonations) {
                    GlobalDeclaration.FARG_TAG = DonateMoneyFragment.class.getSimpleName();
                    selectedFragment = new DonateMoneyFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_mymembreship) {
                    GlobalDeclaration.FARG_TAG = MyMembershipFragment.class.getSimpleName();
                    selectedFragment = new MyMembershipFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_mydonations) {
                    GlobalDeclaration.FARG_TAG = MyDonationsFragment.class.getSimpleName();
                    selectedFragment = new MyDonationsFragment();
                    callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (menuItem.getItemId() == R.id.nav_logout) {
                    onClickExit();
                } else if (menuItem.getItemId() == R.id.nav_exit) {
                    exitHandler();
                } else if (menuItem.getItemId() == R.id.nav_rate_us) {
                    launchMarket();
                } else if (menuItem.getItemId() == R.id.nav_share) {
                    shareApp();
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
                    //   showChangeLangDialog();
                } else if (destination.getId() == R.id.nav_hn) {
                    showChangeLangDialog();
                } else if (destination.getId() == R.id.nav_doateblood) {
                    showChangeLangDialog();
                } else if (destination.getId() == R.id.nav_rate_us) {
                    showChangeLangDialog();
                } else if (destination.getId() == R.id.nav_share) {
                    showChangeLangDialog();
                }


            }
        });


        //Adding animation to drawer

        drawerLayout.setScrimColor(Color.TRANSPARENT);

        try {
            selectedThemeColor = this.getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF), MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);
            if (selectedThemeColor != -1) {
                toolbar.setBackgroundResource(selectedThemeColor);
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    ll_nav_header.setBackgroundResource(R.drawable.redcross1_bg);
                    //navigationView.setBackgroundResource(R.drawable.redcross1_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    ll_nav_header.setBackgroundResource(R.drawable.redcross2_bg);
                    //navigationView.setBackgroundResource(R.drawable.redcross2_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    ll_nav_header.setBackgroundResource(R.drawable.redcross3_bg);
                    //navigationView.setBackgroundResource(R.drawable.redcross3_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    ll_nav_header.setBackgroundResource(R.drawable.redcross4_bg);
                    //navigationView.setBackgroundResource(R.drawable.redcross4_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    ll_nav_header.setBackgroundResource(R.drawable.redcross5_bg);
                    //navigationView.setBackgroundResource(R.drawable.redcross5_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    ll_nav_header.setBackgroundResource(R.drawable.redcross6_bg);
                    //navigationView.setBackgroundResource(R.drawable.redcross6_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    ll_nav_header.setBackgroundResource(R.drawable.redcross7_bg);
                    //navigationView.setBackgroundResource(R.drawable.redcross7_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    ll_nav_header.setBackgroundResource(R.drawable.redcross8_bg);
                    //navigationView.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
                } else {
                    ll_nav_header.setBackgroundResource(R.drawable.redcross6_bg);
                    //  navigationView.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
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


    }

    public void launchMarket() {
        Uri uri = Uri.parse(getResources().getString(R.string.marketval) + this.getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            CitiGuestMainActivity.this.startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
        }
    }

    public void shareApp() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType(getResources().getString(R.string.text_plain));
            i.putExtra(Intent.EXTRA_SUBJECT, "" + getResources().getString(R.string.app_name));
            String sAux = getResources().getString(R.string.letmerecomended);
            sAux = sAux + "https://play.google.com/store/apps/details?id=" + this.getPackageName() + " \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, getResources().getString(R.string.choose_one)));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        } catch (Exception e) {

        }
    }

    private void exitHandler() {
        /*final PrettyDialog dialog = new PrettyDialog(this);
        dialog
                .setTitle("")
                .setMessage("Do you want to Exit from the Application?")
                .setIcon(R.drawable.pdlg_icon_info, R.color.pdlg_color_blue, null)
                .addButton(getResources().getString(R.string.ok), R.color.pdlg_color_white, R.color.pdlg_color_green, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            finishAndRemoveTask();
                        } else {
                            System.exit(0);
                            finishAffinity();
                        }

                    }
                })
                .addButton(getResources().getString(R.string.cancel), R.color.pdlg_color_white, R.color.pdlg_color_red, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        dialog.dismiss();
                        // Toast.makeText(OfficerMainActivity.this, "Cancel selected", Toast.LENGTH_SHORT).show();
                    }
                });

        dialog.show();*/


        AlertDialog.Builder builder1 = new AlertDialog.Builder(CitiGuestMainActivity.this);
        builder1.setMessage(getResources().getString(R.string.exitmsg));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GlobalDeclaration.profilePic = "";
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            finishAffinity();
                        }
                    }
                });

        builder1.setNegativeButton(
                getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                                Intent i = new Intent(MainActivity.this, MainActivity.class);
//                                startActivity(i);
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mGpsSwitchStateReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mGpsSwitchStateReceiver);
    }


    void callFragment(Fragment fragment, String name) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment, name);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }


    void callFragment(Fragment fragment, String name, String fromType) {

        Bundle bundle = new Bundle();
        bundle.putString(getResources().getString(R.string.from_type), fromType);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment, name);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();


    }

    private void showThemePicker() {
        selectedThemeColor = -1;
        final Dialog dialog = new Dialog(CitiGuestMainActivity.this);
        dialog.setTitle(getResources().getString(R.string.selected_theme_color));
        dialog.setContentView(R.layout.fragment_themes);

        ImageView iv_color1 = dialog.findViewById(R.id.color1);
        ImageView iv_color2 = dialog.findViewById(R.id.color2);
        ImageView iv_color3 = dialog.findViewById(R.id.color3);
        ImageView iv_color4 = dialog.findViewById(R.id.color4);
        ImageView iv_color5 = dialog.findViewById(R.id.color5);
        ImageView iv_color6 = dialog.findViewById(R.id.color6);
        ImageView iv_color7 = dialog.findViewById(R.id.color7);
        ImageView iv_color8 = dialog.findViewById(R.id.color8);


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
                    //    navigationView.setBackground(getResources().getDrawable(R.drawable.redcross1_bg));
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
                    //    navigationView.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
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
                    //   navigationView.setBackground(getResources().getDrawable(R.drawable.redcross3_bg));
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
                    //  navigationView.setBackground(getResources().getDrawable(R.drawable.redcross4_bg));
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
                    //   navigationView.setBackground(getResources().getDrawable(R.drawable.redcross5_bg));
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
                    //    navigationView.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
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
                    //   navigationView.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
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
                    //ll_nav_header.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
                    ll_nav_header.setBackgroundResource(R.drawable.redcross8_bg);
                    //    navigationView.setBackground(getResources().getDrawable(R.drawable.redcross_splashscreen_bg));
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
        settings = getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF), MODE_PRIVATE);
        editor = settings.edit();
        editor.putInt(getResources().getString(R.string.theme_color1), selectedThemeColor);
        editor.commit();
    }

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

    private void setFragment(String fargTag) {

        if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(CitizendashboardFragment.class.getSimpleName())) {
            selectedFragment = new CitizendashboardFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(WhoWeAreFragment.class.getSimpleName())) {
            selectedFragment = new WhoWeAreFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(AbstractMembership.class.getSimpleName())) {
            selectedFragment = new AbstractMembership();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(MemberFragment.class.getSimpleName())) {
            selectedFragment = new MemberFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(MembershipFragment.class.getSimpleName())) {
            selectedFragment = new MembershipFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(DownloadCertificate.class.getSimpleName())) {
            selectedFragment = new DownloadCertificate();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(MembershipRegFormFragment.class.getSimpleName())) {
            //   selectedFragment = new MembershipRegFormFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(CapacityBuildingsFragment.class.getSimpleName())) {
            selectedFragment = new CapacityBuildingsFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(Upcoming_fragment.class.getSimpleName())) {
            selectedFragment = new Upcoming_fragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(NewBBInfoFragment.class.getSimpleName())) {
            selectedFragment = new NewBBInfoFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(DonateBloodMainFragment.class.getSimpleName())) {
            selectedFragment = new DonateBloodMainFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(HomeNurseInfoFragment.class.getSimpleName())) {
            selectedFragment = new HomeNurseInfoFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(HomenursingFragment.class.getSimpleName())) {
            selectedFragment = new HomenursingFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(HomeNursingRegistrationFragment.class.getSimpleName())) {
//            selectedFragment = new HomeNursingRegistrationFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(ServicesFragment.class.getSimpleName())) {
            selectedFragment = new ServicesFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(HomeNurseRequest.class.getSimpleName())) {
            selectedFragment = new HomeNurseRequest();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(NewContactusFragment.class.getSimpleName())) {
            selectedFragment = new NewContactusFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(InfoFragment.class.getSimpleName())) {
            selectedFragment = new InfoFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(PrivacyPolicyFragment.class.getSimpleName())) {
            selectedFragment = new PrivacyPolicyFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(CitiNewTCFragment.class.getSimpleName())) {
            selectedFragment = new CitiNewTCFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(NewContactusFragment.class.getSimpleName())) {
            selectedFragment = new NewContactusFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(NewBBInfoFragment.class.getSimpleName())) {
            selectedFragment = new NewBBInfoFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(DonateMoneyFragment.class.getSimpleName())) {
            selectedFragment = new DonateMoneyFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(NewBecomeDonorInfoFragment.class.getSimpleName())) {
            selectedFragment = new NewBecomeDonorInfoFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(DonateBloodMainFragment.class.getSimpleName())) {
            selectedFragment = new DonateBloodMainFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(BlooddonorRegistrationFragment.class.getSimpleName())) {
            selectedFragment = new BlooddonorRegistrationFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(BloodDonorInstructionFragment.class.getSimpleName())) {
            selectedFragment = new BloodDonorInstructionFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(BloodDonorInstructionFragment.class.getSimpleName())) {
            selectedFragment = new BloodDonorInstructionFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(RequestNurseInfoFragment.class.getSimpleName())) {
            selectedFragment = new RequestNurseInfoFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(MyMembershipFragment.class.getSimpleName())) {
            selectedFragment = new MyMembershipFragment();
        } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(MyDonationsFragment.class.getSimpleName())) {
            selectedFragment = new MyDonationsFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_backpress, menu);
        if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(getResources().getString(R.string.CitizendashboardFragment))) {
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(false);
        } else {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.logout:
                // search action
                if (GlobalDeclaration.guest != null) {
                    if (GlobalDeclaration.guest.equalsIgnoreCase(getResources().getString(R.string.y))) {
                        askExit();
                    } else {
                        onClickExit();
                    }
                } else {
                    onClickExit();
                }
                return true;
            case R.id.home:
                startActivity(new Intent(this, CitiGuestMainActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void showChangeLangDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CitiGuestMainActivity.this);

        alertDialogBuilder.setMessage(getResources().getString(R.string.Availed_soon));
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
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
        AlertDialog.Builder builder1 = new AlertDialog.Builder(CitiGuestMainActivity.this);
        builder1.setMessage(getResources().getString(R.string.logoutmsg));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GlobalDeclaration.profilePic = "";
                        dialog.dismiss();
                        startActivity(new Intent(CitiGuestMainActivity.this, TabLoginActivity.class));

                        finish();
                    }
                });

        builder1.setNegativeButton(
                getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                                                   Intent i = new Intent(MainActivity.this, MainActivity.class);
//                                startActivity(i);

                        dialog.dismiss();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(CitizendashboardFragment.class.getSimpleName())) {
                askExit();
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(WhoWeAreFragment.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = CitizendashboardFragment.class.getSimpleName();
                selectedFragment = new CitizendashboardFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(WhoWeAreFragment.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = CitizendashboardFragment.class.getSimpleName();
                selectedFragment = new CitizendashboardFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(AbstractMembership.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = CitizendashboardFragment.class.getSimpleName();
                selectedFragment = new CitizendashboardFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(MemberFragment.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = AbstractMembership.class.getSimpleName();
                selectedFragment = new AbstractMembership();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(MembershipFragment.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = AbstractMembership.class.getSimpleName();
                selectedFragment = new AbstractMembership();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(DownloadCertificate.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = AbstractMembership.class.getSimpleName();
                selectedFragment = new AbstractMembership();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(DownloadCertificate.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = AbstractMembership.class.getSimpleName();
                selectedFragment = new AbstractMembership();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(MembershipRegFormFragment.class.getSimpleName())) {
//                GlobalDeclaration.FARG_TAG = AbstractMembership.class.getSimpleName();
//                selectedFragment = new AbstractMembership();
//                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(CapacityBuildingsFragment.class.getSimpleName())) {

                GlobalDeclaration.FARG_TAG = CitizendashboardFragment.class.getSimpleName();
                selectedFragment = new CitizendashboardFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(Upcoming_fragment.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = CitizendashboardFragment.class.getSimpleName();
                selectedFragment = new CitizendashboardFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(NewBBInfoFragment.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = CitizendashboardFragment.class.getSimpleName();
                selectedFragment = new CitizendashboardFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);

            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(DonateBloodMainFragment.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = CitizendashboardFragment.class.getSimpleName();
                selectedFragment = new CitizendashboardFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(BlooddonorRegistrationFragment.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = DonateBloodMainFragment.class.getSimpleName();
                selectedFragment = new DonateBloodMainFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);

            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(BloodDonorInstructionFragment.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = DonateBloodMainFragment.class.getSimpleName();
                selectedFragment = new DonateBloodMainFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);

            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(NewBecomeDonorInfoFragment.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = DonateBloodMainFragment.class.getSimpleName();
                selectedFragment = new DonateBloodMainFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);

            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(HomeNurseInfoFragment.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = HomenursingFragment.class.getSimpleName();
                selectedFragment = new HomenursingFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(HomenursingFragment.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = CitizendashboardFragment.class.getSimpleName();
                selectedFragment = new CitizendashboardFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(HomeNursingRegistrationFragment.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = HomenursingFragment.class.getSimpleName();
                selectedFragment = new HomenursingFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(ServicesFragment.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = CitizendashboardFragment.class.getSimpleName();
                selectedFragment = new CitizendashboardFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(HomeNurseRequest.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = HomenursingFragment.class.getSimpleName();
                selectedFragment = new HomenursingFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(NewContactusFragment.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = CitizendashboardFragment.class.getSimpleName();
                selectedFragment = new CitizendashboardFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(InfoFragment.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = CitizendashboardFragment.class.getSimpleName();
                selectedFragment = new CitizendashboardFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(PrivacyPolicyFragment.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = CitizendashboardFragment.class.getSimpleName();
                selectedFragment = new CitizendashboardFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(CitiNewTCFragment.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = CitizendashboardFragment.class.getSimpleName();
                selectedFragment = new CitizendashboardFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(RequestNurseInfoFragment.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = HomenursingFragment.class.getSimpleName();
                selectedFragment = new HomenursingFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(MyMembershipFragment.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = CitizendashboardFragment.class.getSimpleName();
                selectedFragment = new CitizendashboardFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            } else if (GlobalDeclaration.FARG_TAG.equalsIgnoreCase(MyDonationsFragment.class.getSimpleName())) {
                GlobalDeclaration.FARG_TAG = CitizendashboardFragment.class.getSimpleName();
                selectedFragment = new CitizendashboardFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            }
        }
    }

    private void askExit() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(CitiGuestMainActivity.this);
        builder1.setMessage(getResources().getString(R.string.exitmsg));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            finishAffinity();
                        }
                    }
                });

        builder1.setNegativeButton(
                getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                                Intent i = new Intent(MainActivity.this, MainActivity.class);
//                                startActivity(i);
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
