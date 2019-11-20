package in.gov.cgg.redcrossphase1.ui_officer.agewise;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_officer.DashboardCountResponse;

import static in.gov.cgg.redcrossphase1.R.color.colorPrimary;
import static in.gov.cgg.redcrossphase1.R.color.white;

public class AgewiseFragment extends Fragment {

    LineChart lineChart;
    TextView tv_jrcocunt, tv_yrccount, tv_lmcount, tv_yrcname, tv_jrcname;
    LinearLayout l_datepicker, ll_jrc, ll_yrc, ll_lm;
    //FragmentAgewiseBinding binding;
    private AgewiseViewModel agewiseViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        agewiseViewModel =
                ViewModelProviders.of(this).get(AgewiseViewModel.class);
        View root = inflater.inflate(R.layout.fragment_agewise, container, false);

        //binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_agewise);


        lineChart = root.findViewById(R.id.chart_age);

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

        getActivity().setTitle("Age wise");

        if (GlobalDeclaration.counts != null) {
            setCountsForDashboard(GlobalDeclaration.counts);
        }

        agewiseViewModel.getAges("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                observe(getActivity(), new Observer<List<Age>>() {
                    @Override
                    public void onChanged(@Nullable List<Age> ageList) {
                        if (ageList != null) {
                            generateDataLine(ageList);
                            //  generateDataLine1(ageList);
                        }
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

                agewiseViewModel.getAges("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                        observe(getActivity(), new Observer<List<Age>>() {
                            @Override
                            public void onChanged(@Nullable List<Age> ageList) {
                                if (ageList != null) {
                                    generateDataLine(ageList);
                                    //  generateDataLine1(ageList);
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
                tv_yrccount.setTextColor(getResources().getColor(white));
                tv_yrcname.setTextColor(getResources().getColor(white));

                agewiseViewModel.getAges("YRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                        observe(getActivity(), new Observer<List<Age>>() {
                            @Override
                            public void onChanged(@Nullable List<Age> ageList) {
                                if (ageList != null) {
                                    generateDataLine(ageList);
                                    //  generateDataLine1(ageList);
                                }
                            }
                        });
            }
        });

        return root;
    }

    private void generateDataLine(List<Age> ageList) {
        LineDataSet lineDataSet = new LineDataSet(getData(ageList), "");
        lineDataSet.setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        lineDataSet.setValueTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr"};
//        ValueFormatter formatter = new ValueFormatter() {
//            @Override
//            public String getAxisLabel(float value, AxisBase axis) {
//                return months[(int) value];
//            }
//        };
        xAxis.setGranularity(1f);
        //xAxis.setValueFormatter(formatter);

        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setEnabled(false);

        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setGranularity(1f);

        LineData data = new LineData(lineDataSet);
        lineChart.setData(data);
        lineChart.animateX(1500);
        lineChart.invalidate();
    }


    private List<Entry> getData(List<Age> ageResponseList) {
        ArrayList<Entry> entries = new ArrayList<>();

        ArrayList<Float> sortedList = new ArrayList<>();
        for (int i = 0; i < ageResponseList.size(); i++) {
            sortedList.add(Float.parseFloat(ageResponseList.get(i).getAge()));
        }
        Collections.sort(sortedList, new Comparator<Float>() {

            public int compare(Float p1, Float p2) {
                return (int) (p1 - p2);
            }
        });


        for (int i = 0; i < ageResponseList.size(); i++) {
            entries.add(new Entry((sortedList.get(i)),
                    Float.parseFloat(ageResponseList.get(i).getCount())));

        }

//        entries.add(new Entry(0f, 4f));
//        entries.add(new Entry(1f, 1f));
//        entries.add(new Entry(2f, 2f));
//        entries.add(new Entry(3f, 4f));
        return entries;
    }

    private void setCountsForDashboard(DashboardCountResponse dashboardCountResponse) {
        if (dashboardCountResponse != null) {

            tv_jrcocunt.setText(String.valueOf(dashboardCountResponse.getJrc()));
            tv_yrccount.setText(String.valueOf(dashboardCountResponse.getYrc()));
        }
        //tv_lmcount.setText(String.valueOf(dashboardCountResponse.getTotal()));
    }

    private void generateDataLine1(List<Age> ageResponseList) {

        ArrayList<Entry> entries = new ArrayList<>();

//        for (int i = 0; i < ageResponseList.size(); i++) {
//            entries.add(new Entry(Float.parseFloat(ageResponseList.get(i).getAge()),
//                    Float.parseFloat(ageResponseList.get(i).getCount())));
//        }

        //entries.add(new BarEntry(Float.parseFloat(ageResponseList.getAge()), Integer.parseInt(ageResponseList.getCount())));

        lineChart.getDescription().setEnabled(false);

        LineDataSet d1 = new LineDataSet(entries, "");
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        //d1.setDrawValues(true);
        d1.setDrawCircleHole(true);
        d1.setCircleColor(Color.BLACK);

        lineChart.getDescription().setEnabled(false);
        lineChart.setDrawGridBackground(false);

//        XAxis xAxis = lineChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
////        xAxis.setTypeface(mTf);
//        xAxis.setDrawGridLines(false);
//        xAxis.setDrawAxisLine(true);
////
//        YAxis leftAxis = lineChart.getAxisLeft();
//        // leftAxis.setTypeface(mTf);
//        leftAxis.setLabelCount(50, true);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

//        YAxis rightAxis = lineChart.getAxisRight();
//       // rightAxis.setTypeface(mTf);
//        rightAxis.setLabelCount(5, false);
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        LineData lineData1 = new LineData(d1);

        lineChart.setData(lineData1);
        lineChart.invalidate();

    }


}