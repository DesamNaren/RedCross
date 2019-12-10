package in.gov.cgg.redcrossphase1.ui_officer_new;

import android.app.Activity;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragemtHomeOfficerNewBinding;
import in.gov.cgg.redcrossphase1.ui_officer.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.agewise.AgewiseFragment;
import in.gov.cgg.redcrossphase1.ui_officer.agewise.AgewiseViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.bloodwise.BloodwiseFragment;
import in.gov.cgg.redcrossphase1.ui_officer.bloodwise.BloodwiseViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.daywisereportcont.DaywiseFragment;
import in.gov.cgg.redcrossphase1.ui_officer.genderwise.GenderwiseFragment;
import in.gov.cgg.redcrossphase1.ui_officer.genderwise.GenderwiseViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.genderwise.OnlyGenderwiseFragment;
import in.gov.cgg.redcrossphase1.ui_officer.govtpvt.GovtPvtFragment;
import in.gov.cgg.redcrossphase1.ui_officer.govtpvt.GovtPvtViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.home_distrcit.CustomDistricClass;
import in.gov.cgg.redcrossphase1.ui_officer.home_distrcit.DistrictViewModel;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;

import static in.gov.cgg.redcrossphase1.R.color.colorPrimary;
import static in.gov.cgg.redcrossphase1.R.color.white;

public class NewOfficerHomeFragment extends Fragment {
    private final RectF onValueSelectedRectF = new RectF();

    int selectedThemeColor = -1;
    GovtPvtViewModel govtPvtViewModel;
    private DistrictViewModel districtViewModel;
    private AgewiseViewModel agewiseViewModel;
    private BloodwiseViewModel bloodwiseVm;
    private GenderwiseViewModel genderwiseViewModel;
    private String gender, bloodwise, age, top5, btm5;
    private Fragment fragment;
    private LineChart lineChart;
    private FragemtHomeOfficerNewBinding binding;
    private String D_TYPE;
    private IHomeListener ihomeListener;
    private String type;
    private HomeViewPagerAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragemt_home_officer_new, container, false);


        getActivity().setTitle("Dashboard");
        if (GlobalDeclaration.role.contains("D")) {
            setupViewPagerAll(binding.viewpagerHome, "Mandals");
        } else {
            setupViewPagerAll(binding.viewpagerHome, "Districts");
        }
        binding.tabsHome.setupWithViewPager(binding.viewpagerHome);

        districtViewModel =
                ViewModelProviders.of(this, new CustomDistricClass(getActivity(), "district")).get(DistrictViewModel.class);
        agewiseViewModel =
                ViewModelProviders.of(this, new CustomDistricClass(getActivity(), "age")).get(AgewiseViewModel.class);
        bloodwiseVm =
                ViewModelProviders.of(this, new CustomDistricClass(getActivity(), "blood")).get(BloodwiseViewModel.class);
        genderwiseViewModel =
                ViewModelProviders.of(this, new CustomDistricClass(getActivity(), "gender")).get(GenderwiseViewModel.class);

        govtPvtViewModel = ViewModelProviders.of(this, new CustomDistricClass(getActivity(), "gvtpvt")).get(GovtPvtViewModel.class);


        GlobalDeclaration.home = true;
        GlobalDeclaration.Selection_type = "JRC";
        reload();


        binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
        binding.customCount.tvAllname.setTextColor(getResources().getColor(white));
        binding.customCount.tvAllcount.setTextColor(getResources().getColor(white));

        binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
        binding.customCount.tvJrccount.setTextColor(getResources().getColor(colorPrimary));
        binding.customCount.tvJrcnme.setTextColor(getResources().getColor(colorPrimary));

        binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
        binding.customCount.tvYrccount.setTextColor(getResources().getColor(colorPrimary));
        binding.customCount.tvYrcnme.setTextColor(getResources().getColor(colorPrimary));

        binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
        binding.customCount.tvLmcount.setTextColor(getResources().getColor(colorPrimary));
        binding.customCount.tvLmname.setTextColor(getResources().getColor(colorPrimary));
//
//        try {
//            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
//            if (selectedThemeColor != -1) {
//
//                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//                binding.customCount.tvAllname.setTextColor(getResources().getColor(white));
//                binding.customCount.tvAllcount.setTextColor(getResources().getColor(white));
//
//                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                binding.customCount.tvJrccount.setTextColor(getResources().getColor(selectedThemeColor));
//                binding.customCount.tvJrcnme.setTextColor(getResources().getColor(selectedThemeColor));
//
//                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                binding.customCount.tvYrccount.setTextColor(getResources().getColor(selectedThemeColor));
//                binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
//
//                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                binding.customCount.tvLmcount.setTextColor(getResources().getColor(selectedThemeColor));
//                binding.customCount.tvLmname.setTextColor(getResources().getColor(selectedThemeColor));
//
//
//                //set themes for linearlayouts for jrc yrc member top5 bottom5
//                if (selectedThemeColor == R.color.redcroosbg_1) {
//                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme1_selectedbg));
//                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//                } else if (selectedThemeColor == R.color.redcroosbg_2) {
//                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme2_selectedbg));
//
//
//                } else if (selectedThemeColor == R.color.redcroosbg_3) {
//                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme3_selectedbg));
//                } else if (selectedThemeColor == R.color.redcroosbg_4) {
//                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme4_selectedbg));
//                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                } else if (selectedThemeColor == R.color.redcroosbg_5) {
//                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme5_selectedbg));
//                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                } else if (selectedThemeColor == R.color.redcroosbg_6) {
//                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
//                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                } else if (selectedThemeColor == R.color.redcroosbg_7) {
//                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme7_seleetedbg));
//                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                } else if (selectedThemeColor == R.color.redcroosbg_8) {
//
//                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//
//
//                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//                    binding.customCount.tvAllcount.setTextColor(getResources().getColor(white));
//                    binding.customCount.tvAllname.setTextColor(getResources().getColor(white));
//
//                }
//            } else {
//                countColorSelection();
//                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
/*
        binding.viewpagerHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("position", "--------------------" + position);

                if (position == 0) {

                    Fragment frag = new NewAllDistrictsFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();

                } else if (position == 1) {
                    Fragment frag = new GenderwiseFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();
                } else if (position == 2) {
                    Fragment frag = new BloodwiseFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();
                } else if (position == 3) {
                    Fragment frag = new AgewiseFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();
                }
                else
                {
                    Fragment frag = new NewAllDistrictsFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
*/

        districtViewModel.getDashboardCounts("", GlobalDeclaration.userID, GlobalDeclaration.districtId)
                .observe(Objects.requireNonNull(getActivity()), new Observer<DashboardCountResponse>() {

                    @Override
                    public void onChanged(@Nullable DashboardCountResponse dashboardCountResponse) {
                        if (dashboardCountResponse != null) {
                            GlobalDeclaration.counts = dashboardCountResponse;
                            setCountsForDashboard(dashboardCountResponse);
                        }
                    }
                });

        binding.fabDaywise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new DaywiseFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_officer, fragment);
                fragmentTransaction.commit();
            }
        });


        binding.customCount.llJrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isOnline(getActivity())) {
                    {
                        setupViewPagerJRC(binding.viewpagerHome);


                        binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
                        binding.customCount.tvJrcnme.setTextColor(getResources().getColor(white));
                        binding.customCount.tvJrccount.setTextColor(getResources().getColor(white));

                        binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                        binding.customCount.tvLmname.setTextColor(getResources().getColor(colorPrimary));
                        binding.customCount.tvLmcount.setTextColor(getResources().getColor(colorPrimary));

                        binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                        binding.customCount.tvYrccount.setTextColor(getResources().getColor(colorPrimary));
                        binding.customCount.tvYrcnme.setTextColor(getResources().getColor(colorPrimary));

                        binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                        binding.customCount.tvAllname.setTextColor(getResources().getColor(colorPrimary));
                        binding.customCount.tvAllcount.setTextColor(getResources().getColor(colorPrimary));
//                        try {
//                            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
//                            if (selectedThemeColor != -1) {
//
//                                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//                                binding.customCount.tvJrcnme.setTextColor(getResources().getColor(white));
//                                binding.customCount.tvJrccount.setTextColor(getResources().getColor(white));
//
//                                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                                binding.customCount.tvLmname.setTextColor(getResources().getColor(selectedThemeColor));
//                                binding.customCount.tvLmcount.setTextColor(getResources().getColor(selectedThemeColor));
//
//                                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                                binding.customCount.tvYrccount.setTextColor(getResources().getColor(selectedThemeColor));
//                                binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
//
//                                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                                binding.customCount.tvAllname.setTextColor(getResources().getColor(selectedThemeColor));
//                                binding.customCount.tvAllcount.setTextColor(getResources().getColor(selectedThemeColor));
//
////
////                                binding.customCount.tvJrcnme.setTextColor(getResources().getColor(white));
////                                binding.customCount.tvJrccount.setTextColor(getResources().getColor(white));
////                                binding.customCount.tvAllcount.setTextColor(getResources().getColor(selectedThemeColor));
////                                binding.customCount.tvAllname.setTextColor(getResources().getColor(selectedThemeColor));
////                                binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
////                                binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
////                                binding.customCount.tvLmname.setTextColor(getResources().getColor(selectedThemeColor));
////                                binding.customCount.tvLmcount.setTextColor(getResources().getColor(selectedThemeColor));
//
//
//                                //set themes for linearlayouts for jrc yrc member top5 bottom5
//                                if (selectedThemeColor == R.color.redcroosbg_1) {
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_selectedbg));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_2) {
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_selectedbg));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//
//                                } else if (selectedThemeColor == R.color.redcroosbg_3) {
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_selectedbg));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_4) {
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_selectedbg));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_5) {
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_selectedbg));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_6) {
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_7) {
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_seleetedbg));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_8) {
//
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//
//
//                                }
//                            } else {
//                                countColorSelection();
//                                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//
//                        }

                        GlobalDeclaration.Selection_type = "JRC";
                        reload();
                    }
                } else {
                    Snackbar snackbar = Snackbar
                            .make(binding.customCount.llJrc, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

        });
        binding.customCount.llYrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isOnline(getActivity())) {
                    {
                        setupViewPagerYRC(binding.viewpagerHome);

                        binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
                        binding.customCount.tvYrcnme.setTextColor(getResources().getColor(white));
                        binding.customCount.tvYrccount.setTextColor(getResources().getColor(white));

                        binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                        binding.customCount.tvLmname.setTextColor(getResources().getColor(colorPrimary));
                        binding.customCount.tvLmcount.setTextColor(getResources().getColor(colorPrimary));

                        binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                        binding.customCount.tvJrccount.setTextColor(getResources().getColor(colorPrimary));
                        binding.customCount.tvJrcnme.setTextColor(getResources().getColor(colorPrimary));

                        binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                        binding.customCount.tvAllname.setTextColor(getResources().getColor(colorPrimary));
                        binding.customCount.tvAllcount.setTextColor(getResources().getColor(colorPrimary));
//
//                        try {
//                            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
//                            if (selectedThemeColor != -1) {
//
//                                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//                                binding.customCount.tvYrcnme.setTextColor(getResources().getColor(white));
//                                binding.customCount.tvYrccount.setTextColor(getResources().getColor(white));
//
//                                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                                binding.customCount.tvLmname.setTextColor(getResources().getColor(selectedThemeColor));
//                                binding.customCount.tvLmcount.setTextColor(getResources().getColor(selectedThemeColor));
//
//                                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                                binding.customCount.tvJrccount.setTextColor(getResources().getColor(selectedThemeColor));
//                                binding.customCount.tvJrcnme.setTextColor(getResources().getColor(selectedThemeColor));
//
//                                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                                binding.customCount.tvAllname.setTextColor(getResources().getColor(selectedThemeColor));
//                                binding.customCount.tvAllcount.setTextColor(getResources().getColor(selectedThemeColor));
//
////
////                                binding.customCount.tvJrcnme.setTextColor(getResources().getColor(white));
////                                binding.customCount.tvJrccount.setTextColor(getResources().getColor(white));
////                                binding.customCount.tvAllcount.setTextColor(getResources().getColor(selectedThemeColor));
////                                binding.customCount.tvAllname.setTextColor(getResources().getColor(selectedThemeColor));
////                                binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
////                                binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
////                                binding.customCount.tvLmname.setTextColor(getResources().getColor(selectedThemeColor));
////                                binding.customCount.tvLmcount.setTextColor(getResources().getColor(selectedThemeColor));
//
//
//                                //set themes for linearlayouts for jrc yrc member top5 bottom5
//                                if (selectedThemeColor == R.color.redcroosbg_1) {
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_selectedbg));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_2) {
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_selectedbg));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//
//                                } else if (selectedThemeColor == R.color.redcroosbg_3) {
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_selectedbg));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_4) {
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_selectedbg));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_5) {
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_selectedbg));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_6) {
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_7) {
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_seleetedbg));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_8) {
//
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//
//
//                                }
//                            } else {
//                                countColorSelection();
//                                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//
//                        }
                        GlobalDeclaration.Selection_type = "YRC";
                        reload();
                    }
                } else {
                    Snackbar snackbar = Snackbar
                            .make(binding.customCount.llJrc, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

        });
        binding.customCount.llLm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isOnline(getActivity())) {
                    {
                        setupViewPagermMembeship(binding.viewpagerHome);

                        binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
                        binding.customCount.tvLmcount.setTextColor(getResources().getColor(white));
                        binding.customCount.tvLmname.setTextColor(getResources().getColor(white));

                        binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                        binding.customCount.tvYrccount.setTextColor(getResources().getColor(colorPrimary));
                        binding.customCount.tvYrcnme.setTextColor(getResources().getColor(colorPrimary));

                        binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                        binding.customCount.tvJrccount.setTextColor(getResources().getColor(colorPrimary));
                        binding.customCount.tvJrcnme.setTextColor(getResources().getColor(colorPrimary));

                        binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                        binding.customCount.tvAllname.setTextColor(getResources().getColor(colorPrimary));
                        binding.customCount.tvAllcount.setTextColor(getResources().getColor(colorPrimary));
//                        try {
//                            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
//                            if (selectedThemeColor != -1) {
//
//                                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//                                binding.customCount.tvLmcount.setTextColor(getResources().getColor(white));
//                                binding.customCount.tvLmname.setTextColor(getResources().getColor(white));
//
//                                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                                binding.customCount.tvYrccount.setTextColor(getResources().getColor(selectedThemeColor));
//                                binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
//
//                                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                                binding.customCount.tvJrccount.setTextColor(getResources().getColor(selectedThemeColor));
//                                binding.customCount.tvJrcnme.setTextColor(getResources().getColor(selectedThemeColor));
//
//                                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                                binding.customCount.tvAllname.setTextColor(getResources().getColor(selectedThemeColor));
//                                binding.customCount.tvAllcount.setTextColor(getResources().getColor(selectedThemeColor));
//
////
////                                binding.customCount.tvJrcnme.setTextColor(getResources().getColor(white));
////                                binding.customCount.tvJrccount.setTextColor(getResources().getColor(white));
////                                binding.customCount.tvAllcount.setTextColor(getResources().getColor(selectedThemeColor));
////                                binding.customCount.tvAllname.setTextColor(getResources().getColor(selectedThemeColor));
////                                binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
////                                binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
////                                binding.customCount.tvLmname.setTextColor(getResources().getColor(selectedThemeColor));
////                                binding.customCount.tvLmcount.setTextColor(getResources().getColor(selectedThemeColor));
//
//
//                                //set themes for linearlayouts for jrc yrc member top5 bottom5
//                                if (selectedThemeColor == R.color.redcroosbg_1) {
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme1_selectedbg));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_2) {
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme2_selectedbg));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//
//                                } else if (selectedThemeColor == R.color.redcroosbg_3) {
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme3_selectedbg));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_4) {
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme4_selectedbg));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_5) {
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme5_selectedbg));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_6) {
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_7) {
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme7_seleetedbg));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_8) {
//
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//
//
//                                }
//                            } else {
//                                countColorSelection();
//                                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//
//                        }
                        GlobalDeclaration.Selection_type = "Membership";
                        reload();
                    }
                } else {
                    Snackbar snackbar = Snackbar
                            .make(binding.customCount.llJrc, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

        });
        binding.customCount.llAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isOnline(getActivity())) {
                    {

                        if (GlobalDeclaration.role.contains("D")) {
                            setupViewPagerAll(binding.viewpagerHome, "Mandals");
                        } else {
                            setupViewPagerAll(binding.viewpagerHome, "Districts");
                        }
                        binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
                        binding.customCount.tvAllname.setTextColor(getResources().getColor(white));
                        binding.customCount.tvAllcount.setTextColor(getResources().getColor(white));

                        binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                        binding.customCount.tvYrccount.setTextColor(getResources().getColor(colorPrimary));
                        binding.customCount.tvYrcnme.setTextColor(getResources().getColor(colorPrimary));

                        binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                        binding.customCount.tvJrccount.setTextColor(getResources().getColor(colorPrimary));
                        binding.customCount.tvJrcnme.setTextColor(getResources().getColor(colorPrimary));

                        binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                        binding.customCount.tvLmcount.setTextColor(getResources().getColor(colorPrimary));
                        binding.customCount.tvLmname.setTextColor(getResources().getColor(colorPrimary));

//                        try {
//                            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
//                            if (selectedThemeColor != -1) {
//
//                                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//                                binding.customCount.tvAllname.setTextColor(getResources().getColor(white));
//                                binding.customCount.tvAllcount.setTextColor(getResources().getColor(white));
//
//                                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                                binding.customCount.tvYrccount.setTextColor(getResources().getColor(selectedThemeColor));
//                                binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
//
//                                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                                binding.customCount.tvJrccount.setTextColor(getResources().getColor(selectedThemeColor));
//                                binding.customCount.tvJrcnme.setTextColor(getResources().getColor(selectedThemeColor));
//
//                                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                                binding.customCount.tvLmcount.setTextColor(getResources().getColor(selectedThemeColor));
//                                binding.customCount.tvLmname.setTextColor(getResources().getColor(selectedThemeColor));
//
////
////                                binding.customCount.tvJrcnme.setTextColor(getResources().getColor(white));
////                                binding.customCount.tvJrccount.setTextColor(getResources().getColor(white));
////                                binding.customCount.tvAllcount.setTextColor(getResources().getColor(selectedThemeColor));
////                                binding.customCount.tvAllname.setTextColor(getResources().getColor(selectedThemeColor));
////                                binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
////                                binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
////                                binding.customCount.tvLmname.setTextColor(getResources().getColor(selectedThemeColor));
////                                binding.customCount.tvLmcount.setTextColor(getResources().getColor(selectedThemeColor));
//
//
//                                //set themes for linearlayouts for jrc yrc member top5 bottom5
//                                if (selectedThemeColor == R.color.redcroosbg_1) {
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme1_selectedbg));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_2) {
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme2_selectedbg));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//
//                                } else if (selectedThemeColor == R.color.redcroosbg_3) {
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme3_selectedbg));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_4) {
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme4_selectedbg));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_5) {
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme5_selectedbg));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_6) {
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_7) {
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme7_seleetedbg));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                                } else if (selectedThemeColor == R.color.redcroosbg_8) {
//
//                                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//                                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//
//
//                                }
//                            } else {
//                                countColorSelection();
//                                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//
//                        }
                        GlobalDeclaration.Selection_type = "JRC";
                        reload();
                    }
                } else {
                    Snackbar snackbar = Snackbar
                            .make(binding.customCount.llJrc, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

        });


        return binding.getRoot();

    }

    @Override
    public void onDetach() {
        ihomeListener = null; // => avoid leaking, thanks @Deepscorn
        super.onDetach();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            ihomeListener = (IHomeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TextClicked");
        }
    }

    private void countColorSelection() {
        binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
        binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
        binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
        binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
    }

    private void reload() {

        adapter.notifyDataSetChanged();
        binding.viewpagerHome.invalidate();

        //pager.setCurrentItem(0);
    }


    private void setupViewPagerAll(ViewPager viewPager_homer, String mandals) {
        adapter = new HomeViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new NewAllDistrictsFragment(), mandals, type);
        adapter.addFragment(new GenderwiseFragment(), "Gender & Age", type);
        adapter.addFragment(new BloodwiseFragment(), "Blood Groups", type);
        adapter.addFragment(new GovtPvtFragment(), "Govt vs Pvt", type);
        viewPager_homer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.viewpagerHome.invalidate();
    }

    private void setupViewPagerYRC(ViewPager viewPager_homer) {
        adapter = new HomeViewPagerAdapter(getActivity().getSupportFragmentManager());
        // adapter.addFragment(new NewAllDistrictsFragment(), "Districts", type);
        adapter.addFragment(new GenderwiseFragment(), "Gender & Age", type);
        adapter.addFragment(new BloodwiseFragment(), "Blood Groups", type);
        adapter.addFragment(new GovtPvtFragment(), "Govt vs Pvt", type);
        viewPager_homer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.viewpagerHome.invalidate();
    }

    private void setupViewPagerJRC(ViewPager viewPager_homer) {
        adapter = new HomeViewPagerAdapter(getActivity().getSupportFragmentManager());
        // adapter.addFragment(new NewAllDistrictsFragment(), "Districts", type);
        adapter.addFragment(new OnlyGenderwiseFragment(), "Gender", type);
        adapter.addFragment(new AgewiseFragment(), "Age", type);
        adapter.addFragment(new GovtPvtFragment(), "GovtVsPvt", type);
        viewPager_homer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.viewpagerHome.invalidate();
    }

    private void setupViewPagermMembeship(ViewPager viewPager_homer) {
        adapter = new HomeViewPagerAdapter(getActivity().getSupportFragmentManager());
        // adapter.addFragment(new NewAllDistrictsFragment(), "Districts", type);
        adapter.addFragment(new OnlyGenderwiseFragment(), "Gender", type);
        adapter.addFragment(new BloodwiseFragment(), "Blood Groups", type);
        adapter.addFragment(new AgewiseFragment(), "Age", type);
        viewPager_homer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.viewpagerHome.invalidate();
    }

    private void setCountsForDashboard(DashboardCountResponse dashboardCountResponse) {
        if (dashboardCountResponse != null) {

            int total = dashboardCountResponse.getJrc() + dashboardCountResponse.getYrc() + dashboardCountResponse.getMs();
            binding.customCount.tvAllcount.setText(String.valueOf(total));
            binding.customCount.tvJrccount.setText(String.valueOf(dashboardCountResponse.getJrc()));
            binding.customCount.tvYrccount.setText(String.valueOf(dashboardCountResponse.getYrc()));
            binding.customCount.tvLmcount.setText(String.valueOf(dashboardCountResponse.getMs()));

        }
    }


    class HomeViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private FragmentManager mFragmentManager;

        HomeViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        public void addFragment(Fragment fragment, String title, String type) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public int getItemPosition(Object object) {
// POSITION_NONE makes it possible to reload the PagerAdapter
            return POSITION_NONE;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

    public static class DayAxisValueFormatter extends ValueFormatter {
        private final BarLineChartBase<?> chart;


        public DayAxisValueFormatter(BarLineChartBase<?> chart) {
            this.chart = chart;
        }

        @Override
        public String getFormattedValue(float value) {
            return "" + (value);
        }

        @Override
        public String getBarLabel(BarEntry barEntry) {
            return "" + (barEntry);
        }
    }
}
