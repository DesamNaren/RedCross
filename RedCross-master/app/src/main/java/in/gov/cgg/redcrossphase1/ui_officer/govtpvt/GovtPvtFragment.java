package in.gov.cgg.redcrossphase1.ui_officer.govtpvt;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;

public class GovtPvtFragment extends Fragment {

    private LineChart lineChart;

    private GovtPvtViewModel govtPvtViewModel;
    // FragmentGenderwiseBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_govtpvt, container, false);

        // binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_genderwise);

        govtPvtViewModel =
                ViewModelProviders.of(this).get(GovtPvtViewModel.class);

        getActivity().setTitle("Gov Vs Pvt Graph");
        lineChart = root.findViewById(R.id.chart_govtpvt);

        GlobalDeclaration.home = false;

        govtPvtViewModel.getGovtPvt("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                observe(getActivity(), new Observer<List<Last10day>>() {
                    @Override
                    public void onChanged(@Nullable List<Last10day> last10dayList) {
                        if (last10dayList != null) {
                            generateDataLine(last10dayList);
                        }
                    }
                });


        return root;
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


}