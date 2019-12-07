package in.gov.cgg.redcrossphase1.ui_officer.genderwise;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentOnlygenderwiseBinding;
import in.gov.cgg.redcrossphase1.ui_officer.agewise.AgewiseViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.home_distrcit.CustomDistricClass;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;

public class OnlyGenderwiseFragment extends Fragment {

    FragmentOnlygenderwiseBinding binding;
    private GenderwiseViewModel genderwiseViewModel;
    private AgewiseViewModel agewiseViewModel;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_onlygenderwise, container, false);
        genderwiseViewModel =
                ViewModelProviders.of(this, new CustomDistricClass(getActivity(), "gender")).get(GenderwiseViewModel.class);


        GlobalDeclaration.home = false;


        if (CheckInternet.isOnline(getActivity())) {
            genderwiseViewModel.getGender(GlobalDeclaration.Selection_type, GlobalDeclaration.districtId, GlobalDeclaration.userID).
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
                    .make(binding.chartGender, "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        }


        return binding.getRoot();
    }


    private void generateDataPie(List<Genders> gendersList) {


        if (gendersList.size() > 0) {
            binding.chartGender.setVisibility(View.VISIBLE);
            binding.tvNodata.setVisibility(View.GONE);
            binding.chartGender.setVisibility(View.VISIBLE);

            ArrayList<PieEntry> entries = new ArrayList<>();

            for (int i = 0; i < gendersList.size(); i++) {
                entries.add(new PieEntry(Float.parseFloat(gendersList.get(i).getCount()), gendersList.get(i).getGender()));
            }

            PieDataSet d = new PieDataSet(entries, "Enrollments");
            d.setValueTextColor(Color.WHITE);
            d.setValueTextSize(10f);
            d.setColors(ColorTemplate.JOYFUL_COLORS);

            Legend l = binding.chartGender.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            l.setOrientation(Legend.LegendOrientation.VERTICAL);
            l.setDrawInside(true);


            binding.chartGender.getDescription().setEnabled(false);

            binding.chartGender.setDrawHoleEnabled(true);
            binding.chartGender.setHoleColor(Color.WHITE);

            binding.chartGender.setTransparentCircleColor(Color.WHITE);
            binding.chartGender.setTransparentCircleAlpha(110);

            binding.chartGender.setHoleRadius(58f);
            binding.chartGender.setTransparentCircleRadius(61f);

            binding.chartGender.setDrawCenterText(true);

            binding.chartGender.setRotationEnabled(false);
            binding.chartGender.setHighlightPerTapEnabled(true);

            binding.chartGender.setMaxAngle(180f); // HALF CHART
            binding.chartGender.setRotationAngle(180f);
            binding.chartGender.setCenterTextOffset(0, -20);


            binding.chartGender.animateY(1400, Easing.EaseInOutQuad);

            binding.chartGender.setEntryLabelColor(Color.WHITE);
            binding.chartGender.setEntryLabelTextSize(12f);
            PieData pieData = new PieData(d);

            binding.chartGender.setData(pieData);
            binding.chartGender.invalidate();
        } else {
            binding.chartGender.setVisibility(View.GONE);
            binding.tvNodata.setVisibility(View.VISIBLE);
        }
    }

}