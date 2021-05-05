package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.DonatebloodMainBinding;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;

import static android.content.Context.MODE_PRIVATE;

public class DonateBloodMainFragment extends Fragment {

    DonatebloodMainBinding binding;
    private int selectedThemeColor = -1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));

        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
                inflater, R.layout.donateblood_main, container, false);
        Objects.requireNonNull(getActivity()).setTitle(getResources().getString(R.string.donate_blood));

        try {
            selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF), MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);
            setThemes();

        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.btnBecomedonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GlobalDeclaration.FARG_TAG = BloodDonorInstructionFragment.class.getSimpleName();
                Fragment fragment = new BloodDonorInstructionFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment, BloodDonorInstructionFragment.class.getSimpleName());
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        });
        binding.tvHowtobecomedonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalDeclaration.FARG_TAG = NewBecomeDonorInfoFragment.class.getSimpleName();
                Fragment fragment = new NewBecomeDonorInfoFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment, NewBecomeDonorInfoFragment.class.getSimpleName());
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        });

        return binding.getRoot();
    }


    private void setThemes() {
        if (selectedThemeColor != -1) {
            if (selectedThemeColor == R.color.redcroosbg_1) {
                binding.rlHomeNurse.setBackground(getResources().getDrawable(R.drawable.redcross1_bg));
                binding.llHowtobecomehomeNurseBg.setBackground(getResources().getDrawable(R.drawable.lltheme1_selectedbg));
                binding.llBecomeahomeNurse.setBackground(getResources().getDrawable(R.drawable.lltheme1_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_2) {
                binding.rlHomeNurse.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
                binding.llHowtobecomehomeNurseBg.setBackground(getResources().getDrawable(R.drawable.lltheme2_selectedbg));
                binding.llBecomeahomeNurse.setBackground(getResources().getDrawable(R.drawable.lltheme2_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_3) {
                binding.rlHomeNurse.setBackground(getResources().getDrawable(R.drawable.redcross3_bg));
                binding.llHowtobecomehomeNurseBg.setBackground(getResources().getDrawable(R.drawable.lltheme3_selectedbg));
                binding.llBecomeahomeNurse.setBackground(getResources().getDrawable(R.drawable.lltheme3_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_4) {
                binding.rlHomeNurse.setBackground(getResources().getDrawable(R.drawable.redcross4_bg));
                binding.llHowtobecomehomeNurseBg.setBackground(getResources().getDrawable(R.drawable.lltheme4_selectedbg));
                binding.llBecomeahomeNurse.setBackground(getResources().getDrawable(R.drawable.lltheme4_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_5) {
                binding.rlHomeNurse.setBackground(getResources().getDrawable(R.drawable.redcross5_bg));
                binding.llHowtobecomehomeNurseBg.setBackground(getResources().getDrawable(R.drawable.lltheme5_selectedbg));
                binding.llBecomeahomeNurse.setBackground(getResources().getDrawable(R.drawable.lltheme5_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_6) {
                binding.rlHomeNurse.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                binding.llHowtobecomehomeNurseBg.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
                binding.llBecomeahomeNurse.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_7) {
                binding.rlHomeNurse.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
                binding.llHowtobecomehomeNurseBg.setBackground(getResources().getDrawable(R.drawable.lltheme7_seleetedbg));
                binding.llBecomeahomeNurse.setBackground(getResources().getDrawable(R.drawable.lltheme7_seleetedbg));
            } else if (selectedThemeColor == R.color.redcroosbg_8) {
                binding.rlHomeNurse.setBackground(getResources().getDrawable(R.drawable.redcross8_bg));
                binding.llHowtobecomehomeNurseBg.setBackground(getResources().getDrawable(R.drawable.lltheme8_seleetedbg));
                binding.llBecomeahomeNurse.setBackground(getResources().getDrawable(R.drawable.lltheme8_seleetedbg));
            } else {
                binding.rlHomeNurse.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                binding.llHowtobecomehomeNurseBg.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
                binding.llBecomeahomeNurse.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));

            }

        } else {
            binding.rlHomeNurse.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
            binding.llHowtobecomehomeNurseBg.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
            binding.llBecomeahomeNurse.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));

        }
    }

}
