package in.gov.cgg.redcrossphase1.ui_officer.bloodwise;


import android.graphics.Color;
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

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_officer.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;

import static in.gov.cgg.redcrossphase1.R.color.colorPrimary;
import static in.gov.cgg.redcrossphase1.R.color.white;

public class BloodwiseFragment extends Fragment {

    private PieChart pieChart;
    private BloodwiseViewModel bloodwiseViewModel;
    private TextView tv_jrcocunt;
    private TextView tv_yrccount, tv_lmcount;
    private TextView tv_yrcname;
    private TextView tv_jrcname, tv_lmname;
    private LinearLayout ll_jrc;
    private LinearLayout ll_yrc, ll_lm;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_bloodwise, container, false);
        bloodwiseViewModel =
                ViewModelProviders.of(this).get(BloodwiseViewModel.class);


        pieChart = root.findViewById(R.id.chart_blood);
        GlobalDeclaration.home = false;

        ll_jrc = root.findViewById(R.id.ll_jrc);
        ll_yrc = root.findViewById(R.id.ll_yrc);
        ll_lm = root.findViewById(R.id.ll_lm);
        tv_jrcocunt = root.findViewById(R.id.tv_jrccount);
        tv_yrccount = root.findViewById(R.id.tv_yrccount);
        tv_lmcount = root.findViewById(R.id.tv_lmcount);
        tv_lmname = root.findViewById(R.id.tv_lmname);
        tv_jrcname = root.findViewById(R.id.tv_jrcnme);
        tv_yrcname = root.findViewById(R.id.tv_yrcnme);

        Objects.requireNonNull(getActivity()).setTitle("Blood group wise");

        setCountsForDashboard(GlobalDeclaration.counts);
        if (CheckInternet.isOnline(getActivity())) {
            bloodwiseViewModel.getBlood("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                    observe(getActivity(), new Observer<List<BloodGroups>>() {
                        @Override
                        public void onChanged(@Nullable List<BloodGroups> bloodGroupsList) {
                            if (bloodGroupsList != null) {
                                generateDataPie(bloodGroupsList);
                            }
                        }
                    });
        } else {
            Snackbar snackbar = Snackbar
                    .make(ll_jrc, "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        ll_jrc.setOnClickListener(new View.OnClickListener() {
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


                    bloodwiseViewModel.getBlood("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                            observe(getActivity(), new Observer<List<BloodGroups>>() {
                                @Override
                                public void onChanged(@Nullable List<BloodGroups> bloodGroupsList) {
                                    if (bloodGroupsList != null) {
                                        generateDataPie(bloodGroupsList);
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

                    bloodwiseViewModel.getBlood("YRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                            observe(Objects.requireNonNull(getActivity()), new Observer<List<BloodGroups>>() {
                                @Override
                                public void onChanged(@Nullable List<BloodGroups> bloodGroupsList) {
                                    if (bloodGroupsList != null) {
                                        generateDataPie(bloodGroupsList);
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

                    bloodwiseViewModel.getBlood("Membership", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                            observe(Objects.requireNonNull(getActivity()), new Observer<List<BloodGroups>>() {
                                @Override
                                public void onChanged(@Nullable List<BloodGroups> bloodGroupsList) {
                                    if (bloodGroupsList != null) {
                                        generateDataPie(bloodGroupsList);
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
            tv_lmcount.setText(String.valueOf(dashboardCountResponse.getYrc()));
        }
        //tv_lmcount.setText(String.valueOf(dashboardCountResponse.getTotal()));
    }

    private void generateDataPie(List<BloodGroups> bloodGroupsList) {


        ArrayList<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i < bloodGroupsList.size(); i++) {
            entries.add(new PieEntry(Float.parseFloat(bloodGroupsList.get(i).getCount()), bloodGroupsList.get(i).getBloodGroup()));
        }

        pieChart.getDescription().setEnabled(false);
        pieChart.setNoDataText("No Data available");

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);


        pieChart.setRotationEnabled(true);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationEnabled(true);

        //.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setDrawCenterText(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.animateY(1400, Easing.EaseInOutQuad);


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
        pieChart.setData(pieData);
        pieChart.invalidate();
    }


}