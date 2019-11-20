package in.gov.cgg.redcrossphase1.ui_officer.home_distrcit;

import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_officer.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.agewise.Age;
import in.gov.cgg.redcrossphase1.ui_officer.agewise.AgewiseViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.bloodwise.BloodGroups;
import in.gov.cgg.redcrossphase1.ui_officer.bloodwise.BloodwiseViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.genderwise.Genders;
import in.gov.cgg.redcrossphase1.ui_officer.genderwise.GenderwiseViewModel;

import static in.gov.cgg.redcrossphase1.R.color.colorPrimary;
import static in.gov.cgg.redcrossphase1.R.color.white;

public class OfficerHomeFragment extends Fragment implements OnChartValueSelectedListener {
    private static final float END_SCALE = 0.7f;
    private final RectF onValueSelectedRectF = new RectF();
    LinearLayout l_datepicker, ll_jrc, ll_yrc, ll_lm;
    String from_date, to_date;
    SimpleDateFormat dateFormat;
    ArrayList<String> datesList;
    ArrayList<String> datesEntryList;
    ImageView home;
    Fragment fragment = null;
    List<String> list = new ArrayList<>();
    TextView tv_fromtodate;
    Spinner spinYear;
    Button button_list;
    //    BarChart barChart_distrcit, barChart_btm5;
    BarChart barchart_age;
    PieChart barchart_gender, bar_blood;
    DistrictViewModel districtViewModel;
    TextView tv_jrcocunt, tv_yrccount, tv_lmcount, tv_yrcname, tv_jrcname, tv_lmname;
    RecyclerView recyclerView_top, recyclerView_bottom;
    DistrictAdapter adapter1, adapter2;
    List<DistrictAdapter> districtAdapterList;
    List<DistrictResponse> topdistrictResponseList = new ArrayList<>();
    List<DistrictResponse> bottomdistrictResponseList = new ArrayList<>();
    FloatingActionMenu fabmenu;
    //  FragemtHomeBinding binding;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    //   BarChart chart;
    private Toolbar toolbar;
    //    private TextView labelView;
    private View contentView;
    private TabHost tabHost;
    //    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int pos;
    private FloatingActionButton fab_distrcit, fab_age, fab_blood, fab_gender, fab_btm5;
    private ScrollView scroll;
    private AgewiseViewModel agewiseViewModel;
    private BloodwiseViewModel bloodwiseVm;
    private GenderwiseViewModel genderwiseViewModel;
    private TextView tv_top5district, tv_gender, tv_blg, tv_age, tv_bottom5;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragemt_home_officer, container, false);

        districtViewModel =
                ViewModelProviders.of(this).get(DistrictViewModel.class);
        agewiseViewModel =
                ViewModelProviders.of(this).get(AgewiseViewModel.class);
        bloodwiseVm =
                ViewModelProviders.of(this).get(BloodwiseViewModel.class);
        genderwiseViewModel =
                ViewModelProviders.of(this).get(GenderwiseViewModel.class);


        findAllVIEWS(root);

        if (GlobalDeclaration.role != null) {
            if (GlobalDeclaration.role.contains("D")) {
                getActivity().setTitle("Top 5 Mandals");
                tv_top5district.setText("Top 5 Mandals");
                tv_bottom5.setText("Bottom 5 Mandals");
            } else if (GlobalDeclaration.role.contains("S")) {
                getActivity().setTitle("Top 5 Districts");
                //tv_bottom5.setText("Bottom 5 Mandals");
                tv_top5district.setText("Top 5 Districts");
                tv_bottom5.setText("Bottom 5 Districts");
            } else if (GlobalDeclaration.role.contains("G")) {
                getActivity().setTitle("Top 5 Districts");
                //tv_bottom5.setText("Bottom 5 Mandals");
                tv_top5district.setText("Top 5 Districts");
                tv_bottom5.setText("Bottom 5 Districts");
            } else {
                getActivity().setTitle("Dashboard");
                tv_bottom5.setText("");
                tv_top5district.setText("");

            }
        }
        districtViewModel.getDashboardCounts("JRC", GlobalDeclaration.userID, GlobalDeclaration.districtId)
                .observe(getActivity(), new Observer<DashboardCountResponse>() {
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

        fab_distrcit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        scroll.scrollTo(0, tv_top5district.getTop());
                        fabmenu.close(true);

                        //fabmenu.hideMenu(true);

                    }
                });
            }
        });
        fab_btm5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        scroll.scrollTo(0, tv_bottom5.getTop());
                        fabmenu.close(true);

                        //fabmenu.hideMenu(true);

                    }
                });
            }
        });
        fab_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        scroll.scrollTo(0, tv_age.getTop());
                        fabmenu.close(true);
                        //fabmenu.hideMenu(true);


                    }
                });

            }
        });
        fab_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        scroll.scrollTo(0, tv_gender.getTop());
                        fabmenu.close(true);
                        //fabmenu.hideMenu(true);


                    }
                });

            }
        });
        fab_blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        scroll.scrollTo(0, tv_blg.getTop());
                        fabmenu.close(true);
                        //fabmenu.hideMenu(true);

                    }
                });
            }
        });

        ll_jrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_jrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
                tv_jrcocunt.setTextColor(getResources().getColor(white));
                tv_jrcname.setTextColor(getResources().getColor(white));
//
                ll_yrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                tv_yrcname.setTextColor(getResources().getColor(colorPrimary));
                tv_yrccount.setTextColor(getResources().getColor(colorPrimary));


                ll_lm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                tv_lmcount.setTextColor(getResources().getColor(colorPrimary));
                tv_lmname.setTextColor(getResources().getColor(colorPrimary));


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

            }
        });

        ll_yrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_jrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                tv_jrcocunt.setTextColor(getResources().getColor(colorPrimary));
                tv_jrcname.setTextColor(getResources().getColor(colorPrimary));
//
                ll_yrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
                tv_yrcname.setTextColor(getResources().getColor(white));
                tv_yrccount.setTextColor(getResources().getColor(white));

                ll_lm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                tv_lmcount.setTextColor(getResources().getColor(colorPrimary));
                tv_lmname.setTextColor(getResources().getColor(colorPrimary));

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

            }
        });
        ll_lm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_lm.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
                tv_lmcount.setTextColor(getResources().getColor(white));
                tv_lmname.setTextColor(getResources().getColor(white));
//
                ll_yrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                tv_yrcname.setTextColor(getResources().getColor(colorPrimary));
                tv_yrccount.setTextColor(getResources().getColor(colorPrimary));

                ll_jrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                tv_jrcname.setTextColor(getResources().getColor(colorPrimary));
                tv_jrcocunt.setTextColor(getResources().getColor(colorPrimary));

                bloodwiseVm.getBlood("LM", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                        observe(getActivity(), new Observer<List<BloodGroups>>() {
                            @Override
                            public void onChanged(@Nullable List<BloodGroups> bloodGroupsList) {
                                if (bloodGroupsList != null) {
                                    generateDataPie(bloodGroupsList);
                                }
                            }
                        });

                genderwiseViewModel.getGender("LM", GlobalDeclaration.districtId, GlobalDeclaration.userID)
                        .observe(getActivity(), new Observer<List<Genders>>() {
                            @Override
                            public void onChanged(@Nullable List<Genders> gendersList) {
                                if (gendersList != null) {
                                    generateGenderPiechart(gendersList);
                                }
                            }
                        });
                agewiseViewModel.getAges("LM", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                        observe(getActivity(), new Observer<List<Age>>() {
                            @Override
                            public void onChanged(@Nullable List<Age> ageList) {
                                if (ageList != null) {
                                    drawAge2BarGraph(ageList);
                                    //  generateDataLine1(ageList);
                                }
                            }
                        });

                districtViewModel.getTopDistricts("LM", GlobalDeclaration.districtId, GlobalDeclaration.userID).observe(getActivity(), new Observer<List<Top5>>() {
                    @Override
                    public void onChanged(@Nullable List<Top5> top5List) {
                        if (top5List != null) {
                            //generateTop5Districts(top5List);
                            // drawGraph(top5List);
                            setDataForTopList(top5List);
                        }
                    }
                });
                districtViewModel.getBottomDistricts("LM", GlobalDeclaration.districtId, GlobalDeclaration.userID)
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

            }
        });


        return root;

    }

    private void generateGenderPiechart(List<Genders> gendersList) {

        ArrayList<PieEntry> entries = new ArrayList<>();

//        }
        barchart_gender.getDescription().setEnabled(false);

        for (int i = 0; i < gendersList.size(); i++) {
            entries.add(new PieEntry(Float.parseFloat(gendersList.get(i).getCount()), gendersList.get(i).getGender()));
        }

        barchart_gender.getDescription().setEnabled(false);
        barchart_gender.setExtraOffsets(5, 10, 5, 5);

        barchart_gender.setDragDecelerationFrictionCoef(0.95f);

        // barchart_gender.setCenterTextTypeface(tfLight);
        //barchart_gender.setCenterText(generateCenterSpannableText());

        barchart_gender.setDrawHoleEnabled(true);
        barchart_gender.setHoleColor(Color.WHITE);

        barchart_gender.setTransparentCircleColor(Color.WHITE);
        barchart_gender.setTransparentCircleAlpha(110);

        barchart_gender.setHoleRadius(58f);
        barchart_gender.setTransparentCircleRadius(61f);

        barchart_gender.setDrawCenterText(true);

        barchart_gender.setRotationAngle(0);
        // enable rotation of the chart by touch
        barchart_gender.setRotationEnabled(true);
        barchart_gender.setHighlightPerTapEnabled(true);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        PieDataSet d = new PieDataSet(entries, "");
        d.setSliceSpace(2f);
        // d.setValueLinePart1OffsetPercentage(80.f);
        //d.setValueLinePart1Length(0.2f);
        //d.setValueLinePart2Length(0.4f);
        //dataSet.setUsingSliceColorAsValueLineColor(true);

        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        //d.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        d.setColors(ColorTemplate.JOYFUL_COLORS);
        d.setSliceSpace(2f);
        d.setValueTextColor(Color.WHITE);
        d.setValueTextSize(10f);
        d.setSliceSpace(5f);
        PieData pieData = new PieData(d);
        //  pieData.setValueFormatter(new PercentFormatter());
        //pieData.setValueTextSize(11f);
        //  pieData.setValueTextColor(Color.BLACK);
        barchart_gender.setData(pieData);
        barchart_gender.animateY(1500);


        barchart_gender.invalidate();

    }

    private void generateDataPie(List<BloodGroups> bloodGroupsList) {


        ArrayList<PieEntry> entries = new ArrayList<>();

//        ArrayList<String> groups = new ArrayList<>();
//
//        groups.add(0, "Male");
//        groups.add(1, "Female");


//        for (int i = 0; i < 2; i++) {
//            entries.add(new PieEntry((float) ((Math.random() * 70) + 30), groups.get(i)));
//        }
        bar_blood.getDescription().setEnabled(false);

        for (int i = 0; i < bloodGroupsList.size(); i++) {
            entries.add(new PieEntry(Float.parseFloat(bloodGroupsList.get(i).getCount()), bloodGroupsList.get(i).getBloodGroup()));
        }

        // entries.add(new PieEntry(Float.parseFloat(genderResponse.getCount()), genderResponse.getGender()));


        PieDataSet d = new PieDataSet(entries, "Count");
        d.setSliceSpace(2f);
        d.setValueLinePart1OffsetPercentage(80.f);
        d.setValueLinePart1Length(0.2f);
        d.setValueLinePart2Length(0.4f);
        //dataSet.setUsingSliceColorAsValueLineColor(true);

        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        d.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        d.setColors(ColorTemplate.MATERIAL_COLORS);
//        d.setSliceSpace(2f);
//        d.setValueTextColor(Color.WHITE);
//        d.setValueTextSize(10f);
//        d.setSliceSpace(5f);
        PieData pieData = new PieData(d);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.BLACK);
        bar_blood.animateY(1500);

        bar_blood.setData(pieData);

        bar_blood.invalidate();
    }


    private void findAllVIEWS(View root) {
        //labelView = (TextView) findViewById(R.id.label);
        l_datepicker = root.findViewById(R.id.ll_picker);
        ll_jrc = root.findViewById(R.id.ll_jrc);
        ll_yrc = root.findViewById(R.id.ll_yrc);
        ll_lm = root.findViewById(R.id.ll_lm);
        spinYear = root.findViewById(R.id.spn_financialyear);
        // button_list = root.findViewById(R.id.button_list);
        //    barChart_distrcit = root.findViewById(R.id.barchart_district);
        //barChart_btm5 = root.findViewById(R.id.barchart_btm5);
        barchart_age = root.findViewById(R.id.barchart_age);
        barchart_gender = root.findViewById(R.id.barchart_gender);
        bar_blood = root.findViewById(R.id.barchart_blodd);

        tv_jrcocunt = root.findViewById(R.id.tv_jrccount);
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
        scroll = root.findViewById(R.id.scroll);

        tv_top5district = root.findViewById(R.id.tv_topp5distrct);
        tv_bottom5 = root.findViewById(R.id.tv_btm5distrct);
        tv_gender = root.findViewById(R.id.tv_gender);
        tv_blg = root.findViewById(R.id.tv_blg);
        tv_age = root.findViewById(R.id.tv_age);

    }

    private void setDataForTopList(List<Top5> top5List) {
        recyclerView_top.setHasFixedSize(true);
        recyclerView_top.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter1 = new DistrictAdapter(getActivity(), top5List);
        recyclerView_top.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();

    }

    private void setDataForBottomList(List<Top5> bottomList) {

        recyclerView_bottom.setHasFixedSize(true);
        recyclerView_bottom.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter2 = new DistrictAdapter(getActivity(), bottomList);
        recyclerView_bottom.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();

    }

    public void drawAge2BarGraph(List<Age> ageList) {

        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < ageList.size(); i++) {
            entries.add(new BarEntry(Float.parseFloat(ageList.get(i).getAge()), Float.parseFloat(ageList.get(i).getCount())));
        }

        BarDataSet d = new BarDataSet(entries, "");
        d.setColors(ColorTemplate.JOYFUL_COLORS);
        d.setHighLightAlpha(255);

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

        Log.i("x-index",
                "low: " + barchart_age.getLowestVisibleX() + ", high: "
                        + barchart_age.getHighestVisibleX());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {

    }

    public class DayAxisValueFormatter extends ValueFormatter {
        private final BarLineChartBase<?> chart;


        public DayAxisValueFormatter(BarLineChartBase<?> chart) {
            this.chart = chart;
        }

        @Override
        public String getFormattedValue(float value) {
            return "" + (value);
        }
    }

}
