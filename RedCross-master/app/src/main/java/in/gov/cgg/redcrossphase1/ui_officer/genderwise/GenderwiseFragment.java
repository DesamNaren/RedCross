package in.gov.cgg.redcrossphase1.ui_officer.genderwise;


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

public class GenderwiseFragment extends Fragment {

    private PieChart piechart;
    private TextView tv_jrcocunt, tv_yrccount, tv_lmcount, tv_yrcname, tv_jrcname, tv_lmname;
    private LinearLayout ll_jrc;
    private LinearLayout ll_yrc, ll_lm;

    private GenderwiseViewModel genderwiseViewModel;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_genderwise, container, false);


        genderwiseViewModel =
                ViewModelProviders.of(this).get(GenderwiseViewModel.class);
        Objects.requireNonNull(getActivity()).setTitle("Gender wise");

        piechart = root.findViewById(R.id.chart_gender);
        GlobalDeclaration.home = false;

        ll_jrc = root.findViewById(R.id.ll_jrc);
        ll_yrc = root.findViewById(R.id.ll_yrc);
        ll_lm = root.findViewById(R.id.ll_lm);
        tv_jrcocunt = root.findViewById(R.id.tv_jrccount);
        tv_yrccount = root.findViewById(R.id.tv_yrccount);
        tv_lmcount = root.findViewById(R.id.tv_lmcount);
        tv_jrcname = root.findViewById(R.id.tv_jrcnme);
        tv_yrcname = root.findViewById(R.id.tv_yrcnme);
        tv_lmname = root.findViewById(R.id.tv_lmname);

        setCountsForDashboard(GlobalDeclaration.counts);

        if (CheckInternet.isOnline(getActivity())) {
            genderwiseViewModel.getGender("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                    observe(getActivity(), new Observer<List<Genders>>() {
                        @Override
                        public void onChanged(@Nullable List<Genders> gendersList) {
                            if (gendersList != null) {
                                generateDataPie(gendersList);
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


                    genderwiseViewModel.getGender("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                            observe(Objects.requireNonNull(getActivity()), new Observer<List<Genders>>() {
                                @Override
                                public void onChanged(@Nullable List<Genders> gendersList) {
                                    if (gendersList != null) {
                                        generateDataPie(gendersList);
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


                    genderwiseViewModel.getGender("YRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                            observe(Objects.requireNonNull(getActivity()), new Observer<List<Genders>>() {
                                @Override
                                public void onChanged(@Nullable List<Genders> gendersList) {
                                    if (gendersList != null) {
                                        generateDataPie(gendersList);
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


                    genderwiseViewModel.getGender("Membership", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                            observe(Objects.requireNonNull(getActivity()), new Observer<List<Genders>>() {
                                @Override
                                public void onChanged(@Nullable List<Genders> gendersList) {
                                    if (gendersList != null) {
                                        generateDataPie(gendersList);
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

    private void generateDataPie(List<Genders> gendersList) {

        ArrayList<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i < gendersList.size(); i++) {
            entries.add(new PieEntry(Float.parseFloat(gendersList.get(i).getCount()), gendersList.get(i).getGender()));
        }

        PieDataSet d = new PieDataSet(entries, "Enrollments");
        d.setValueTextColor(Color.WHITE);
        d.setValueTextSize(10f);
        d.setColors(ColorTemplate.JOYFUL_COLORS);

        Legend l = piechart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        //l.setXEntrySpace(7f);
        // l.setYEntrySpace(0f);
        // l.setYOffset(0f);
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

        piechart.getDescription().setEnabled(false);

        piechart.setDrawHoleEnabled(true);
        piechart.setHoleColor(Color.WHITE);

        piechart.setTransparentCircleColor(Color.WHITE);
        piechart.setTransparentCircleAlpha(110);

        piechart.setHoleRadius(58f);
        piechart.setTransparentCircleRadius(61f);

        piechart.setDrawCenterText(true);

        piechart.setRotationEnabled(false);
        piechart.setHighlightPerTapEnabled(true);

        piechart.setMaxAngle(180f); // HALF CHART
        piechart.setRotationAngle(180f);
        piechart.setCenterTextOffset(0, -20);


        piechart.animateY(1400, Easing.EaseInOutQuad);

//        Legend l = chart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);

        // entry label styling
        piechart.setEntryLabelColor(Color.WHITE);
        piechart.setEntryLabelTextSize(12f);
        PieData pieData = new PieData(d);

        piechart.setData(pieData);
        piechart.invalidate();
    }


}