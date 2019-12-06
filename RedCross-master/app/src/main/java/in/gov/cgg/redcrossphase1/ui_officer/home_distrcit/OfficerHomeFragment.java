package in.gov.cgg.redcrossphase1.ui_officer.home_distrcit;

import android.graphics.Color;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_officer.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.agewise.Age;
import in.gov.cgg.redcrossphase1.ui_officer.agewise.AgewiseViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport.AllDistrictsFragment;
import in.gov.cgg.redcrossphase1.ui_officer.bloodwise.BloodGroups;
import in.gov.cgg.redcrossphase1.ui_officer.bloodwise.BloodwiseViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.genderwise.Genders;
import in.gov.cgg.redcrossphase1.ui_officer.genderwise.GenderwiseViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.govtpvt.GovtPvtViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.govtpvt.Last10day;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;

import static android.content.Context.MODE_PRIVATE;
import static in.gov.cgg.redcrossphase1.R.color.colorPrimary;
import static in.gov.cgg.redcrossphase1.R.color.white;

public class OfficerHomeFragment extends Fragment implements OnChartValueSelectedListener {
    private final RectF onValueSelectedRectF = new RectF();
    // ProgressDialog pd;
    private LinearLayout ll_jrc;
    private LinearLayout ll_yrc;
    private LinearLayout ll_lm;
    int selectedThemeColor = -1;
    private BarChart barchart_age;
    private PieChart barchart_gender, bar_blood;
    private DistrictViewModel districtViewModel;
    SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView_top, recyclerView_bottom;
    private FloatingActionMenu fabmenu;
    private FloatingActionButton fab_distrcit, fab_age, fab_blood, fab_gender, fab_btm5, fab_drill, fab_districtwise;
    private AgewiseViewModel agewiseViewModel;
    private BloodwiseViewModel bloodwiseVm;
    private GenderwiseViewModel genderwiseViewModel;
    private TextView tv_top5district, tv_gender, tv_blg, tv_age, tv_bottom5;
    private NestedScrollView scroll;
    private LinearLayout ll_tp5, ll_bm5, ll_genderwise, ll_agewise, ll_blodwise, ll_govtpvt;
    private String gender, bloodwise, age, top5, btm5;
    private Fragment fragment;
    private LineChart lineChart;
    GovtPvtViewModel govtPvtViewModel;
    private LinearLayout ll_nameenrollRankTop, ll_nameenrollRankBottom;
    private TextView tv_jrcocunt, tv_yrccount, tv_lmcount, tv_yrcname, tv_jrcname, tv_lmname, tv_totalcount, tv_govtpvt, tv_top5distName, tv_top5distEnrollement, tv_top5distRank, tv_btm5distrctName, tv_btm5distrctEnrollement, tv_btm5distrctRank, tv_total;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragemt_home_officer, container, false);

//        pd = new ProgressDialog(getActivity());
//        pd.setMessage("Loading ,Please wait");

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
        findAllVIEWS(root);


        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {

                //set theme for age gender top 5 bottom 5 name enrollement rank totalcount fab textcolour and backgrounds
                tv_age.setBackgroundColor(getResources().getColor(selectedThemeColor));
                tv_top5district.setBackgroundColor(getResources().getColor(selectedThemeColor));
                tv_bottom5.setBackgroundColor(getResources().getColor(selectedThemeColor));
                tv_gender.setBackgroundColor(getResources().getColor(selectedThemeColor));
                tv_blg.setBackgroundColor(getResources().getColor(selectedThemeColor));
                tv_govtpvt.setBackgroundColor(getResources().getColor(selectedThemeColor));
                tv_top5distName.setTextColor(getResources().getColor(selectedThemeColor));
                tv_top5distEnrollement.setTextColor(getResources().getColor(selectedThemeColor));
                tv_top5distRank.setTextColor(getResources().getColor(selectedThemeColor));
                tv_btm5distrctName.setTextColor(getResources().getColor(selectedThemeColor));
                tv_btm5distrctEnrollement.setTextColor(getResources().getColor(selectedThemeColor));
                tv_btm5distrctRank.setTextColor(getResources().getColor(selectedThemeColor));
                fabmenu.setMenuButtonColorNormal(getResources().getColor(selectedThemeColor));
                tv_total.setTextColor(getResources().getColor(selectedThemeColor));
                countColorSelection();
                ll_jrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
                tv_jrcname.setTextColor(getResources().getColor(white));
                tv_jrcocunt.setTextColor(getResources().getColor(white));
                tv_yrcname.setTextColor(getResources().getColor(selectedThemeColor));
                tv_yrccount.setTextColor(getResources().getColor(selectedThemeColor));
                tv_lmname.setTextColor(getResources().getColor(selectedThemeColor));
                tv_lmcount.setTextColor(getResources().getColor(selectedThemeColor));

                tv_yrcname.setTextColor(getResources().getColor(selectedThemeColor));
                tv_yrcname.setTextColor(getResources().getColor(selectedThemeColor));
                tv_lmname.setTextColor(getResources().getColor(selectedThemeColor));
                tv_lmcount.setTextColor(getResources().getColor(selectedThemeColor));


                //set themes for linearlayouts for jrc yrc member top5 bottom5
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_selectedbg));
                    ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                    ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                    ll_nameenrollRankTop.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                    ll_nameenrollRankBottom.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_selectedbg));
                    ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                    ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                    ll_nameenrollRankTop.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                    ll_nameenrollRankBottom.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_selectedbg));
                    ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                    ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                    ll_nameenrollRankTop.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                    ll_nameenrollRankBottom.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_selectedbg));
                    ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                    ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                    ll_nameenrollRankTop.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                    ll_nameenrollRankBottom.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_selectedbg));
                    ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                    ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                    ll_nameenrollRankTop.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                    ll_nameenrollRankBottom.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
                    ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                    ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                    ll_nameenrollRankTop.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                    ll_nameenrollRankBottom.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_seleetedbg));
                    ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                    ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                    ll_nameenrollRankTop.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                    ll_nameenrollRankBottom.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_8) {

                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
                    ll_yrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                    ll_lm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));


                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
                    tv_jrcocunt.setTextColor(getResources().getColor(white));
                    tv_jrcocunt.setTextColor(getResources().getColor(white));

                    ll_nameenrollRankTop.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                    ll_nameenrollRankBottom.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                }
            } else {
                tv_age.setBackgroundColor(getResources().getColor(colorPrimary));
                tv_top5district.setBackgroundColor(getResources().getColor(colorPrimary));
                tv_bottom5.setBackgroundColor(getResources().getColor(colorPrimary));
                tv_gender.setBackgroundColor(getResources().getColor(colorPrimary));
                tv_blg.setBackgroundColor(getResources().getColor(colorPrimary));
                tv_govtpvt.setBackgroundColor(getResources().getColor(colorPrimary));
                fabmenu.setMenuButtonColorNormal(getResources().getColor(colorPrimary));
                tv_top5distName.setTextColor(getResources().getColor(colorPrimary));
                tv_top5distEnrollement.setTextColor(getResources().getColor(colorPrimary));
                tv_top5distRank.setTextColor(getResources().getColor(colorPrimary));
                tv_btm5distrctName.setTextColor(getResources().getColor(colorPrimary));
                tv_btm5distrctEnrollement.setTextColor(getResources().getColor(colorPrimary));
                tv_btm5distrctRank.setTextColor(getResources().getColor(colorPrimary));
                tv_total.setTextColor(getResources().getColor(colorPrimary));
                tv_jrcocunt.setTextColor(getResources().getColor(colorPrimary));
                tv_jrcname.setTextColor(getResources().getColor(colorPrimary));
                tv_yrcname.setTextColor(getResources().getColor(colorPrimary));
                tv_yrccount.setTextColor(getResources().getColor(colorPrimary));
                tv_lmname.setTextColor(getResources().getColor(colorPrimary));
                tv_lmcount.setTextColor(getResources().getColor(colorPrimary));

                tv_jrcname.setTextColor(getResources().getColor(white));
                tv_jrcocunt.setTextColor(getResources().getColor(white));
                countColorSelection();
                ll_jrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));

            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                // code on swipe refresh
                callDashboardServices();

            }
        });
        swipeRefreshLayout.setColorSchemeColors(Color.YELLOW);


//
        if (GlobalDeclaration.role != null) {
            if (GlobalDeclaration.role.contains("D")) {
                Objects.requireNonNull(getActivity()).setTitle("Dashboard");
                tv_top5district.setText("Top 5 Mandals");
                tv_bottom5.setText("Bottom 5 Mandals");
                //ll_govtpvt.setVisibility(View.GONE);


            } else {
                Objects.requireNonNull(getActivity()).setTitle("Dashboard");
                //tv_bottom5.setText("Bottom 5 Mandals");
                tv_top5district.setText("Top 5 Districts");
                tv_bottom5.setText("Bottom 5 Districts");
                // ll_govtpvt.setVisibility(View.VISIBLE);

            }
        }
        if (CheckInternet.isOnline(getActivity())) {

            callDashboardServices();

        } else {
            Snackbar snackbar = Snackbar.make(ll_jrc, "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        }


//        fab_drill.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fragment = new GetDrilldownFragment();
//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.nav_host_fragment_officer, fragment);
//                fragmentTransaction.commit();
//            }
//        });

        fab_districtwise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new AllDistrictsFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_officer, fragment);
                fragmentTransaction.commit();
            }
        });


        ll_jrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckInternet.isOnline(getActivity())) {

                    if (selectedThemeColor != -1) {

                        tv_jrcocunt.setTextColor(getResources().getColor(white));
                        tv_jrcname.setTextColor(getResources().getColor(white));
                        tv_yrcname.setTextColor(getResources().getColor(selectedThemeColor));
                        tv_yrccount.setTextColor(getResources().getColor(selectedThemeColor));
                        tv_lmname.setTextColor(getResources().getColor(selectedThemeColor));
                        tv_lmcount.setTextColor(getResources().getColor(selectedThemeColor));

                        countColorSelection();
                        ll_jrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));

                        if (selectedThemeColor == R.color.redcroosbg_1) {
                            ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_selectedbg));
                            ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                            ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));


                        } else if (selectedThemeColor == R.color.redcroosbg_2) {
                            ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_selectedbg));
                            ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                            ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_3) {
                            ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_selectedbg));
                            ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                            ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                        } else if (selectedThemeColor == R.color.redcroosbg_4) {
                            ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_selectedbg));
                            ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                            ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                        } else if (selectedThemeColor == R.color.redcroosbg_5) {
                            ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_selectedbg));
                            ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                            ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                        } else if (selectedThemeColor == R.color.redcroosbg_6) {
                            ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
                            ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                            ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                        } else if (selectedThemeColor == R.color.redcroosbg_7) {
                            ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_seleetedbg));
                            ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                            ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                        }


                    }


                    bloodwiseVm.getBlood("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                            observe(getActivity(), new Observer<List<BloodGroups>>() {
                                @Override
                                public void onChanged(@Nullable List<BloodGroups> bloodGroupsList) {
                                    if (bloodGroupsList != null) {
                                        generateDataPie(bloodGroupsList);
                                    }
                                }
                            });

                    genderwiseViewModel.getGender("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID)
                            .observe(getActivity(), new Observer<List<Genders>>() {
                                @Override
                                public void onChanged(@Nullable List<Genders> gendersList) {
                                    if (gendersList != null) {
                                        generateGenderPiechart(gendersList);
                                    }
                                }
                            });
                    agewiseViewModel.getAges("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                            observe(getActivity(), new Observer<List<Age>>() {
                                @Override
                                public void onChanged(@Nullable List<Age> ageList) {
                                    if (ageList != null) {
                                        drawAge2BarGraph(ageList);
                                        //  generateDataLine1(ageList);
                                    }
                                }
                            });

                    districtViewModel.getTopDistricts("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).observe(getActivity(), new Observer<List<Top5>>() {
                        @Override
                        public void onChanged(@Nullable List<Top5> top5List) {
                            if (top5List != null) {
                                //generateTop5Districts(top5List);
                                // drawGraph(top5List);
                                setDataForTopList(top5List);
                            }
                        }
                    });
                    districtViewModel.getBottomDistricts("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID)
                            .observe(getActivity(), new Observer<List<Top5>>() {
                                @Override
                                public void onChanged(@Nullable List<Top5> bottom5list) {
                                    if (bottom5list != null) {
                                        //generateTop5Districts(top5List);
                                        // drawGraph(bottom5list);
                                        setDataForBottomList(bottom5list);
                                    }
                                }
                            });


                } else {
                    Snackbar snackbar = Snackbar.make(ll_jrc, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

        });

        ll_yrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isOnline(getActivity())) {

                    if (selectedThemeColor != -1) {

                        tv_yrcname.setTextColor(getResources().getColor(white));
                        tv_yrccount.setTextColor(getResources().getColor(white));
                        tv_jrcname.setTextColor(getResources().getColor(selectedThemeColor));
                        tv_jrcocunt.setTextColor(getResources().getColor(selectedThemeColor));
                        tv_lmname.setTextColor(getResources().getColor(selectedThemeColor));
                        tv_lmcount.setTextColor(getResources().getColor(selectedThemeColor));

                        countColorSelection();
                        ll_yrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));

                        if (selectedThemeColor == R.color.redcroosbg_1) {
                            ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_selectedbg));
                            ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                            ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));


                        } else if (selectedThemeColor == R.color.redcroosbg_2) {
                            ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_selectedbg));
                            ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                            ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_3) {
                            ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_selectedbg));
                            ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                            ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_4) {
                            ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_selectedbg));
                            ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                            ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_5) {
                            ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_selectedbg));
                            ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                            ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_6) {
                            ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
                            ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                            ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_7) {
                            ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_seleetedbg));
                            ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                            ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));

                        }

                    }


                    bloodwiseVm.getBlood("YRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                            observe(getActivity(), new Observer<List<BloodGroups>>() {
                                @Override
                                public void onChanged(@Nullable List<BloodGroups> bloodGroupsList) {
                                    if (bloodGroupsList != null) {
                                        generateDataPie(bloodGroupsList);
                                    }
                                }
                            });

                    genderwiseViewModel.getGender("YRC", GlobalDeclaration.districtId, GlobalDeclaration.userID)
                            .observe(getActivity(), new Observer<List<Genders>>() {
                                @Override
                                public void onChanged(@Nullable List<Genders> gendersList) {
                                    if (gendersList != null) {
                                        generateGenderPiechart(gendersList);
                                    }
                                }
                            });
                    agewiseViewModel.getAges("YRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                            observe(getActivity(), new Observer<List<Age>>() {
                                @Override
                                public void onChanged(@Nullable List<Age> ageList) {
                                    if (ageList != null) {
                                        drawAge2BarGraph(ageList);
                                        //  generateDataLine1(ageList);
                                    }
                                }
                            });

                    districtViewModel.getTopDistricts("YRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).observe(getActivity(), new Observer<List<Top5>>() {
                        @Override
                        public void onChanged(@Nullable List<Top5> top5List) {
                            if (top5List != null) {
                                //generateTop5Districts(top5List);
                                // drawGraph(top5List);
                                setDataForTopList(top5List);
                            }
                        }
                    });
                    districtViewModel.getBottomDistricts("YRC", GlobalDeclaration.districtId, GlobalDeclaration.userID)
                            .observe(getActivity(), new Observer<List<Top5>>() {
                                @Override
                                public void onChanged(@Nullable List<Top5> bottom5list) {
                                    if (bottom5list != null) {
                                        //generateTop5Districts(top5List);
                                        // drawGraph(bottom5list);
                                        setDataForBottomList(bottom5list);
                                    }
                                }
                            });

                } else {
                    Snackbar snackbar = Snackbar
                            .make(ll_yrc, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

        });
        ll_lm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckInternet.isOnline(getActivity())) {

                    if (selectedThemeColor != -1) {

                        tv_lmname.setTextColor(getResources().getColor(white));
                        tv_lmcount.setTextColor(getResources().getColor(white));
                        tv_yrcname.setTextColor(getResources().getColor(selectedThemeColor));
                        tv_yrccount.setTextColor(getResources().getColor(selectedThemeColor));
                        tv_jrcname.setTextColor(getResources().getColor(selectedThemeColor));
                        tv_jrcocunt.setTextColor(getResources().getColor(selectedThemeColor));

                        countColorSelection();
                        ll_lm.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));

                        if (selectedThemeColor == R.color.redcroosbg_1) {
                            ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme1_selectedbg));
                            ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                            ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_2) {
                            ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme2_selectedbg));
                            ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                            ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_3) {
                            ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme3_selectedbg));
                            ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                            ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_4) {
                            ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme4_selectedbg));
                            ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                            ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_5) {
                            ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme5_selectedbg));
                            ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                            ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_6) {
                            ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
                            ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                            ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_7) {
                            ll_lm.setBackground(getResources().getDrawable(R.drawable.lltheme7_seleetedbg));
                            ll_yrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                            ll_jrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));

                        }


                    }
                    bloodwiseVm.getBlood("Membership", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                            observe(getActivity(), new Observer<List<BloodGroups>>() {
                                @Override
                                public void onChanged(@Nullable List<BloodGroups> bloodGroupsList) {
                                    if (bloodGroupsList != null) {
                                        generateDataPie(bloodGroupsList);
                                    }
                                }
                            });

                    genderwiseViewModel.getGender("Membership", GlobalDeclaration.districtId, GlobalDeclaration.userID)
                            .observe(getActivity(), new Observer<List<Genders>>() {
                                @Override
                                public void onChanged(@Nullable List<Genders> gendersList) {
                                    if (gendersList != null) {
                                        generateGenderPiechart(gendersList);
                                    }
                                }
                            });
                    agewiseViewModel.getAges("Membership", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                            observe(getActivity(), new Observer<List<Age>>() {
                                @Override
                                public void onChanged(@Nullable List<Age> ageList) {
                                    if (ageList != null) {
                                        drawAge2BarGraph(ageList);
                                        //  generateDataLine1(ageList);
                                    }
                                }
                            });

                    districtViewModel.getTopDistricts("Membership", GlobalDeclaration.districtId, GlobalDeclaration.userID).observe(getActivity(), new Observer<List<Top5>>() {
                        @Override
                        public void onChanged(@Nullable List<Top5> top5List) {
                            if (top5List != null) {
                                //generateTop5Districts(top5List);
                                // drawGraph(top5List);
                                setDataForTopList(top5List);
                            }
                        }
                    });
                    districtViewModel.getBottomDistricts("Membership", GlobalDeclaration.districtId, GlobalDeclaration.userID)
                            .observe(getActivity(), new Observer<List<Top5>>() {
                                @Override
                                public void onChanged(@Nullable List<Top5> bottom5list) {
                                    if (bottom5list != null) {
                                        //generateTop5Districts(top5List);
                                        // drawGraph(bottom5list);
                                        setDataForBottomList(bottom5list);
                                    }
                                }
                            });

                } else {
                    Snackbar snackbar = Snackbar
                            .make(ll_lm, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });


        return root;

    }

    private void countColorSelection() {
        ll_jrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
        ll_yrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
        ll_lm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void callDashboardServices() {
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
        districtViewModel.getTopDistricts("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                observe(getActivity(), new Observer<List<Top5>>() {
                    @Override
                    public void onChanged(@Nullable List<Top5> top5List) {
                        if (top5List != null) {
                            setDataForTopList(top5List);
                        }
                    }
                });
        districtViewModel.getBottomDistricts("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID)
                .observe(getActivity(), new Observer<List<Top5>>() {
                    @Override
                    public void onChanged(@Nullable List<Top5> bottom5list) {
                        if (bottom5list != null) {
                            //generateTop5Districts(top5List);
                            // drawGraph(bottom5list);
                            setDataForBottomList(bottom5list);
                        }
                    }
                });

        agewiseViewModel.getAges("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                observe(getActivity(), new Observer<List<Age>>() {
                    @Override
                    public void onChanged(@Nullable List<Age> ageList) {
                        if (ageList != null) {
                            //generateDataLine(ageList);
                            drawAge2BarGraph(ageList);
                        }
                    }
                });

        bloodwiseVm.getBlood("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                observe(getActivity(), new Observer<List<BloodGroups>>() {
                    @Override
                    public void onChanged(@Nullable List<BloodGroups> bloodGroupsList) {
                        if (bloodGroupsList != null) {
                            generateDataPie(bloodGroupsList);
                        }
                    }
                });
        genderwiseViewModel.getGender("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                observe(getActivity(), new Observer<List<Genders>>() {
                    @Override
                    public void onChanged(@Nullable List<Genders> gendersList) {
                        if (gendersList != null) {
                            generateGenderPiechart(gendersList);
                        }
                    }
                });


        govtPvtViewModel.getGovtPvt(GlobalDeclaration.districtId).
                observe(getActivity(), new Observer<List<Last10day>>() {
                    @Override
                    public void onChanged(@Nullable List<Last10day> last10dayList) {
                        if (last10dayList != null) {
                            try {
                                if (last10dayList.size() > 1) {
                                    generateDataLine(last10dayList);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }
                    }
                });
    }


    private void generateGenderPiechart(List<Genders> gendersList) {


        ArrayList<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i < gendersList.size(); i++) {
            entries.add(new PieEntry(Float.parseFloat(gendersList.get(i).getCount()), gendersList.get(i).getGender()));
        }

        PieDataSet d = new PieDataSet(entries, "Enrollments");
        d.setValueTextColor(Color.WHITE);
        d.setValueTextSize(10f);
        d.setColors(ColorTemplate.JOYFUL_COLORS);

        Legend l = barchart_gender.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
//        Legend l = barchart_gender.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(true);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);
//        //piechart.setUsePercentValues(true);
//        piechart.getDescription().setEnabled(false);
//        piechart.setExtraOffsets(5, 10, 5, 5);
//
//
//
//        piechart.setMaxAngle(180f); // HALF CHART
//        piechart.setRotationAngle(180f);
//        piechart.setCenterTextOffset(0, -20);
//
//
//        piechart.setDragDecelerationFrictionCoef(0.95f);
//
//        // pieChart.setCenterTextTypeface(tfLight);
//        //pieChart.setCenterText(generateCenterSpannableText());
//
//        piechart.setDrawHoleEnabled(true);
//        piechart.setHoleColor(Color.WHITE);
//        d.setValueTextColor(Color.WHITE);
//        d.setValueTextSize(10f);
//        piechart.setTransparentCircleColor(Color.WHITE);
//        piechart.setTransparentCircleAlpha(110);
//
//        piechart.setHoleRadius(58f);
//        piechart.setTransparentCircleRadius(61f);
//
//        piechart.setDrawCenterText(true);
//
//        piechart.setRotationAngle(0);
//        piechart.animateY(1400, Easing.EaseInOutQuad);
//        //enable rotation of the chart by touch
//        piechart.setRotationEnabled(true);
//        piechart.setHighlightPerTapEnabled(true);
//        // space between slices
//        d.setSliceSpace(2f);
//        d.setColors(ColorTemplate.JOYFUL_COLORS);

        barchart_gender.getDescription().setEnabled(false);

        barchart_gender.setDrawHoleEnabled(true);
        barchart_gender.setHoleColor(Color.WHITE);

        barchart_gender.setTransparentCircleColor(Color.WHITE);
        barchart_gender.setTransparentCircleAlpha(110);

        barchart_gender.setHoleRadius(58f);
        barchart_gender.setTransparentCircleRadius(61f);

        barchart_gender.setDrawCenterText(true);

        barchart_gender.setRotationEnabled(false);
        barchart_gender.setHighlightPerTapEnabled(true);

        barchart_gender.setMaxAngle(180f); // HALF CHART
        barchart_gender.setRotationAngle(180f);
        barchart_gender.setCenterTextOffset(0, -20);


        barchart_gender.animateY(1400, Easing.EaseInOutQuad);

//        Legend l = chart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);

        // entry label styling
        barchart_gender.setEntryLabelColor(Color.WHITE);
        barchart_gender.setEntryLabelTextSize(12f);
        PieData pieData = new PieData(d);

        barchart_gender.setData(pieData);
        barchart_gender.invalidate();
    }


    private void generateDataPie(List<BloodGroups> bloodGroupsList) {


        ArrayList<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i < bloodGroupsList.size(); i++) {
            entries.add(new PieEntry(Float.parseFloat(bloodGroupsList.get(i).getCount()), bloodGroupsList.get(i).getBloodGroup()));
        }

        bar_blood.getDescription().setEnabled(false);
        bar_blood.setNoDataText("No Data available");

        Legend l = bar_blood.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        bar_blood.setDrawCenterText(true);
        bar_blood.setRotationEnabled(true);

        //.setUsePercentValues(true);
        bar_blood.setDrawHoleEnabled(true);
        bar_blood.setHoleColor(Color.WHITE);
        bar_blood.setTransparentCircleColor(Color.WHITE);
        bar_blood.setTransparentCircleAlpha(110);
        bar_blood.setHoleRadius(58f);
        bar_blood.setTransparentCircleRadius(61f);
        bar_blood.setDrawCenterText(true);
        bar_blood.setHighlightPerTapEnabled(true);
        bar_blood.animateY(1400, Easing.EaseInOutQuad);


        PieDataSet d = new PieDataSet(entries, "Enrollments ");
        d.setSliceSpace(2f);
        d.setValueLinePart1OffsetPercentage(80.f);
        d.setValueLinePart1Length(0.2f);
        d.setValueLinePart2Length(0.4f);
        d.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        d.setColors(ColorTemplate.MATERIAL_COLORS);
        d.setSliceSpace(2f);
        d.setValueTextColor(Color.WHITE);
        d.setValueTextSize(10f);
        d.setSliceSpace(5f);


        PieData pieData = new PieData(d);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.BLACK);
        bar_blood.setData(pieData);
        bar_blood.invalidate();
    }


    private void findAllVIEWS(View root) {
        //labelView = (TextView) findViewById(R.id.label);
        swipeRefreshLayout = root.findViewById(R.id.refreshLayout);
        ll_jrc = root.findViewById(R.id.ll_jrc);
        ll_yrc = root.findViewById(R.id.ll_yrc);
        ll_lm = root.findViewById(R.id.ll_lm);
        Spinner spinYear = root.findViewById(R.id.spn_financialyear);
        // button_list = root.findViewById(R.id.button_list);
        //    barChart_distrcit = root.findViewById(R.id.barchart_district);
        //barChart_btm5 = root.findViewById(R.id.barchart_btm5);
        barchart_age = root.findViewById(R.id.barchart_age);
        barchart_gender = root.findViewById(R.id.barchart_gender);
        bar_blood = root.findViewById(R.id.barchart_blodd);

        tv_totalcount = root.findViewById(R.id.tv_totalcount);
        tv_jrcocunt = root.findViewById(R.id.tv_jrccount);
        tv_govtpvt = root.findViewById(R.id.tv_govtpvt);
        tv_yrccount = root.findViewById(R.id.tv_yrccount);
        tv_lmcount = root.findViewById(R.id.tv_lmcount);
        tv_lmname = root.findViewById(R.id.tv_lmname);
        tv_jrcname = root.findViewById(R.id.tv_jrcnme);
        tv_yrcname = root.findViewById(R.id.tv_yrcnme);

        recyclerView_top = root.findViewById(R.id.recyclerview_top);
        recyclerView_bottom = root.findViewById(R.id.recyclerview_bottom);

        fab_distrcit = root.findViewById(R.id.fabdistrcit);
        fab_btm5 = root.findViewById(R.id.fab_btm5);
        fab_blood = root.findViewById(R.id.fabblood);
        fab_age = root.findViewById(R.id.fabage);
        fab_gender = root.findViewById(R.id.fabgender);
        fabmenu = root.findViewById(R.id.fab_main);
        fab_districtwise = root.findViewById(R.id.fab_districtwise);
        fab_drill = root.findViewById(R.id.fab_drilldown);
        scroll = root.findViewById(R.id.scroll);

        tv_top5district = root.findViewById(R.id.tv_topp5distrct);
        tv_bottom5 = root.findViewById(R.id.tv_btm5distrct);
        tv_gender = root.findViewById(R.id.tv_gender);
        tv_blg = root.findViewById(R.id.tv_blg);
        tv_age = root.findViewById(R.id.tv_age);

        ll_tp5 = root.findViewById(R.id.ll_top5);
        ll_bm5 = root.findViewById(R.id.ll_btm5);
        ll_genderwise = root.findViewById(R.id.ll_genderwise);
        ll_blodwise = root.findViewById(R.id.ll_bloodwise);
        ll_agewise = root.findViewById(R.id.ll_agewise);
        ll_govtpvt = root.findViewById(R.id.ll_govtpvt);

        lineChart = root.findViewById(R.id.chart_govtpvt);


        tv_top5distName = root.findViewById(R.id.tv_top5distName);
        tv_top5distEnrollement = root.findViewById(R.id.tv_top5distEnrollement);
        tv_top5distRank = root.findViewById(R.id.tv_top5distRank);
        tv_btm5distrctName = root.findViewById(R.id.tv_btm5distrctName);
        tv_btm5distrctEnrollement = root.findViewById(R.id.tv_btm5distrctEnrollement);
        tv_btm5distrctRank = root.findViewById(R.id.tv_btm5distrctRank);

        ll_nameenrollRankTop = root.findViewById(R.id.ll_nameenrollRankTop);
        ll_nameenrollRankBottom = root.findViewById(R.id.ll_nameenrollRankBottom);


    }

    private void setDataForTopList(List<Top5> top5List) {
        recyclerView_top.setHasFixedSize(true);
        recyclerView_top.setLayoutManager(new LinearLayoutManager(getActivity()));
        TBDistrictAdapter adapter1 = new TBDistrictAdapter(getActivity(), top5List, false);
        recyclerView_top.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();

    }

    private void setDataForBottomList(List<Top5> bottomList) {

        recyclerView_bottom.setHasFixedSize(true);
        recyclerView_bottom.setLayoutManager(new LinearLayoutManager(getActivity()));
        TBDistrictAdapter adapter2 = new TBDistrictAdapter(getActivity(), bottomList, true);
        recyclerView_bottom.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();

    }

    public void drawAge2BarGraph(List<Age> ageList) {

        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < ageList.size(); i++) {
            entries.add(new BarEntry(Float.parseFloat(ageList.get(i).getAge()), Float.parseFloat(ageList.get(i).getCount())));
        }

        BarDataSet d = new BarDataSet(entries, "Enrollments");
        d.setColors(ColorTemplate.JOYFUL_COLORS);
        d.setHighLightAlpha(255);
        d.setValueTextSize(11f);

        BarData cd = new BarData(d);
        cd.setBarWidth(0.9f);

//        Description des = new Description();
//        des.setText("test chhart");

        barchart_age.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        // barchart_age.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        barchart_age.setPinchZoom(false);

        barchart_age.setDrawBarShadow(false);
        barchart_age.setDrawGridBackground(false);

        XAxis xAxis = barchart_age.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        barchart_age.getAxisLeft().setDrawGridLines(false);


        // add a nice and smooth animation
        barchart_age.animateY(1500);

        // barChart.getLegend().setEnabled(false);
        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(barchart_age);


        xAxis.setValueFormatter(xAxisFormatter);

        XYMarkerView mv = new XYMarkerView(getActivity(), xAxisFormatter);
        mv.setChartView(barchart_age); // For bounds control
        barchart_age.setMarker(mv);

        barchart_age.setData(cd);

    }


    private void setCountsForDashboard(DashboardCountResponse dashboardCountResponse) {
        if (dashboardCountResponse != null) {

            int total = dashboardCountResponse.getJrc() + dashboardCountResponse.getYrc() + dashboardCountResponse.getMs();
            tv_jrcocunt.setText(String.valueOf(dashboardCountResponse.getJrc()));
            tv_yrccount.setText(String.valueOf(dashboardCountResponse.getYrc()));
            tv_lmcount.setText(String.valueOf(dashboardCountResponse.getMs()));
        }
        //tv_lmcount.setText(String.valueOf(dashboardCountResponse.getTotal()));
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;

        RectF bounds = onValueSelectedRectF;
        barchart_age.getBarBounds((BarEntry) e, bounds);
        MPPointF position = barchart_age.getPosition(e, YAxis.AxisDependency.LEFT);

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        Log.i("Age:",
                "low: " + barchart_age.getLowestVisibleX() + ", Count: "
                        + barchart_age.getHighestVisibleX());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {

    }

    private void generateDataLine(List<Last10day> ageList) {

        ArrayList<Entry> entriesGov = new ArrayList<>();
        ArrayList<Entry> entriesPvt = new ArrayList<>();

        for (int i = 0; i < ageList.size(); i++) {
            entriesGov.add(new Entry(((i)), Float.parseFloat(ageList.get(i).getGov())));
        }

        for (int i = 0; i < ageList.size(); i++) {
            entriesPvt.add(new Entry(((i)), Float.parseFloat(ageList.get(i).getPvt())));
        }

        LineDataSet lineDataSetGov = new LineDataSet(entriesGov, "Gov Enrollments");
        lineDataSetGov.setLineWidth(2.5f);
        lineDataSetGov.setCircleRadius(4.5f);
        lineDataSetGov.setDrawValues(false);


        LineDataSet lineDataSetPvt = new LineDataSet(entriesPvt, "Pvt Enrollments");
        lineDataSetPvt.setLineWidth(2.5f);
        lineDataSetPvt.setCircleRadius(4.5f);
        lineDataSetPvt.setDrawValues(false);


        lineDataSetGov.setColor(ContextCompat.getColor(getActivity(), R.color.green));
        lineDataSetGov.setValueTextColor(ContextCompat.getColor(getActivity(), R.color.black));

        lineDataSetPvt.setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        lineDataSetPvt.setValueTextColor(ContextCompat.getColor(getActivity(), R.color.black));

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(9f);
        final List<String> stringList = new ArrayList<>();

        for (int i = 0; i < ageList.size(); i++) {
            stringList.add(ageList.get(i).getDate());
        }
        //final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr","March","test"};

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return stringList.get((int) value);
            }

        };

        try {
            LineXYMarkerView mv = new LineXYMarkerView(getActivity(), formatter);
            mv.setChartView(lineChart);
            lineChart.setMarker(mv);

            xAxis.setGranularity(1f);
            xAxis.setValueFormatter(formatter);

            YAxis yAxisRight = lineChart.getAxisRight();
            yAxisRight.setEnabled(false);

            YAxis yAxisLeft = lineChart.getAxisLeft();
            yAxisLeft.setGranularity(1f);

            // get the legend (only possible after setting data)
            Legend l = lineChart.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);
            l.setXEntrySpace(7f);
            l.setYEntrySpace(0f);
            l.setYOffset(0f);


            LineData data = new LineData(lineDataSetGov, lineDataSetPvt);
            lineChart.setData(data);
            lineChart.getDescription().setEnabled(false);
            lineChart.animateX(1500);
            lineChart.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
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
