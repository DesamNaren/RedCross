package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_citiguest.HomeNursingActivity;

import static android.content.Context.MODE_PRIVATE;

public class HomenursingFragment extends Fragment {
    TextView btn_homeNursingRegProceed, btn_RaisehomeNursingRegProceed, btn_BecomehomeNursing, btn_HowtobecomehomeNursing1;
    LinearLayout ll_bg, homeNurse, become_home_nurse_layout;
    private int selectedThemeColor = -1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.homenursing_fragment_layout, container, false);
        //binding = DataBindingUtil.inflate(inflater, R.layout.homenursing_fragment_layout, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Home Nursing");
        btn_homeNursingRegProceed = root.findViewById(R.id.btn_homeNursingRegProceed);
        btn_RaisehomeNursingRegProceed = root.findViewById(R.id.btn_RaisehomeNursingRegProceed);
        btn_HowtobecomehomeNursing1 = root.findViewById(R.id.btn_HowtobecomehomeNursing1);
        btn_BecomehomeNursing = root.findViewById(R.id.btn_BecomehomeNursing);
        become_home_nurse_layout = root.findViewById(R.id.become_home_nurse_layout);
        ll_bg = root.findViewById(R.id.ll_bg);
        homeNurse = root.findViewById(R.id.homeNurse);
        homeNurse.setVisibility(View.VISIBLE);
        btn_BecomehomeNursing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(getActivity(), HomeNursingActivity.class);
//                startActivity(i);
                Fragment fragment = new HomeNursingRegistrationFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment, HomeNursingRegistrationFragment.class.getSimpleName());
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        });
        btn_HowtobecomehomeNursing1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = Intent
                become_home_nurse_layout.setVisibility(View.VISIBLE);
                homeNurse.setVisibility(View.GONE);

            }
        });
        btn_RaisehomeNursingRegProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new HomeNurseRequest();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment, HomeNurseRequest.class.getSimpleName());
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        });

        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    ll_bg.setBackgroundResource(R.drawable.redcross1_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    ll_bg.setBackgroundResource(R.drawable.redcross2_bg);

                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    ll_bg.setBackgroundResource(R.drawable.redcross3_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    ll_bg.setBackgroundResource(R.drawable.redcross4_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    ll_bg.setBackgroundResource(R.drawable.redcross5_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    ll_bg.setBackgroundResource(R.drawable.redcross6_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    ll_bg.setBackgroundResource(R.drawable.redcross7_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    ll_bg.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        btn_homeNursingRegProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeNursingActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}
