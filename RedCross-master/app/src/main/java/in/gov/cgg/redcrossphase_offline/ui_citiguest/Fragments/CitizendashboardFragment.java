package in.gov.cgg.redcrossphase_offline.ui_citiguest.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

import in.gov.cgg.redcrossphase_offline.R;
import in.gov.cgg.redcrossphase_offline.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.LocateActivity;

import static android.content.Context.MODE_PRIVATE;


public class CitizendashboardFragment extends Fragment {
    ImageView iv_whoweAre, iv_history, iv_vission, iv_mission, iv_contactUs, iv_bloodbankDetails;
    TextView tv_username;
    int selectedThemeColor = -1;
    RelativeLayout fragment_citizendashboard;
    private FragmentActivity c;
    LinearLayout ll_wwa, ll_mebership, ll_capityBuildings, ll_loate, ll_Serices, ll_hoeNursing, ll_bloodbankInfo, ll_contactUs, ll_donor;
    private Fragment selectedFragment;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_citizendashboard, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Dashboard");
        c = getActivity();
        setHasOptionsMenu(true);
        findViews(root);


        ll_wwa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalDeclaration.FARG_TAG = WhoWeAreFragment.class.getSimpleName();
                selectedFragment = new WhoWeAreFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            }
        });

        ll_donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalDeclaration.FARG_TAG = BlooddonorRegistrationFragment.class.getSimpleName();
                selectedFragment = new BlooddonorRegistrationFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            }
        });
        ll_mebership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalDeclaration.FARG_TAG = AbstractMembership.class.getSimpleName();
                selectedFragment = new AbstractMembership();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            }
        });
        ll_capityBuildings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalDeclaration.FARG_TAG = CapacityBuildingsFragment.class.getSimpleName();
                selectedFragment = new CapacityBuildingsFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            }
        });
        ll_loate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), LocateActivity.class);
                startActivity(i);
               /* GlobalDeclaration.FARG_TAG = LocationsFragment.class.getSimpleName();
                selectedFragment = new LocationsFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);*/
            }
        });
        ll_Serices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalDeclaration.FARG_TAG = ServicesFragment.class.getSimpleName();
                selectedFragment = new ServicesFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            }
        });
        ll_hoeNursing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalDeclaration.FARG_TAG = HomenursingFragment.class.getSimpleName();
                selectedFragment = new HomenursingFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            }
        });
        ll_bloodbankInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalDeclaration.FARG_TAG = BBInfoFragment.class.getSimpleName();
                selectedFragment = new BBInfoFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            }
        });
        ll_contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalDeclaration.FARG_TAG = ContactusFragment.class.getSimpleName();
                selectedFragment = new ContactusFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            }
        });
        try {
            if (GlobalDeclaration.guest != null) {
                if (GlobalDeclaration.guest.equalsIgnoreCase("y")) {
                    tv_username.setText("Welcome");

                } else if (GlobalDeclaration.loginresponse != null) {
                    tv_username.setText("Hello  " + GlobalDeclaration.loginresponse.getName());
                } else if (GlobalDeclaration.unamefromReg != null) {
                    tv_username.setText("Welcome  " + GlobalDeclaration.unamefromReg);
                } else {
                    tv_username.setText("Welcome to IRSC");
                }
            } else if (GlobalDeclaration.loginresponse != null) {
                tv_username.setText("Hello  " + GlobalDeclaration.loginresponse.getName());
            } else if (GlobalDeclaration.unamefromReg != null) {
                tv_username.setText("Welcome  " + GlobalDeclaration.unamefromReg);
            } else {
                tv_username.setText("Welcome to IRSC");
            }
        } catch (Exception e) {

        }
        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    fragment_citizendashboard.setBackgroundResource(R.drawable.redcross1_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    fragment_citizendashboard.setBackgroundResource(R.drawable.redcross2_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    fragment_citizendashboard.setBackgroundResource(R.drawable.redcross3_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    fragment_citizendashboard.setBackgroundResource(R.drawable.redcross4_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    fragment_citizendashboard.setBackgroundResource(R.drawable.redcross5_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    fragment_citizendashboard.setBackgroundResource(R.drawable.redcross6_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    fragment_citizendashboard.setBackgroundResource(R.drawable.redcross7_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    fragment_citizendashboard.setBackgroundResource(R.drawable.redcross8_bg);
                } else {
                    fragment_citizendashboard.setBackgroundResource(R.drawable.redcross7_bg);
                }

            } else {
                fragment_citizendashboard.setBackgroundResource(R.drawable.redcross7_bg);

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return root;


    }

    private void findViews(View root) {
        iv_whoweAre = root.findViewById(R.id.iv_whoweAre);
        iv_history = root.findViewById(R.id.iv_history);
        iv_vission = root.findViewById(R.id.iv_vission);
        iv_mission = root.findViewById(R.id.iv_mission);
        iv_contactUs = root.findViewById(R.id.iv_contactUs);
        iv_bloodbankDetails = root.findViewById(R.id.iv_bloodbankDetails);
        tv_username = root.findViewById(R.id.tv_username);
        ll_donor = root.findViewById(R.id.ll_donor);
        //    ll_1 = root.findViewById(R.id.ll_wwa);
        //    ll_bloodbankDetails = root.findViewById(R.id.ll_bloodbankDetails);
        //     ll_contactus = root.findViewById(R.id.ll_contactus);
        //    ll_vision = root.findViewById(R.id.ll_vision);
        //    ll_mission = root.findViewById(R.id.ll_mission);
        //    ll_history = root.findViewById(R.id.ll_history);
        fragment_citizendashboard = root.findViewById(R.id.fragment_citizendashboard);


        ll_wwa = root.findViewById(R.id.ll_wwa);
        ll_mebership = root.findViewById(R.id.ll_mebership);
        ll_capityBuildings = root.findViewById(R.id.ll_capityBuildings);
        ll_loate = root.findViewById(R.id.ll_loate);
        ll_Serices = root.findViewById(R.id.ll_Serices);
        ll_hoeNursing = root.findViewById(R.id.ll_hoeNursing);
        ll_bloodbankInfo = root.findViewById(R.id.ll_bloodbankInfo);
        ll_contactUs = root.findViewById(R.id.ll_contactUs);


    }

    void callFragment(Fragment fragment, String name) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment, name);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.iv_whoweAre:
//                WhoWeAreFragment nextFrag = new WhoWeAreFragment();
////                getActivity().getSupportFragmentManager().beginTransaction()
////                        .replace(R.id.fragment_citizendashboard, nextFrag, "findThisFragment")
////                        .addToBackStack(null)
////                        .commit();
//                GlobalDeclaration.FARG_TAG = CitizendashboardFragment.class.getSimpleName();
//                selectedFragment = new CitizendashboardFragment();
//                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
//                break;
//            case R.id.iv_history:
//                HistoryFragment nextFrag1 = new HistoryFragment();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_citizendashboard, nextFrag1, "findThisFragment")
//                        .addToBackStack(null)
//                        .commit();
//                break;
//            case R.id.iv_vission:
//                VisionFragment nextFrag2 = new VisionFragment();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_citizendashboard, nextFrag2, "findThisFragment")
//                        .addToBackStack(null)
//                        .commit();
//                break;
//            case R.id.iv_mission:
//                MissionFragment nextFrag3 = new MissionFragment();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_citizendashboard, nextFrag3, "findThisFragment")
//                        .addToBackStack(null)
//                        .commit();
//                break;
//            case R.id.iv_contactUs:
//                ContactusFragment nextFrag4 = new ContactusFragment();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_citizendashboard, nextFrag4, "findThisFragment")
//                        .addToBackStack(null)
//                        .commit();
//                break;
//            case R.id.iv_bloodbankDetails:
//                LocateBloodbanksFragment nextFrag5 = new LocateBloodbanksFragment();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_citizendashboard, nextFrag5, "findThisFragment")
//                        .addToBackStack(null)
//                        .commit();
//                break;
//            default:
//                break;
//        }
//    }


}
