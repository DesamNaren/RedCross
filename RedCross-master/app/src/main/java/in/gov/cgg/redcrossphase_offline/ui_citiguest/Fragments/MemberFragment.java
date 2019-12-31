package in.gov.cgg.redcrossphase_offline.ui_citiguest.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

import in.gov.cgg.redcrossphase_offline.R;
import in.gov.cgg.redcrossphase_offline.retrofit.GlobalDeclaration;

import static android.content.Context.MODE_PRIVATE;

public class MemberFragment extends Fragment {
    TextView btn_homeNursingRegProceed;
    LinearLayout ll_bg, homeNurse, become_home_nurse_layout;
    private int selectedThemeColor = -1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.become_member_layout, container, false);
        //binding = DataBindingUtil.inflate(inflater, R.layout.homenursing_fragment_layout, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Become a Member");
        btn_homeNursingRegProceed = root.findViewById(R.id.btn_homeNursingRegProceed);
        ll_bg = root.findViewById(R.id.ll_bg);

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
                    ll_bg.setBackgroundResource(R.drawable.redcross8_bg);
                } else {
                    ll_bg.setBackgroundResource(R.drawable.redcross7_bg);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        btn_homeNursingRegProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalDeclaration.FARG_TAG = MembershipFragment.class.getSimpleName();
                Fragment fragment = new MembershipFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment, MembershipFragment.class.getSimpleName());
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        });

        return root;
    }
}
