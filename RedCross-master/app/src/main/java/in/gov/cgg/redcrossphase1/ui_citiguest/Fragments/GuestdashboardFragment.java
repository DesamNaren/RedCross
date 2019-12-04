package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

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

import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;

import static android.content.Context.MODE_PRIVATE;


public class GuestdashboardFragment extends Fragment implements View.OnClickListener {
    ImageView iv_whoweAre, iv_history, iv_vission, iv_mission, iv_contactUs, iv_bloodbankDetails;
    TextView tv_username;
    int selectedThemeColor = -1;
    RelativeLayout fragment_citizendashboard;
    LinearLayout ll_1;
    private FragmentActivity c;

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


        iv_whoweAre.setOnClickListener(this);
        iv_history.setOnClickListener(this);
        iv_vission.setOnClickListener(this);
        iv_mission.setOnClickListener(this);
        iv_contactUs.setOnClickListener(this);
        iv_bloodbankDetails.setOnClickListener(this);

//        if (GlobalDeclaration.guest.equalsIgnoreCase("y")) {
        tv_username.setText("Hello  " + "Guest");

//        } else {
//            tv_username.setText("Hello  " + GlobalDeclaration.loginresponse.getName());
//
//        }

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
        fragment_citizendashboard = root.findViewById(R.id.fragment_citizendashboard);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_whoweAre:
                WhoWeAreFragment nextFrag = new WhoWeAreFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_citizendashboard, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.iv_history:
                HistoryFragment nextFrag1 = new HistoryFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_citizendashboard, nextFrag1, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.iv_vission:
                VisionFragment nextFrag2 = new VisionFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_citizendashboard, nextFrag2, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.iv_mission:
                MissionFragment nextFrag3 = new MissionFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_citizendashboard, nextFrag3, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.iv_contactUs:
                ContactusFragment nextFrag4 = new ContactusFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_citizendashboard, nextFrag4, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.iv_bloodbankDetails:
                LocateBloodbanksFragment nextFrag5 = new LocateBloodbanksFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_citizendashboard, nextFrag5, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                break;
        }
    }
}
