package in.gov.cgg.redcrossphase1.ui_cgcitizen.bloodwise;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentBloodwiseBinding;

public class BloodwiseFragment extends Fragment {

    List<BloodGroups> bloodGroupsList = new ArrayList<>();
    PieChart pieChart;
    BloodwiseViewModel bloodwiseViewModel;
    FragmentBloodwiseBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_bloodwise, container, false);
        bloodwiseViewModel =
                ViewModelProviders.of(this).get(BloodwiseViewModel.class);

        binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_bloodwise);

        pieChart = binding.chartBlood;

//        BloodwiseViewModel model = ViewModelProviders.of(this).get(BloodwiseViewModel.class);
//
//        model.getAges().observe(this, new Observer<BloodResponse>() {
//            @Override
//            public void onChanged(@Nullable BloodResponse ageList) {
//                generateDataPie();
//            }
//        });
        //loadBlood();
        generateDataPie();


        return root;
    }

    private void generateDataPie() {

        ArrayList<PieEntry> entries = new ArrayList<>();

        ArrayList<String> bgroups = new ArrayList<>();

        bgroups.add(0, "B+");
        bgroups.add(1, "A+");
        bgroups.add(2, "O+");
        bgroups.add(3, "B-");


        for (int i = 0; i < 4; i++) {
            entries.add(new PieEntry((float) ((Math.random() * 70) + 30), bgroups.get(i)));
        }

        PieDataSet d = new PieDataSet(entries, "");

//        Description des = new Description();
//        des.setText("Test chart");

//        pieChart.setDescription(des);
        pieChart.setNoDataText("No data available for chart");

        pieChart.setUsePercentValues(true);
//        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        // pieChart.setCenterTextTypeface(tfLight);
/*
        pieChart.setCenterText(generateCenterSpannableText());
*/

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        //enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        // space between slices
        d.setSliceSpace(2f);
        d.setColors(ColorTemplate.JOYFUL_COLORS);
        d.setSliceSpace(2f);
        d.setValueTextColor(Color.WHITE);
        d.setValueTextSize(10f);
        d.setSliceSpace(5f);
        PieData pieData = new PieData(d);

        pieChart.setData(pieData);


    }


}