package in.gov.cgg.redcrossphase1.ui_officer.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;
import java.util.List;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.TestFrag;
import in.gov.cgg.redcrossphase1.databinding.NewfragmentGovtpvtnewBinding;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_officer.adapters.GovtPvtAdapter;
import in.gov.cgg.redcrossphase1.ui_officer.custom_officer.CustomDistricClass;
import in.gov.cgg.redcrossphase1.ui_officer.custom_officer.LineXYMarkerView;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.Last10day;
import in.gov.cgg.redcrossphase1.ui_officer.viewmodels.GovtPvtViewModel;

import static android.content.Context.MODE_PRIVATE;

public class GovtPvtFragment extends TestFrag implements OnChartValueSelectedListener {

    private GovtPvtViewModel govtPvtViewModel;
    NewfragmentGovtpvtnewBinding binding;
    int i;
    private int selectedThemeColor = -1;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.newfragment_govtpvtnew, container, false);

        govtPvtViewModel = ViewModelProviders.of(this, new CustomDistricClass(getActivity(), "gvtpvt")).get(GovtPvtViewModel.class);


        GlobalDeclaration.home = false;

        try {
            selectedThemeColor = context.getSharedPreferences("THEMECOLOR_PREF",
                    MODE_PRIVATE).getInt("theme_color", -1);

        } catch (Exception e) {
            e.printStackTrace();

        }

        govtPvtViewModel.getGovtPvt(GlobalDeclaration.districtId).
                observe(getActivity(), new Observer<List<Last10day>>() {
                    @Override
                    public void onChanged(@Nullable List<Last10day> last10dayList) {
                        if (last10dayList != null) {
                            if (last10dayList.size() > 1) {
                                setDataforRV(last10dayList);
                            }
//                            generateDataBar(last10dayList);
                            //test(last10dayList);
                        }
                    }
                });
        binding.btnFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                binding.easyFlipView.flipTheView();

            }
        });
        binding.easyFlipView.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
            @Override
            public void onViewFlipCompleted(EasyFlipView easyFlipView, EasyFlipView.FlipState newCurrentSide) {
                if (i % 2 == 0) {
                    binding.easyFlipView.flipTheView();
                    binding.btnFlip.setText("View Data");
                } else {
                    binding.easyFlipView.flipTheView();
                    binding.btnFlip.setText("View Chart");
                }
            }
        });

        return binding.getRoot();
    }

    private void setDataforRV(List<Last10day> last10days) {

        //int r= sortDatesHere();
        if (last10days.size() > 0) {
            binding.chartGovtpvt.setVisibility(View.VISIBLE);
            binding.btnFlip.setVisibility(View.VISIBLE);
            binding.tvNodata.setVisibility(View.GONE);
            binding.rvGovtpvtlist.setHasFixedSize(true);
            binding.rvGovtpvtlist.setLayoutManager(new LinearLayoutManager(context));


            GovtPvtAdapter adapter1 = new GovtPvtAdapter(context, last10days, selectedThemeColor);
            binding.rvGovtpvtlist.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();
            generateDataLine(last10days);


        } else {
            binding.btnFlip.setVisibility(View.GONE);
            binding.chartGovtpvt.setVisibility(View.GONE);
            binding.rvGovtpvtlist.setVisibility(View.GONE);
            binding.tvNodata.setVisibility(View.VISIBLE);

        }


    }


/*
    private void generateDataBar(List<Last10day> ageList) {

        ArrayList<BarEntry> entriesGov = new ArrayList<>();
        ArrayList<BarEntry> entriesPvt = new ArrayList<>();

        for (int i = 0; i < ageList.size(); i++) {
            entriesGov.add(new BarEntry(((i)), Float.parseFloat(ageList.get(i).getGov())));
        }

        for (int i = 0; i < ageList.size(); i++) {
            entriesPvt.add(new BarEntry(((i)), Float.parseFloat(ageList.get(i).getPvt())));
        }

        BarDataSet lineDataSetGov = new BarDataSet(entriesGov, "Gov Enrollments");

        BarDataSet lineDataSetPvt = new BarDataSet(entriesPvt, "Pvt Enrollments");

        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {

            lineDataSetGov = (BarDataSet) lineChart.getData().getDataSetByIndex(0);
            lineDataSetPvt = (BarDataSet) lineChart.getData().getDataSetByIndex(1);

            lineDataSetGov.setValues(entriesGov);
            lineDataSetPvt.setValues(entriesPvt);

            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();

        }
        else
        {
            //lineChart.setNoDataText("No data available");
            // create 4 DataSets
            lineDataSetGov = new BarDataSet(entriesGov, "Company A");
            lineDataSetGov.setColor(Color.rgb(104, 241, 175));

            lineDataSetPvt = new BarDataSet(entriesGov, "Company B");
            lineDataSetPvt.setColor(Color.rgb(104, 241, 175));

            BarData data = new BarData(lineDataSetGov,lineDataSetPvt);
            data.setValueFormatter(new LargeValueFormatter());

            lineChart.setData(data);

        }

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
        mv.setChartView(lineChart); // For bounds control
        lineChart.setMarker(mv); // Set the marker to the chart

        lineDataSetGov.setColor(ContextCompat.getColor(getActivity(), R.color.green));
        lineDataSetGov.setValueTextColor(ContextCompat.getColor(getActivity(), R.color.black));

        lineDataSetPvt.setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        lineDataSetPvt.setValueTextColor(ContextCompat.getColor(getActivity(), R.color.black));

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
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

        // specify the width each bar should have
        lineChart.getBarData().setBarWidth((float) 0.6);

        lineChart.invalidate();

//
//        BarData data = new BarData(lineDataSetGov, lineDataSetPvt);
//        lineChart.setData(data);
//        lineChart.getDescription().setEnabled(false);
//        lineChart.animateX(1500);
//        lineChart.invalidate();
    }
*/

    /*
     */
//   public  void test(List<Last10day> last10dayList)
//       {
//           float groupSpace = 0.08f;
//           float barSpace = 0.03f; // x4 DataSet
//           float barWidth = 0.2f; // x4 DataSet
//           // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"
//
////           int startYear = 1980;
//           chart.setOnChartValueSelectedListener(this);
//           chart.getDescription().setEnabled(false);
//
//   //        chart.setDrawBorders(true);
//
//           // scaling can now only be done on x- and y-axis separately
//           chart.setPinchZoom(false);
//
//           chart.setDrawBarShadow(false);
//
//           chart.setDrawGridBackground(false);
//
//
//
//           Legend l = chart.getLegend();
//           l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//           l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//           l.setOrientation(Legend.LegendOrientation.VERTICAL);
//           l.setDrawInside(true);
//           l.setYOffset(0f);
//           l.setXOffset(10f);
//           l.setYEntrySpace(0f);
//           l.setTextSize(8f);
//
//           XAxis xAxis = chart.getXAxis();
//           xAxis.setGranularity(1f);
//           xAxis.setCenterAxisLabels(true);
//           ValueFormatter valueFormatter=new DayAxisValueFormatter(chart);
//           xAxis.setValueFormatter(new ValueFormatter() {
//               @Override
//               public String getFormattedValue(float value) {
//                   return String.valueOf((int) value);
//               }
//           });
//
//           YAxis leftAxis = chart.getAxisLeft();
//           leftAxis.setDrawGridLines(false);
//           leftAxis.setSpaceTop(35f);
//           leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//
//           // create a custom MarkerView (extend MarkerView) and specify the layout
//           // to use for it
////           MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
////           mv.setChartView(chart); // For bounds control
////           chart.setMarker(mv); // Set the marker to the chart
//
//           GovtXYMarkerView mv = new GovtXYMarkerView(getActivity(), valueFormatter);
//           mv.setChartView(chart); // For bounds control
//           chart.setMarker(mv);
//
//           chart.getAxisRight().setEnabled(false);
//
//           ArrayList<BarEntry> values1 = new ArrayList<>();
//           ArrayList<BarEntry> values2 = new ArrayList<>();
//
//
////           for (int i = 1; i < 10; i++) {
////               values1.add(new BarEntry(i, (float) (Math.random() * 5)));
////               values2.add(new BarEntry(i, (float) (Math.random() * 6)));
////
////           }
//
//
//           for (int i = 0; i < last10dayList.size(); i++) {
//               values1.add(new BarEntry(((i)), Float.parseFloat(last10dayList.get(i).getGov())));
//           }
//
//           for (int i = 0; i < last10dayList.size(); i++) {
//               values1.add(new BarEntry(((i)), Float.parseFloat(last10dayList.get(i).getPvt())));
//           }
//
//           BarDataSet set1, set2;
//
//           if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
//
//               set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
//               set2 = (BarDataSet) chart.getData().getDataSetByIndex(1);
//
//               set1.setValues(values1);
//               set2.setValues(values2);
//
//               chart.getData().notifyDataChanged();
//               chart.notifyDataSetChanged();
//
//           } else {
//               // create 4 DataSets
//               set1 = new BarDataSet(values1, "Govt");
//               set1.setColor(Color.rgb(104, 241, 175));
//               set2 = new BarDataSet(values2, "Pvt");
//               set2.setColor(Color.rgb(164, 228, 251));
//
//               BarData data = new BarData(set1, set2);
//               data.setValueFormatter(new LargeValueFormatter());
//
//               chart.setData(data);
//           }
//
//           // specify the width each bar should have
//           chart.getBarData().setBarWidth(barWidth);
//
//           // restrict the x-axis range
////           chart.getXAxis().setAxisMinimum(startYear);
//
//           // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
//           chart.getXAxis().setAxisMaximum( chart.getBarData().getGroupWidth(groupSpace, barSpace) * last10dayList.size());
//           chart.groupBars(1, groupSpace, barSpace);
//           chart.invalidate();
//       }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

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

        if (getActivity() != null) {
            lineDataSetGov.setColor(ContextCompat.getColor(getActivity(), R.color.green));
            lineDataSetGov.setValueTextColor(ContextCompat.getColor(getActivity(), R.color.black));

            lineDataSetPvt.setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
            lineDataSetPvt.setValueTextColor(ContextCompat.getColor(getActivity(), R.color.black));
        } else {
            lineDataSetGov.setColor(ContextCompat.getColor(context, R.color.green));
            lineDataSetGov.setValueTextColor(ContextCompat.getColor(context, R.color.black));

            lineDataSetPvt.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
            lineDataSetPvt.setValueTextColor(ContextCompat.getColor(context, R.color.black));
        }

        XAxis xAxis = binding.chartGovtpvt.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
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
        LineXYMarkerView mv = new LineXYMarkerView(context, formatter);
        mv.setChartView(binding.chartGovtpvt);
        binding.chartGovtpvt.setMarker(mv);

        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

        YAxis yAxisRight = binding.chartGovtpvt.getAxisRight();
        yAxisRight.setEnabled(false);

        YAxis yAxisLeft = binding.chartGovtpvt.getAxisLeft();
        yAxisLeft.setGranularity(1f);

        // get the legend (only possible after setting data)
        Legend l = binding.chartGovtpvt.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        lineDataSetGov.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSetPvt.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);


        LineData data = new LineData(lineDataSetGov, lineDataSetPvt);
        binding.chartGovtpvt.setData(data);
        binding.chartGovtpvt.getDescription().setEnabled(false);
        binding.chartGovtpvt.animateY(1500);
        binding.chartGovtpvt.invalidate();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

    }
}