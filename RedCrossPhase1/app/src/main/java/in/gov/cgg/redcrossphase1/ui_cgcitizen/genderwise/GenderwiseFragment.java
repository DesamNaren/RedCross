package in.gov.cgg.redcrossphase1.ui_cgcitizen.genderwise;


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
import in.gov.cgg.redcrossphase1.databinding.FragmentGenderwiseBinding;
import in.gov.cgg.redcrossphase1.ui_cgcitizen.bloodwise.BloodGroups;

public class GenderwiseFragment extends Fragment {

    List<BloodGroups> bloodGroupsList = new ArrayList<>();
    PieChart piechart;

    GenderwiseViewModel genderwiseViewModel;
    FragmentGenderwiseBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_genderwise, container, false);

        binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_genderwise);

        genderwiseViewModel =
                ViewModelProviders.of(this).get(GenderwiseViewModel.class);

//        genderwiseViewModel.getGender().observe(this, new Observer<GenderResponse>() {
//            @Override
//            public void onChanged(@Nullable GenderResponse ageList) {
//                generateDataPie();
//            }
//        });

        piechart = binding.chartGender;

        //  loadGender();

        generateDataPie();

        return root;
    }

    private void generateDataPie() {


        ArrayList<PieEntry> entries = new ArrayList<>();

        ArrayList<String> groups = new ArrayList<>();

        groups.add(0, "Male");
        groups.add(1, "Female");


        for (int i = 0; i < 2; i++) {
            entries.add(new PieEntry((float) ((Math.random() * 70) + 30), groups.get(i)));
        }

        // entries.add(new PieEntry(Float.parseFloat(genderResponse.getCount()), genderResponse.getGender()));


        PieDataSet d = new PieDataSet(entries, "Count");

        d.setColors(ColorTemplate.JOYFUL_COLORS);
        d.setSliceSpace(2f);
        d.setValueTextColor(Color.WHITE);
        d.setValueTextSize(10f);
        d.setSliceSpace(5f);
        PieData pieData = new PieData(d);
        piechart.setData(pieData);

        piechart.invalidate();
    }


}