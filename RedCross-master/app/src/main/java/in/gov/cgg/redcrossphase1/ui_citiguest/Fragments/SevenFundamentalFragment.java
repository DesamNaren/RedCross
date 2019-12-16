package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import in.gov.cgg.redcrossphase1.R;

import static android.content.Context.MODE_PRIVATE;


public class SevenFundamentalFragment extends Fragment {
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    int selectedThemeColor = -1;
    LinearLayout ll_sevenPrinciples;
    TextView tv_humanityHeading, tv_ImpartialityHeading, tv_NeutralityHeading, tv_IndependenceyHeading, tv_VoluntaryService, tv_UnityHeading, tv_UniversalityHeading;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //   Objects.requireNonNull(getActivity()).setTitle("Fundamental Principles");

        View root = inflater.inflate(R.layout.fragment_sevenprinciples, container, false);
        ll_sevenPrinciples = root.findViewById(R.id.ll_sevenPrinciples);
        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    ll_sevenPrinciples.setBackgroundResource(R.drawable.redcross1_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    ll_sevenPrinciples.setBackgroundResource(R.drawable.redcross2_bg);

                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    ll_sevenPrinciples.setBackgroundResource(R.drawable.redcross3_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    ll_sevenPrinciples.setBackgroundResource(R.drawable.redcross4_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    ll_sevenPrinciples.setBackgroundResource(R.drawable.redcross5_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    ll_sevenPrinciples.setBackgroundResource(R.drawable.redcross6_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    ll_sevenPrinciples.setBackgroundResource(R.drawable.redcross7_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    ll_sevenPrinciples.setBackgroundResource(R.drawable.redcross8_bg);
                } else {
                    ll_sevenPrinciples.setBackgroundResource(R.drawable.redcross7_bg);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        findViews(root);

        // GlobalDeclaration.home = false;
//        Objects.requireNonNull(
//
//                getActivity()).
//
//                setTitle("Dashboard");

        Log.d("Test", "..................");
//        try {
//            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
//            if (selectedThemeColor != -1) {
//                if (selectedThemeColor == R.color.redcroosbg_1) {
//                    ll_sevenPrinciples.setBackgroundResource(R.drawable.redcross1_bg);
//                    tv_humanityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_ImpartialityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_NeutralityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_IndependenceyHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_VoluntaryService.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_UniversalityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_UnityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                } else if (selectedThemeColor == R.color.redcroosbg_2) {
//                    ll_sevenPrinciples.setBackgroundResource(R.drawable.redcross2_bg);
//                    tv_humanityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_ImpartialityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_NeutralityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_IndependenceyHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_VoluntaryService.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_UniversalityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_UnityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                } else if (selectedThemeColor == R.color.redcroosbg_3) {
//                    ll_sevenPrinciples.setBackgroundResource(R.drawable.redcross3_bg);
//                    tv_humanityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_ImpartialityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_NeutralityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_IndependenceyHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_VoluntaryService.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_UniversalityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_UnityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//
//                } else if (selectedThemeColor == R.color.redcroosbg_4) {
//                    ll_sevenPrinciples.setBackgroundResource(R.drawable.redcross4_bg);
//                    tv_humanityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_ImpartialityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_NeutralityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_IndependenceyHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_VoluntaryService.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_UniversalityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_UnityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                } else if (selectedThemeColor == R.color.redcroosbg_5) {
//                    ll_sevenPrinciples.setBackgroundResource(R.drawable.redcross5_bg);
//                    tv_humanityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_ImpartialityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_NeutralityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_IndependenceyHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_VoluntaryService.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_UniversalityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_UnityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                } else if (selectedThemeColor == R.color.redcroosbg_6) {
//                    ll_sevenPrinciples.setBackgroundResource(R.drawable.redcross6_bg);
//                    tv_humanityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_ImpartialityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_NeutralityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_IndependenceyHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_VoluntaryService.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_UniversalityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_UnityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                } else if (selectedThemeColor == R.color.redcroosbg_7) {
//
//                    ll_sevenPrinciples.setBackgroundResource(R.drawable.redcross7_bg);
//                    tv_humanityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_ImpartialityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_NeutralityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_IndependenceyHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_VoluntaryService.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_UniversalityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_UnityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                } else if (selectedThemeColor == R.color.redcroosbg_8) {
//
//                    ll_sevenPrinciples.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
//                    tv_humanityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_ImpartialityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_NeutralityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_IndependenceyHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_VoluntaryService.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_UniversalityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//                    tv_UnityHeading.setTextColor(getResources().getColor(selectedThemeColor));
//
//                }
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
        return root;
    }

    private void findViews(View root) {
        ll_sevenPrinciples = root.findViewById(R.id.ll_sevenPrinciples);
        tv_humanityHeading = root.findViewById(R.id.tv_humanityHeading);
        tv_ImpartialityHeading = root.findViewById(R.id.tv_ImpartialityHeading);
        tv_NeutralityHeading = root.findViewById(R.id.tv_NeutralityHeading);
        tv_IndependenceyHeading = root.findViewById(R.id.tv_IndependenceyHeading);
        tv_VoluntaryService = root.findViewById(R.id.tv_VoluntaryService);
        tv_UnityHeading = root.findViewById(R.id.tv_UnityHeading);
        tv_UniversalityHeading = root.findViewById(R.id.tv_UniversalityHeading);
    }

}