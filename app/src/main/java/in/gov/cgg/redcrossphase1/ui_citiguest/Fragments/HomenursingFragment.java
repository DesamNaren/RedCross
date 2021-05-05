package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.ui_citiguest.HomeNurseregNewActivity;

import static android.content.Context.MODE_PRIVATE;

public class HomenursingFragment extends Fragment {
    TextView btn_RaisehomeNursingRegProceed, btn_BecomehomeNursing, tv_HowtobecomehomeNursing1;
    LinearLayout ll_bg, homeNurse, become_home_nurse_layout;
    RelativeLayout rl_homeNurse;
    LinearLayout ll_requestBg, ll_howtobecomehomeNurseBg, ll_becomeahomeNurse, ll_howtobecomehomeNurse;
    ImageView iv_nurse;
    Button btn_homeNursingRegProceed;
    private int selectedThemeColor = -1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.homenursing_fragment_layout, container, false);
        //binding = DataBindingUtil.inflate(inflater, R.layout.homenursing_fragment_layout, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Home Nursing");
        btn_homeNursingRegProceed = root.findViewById(R.id.btn_homeNursingRegProceed);
        btn_RaisehomeNursingRegProceed = root.findViewById(R.id.btn_RaisehomeNursingRegProceed);
        tv_HowtobecomehomeNursing1 = root.findViewById(R.id.tv_HowtobecomehomeNursing1);
        btn_BecomehomeNursing = root.findViewById(R.id.btn_BecomehomeNursing);
        become_home_nurse_layout = root.findViewById(R.id.become_home_nurse_layout);
        ll_bg = root.findViewById(R.id.ll_bg);
        homeNurse = root.findViewById(R.id.homeNurse);

        findView(root);
        homeNurse.setVisibility(View.VISIBLE);
        btn_BecomehomeNursing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), HomeNurseregNewActivity.class);
                startActivity(i);
//                GlobalDeclaration.FARG_TAG = HomeNursingRegistrationFragment.class.getSimpleName();
//                Fragment fragment = new HomeNursingRegistrationFragment();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                transaction.replace(R.id.nav_host_fragment, fragment, HomeNursingRegistrationFragment.class.getSimpleName());
//                transaction.addToBackStack(null);
//                transaction.commitAllowingStateLoss();
            }
        });
        tv_HowtobecomehomeNursing1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = Intent
                GlobalDeclaration.FARG_TAG = HomeNurseInfoFragment.class.getSimpleName();
                Fragment fragment = new HomeNurseInfoFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment, HomeNurseInfoFragment.class.getSimpleName());
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();//                become_home_nurse_layout.setVisibility(View.VISIBLE);
//                iv_nurse.setVisibility(View.GONE);
//                homeNurse.setVisibility(View.GONE);

            }
        });
        btn_RaisehomeNursingRegProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalDeclaration.FARG_TAG = RequestNurseInfoFragment.class.getSimpleName();
                Fragment fragment = new RequestNurseInfoFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment, RequestNurseInfoFragment.class.getSimpleName());
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        });


        try {
            selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF), MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1
            ), -1);
            setThemes();

        } catch (Exception e) {
            e.printStackTrace();
        }


        btn_homeNursingRegProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeNurseregNewActivity.class);
                startActivity(intent);
//                GlobalDeclaration.FARG_TAG = HomeNursingRegistrationFragment.class.getSimpleName();
//                Fragment fragment = new HomeNursingRegistrationFragment();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                transaction.replace(R.id.nav_host_fragment, fragment, HomeNursingRegistrationFragment.class.getSimpleName());
//                transaction.addToBackStack(null);
//                transaction.commitAllowingStateLoss();
            }
        });

        return root;
    }

    private void findView(View root) {
        btn_homeNursingRegProceed = root.findViewById(R.id.btn_homeNursingRegProceed);
        btn_RaisehomeNursingRegProceed = root.findViewById(R.id.btn_RaisehomeNursingRegProceed);
        //    btn_HowtobecomehomeNursing1 = root.findViewById(R.id.btn_HowtobecomehomeNursing1);
        become_home_nurse_layout = root.findViewById(R.id.become_home_nurse_layout);
        rl_homeNurse = root.findViewById(R.id.rl_homeNurse);
        ll_bg = root.findViewById(R.id.ll_bg);
        homeNurse = root.findViewById(R.id.homeNurse);

        ll_requestBg = root.findViewById(R.id.ll_requestBg);
        ll_howtobecomehomeNurseBg = root.findViewById(R.id.ll_howtobecomehomeNurseBg);
        ll_becomeahomeNurse = root.findViewById(R.id.ll_becomeahomeNurse);
        btn_BecomehomeNursing = root.findViewById(R.id.btn_BecomehomeNursing);

        ll_howtobecomehomeNurse = root.findViewById(R.id.ll_howtobecomehomeNurse);

        iv_nurse = root.findViewById(R.id.iv_nurse);

    }

    private void setThemes() {
        if (selectedThemeColor != -1) {
            if (selectedThemeColor == R.color.redcroosbg_1) {
                ll_bg.setBackground(getResources().getDrawable(R.drawable.redcross1_bg));
                rl_homeNurse.setBackground(getResources().getDrawable(R.drawable.redcross1_bg));
                ll_requestBg.setBackgroundColor(getResources().getColor(R.color.redcroosbg_1));
                ll_howtobecomehomeNurseBg.setBackgroundColor(getResources().getColor(selectedThemeColor));
                ll_becomeahomeNurse.setBackgroundColor(getResources().getColor(selectedThemeColor));

            } else if (selectedThemeColor == R.color.redcroosbg_2) {
                ll_bg.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
                rl_homeNurse.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
                ll_requestBg.setBackgroundColor(getResources().getColor(R.color.redcroosbg_2));
                ll_howtobecomehomeNurseBg.setBackgroundColor(getResources().getColor(selectedThemeColor));
                ll_becomeahomeNurse.setBackgroundColor(getResources().getColor(selectedThemeColor));


            } else if (selectedThemeColor == R.color.redcroosbg_3) {
                ll_bg.setBackground(getResources().getDrawable(R.drawable.redcross3_bg));
                rl_homeNurse.setBackground(getResources().getDrawable(R.drawable.redcross3_bg));
                ll_requestBg.setBackgroundColor(getResources().getColor(R.color.redcroosbg_3));
                ll_howtobecomehomeNurseBg.setBackgroundColor(getResources().getColor(selectedThemeColor));
                ll_becomeahomeNurse.setBackgroundColor(getResources().getColor(selectedThemeColor));
            } else if (selectedThemeColor == R.color.redcroosbg_4) {
                ll_bg.setBackground(getResources().getDrawable(R.drawable.redcross4_bg));
                rl_homeNurse.setBackground(getResources().getDrawable(R.drawable.redcross4_bg));
                ll_requestBg.setBackgroundColor(getResources().getColor(R.color.redcroosbg_4));
                ll_howtobecomehomeNurseBg.setBackgroundColor(getResources().getColor(selectedThemeColor));
                ll_becomeahomeNurse.setBackgroundColor(getResources().getColor(selectedThemeColor));
            } else if (selectedThemeColor == R.color.redcroosbg_5) {
                ll_bg.setBackground(getResources().getDrawable(R.drawable.redcross5_bg));
                rl_homeNurse.setBackground(getResources().getDrawable(R.drawable.redcross5_bg));
                ll_requestBg.setBackgroundColor(getResources().getColor(R.color.redcroosbg_5));
                ll_howtobecomehomeNurseBg.setBackgroundColor(getResources().getColor(selectedThemeColor));
                ll_becomeahomeNurse.setBackgroundColor(getResources().getColor(selectedThemeColor));
            } else if (selectedThemeColor == R.color.redcroosbg_6) {
                ll_bg.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                rl_homeNurse.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                ll_howtobecomehomeNurseBg.setBackgroundColor(getResources().getColor(selectedThemeColor));
                ll_becomeahomeNurse.setBackgroundColor(getResources().getColor(selectedThemeColor));
                ll_requestBg.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));
//                ll_howtobecomehomeNurse.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));
            } else if (selectedThemeColor == R.color.redcroosbg_7) {
                ll_bg.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
                rl_homeNurse.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
                ll_requestBg.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));
                ll_howtobecomehomeNurseBg.setBackgroundColor(getResources().getColor(selectedThemeColor));
                ll_becomeahomeNurse.setBackgroundColor(getResources().getColor(selectedThemeColor));
            } else if (selectedThemeColor == R.color.redcroosbg_8) {
                ll_bg.setBackground(getResources().getDrawable(R.drawable.redcross8_bg));
                rl_homeNurse.setBackground(getResources().getDrawable(R.drawable.redcross8_bg));
                ll_requestBg.setBackgroundColor(getResources().getColor(R.color.redcroosbg_8));
                ll_howtobecomehomeNurseBg.setBackgroundColor(getResources().getColor(selectedThemeColor));
                ll_becomeahomeNurse.setBackgroundColor(getResources().getColor(selectedThemeColor));
            } else {
                ll_bg.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                rl_homeNurse.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                ll_requestBg.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));
                ll_howtobecomehomeNurseBg.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ll_becomeahomeNurse.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            }

        } else {
            ll_bg.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
            rl_homeNurse.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
            ll_requestBg.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));
            ll_howtobecomehomeNurseBg.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            ll_becomeahomeNurse.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


        }
    }

}
