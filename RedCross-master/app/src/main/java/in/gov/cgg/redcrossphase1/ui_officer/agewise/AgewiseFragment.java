package in.gov.cgg.redcrossphase1.ui_officer.agewise;


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_officer.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.home_distrcit.OfficerHomeFragment;
import in.gov.cgg.redcrossphase1.ui_officer.home_distrcit.XYMarkerView;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;

import static in.gov.cgg.redcrossphase1.R.color.colorPrimary;
import static in.gov.cgg.redcrossphase1.R.color.white;

public class AgewiseFragment extends Fragment {

    private BarChart barchart_age;
    private TextView tv_jrcocunt, tv_yrccount, tv_lmcount, tv_yrcname, tv_jrcname;
    private LinearLayout ll_jrc, ll_yrc, ll_lm;
    //FragmentAgewiseBinding binding;
    private AgewiseViewModel agewiseViewModel;
    private TextView tv_lmname;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        agewiseViewModel =
                ViewModelProviders.of(this).get(AgewiseViewModel.class);
        View root = inflater.inflate(R.layout.fragment_agewise, container, false);

        //binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_agewise);
        GlobalDeclaration.home = false;


        barchart_age = root.findViewById(R.id.chart_age);

        //loadAges();
        //generateDataLine(10);

        ll_jrc = root.findViewById(R.id.ll_jrc);
        ll_yrc = root.findViewById(R.id.ll_yrc);
        ll_lm = root.findViewById(R.id.ll_lm);
        tv_jrcocunt = root.findViewById(R.id.tv_jrccount);
        tv_yrccount = root.findViewById(R.id.tv_yrccount);
        tv_lmcount = root.findViewById(R.id.tv_lmcount);
        tv_jrcname = root.findViewById(R.id.tv_jrcnme);
        tv_yrcname = root.findViewById(R.id.tv_yrcnme);
        tv_lmname = root.findViewById(R.id.tv_lmname);
        tv_lmcount = root.findViewById(R.id.tv_lmcount);

        Objects.requireNonNull(getActivity()).setTitle("Age wise");

        if (GlobalDeclaration.counts != null) {
            setCountsForDashboard(GlobalDeclaration.counts);
        }

        if (CheckInternet.isOnline(getActivity())) {
            agewiseViewModel.getAges("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                    observe(getActivity(), new Observer<List<Age>>() {
                        @Override
                        public void onChanged(@Nullable List<Age> ageList) {
                            if (ageList != null) {
                                generateDataBar(ageList);
                                //  generateDataLine1(ageList);
                            }
                        }
                    });
        } else {
            Snackbar snackbar = Snackbar
                    .make(ll_jrc, "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        ll_jrc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                if (CheckInternet.isOnline(getActivity())) {
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


                    agewiseViewModel.getAges("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                            observe(Objects.requireNonNull(getActivity()), new Observer<List<Age>>() {
                                @Override
                                public void onChanged(@Nullable List<Age> ageList) {
                                    if (ageList != null) {
                                        generateDataBar(ageList);
                                        //  generateDataLine1(ageList);
                                    }
                                }
                            });
                } else {
                    Snackbar snackbar = Snackbar
                            .make(ll_jrc, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

        });

        ll_yrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckInternet.isOnline(getActivity())) {
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


                    agewiseViewModel.getAges("YRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                            observe(Objects.requireNonNull(getActivity()), new Observer<List<Age>>() {
                                @Override
                                public void onChanged(@Nullable List<Age> ageList) {
                                    if (ageList != null) {
                                        generateDataBar(ageList);
                                        //  generateDataLine1(ageList);
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

                    agewiseViewModel.getAges("Membership", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                            observe(Objects.requireNonNull(getActivity()), new Observer<List<Age>>() {
                                @Override
                                public void onChanged(@Nullable List<Age> ageList) {
                                    if (ageList != null) {
                                        generateDataBar(ageList);
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


    private void setCountsForDashboard(DashboardCountResponse dashboardCountResponse) {
        if (dashboardCountResponse != null) {

            tv_jrcocunt.setText(String.valueOf(dashboardCountResponse.getJrc()));
            tv_yrccount.setText(String.valueOf(dashboardCountResponse.getYrc()));
            tv_lmcount.setText(String.valueOf(dashboardCountResponse.getMs()));
        }
    }

    private void generateDataBar(List<Age> ageList) {
        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < ageList.size(); i++) {
            entries.add(new BarEntry(Float.parseFloat(ageList.get(i).getAge()), Float.parseFloat(ageList.get(i).getCount())));
        }

        BarDataSet d = new BarDataSet(entries, "Enrollment Count");
        d.setColors(ColorTemplate.JOYFUL_COLORS);
        d.setHighLightAlpha(255);

        BarData cd = new BarData(d);
        cd.setBarWidth(0.9f);

        Legend l = barchart_age.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        barchart_age.getDescription().setEnabled(false);
        barchart_age.setNoDataText("No Data available");

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
        ValueFormatter xAxisFormatter = new OfficerHomeFragment.DayAxisValueFormatter(barchart_age);


        xAxis.setValueFormatter(xAxisFormatter);

        XYMarkerView mv = new XYMarkerView(getActivity(), xAxisFormatter);
        mv.setChartView(barchart_age); // For bounds control
        barchart_age.setMarker(mv);

        barchart_age.setData(cd);

    }


}