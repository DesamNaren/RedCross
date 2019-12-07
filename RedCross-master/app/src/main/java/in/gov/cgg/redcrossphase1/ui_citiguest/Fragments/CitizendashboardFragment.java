package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.net.Uri;
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

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;

import static android.content.Context.MODE_PRIVATE;


public class CitizendashboardFragment extends Fragment implements WhoWeAreFragment.OnFragmentInteractionListener, ContactusFragment.OnFragmentInteractionListener, HistoryFragment.OnFragmentInteractionListener, MissionFragment.OnFragmentInteractionListener, VisionFragment.OnFragmentInteractionListener, LocateBloodbanksFragment.OnFragmentInteractionListener {
    ImageView iv_whoweAre, iv_history, iv_vission, iv_mission, iv_contactUs, iv_bloodbankDetails;
    TextView tv_username;
    int selectedThemeColor = -1;
    RelativeLayout fragment_citizendashboard;
    private FragmentActivity c;
    LinearLayout ll_1, ll_history, ll_vision, ll_mission, ll_contactus, ll_bloodbankDetails;
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


        ll_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalDeclaration.FARG_TAG = WhoWeAreFragment.class.getSimpleName();
                selectedFragment = new WhoWeAreFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            }
        });
        ll_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalDeclaration.FARG_TAG = HistoryFragment.class.getSimpleName();
                selectedFragment = new HistoryFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            }
        });
        ll_vision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalDeclaration.FARG_TAG = VisionFragment.class.getSimpleName();
                selectedFragment = new VisionFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            }
        });
        ll_mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalDeclaration.FARG_TAG = MissionFragment.class.getSimpleName();
                selectedFragment = new MissionFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            }
        });
        ll_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalDeclaration.FARG_TAG = ContactusFragment.class.getSimpleName();
                selectedFragment = new ContactusFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            }
        });
        ll_bloodbankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalDeclaration.FARG_TAG = LocateBloodbanksFragment.class.getSimpleName();
                selectedFragment = new LocateBloodbanksFragment();
                callFragment(selectedFragment, GlobalDeclaration.FARG_TAG);
            }
        });
        try {
            if (GlobalDeclaration.guest != null) {
                if (GlobalDeclaration.guest.equalsIgnoreCase("y")) {
                    tv_username.setText("Hello  " + "Guest");

                } else {
                    tv_username.setText("Hello  " + GlobalDeclaration.loginresponse.getName());

                }
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
                    fragment_citizendashboard.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
                }

            } else {
                fragment_citizendashboard.setBackgroundResource(R.drawable.redcross_splashscreen_bg);

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
        ll_1 = root.findViewById(R.id.ll_wwa);
        ll_bloodbankDetails = root.findViewById(R.id.ll_bloodbankDetails);
        ll_contactus = root.findViewById(R.id.ll_contactus);
        ll_vision = root.findViewById(R.id.ll_vision);
        ll_mission = root.findViewById(R.id.ll_mission);
        ll_history = root.findViewById(R.id.ll_history);
        fragment_citizendashboard = root.findViewById(R.id.fragment_citizendashboard);


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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
