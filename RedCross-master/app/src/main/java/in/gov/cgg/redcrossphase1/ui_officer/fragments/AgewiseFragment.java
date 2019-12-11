package in.gov.cgg.redcrossphase1.ui_officer.fragments;


import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentAgewiseBinding;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_officer.custom_officer.CustomDistricClass;
import in.gov.cgg.redcrossphase1.ui_officer.custom_officer.XYMarkerView;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.Age;
import in.gov.cgg.redcrossphase1.ui_officer.viewmodels.AgewiseViewModel;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;

public class AgewiseFragment extends Fragment {

    FragmentAgewiseBinding binding;
    private AgewiseViewModel agewiseViewModel;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_agewise, container, false);

        agewiseViewModel =
                ViewModelProviders.of(this, new CustomDistricClass(getActivity(), "age")).get(AgewiseViewModel.class);


//        Objects.requireNonNull(getActivity()).setTitle("Age wise");
        

        if (CheckInternet.isOnline(getActivity())) {
            Log.e("Selection_type", "........age............" + GlobalDeclaration.Selection_type);
            agewiseViewModel.getAges(GlobalDeclaration.Selection_type, GlobalDeclaration.districtId, GlobalDeclaration.userID).
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
                    .make(binding.chartAge, "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        }


        return binding.getRoot();
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

        Legend l = binding.chartAge.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        binding.chartAge.getDescription().setEnabled(false);
        binding.chartAge.setNoDataText("No Data available");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        // binding.chartAge.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        binding.chartAge.setPinchZoom(false);

        binding.chartAge.setDrawBarShadow(false);
        binding.chartAge.setDrawGridBackground(false);


        XAxis xAxis = binding.chartAge.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        binding.chartAge.getAxisLeft().setDrawGridLines(false);


        // add a nice and smooth animation
        binding.chartAge.animateY(1500);

        // barChart.getLegend().setEnabled(false);
        ValueFormatter xAxisFormatter = new NewOfficerHomeFragment.DayAxisValueFormatter(binding.chartAge);


        xAxis.setValueFormatter(xAxisFormatter);

        XYMarkerView mv = new XYMarkerView(getActivity(), xAxisFormatter);
        mv.setChartView(binding.chartAge); // For bounds control
        binding.chartAge.setMarker(mv);

        binding.chartAge.setData(cd);

    }


}