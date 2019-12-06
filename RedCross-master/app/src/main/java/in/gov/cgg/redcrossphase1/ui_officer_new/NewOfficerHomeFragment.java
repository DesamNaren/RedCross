package in.gov.cgg.redcrossphase1.ui_officer_new;

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
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.charts.LineChart;
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
import in.gov.cgg.redcrossphase1.ui_officer.govtpvt.GovtPvtViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.home_distrcit.CustomDistricClass;
import in.gov.cgg.redcrossphase1.ui_officer.home_distrcit.DistrictViewModel;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragemt_home_officer_new, container, false);


        setupViewPager(binding.viewpagerHome);
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
//
//        binding.viewpagerHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            public void onPageScrollStateChanged(int state) {
//            }
//
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            public void onPageSelected(int position) {
//                if (position == 0) {
//                    Objects.requireNonNull(getActivity()).setTitle("District wise");
//
//                } else if (position == 1) {
//                    Objects.requireNonNull(getActivity()).setTitle("Gender wise");
//
//                } else if (position == 1) {
//                    Objects.requireNonNull(getActivity()).setTitle("Bloodgroup wise");
//
//                } else if (position == 1) {
//                    Objects.requireNonNull(getActivity()).setTitle("Age wise");
//                } else {
//                    Objects.requireNonNull(getActivity()).setTitle("Dashboard");
//
//                }
//            }
//        });

        districtViewModel.getDashboardCounts("JRC", GlobalDeclaration.userID, GlobalDeclaration.districtId)
                .observe(Objects.requireNonNull(getActivity()), new Observer<DashboardCountResponse>() {

                    @Override
                    public void onChanged(@Nullable DashboardCountResponse dashboardCountResponse) {
                        if (dashboardCountResponse != null) {
                            GlobalDeclaration.counts = dashboardCountResponse;
                            setCountsForDashboard(dashboardCountResponse);
                        }
                    }
                });

//        try {
//            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
//            if (selectedThemeColor != -1) {
//
//                //set theme for age gender top 5 bottom 5 name enrollement rank totalcount fab textcolour and backgrounds
//                tv_age.setBackgroundColor(getResources().getColor(selectedThemeColor));
//                tv_top5district.setBackgroundColor(getResources().getColor(selectedThemeColor));
//                tv_bottom5.setBackgroundColor(getResources().getColor(selectedThemeColor));
//                tv_gender.setBackgroundColor(getResources().getColor(selectedThemeColor));
//                tv_blg.setBackgroundColor(getResources().getColor(selectedThemeColor));
//                tv_govtpvt.setBackgroundColor(getResources().getColor(selectedThemeColor));
//                tv_top5distName.setTextColor(getResources().getColor(selectedThemeColor));
//                tv_top5distEnrollement.setTextColor(getResources().getColor(selectedThemeColor));
//                tv_top5distRank.setTextColor(getResources().getColor(selectedThemeColor));
//                tv_btm5distrctName.setTextColor(getResources().getColor(selectedThemeColor));
//                tv_btm5distrctEnrollement.setTextColor(getResources().getColor(selectedThemeColor));
//                tv_btm5distrctRank.setTextColor(getResources().getColor(selectedThemeColor));
//                fabmenu.setMenuButtonColorNormal(getResources().getColor(selectedThemeColor));
//                tv_total.setTextColor(getResources().getColor(selectedThemeColor));
//                countColorSelection();
//                ll_jrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//                tv_jrcname.setTextColor(getResources().getColor(white));
//                tv_jrcocunt.setTextColor(getResources().getColor(white));
//                tv_yrcname.setTextColor(getResources().getColor(selectedThemeColor));
//                tv_yrccount.setTextColor(getResources().getColor(selectedThemeColor));
//                tv_lmname.setTextColor(getResources().getColor(selectedThemeColor));
//                tv_lmcount.setTextColor(getResources().getColor(selectedThemeColor));
//
//                tv_yrcname.setTextColor(getResources().getColor(selectedThemeColor));
//                tv_yrcname.setTextColor(getResources().getColor(selectedThemeColor));
//                tv_lmname.setTextColor(getResources().getColor(selectedThemeColor));
//                tv_lmcount.setTextColor(getResources().getColor(selectedThemeColor));
//
//
//                //set themes for linearlayouts for jrc yrc member top5 bottom5
//                if (selectedThemeColor == R.color.redcroosbg_1) {
//                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_selectedbg));
//                    ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//                    ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//                    ll_nameenrollRankTop.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//                    ll_nameenrollRankBottom.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//                } else if (selectedThemeColor == R.color.redcroosbg_2) {
//                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_selectedbg));
//                    ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//                    ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//                    ll_nameenrollRankTop.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//                    ll_nameenrollRankBottom.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//                } else if (selectedThemeColor == R.color.redcroosbg_3) {
//                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_selectedbg));
//                    ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                    ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                    ll_nameenrollRankTop.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                    ll_nameenrollRankBottom.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                } else if (selectedThemeColor == R.color.redcroosbg_4) {
//                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_selectedbg));
//                    ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                    ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                    ll_nameenrollRankTop.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                    ll_nameenrollRankBottom.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                } else if (selectedThemeColor == R.color.redcroosbg_5) {
//                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_selectedbg));
//                    ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                    ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                    ll_nameenrollRankTop.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                    ll_nameenrollRankBottom.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                } else if (selectedThemeColor == R.color.redcroosbg_6) {
//                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
//                    ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                    ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                    ll_nameenrollRankTop.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                    ll_nameenrollRankBottom.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                } else if (selectedThemeColor == R.color.redcroosbg_7) {
//                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_seleetedbg));
//                    ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                    ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                    ll_nameenrollRankTop.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                    ll_nameenrollRankBottom.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                } else if (selectedThemeColor == R.color.redcroosbg_8) {
//
//                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//                    ll_yrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                    ll_lm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//
//
//                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//                    tv_jrcocunt.setTextColor(getResources().getColor(white));
//                    tv_jrcocunt.setTextColor(getResources().getColor(white));
//
//                    ll_nameenrollRankTop.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                    ll_nameenrollRankBottom.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                }
//            } else {
//                tv_age.setBackgroundColor(getResources().getColor(colorPrimary));
//                tv_top5district.setBackgroundColor(getResources().getColor(colorPrimary));
//                tv_bottom5.setBackgroundColor(getResources().getColor(colorPrimary));
//                tv_gender.setBackgroundColor(getResources().getColor(colorPrimary));
//                tv_blg.setBackgroundColor(getResources().getColor(colorPrimary));
//                tv_govtpvt.setBackgroundColor(getResources().getColor(colorPrimary));
//                fabmenu.setMenuButtonColorNormal(getResources().getColor(colorPrimary));
//                tv_top5distName.setTextColor(getResources().getColor(colorPrimary));
//                tv_top5distEnrollement.setTextColor(getResources().getColor(colorPrimary));
//                tv_top5distRank.setTextColor(getResources().getColor(colorPrimary));
//                tv_btm5distrctName.setTextColor(getResources().getColor(colorPrimary));
//                tv_btm5distrctEnrollement.setTextColor(getResources().getColor(colorPrimary));
//                tv_btm5distrctRank.setTextColor(getResources().getColor(colorPrimary));
//                tv_total.setTextColor(getResources().getColor(colorPrimary));
//                tv_jrcocunt.setTextColor(getResources().getColor(colorPrimary));
//                tv_jrcname.setTextColor(getResources().getColor(colorPrimary));
//                tv_yrcname.setTextColor(getResources().getColor(colorPrimary));
//                tv_yrccount.setTextColor(getResources().getColor(colorPrimary));
//                tv_lmname.setTextColor(getResources().getColor(colorPrimary));
//                tv_lmcount.setTextColor(getResources().getColor(colorPrimary));
//
//                tv_jrcname.setTextColor(getResources().getColor(white));
//                tv_jrcocunt.setTextColor(getResources().getColor(white));
//                countColorSelection();
//                ll_jrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }

//        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                binding.refreshLayout.setRefreshing(false);
//                // code on swipe refresh
//                //callDashboardServices();
//
//            }
//        });
//        binding.refreshLayout.setColorSchemeColors(Color.YELLOW);


        if (CheckInternet.isOnline(getActivity())) {

            //callDashboardServices();

        } else {
            Snackbar snackbar = Snackbar.make(binding.customCount.llAll, "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        }


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

                    if (selectedThemeColor != -1) {

                        binding.customCount.tvJrcnme.setTextColor(getResources().getColor(white));
                        binding.customCount.tvJrcnme.setTextColor(getResources().getColor(white));
                        binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvYrccount.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvLmname.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvLmcount.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvAllcount.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvAllname.setTextColor(getResources().getColor(selectedThemeColor));

                        countColorSelection();
                        binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));

                        if (selectedThemeColor == R.color.redcroosbg_1) {
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_selectedbg));
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));


                        } else if (selectedThemeColor == R.color.redcroosbg_2) {
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_selectedbg));
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_3) {
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_selectedbg));
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                        } else if (selectedThemeColor == R.color.redcroosbg_4) {
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_selectedbg));
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                        } else if (selectedThemeColor == R.color.redcroosbg_5) {
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_selectedbg));
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                        } else if (selectedThemeColor == R.color.redcroosbg_6) {
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                        } else if (selectedThemeColor == R.color.redcroosbg_7) {
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_seleetedbg));
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                        }


                    }


//                    bloodwiseVm.getBlood("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
//                            observe(getActivity(), new Observer<List<BloodGroups>>() {
//                                @Override
//                                public void onChanged(@Nullable List<BloodGroups> bloodGroupsList) {
//                                    if (bloodGroupsList != null) {
//                                        generateDataPie(bloodGroupsList);
//                                    }
//                                }
//                            });
//
//                    genderwiseViewModel.getGender("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID)
//                            .observe(getActivity(), new Observer<List<Genders>>() {
//                                @Override
//                                public void onChanged(@Nullable List<Genders> gendersList) {
//                                    if (gendersList != null) {
//                                        generateGenderPiechart(gendersList);
//                                    }
//                                }
//                            });
//                    agewiseViewModel.getAges("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
//                            observe(getActivity(), new Observer<List<Age>>() {
//                                @Override
//                                public void onChanged(@Nullable List<Age> ageList) {
//                                    if (ageList != null) {
//                                        drawAge2BarGraph(ageList);
//                                        //  generateDataLine1(ageList);
//                                    }
//                                }
//                            });
//
//                    districtViewModel.getTopDistricts("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).observe(getActivity(), new Observer<List<Top5>>() {
//                        @Override
//                        public void onChanged(@Nullable List<Top5> top5List) {
//                            if (top5List != null) {
//                                //generateTop5Districts(top5List);
//                                // drawGraph(top5List);
//                                setDataForTopList(top5List);
//                            }
//                        }
//                    });
//                    districtViewModel.getBottomDistricts("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID)
//                            .observe(getActivity(), new Observer<List<Top5>>() {
//                                @Override
//                                public void onChanged(@Nullable List<Top5> bottom5list) {
//                                    if (bottom5list != null) {
//                                        //generateTop5Districts(top5List);
//                                        // drawGraph(bottom5list);
//                                        setDataForBottomList(bottom5list);
//                                    }
//                                }
//                            });


                } else {
                    Snackbar snackbar = Snackbar.make(binding.customCount.llAll, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

        });

        binding.customCount.llYrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isOnline(getActivity())) {

                    if (selectedThemeColor != -1) {

                        binding.customCount.tvYrcnme.setTextColor(getResources().getColor(white));
                        binding.customCount.tvYrccount.setTextColor(getResources().getColor(white));
                        binding.customCount.tvJrcnme.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvJrccount.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvLmcount.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvLmname.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvAllcount.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvAllname.setTextColor(getResources().getColor(selectedThemeColor));

                        countColorSelection();
                        binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));

                        if (selectedThemeColor == R.color.redcroosbg_1) {
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_selectedbg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));


                        } else if (selectedThemeColor == R.color.redcroosbg_2) {
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_selectedbg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_3) {
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_selectedbg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_4) {
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_selectedbg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_5) {
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_selectedbg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_6) {
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_7) {
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_seleetedbg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));

                        }

                    }


//                    bloodwiseVm.getBlood("YRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
//                            observe(getActivity(), new Observer<List<BloodGroups>>() {
//                                @Override
//                                public void onChanged(@Nullable List<BloodGroups> bloodGroupsList) {
//                                    if (bloodGroupsList != null) {
//                                        generateDataPie(bloodGroupsList);
//                                    }
//                                }
//                            });
//
//                    genderwiseViewModel.getGender("YRC", GlobalDeclaration.districtId, GlobalDeclaration.userID)
//                            .observe(getActivity(), new Observer<List<Genders>>() {
//                                @Override
//                                public void onChanged(@Nullable List<Genders> gendersList) {
//                                    if (gendersList != null) {
//                                        generateGenderPiechart(gendersList);
//                                    }
//                                }
//                            });
//                    agewiseViewModel.getAges("YRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
//                            observe(getActivity(), new Observer<List<Age>>() {
//                                @Override
//                                public void onChanged(@Nullable List<Age> ageList) {
//                                    if (ageList != null) {
//                                        drawAge2BarGraph(ageList);
//                                        //  generateDataLine1(ageList);
//                                    }
//                                }
//                            });
//
//                    districtViewModel.getTopDistricts("YRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).observe(getActivity(), new Observer<List<Top5>>() {
//                        @Override
//                        public void onChanged(@Nullable List<Top5> top5List) {
//                            if (top5List != null) {
//                                //generateTop5Districts(top5List);
//                                // drawGraph(top5List);
//                                setDataForTopList(top5List);
//                            }
//                        }
//                    });
//                    districtViewModel.getBottomDistricts("YRC", GlobalDeclaration.districtId, GlobalDeclaration.userID)
//                            .observe(getActivity(), new Observer<List<Top5>>() {
//                                @Override
//                                public void onChanged(@Nullable List<Top5> bottom5list) {
//                                    if (bottom5list != null) {
//                                        //generateTop5Districts(top5List);
//                                        // drawGraph(bottom5list);
//                                        setDataForBottomList(bottom5list);
//                                    }
//                                }
//                            });

                } else {
                    Snackbar snackbar = Snackbar
                            .make(binding.customCount.llAll, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

        });
        binding.customCount.llLm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckInternet.isOnline(getActivity())) {

                    if (selectedThemeColor != -1) {

                        binding.customCount.tvLmname.setTextColor(getResources().getColor(white));
                        binding.customCount.tvLmcount.setTextColor(getResources().getColor(white));
                        binding.customCount.tvYrccount.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvJrccount.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvJrcnme.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvAllcount.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvAllname.setTextColor(getResources().getColor(selectedThemeColor));

                        countColorSelection();
                        binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));

                        if (selectedThemeColor == R.color.redcroosbg_1) {
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme1_selectedbg));
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_2) {
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme2_selectedbg));
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_3) {
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme3_selectedbg));
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_4) {
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme4_selectedbg));
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_5) {
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme5_selectedbg));
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_6) {
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_7) {
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme7_seleetedbg));
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));

                        }


                    }
//                    bloodwiseVm.getBlood("Membership", GlobalDeclaration.districtId, GlobalDeclaration.userID).
//                            observe(getActivity(), new Observer<List<BloodGroups>>() {
//                                @Override
//                                public void onChanged(@Nullable List<BloodGroups> bloodGroupsList) {
//                                    if (bloodGroupsList != null) {
//                                        generateDataPie(bloodGroupsList);
//                                    }
//                                }
//                            });
//
//                    genderwiseViewModel.getGender("Membership", GlobalDeclaration.districtId, GlobalDeclaration.userID)
//                            .observe(getActivity(), new Observer<List<Genders>>() {
//                                @Override
//                                public void onChanged(@Nullable List<Genders> gendersList) {
//                                    if (gendersList != null) {
//                                        generateGenderPiechart(gendersList);
//                                    }
//                                }
//                            });
//                    agewiseViewModel.getAges("Membership", GlobalDeclaration.districtId, GlobalDeclaration.userID).
//                            observe(getActivity(), new Observer<List<Age>>() {
//                                @Override
//                                public void onChanged(@Nullable List<Age> ageList) {
//                                    if (ageList != null) {
//                                        drawAge2BarGraph(ageList);
//                                        //  generateDataLine1(ageList);
//                                    }
//                                }
//                            });
//
//                    districtViewModel.getTopDistricts("Membership", GlobalDeclaration.districtId, GlobalDeclaration.userID).observe(getActivity(), new Observer<List<Top5>>() {
//                        @Override
//                        public void onChanged(@Nullable List<Top5> top5List) {
//                            if (top5List != null) {
//                                //generateTop5Districts(top5List);
//                                // drawGraph(top5List);
//                                setDataForTopList(top5List);
//                            }
//                        }
//                    });
//                    districtViewModel.getBottomDistricts("Membership", GlobalDeclaration.districtId, GlobalDeclaration.userID)
//                            .observe(getActivity(), new Observer<List<Top5>>() {
//                                @Override
//                                public void onChanged(@Nullable List<Top5> bottom5list) {
//                                    if (bottom5list != null) {
//                                        //generateTop5Districts(top5List);
//                                        // drawGraph(bottom5list);
//                                        setDataForBottomList(bottom5list);
//                                    }
//                                }
//                            });

                } else {
                    Snackbar snackbar = Snackbar
                            .make(binding.customCount.llAll, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
        binding.customCount.llAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckInternet.isOnline(getActivity())) {

                    if (selectedThemeColor != -1) {

                        binding.customCount.tvAllname.setTextColor(getResources().getColor(white));
                        binding.customCount.tvAllcount.setTextColor(getResources().getColor(white));

                        binding.customCount.tvYrccount.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));

                        binding.customCount.tvJrccount.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvJrcnme.setTextColor(getResources().getColor(selectedThemeColor));

                        binding.customCount.tvLmname.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvLmcount.setTextColor(getResources().getColor(selectedThemeColor));

                        countColorSelection();
                        binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));

                        if (selectedThemeColor == R.color.redcroosbg_1) {
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme1_selectedbg));
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_2) {
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme2_selectedbg));
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_3) {
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme3_selectedbg));
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_4) {
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme4_selectedbg));
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_5) {
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme5_selectedbg));
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_6) {
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_7) {
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme7_seleetedbg));
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));

                        }


                    }
//                    bloodwiseVm.getBlood("Membership", GlobalDeclaration.districtId, GlobalDeclaration.userID).
//                            observe(getActivity(), new Observer<List<BloodGroups>>() {
//                                @Override
//                                public void onChanged(@Nullable List<BloodGroups> bloodGroupsList) {
//                                    if (bloodGroupsList != null) {
//                                        generateDataPie(bloodGroupsList);
//                                    }
//                                }
//                            });
//
//                    genderwiseViewModel.getGender("Membership", GlobalDeclaration.districtId, GlobalDeclaration.userID)
//                            .observe(getActivity(), new Observer<List<Genders>>() {
//                                @Override
//                                public void onChanged(@Nullable List<Genders> gendersList) {
//                                    if (gendersList != null) {
//                                        generateGenderPiechart(gendersList);
//                                    }
//                                }
//                            });
//                    agewiseViewModel.getAges("Membership", GlobalDeclaration.districtId, GlobalDeclaration.userID).
//                            observe(getActivity(), new Observer<List<Age>>() {
//                                @Override
//                                public void onChanged(@Nullable List<Age> ageList) {
//                                    if (ageList != null) {
//                                        drawAge2BarGraph(ageList);
//                                        //  generateDataLine1(ageList);
//                                    }
//                                }
//                            });
//
//                    districtViewModel.getTopDistricts("Membership", GlobalDeclaration.districtId, GlobalDeclaration.userID).observe(getActivity(), new Observer<List<Top5>>() {
//                        @Override
//                        public void onChanged(@Nullable List<Top5> top5List) {
//                            if (top5List != null) {
//                                //generateTop5Districts(top5List);
//                                // drawGraph(top5List);
//                                setDataForTopList(top5List);
//                            }
//                        }
//                    });
//                    districtViewModel.getBottomDistricts("Membership", GlobalDeclaration.districtId, GlobalDeclaration.userID)
//                            .observe(getActivity(), new Observer<List<Top5>>() {
//                                @Override
//                                public void onChanged(@Nullable List<Top5> bottom5list) {
//                                    if (bottom5list != null) {
//                                        //generateTop5Districts(top5List);
//                                        // drawGraph(bottom5list);
//                                        setDataForBottomList(bottom5list);
//                                    }
//                                }
//                            });

                } else {
                    Snackbar snackbar = Snackbar
                            .make(binding.customCount.llAll, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });


        return binding.getRoot();

    }

    private void countColorSelection() {
        binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
        binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
        binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
    }

    private void setupViewPager(ViewPager viewPager_homer) {
        HomeViewPagerAdapter adapter = new HomeViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new NewAllDistrictsFragment(), "Districtwise");
        adapter.addFragment(new GenderwiseFragment(), "Genderwise");
        adapter.addFragment(new BloodwiseFragment(), "BloodGroupwise");
        adapter.addFragment(new AgewiseFragment(), "Agewise");

        viewPager_homer.setAdapter(adapter);
    }

    private void setCountsForDashboard(DashboardCountResponse dashboardCountResponse) {
        if (dashboardCountResponse != null) {

            int total = dashboardCountResponse.getJrc() + dashboardCountResponse.getYrc() + dashboardCountResponse.getMs();
            binding.customCount.tvAllcount.setText(String.valueOf(total));
            binding.customCount.tvJrccount.setText(String.valueOf(dashboardCountResponse.getJrc()));
            binding.customCount.tvYrccount.setText(String.valueOf(dashboardCountResponse.getYrc()));
            binding.customCount.tvLmcount.setText(String.valueOf(dashboardCountResponse.getMs()));

        }
        //tv_lmcount.setText(String.valueOf(dashboardCountResponse.getTotal()));
    }
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    private void callDashboardServices() {
//        districtViewModel.getDashboardCounts("JRC", GlobalDeclaration.userID, GlobalDeclaration.districtId)
//                .observe(Objects.requireNonNull(getActivity()), new Observer<DashboardCountResponse>() {
//
//                    @Override
//                    public void onChanged(@Nullable DashboardCountResponse dashboardCountResponse) {
//                        if (dashboardCountResponse != null) {
//                            GlobalDeclaration.counts = dashboardCountResponse;
//                            setCountsForDashboard(dashboardCountResponse);
//                        }
//                    }
//                });
//        districtViewModel.getTopDistricts("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
//                observe(getActivity(), new Observer<List<Top5>>() {
//                    @Override
//                    public void onChanged(@Nullable List<Top5> top5List) {
//                        if (top5List != null) {
//                            setDataForTopList(top5List);
//                        }
//                    }
//                });
//        districtViewModel.getBottomDistricts("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID)
//                .observe(getActivity(), new Observer<List<Top5>>() {
//                    @Override
//                    public void onChanged(@Nullable List<Top5> bottom5list) {
//                        if (bottom5list != null) {
//                            //generateTop5Districts(top5List);
//                            // drawGraph(bottom5list);
//                            setDataForBottomList(bottom5list);
//                        }
//                    }
//                });
//
//        agewiseViewModel.getAges("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
//                observe(getActivity(), new Observer<List<Age>>() {
//                    @Override
//                    public void onChanged(@Nullable List<Age> ageList) {
//                        if (ageList != null) {
//                            //generateDataLine(ageList);
//                            drawAge2BarGraph(ageList);
//                        }
//                    }
//                });
//
//        bloodwiseVm.getBlood("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
//                observe(getActivity(), new Observer<List<BloodGroups>>() {
//                    @Override
//                    public void onChanged(@Nullable List<BloodGroups> bloodGroupsList) {
//                        if (bloodGroupsList != null) {
//                            generateDataPie(bloodGroupsList);
//                        }
//                    }
//                });
//        genderwiseViewModel.getGender("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
//                observe(getActivity(), new Observer<List<Genders>>() {
//                    @Override
//                    public void onChanged(@Nullable List<Genders> gendersList) {
//                        if (gendersList != null) {
//                            generateGenderPiechart(gendersList);
//                        }
//                    }
//                });
//
//
//        govtPvtViewModel.getGovtPvt(GlobalDeclaration.districtId).
//                observe(getActivity(), new Observer<List<Last10day>>() {
//                    @Override
//                    public void onChanged(@Nullable List<Last10day> last10dayList) {
//                        if (last10dayList != null) {
//                            try {
//                                if (last10dayList.size() > 1) {
//                                    generateDataLine(last10dayList);
//                                }
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//
//                            }
//                        }
//                    }
//                });
//    }

//
//    private void generateGenderPiechart(List<Genders> gendersList) {
//
//
//        ArrayList<PieEntry> entries = new ArrayList<>();
//
//        for (int i = 0; i < gendersList.size(); i++) {
//            entries.add(new PieEntry(Float.parseFloat(gendersList.get(i).getCount()), gendersList.get(i).getGender()));
//        }
//
//        PieDataSet d = new PieDataSet(entries, "Enrollments");
//        d.setValueTextColor(Color.WHITE);
//        d.setValueTextSize(10f);
//        d.setColors(ColorTemplate.JOYFUL_COLORS);
//
//        Legend l = binding..getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(true);
//
//        barchart_gender.getDescription().setEnabled(false);
//
//        barchart_gender.setDrawHoleEnabled(true);
//        barchart_gender.setHoleColor(Color.WHITE);
//
//        barchart_gender.setTransparentCircleColor(Color.WHITE);
//        barchart_gender.setTransparentCircleAlpha(110);
//
//        barchart_gender.setHoleRadius(58f);
//        barchart_gender.setTransparentCircleRadius(61f);
//
//        barchart_gender.setDrawCenterText(true);
//
//        barchart_gender.setRotationEnabled(false);
//        barchart_gender.setHighlightPerTapEnabled(true);
//
//        barchart_gender.setMaxAngle(180f); // HALF CHART
//        barchart_gender.setRotationAngle(180f);
//        barchart_gender.setCenterTextOffset(0, -20);
//
//
//        barchart_gender.animateY(1400, Easing.EaseInOutQuad);
//
////        Legend l = chart.getLegend();
////        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
////        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
////        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
////        l.setDrawInside(false);
////        l.setXEntrySpace(7f);
////        l.setYEntrySpace(0f);
////        l.setYOffset(0f);
//
//        // entry label styling
//        barchart_gender.setEntryLabelColor(Color.WHITE);
//        barchart_gender.setEntryLabelTextSize(12f);
//        PieData pieData = new PieData(d);
//
//        barchart_gender.setData(pieData);
//        barchart_gender.invalidate();
//    }
//
//
//    private void generateDataPie(List<BloodGroups> bloodGroupsList) {
//
//
//        ArrayList<PieEntry> entries = new ArrayList<>();
//
//        for (int i = 0; i < bloodGroupsList.size(); i++) {
//            entries.add(new PieEntry(Float.parseFloat(bloodGroupsList.get(i).getCount()), bloodGroupsList.get(i).getBloodGroup()));
//        }
//
//        bar_blood.getDescription().setEnabled(false);
//        bar_blood.setNoDataText("No Data available");
//
//        Legend l = bar_blood.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);
//        bar_blood.setDrawCenterText(true);
//        bar_blood.setRotationEnabled(true);
//
//        //.setUsePercentValues(true);
//        bar_blood.setDrawHoleEnabled(true);
//        bar_blood.setHoleColor(Color.WHITE);
//        bar_blood.setTransparentCircleColor(Color.WHITE);
//        bar_blood.setTransparentCircleAlpha(110);
//        bar_blood.setHoleRadius(58f);
//        bar_blood.setTransparentCircleRadius(61f);
//        bar_blood.setDrawCenterText(true);
//        bar_blood.setHighlightPerTapEnabled(true);
//        bar_blood.animateY(1400, Easing.EaseInOutQuad);
//
//
//        PieDataSet d = new PieDataSet(entries, "Enrollments ");
//        d.setSliceSpace(2f);
//        d.setValueLinePart1OffsetPercentage(80.f);
//        d.setValueLinePart1Length(0.2f);
//        d.setValueLinePart2Length(0.4f);
//        d.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        d.setColors(ColorTemplate.MATERIAL_COLORS);
//        d.setSliceSpace(2f);
//        d.setValueTextColor(Color.WHITE);
//        d.setValueTextSize(10f);
//        d.setSliceSpace(5f);
//
//
//        PieData pieData = new PieData(d);
//        pieData.setValueFormatter(new PercentFormatter());
//        pieData.setValueTextSize(11f);
//        pieData.setValueTextColor(Color.BLACK);
//        bar_blood.setData(pieData);
//        bar_blood.invalidate();
//    }
//
//
//
//    private void setDataForTopList(List<Top5> top5List) {
//        recyclerView_top.setHasFixedSize(true);
//        recyclerView_top.setLayoutManager(new LinearLayoutManager(getActivity()));
//        TBDistrictAdapter adapter1 = new TBDistrictAdapter(getActivity(), top5List, false);
//        recyclerView_top.setAdapter(adapter1);
//        adapter1.notifyDataSetChanged();
//
//    }
//
//    private void setDataForBottomList(List<Top5> bottomList) {
//
//        recyclerView_bottom.setHasFixedSize(true);
//        recyclerView_bottom.setLayoutManager(new LinearLayoutManager(getActivity()));
//        TBDistrictAdapter adapter2 = new TBDistrictAdapter(getActivity(), bottomList, true);
//        recyclerView_bottom.setAdapter(adapter2);
//        adapter2.notifyDataSetChanged();
//
//    }
//
//    public void drawAge2BarGraph(List<Age> ageList) {
//
//        ArrayList<BarEntry> entries = new ArrayList<>();
//
//        for (int i = 0; i < ageList.size(); i++) {
//            entries.add(new BarEntry(Float.parseFloat(ageList.get(i).getAge()), Float.parseFloat(ageList.get(i).getCount())));
//        }
//
//        BarDataSet d = new BarDataSet(entries, "Enrollments");
//        d.setColors(ColorTemplate.JOYFUL_COLORS);
//        d.setHighLightAlpha(255);
//        d.setValueTextSize(11f);
//
//        BarData cd = new BarData(d);
//        cd.setBarWidth(0.9f);
//
////        Description des = new Description();
////        des.setText("test chhart");
//
//        barchart_age.getDescription().setEnabled(false);
//
//        // if more than 60 entries are displayed in the chart, no values will be
//        // drawn
//        // barchart_age.setMaxVisibleValueCount(60);
//
//        // scaling can now only be done on x- and y-axis separately
//        barchart_age.setPinchZoom(false);
//
//        barchart_age.setDrawBarShadow(false);
//        barchart_age.setDrawGridBackground(false);
//
//        XAxis xAxis = barchart_age.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(false);
//
//        barchart_age.getAxisLeft().setDrawGridLines(false);
//
//
//        // add a nice and smooth animation
//        barchart_age.animateY(1500);
//
//        // barChart.getLegend().setEnabled(false);
//        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(barchart_age);
//
//
//        xAxis.setValueFormatter(xAxisFormatter);
//
//        XYMarkerView mv = new XYMarkerView(getActivity(), xAxisFormatter);
//        mv.setChartView(barchart_age); // For bounds control
//        barchart_age.setMarker(mv);
//
//        barchart_age.setData(cd);
//
//    }
//
//
//    private void setCountsForDashboard(DashboardCountResponse dashboardCountResponse) {
//        if (dashboardCountResponse != null) {
//
//            int total = dashboardCountResponse.getJrc() + dashboardCountResponse.getYrc() + dashboardCountResponse.getMs();
//            tv_jrcocunt.setText(String.valueOf(dashboardCountResponse.getJrc()));
//            tv_yrccount.setText(String.valueOf(dashboardCountResponse.getYrc()));
//            tv_lmcount.setText(String.valueOf(dashboardCountResponse.getMs()));
//            tv_totalcount.setText(String.valueOf(total));
//        }
//        //tv_lmcount.setText(String.valueOf(dashboardCountResponse.getTotal()));
//    }
//
//
//    @Override
//    public void onValueSelected(Entry e, Highlight h) {
//        if (e == null)
//            return;
//
//        RectF bounds = onValueSelectedRectF;
//        barchart_age.getBarBounds((BarEntry) e, bounds);
//        MPPointF position = barchart_age.getPosition(e, YAxis.AxisDependency.LEFT);
//
//        Log.i("bounds", bounds.toString());
//        Log.i("position", position.toString());
//
//        Log.i("Age:",
//                "low: " + barchart_age.getLowestVisibleX() + ", Count: "
//                        + barchart_age.getHighestVisibleX());
//
//        MPPointF.recycleInstance(position);
//    }
//
//    @Override
//    public void onNothingSelected() {
//
//    }
//
//    private void generateDataLine(List<Last10day> ageList) {
//
//        ArrayList<Entry> entriesGov = new ArrayList<>();
//        ArrayList<Entry> entriesPvt = new ArrayList<>();
//
//        for (int i = 0; i < ageList.size(); i++) {
//            entriesGov.add(new Entry(((i)), Float.parseFloat(ageList.get(i).getGov())));
//        }
//
//        for (int i = 0; i < ageList.size(); i++) {
//            entriesPvt.add(new Entry(((i)), Float.parseFloat(ageList.get(i).getPvt())));
//        }
//
//        LineDataSet lineDataSetGov = new LineDataSet(entriesGov, "Gov Enrollments");
//        lineDataSetGov.setLineWidth(2.5f);
//        lineDataSetGov.setCircleRadius(4.5f);
//        lineDataSetGov.setDrawValues(false);
//
//
//        LineDataSet lineDataSetPvt = new LineDataSet(entriesPvt, "Pvt Enrollments");
//        lineDataSetPvt.setLineWidth(2.5f);
//        lineDataSetPvt.setCircleRadius(4.5f);
//        lineDataSetPvt.setDrawValues(false);
//
//
//        lineDataSetGov.setColor(ContextCompat.getColor(getActivity(), R.color.green));
//        lineDataSetGov.setValueTextColor(ContextCompat.getColor(getActivity(), R.color.black));
//
//        lineDataSetPvt.setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
//        lineDataSetPvt.setValueTextColor(ContextCompat.getColor(getActivity(), R.color.black));
//
//        XAxis xAxis = lineChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTextSize(9f);
//        final List<String> stringList = new ArrayList<>();
//
//        for (int i = 0; i < ageList.size(); i++) {
//            stringList.add(ageList.get(i).getDate());
//        }
//        //final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr","March","test"};
//
//        ValueFormatter formatter = new ValueFormatter() {
//            @Override
//            public String getAxisLabel(float value, AxisBase axis) {
//                return stringList.get((int) value);
//            }
//
//        };
//
//        try {
//            LineXYMarkerView mv = new LineXYMarkerView(getActivity(), formatter);
//            mv.setChartView(lineChart);
//            lineChart.setMarker(mv);
//
//            xAxis.setGranularity(1f);
//            xAxis.setValueFormatter(formatter);
//
//            YAxis yAxisRight = lineChart.getAxisRight();
//            yAxisRight.setEnabled(false);
//
//            YAxis yAxisLeft = lineChart.getAxisLeft();
//            yAxisLeft.setGranularity(1f);
//
//            // get the legend (only possible after setting data)
//            Legend l = lineChart.getLegend();
//            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//            l.setDrawInside(false);
//            l.setXEntrySpace(7f);
//            l.setYEntrySpace(0f);
//            l.setYOffset(0f);
//
//
//            LineData data = new LineData(lineDataSetGov, lineDataSetPvt);
//            lineChart.setData(data);
//            lineChart.getDescription().setEnabled(false);
//            lineChart.animateX(1500);
//            lineChart.invalidate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//
//    }
//
//    public static class DayAxisValueFormatter extends ValueFormatter {
//        private final BarLineChartBase<?> chart;
//
//
//        public DayAxisValueFormatter(BarLineChartBase<?> chart) {
//            this.chart = chart;
//        }
//
//        @Override
//        public String getFormattedValue(float value) {
//            return "" + (value);
//        }
//
//        @Override
//        public String getBarLabel(BarEntry barEntry) {
//            return "" + (barEntry);
//        }
//    }

    class HomeViewPagerAdapter extends FragmentPagerAdapter {
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

        public void addFragment(Fragment fragment, String title) {
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
}
