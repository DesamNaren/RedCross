package in.gov.cgg.redcrossphase1.ui_officer.govtpvt;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.List;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_officer.home_distrcit.CustomDistricClass;

public class GovtPvtFragment extends Fragment implements OnChartValueSelectedListener {

    private BarChart chart;

    private GovtPvtViewModel govtPvtViewModel;
    // FragmentGenderwiseBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_govtpvtnew, container, false);

        // binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_genderwise);

        govtPvtViewModel = ViewModelProviders.of(this, new CustomDistricClass(getActivity(), "gvtpvt")).get(GovtPvtViewModel.class);

        getActivity().setTitle("Gov Vs Pvt Graph");
        chart = root.findViewById(R.id.chart_govtpvt);

        GlobalDeclaration.home = false;

        govtPvtViewModel.getGovtPvt(GlobalDeclaration.districtId).
                observe(getActivity(), new Observer<List<Last10day>>() {
                    @Override
                    public void onChanged(@Nullable List<Last10day> last10dayList) {
                        if (last10dayList != null) {
                            //generateDataLine(last10dayList);
                            //generateDataBar(last10dayList);
                        }
                    }
                });


        return root;
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

    /*   public  void test()
       {
           chart.setOnChartValueSelectedListener(this);
           chart.getDescription().setEnabled(false);

   //        chart.setDrawBorders(true);

           // scaling can now only be done on x- and y-axis separately
           chart.setPinchZoom(false);

           chart.setDrawBarShadow(false);

           chart.setDrawGridBackground(false);

           // create a custom MarkerView (extend MarkerView) and specify the layout
           // to use for it
           MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
           mv.setChartView(chart); // For bounds control
           chart.setMarker(mv); // Set the marker to the chart

           seekBarX.setProgress(10);
           seekBarY.setProgress(100);

           Legend l = chart.getLegend();
           l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
           l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
           l.setOrientation(Legend.LegendOrientation.VERTICAL);
           l.setDrawInside(true);
           l.setTypeface(tfLight);
           l.setYOffset(0f);
           l.setXOffset(10f);
           l.setYEntrySpace(0f);
           l.setTextSize(8f);

           XAxis xAxis = chart.getXAxis();
           xAxis.setTypeface(tfLight);
           xAxis.setGranularity(1f);
           xAxis.setCenterAxisLabels(true);
           xAxis.setValueFormatter(new ValueFormatter() {
               @Override
               public String getFormattedValue(float value) {
                   return String.valueOf((int) value);
               }
           });

           YAxis leftAxis = chart.getAxisLeft();
           leftAxis.setTypeface(tfLight);
           leftAxis.setValueFormatter(new LargeValueFormatter());
           leftAxis.setDrawGridLines(false);
           leftAxis.setSpaceTop(35f);
           leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

           chart.getAxisRight().setEnabled(false);

           ArrayList<BarEntry> values1 = new ArrayList<>();
           ArrayList<BarEntry> values2 = new ArrayList<>();
           ArrayList<BarEntry> values3 = new ArrayList<>();
           ArrayList<BarEntry> values4 = new ArrayList<>();


           for (int i = startYear; i < endYear; i++) {
               values1.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
               values2.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
               values3.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
               values4.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
           }

           BarDataSet set1, set2, set3, set4;

           if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {

               set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
               set2 = (BarDataSet) chart.getData().getDataSetByIndex(1);
               set3 = (BarDataSet) chart.getData().getDataSetByIndex(2);
               set4 = (BarDataSet) chart.getData().getDataSetByIndex(3);
               set1.setValues(values1);
               set2.setValues(values2);
               set3.setValues(values3);
               set4.setValues(values4);
               chart.getData().notifyDataChanged();
               chart.notifyDataSetChanged();

           } else {
               // create 4 DataSets
               set1 = new BarDataSet(values1, "Company A");
               set1.setColor(Color.rgb(104, 241, 175));
               set2 = new BarDataSet(values2, "Company B");
               set2.setColor(Color.rgb(164, 228, 251));
               set3 = new BarDataSet(values3, "Company C");
               set3.setColor(Color.rgb(242, 247, 158));
               set4 = new BarDataSet(values4, "Company D");
               set4.setColor(Color.rgb(255, 102, 0));

               BarData data = new BarData(set1, set2, set3, set4);
               data.setValueFormatter(new LargeValueFormatter());

               chart.setData(data);
           }

           // specify the width each bar should have
           chart.getBarData().setBarWidth(barWidth);

           // restrict the x-axis range
           chart.getXAxis().setAxisMinimum(startYear);

           // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
           chart.getXAxis().setAxisMaximum(startYear + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
           chart.groupBars(startYear, groupSpace, barSpace);
           chart.invalidate();
       }
   */
    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }



/*
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


        LineData data = new LineData(lineDataSetGov, lineDataSetPvt);
        lineChart.setData(data);
        lineChart.getDescription().setEnabled(false);
        lineChart.animateX(1500);
        lineChart.invalidate();
    }
*/


}