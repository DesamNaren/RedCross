package in.gov.cgg.redcrossphase1.ui_officer.bloodwise;


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
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentBloodwiseBinding;
import in.gov.cgg.redcrossphase1.ui_officer.home_distrcit.CustomDistricClass;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;

public class BloodwiseFragment extends Fragment {

    FragmentBloodwiseBinding binding;
    private BloodwiseViewModel bloodwiseVm;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_bloodwise, container, false);

        bloodwiseVm =
                ViewModelProviders.of(this, new CustomDistricClass(getActivity(), "blood")).get(BloodwiseViewModel.class);

        GlobalDeclaration.home = false;


//        Objects.requireNonNull(getActivity()).setTitle("Blood group wise");

        if (CheckInternet.isOnline(getActivity())) {
            bloodwiseVm.getBlood("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
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
                    .make(binding.chartBlood, "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

//        ll_jrc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (CheckInternet.isOnline(getActivity())) {
//                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//                    tv_jrcocunt.setTextColor(getResources().getColor(white));
//                    tv_jrcname.setTextColor(getResources().getColor(white));
////
//                    ll_yrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                    tv_yrcname.setTextColor(getResources().getColor(colorPrimary));
//                    tv_yrccount.setTextColor(getResources().getColor(colorPrimary));
//
//
//                    ll_lm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                    tv_lmcount.setTextColor(getResources().getColor(colorPrimary));
//                    tv_lmname.setTextColor(getResources().getColor(colorPrimary));
//
//
//                    bloodwiseVm.getBlood("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
//                            observe(getActivity(), new Observer<List<BloodGroups>>() {
//                                @Override
//                                public void onChanged(@Nullable List<BloodGroups> bloodGroupsList) {
//                                    if (bloodGroupsList != null) {
//                                        generateDataPie(bloodGroupsList);
//                                    }
//                                }
//                            });
//                } else {
//                    Snackbar snackbar = Snackbar
//                            .make(ll_jrc, "No Internet Connection", Snackbar.LENGTH_LONG);
//                    snackbar.show();
//                }
//
//            }
//        });
//
//        ll_yrc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (CheckInternet.isOnline(getActivity())) {
//                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                    tv_jrcocunt.setTextColor(getResources().getColor(colorPrimary));
//                    tv_jrcname.setTextColor(getResources().getColor(colorPrimary));
////
//                    ll_yrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//                    tv_yrcname.setTextColor(getResources().getColor(white));
//                    tv_yrccount.setTextColor(getResources().getColor(white));
//
//                    ll_lm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                    tv_lmcount.setTextColor(getResources().getColor(colorPrimary));
//                    tv_lmname.setTextColor(getResources().getColor(colorPrimary));
//
//                    bloodwiseVm.getBlood("YRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
//                            observe(Objects.requireNonNull(getActivity()), new Observer<List<BloodGroups>>() {
//                                @Override
//                                public void onChanged(@Nullable List<BloodGroups> bloodGroupsList) {
//                                    if (bloodGroupsList != null) {
//                                        generateDataPie(bloodGroupsList);
//                                    }
//                                }
//                            });
//                } else {
//                    Snackbar snackbar = Snackbar
//                            .make(ll_yrc, "No Internet Connection", Snackbar.LENGTH_LONG);
//                    snackbar.show();
//                }
//            }
//        });
//        ll_lm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (CheckInternet.isOnline(getActivity())) {
//                    ll_lm.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
//                    tv_lmcount.setTextColor(getResources().getColor(white));
//                    tv_lmname.setTextColor(getResources().getColor(white));
////
//                    ll_yrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                    tv_yrcname.setTextColor(getResources().getColor(colorPrimary));
//                    tv_yrccount.setTextColor(getResources().getColor(colorPrimary));
//
//                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                    tv_jrcname.setTextColor(getResources().getColor(colorPrimary));
//                    tv_jrcocunt.setTextColor(getResources().getColor(colorPrimary));
//
//                    bloodwiseVm.getBlood("Membership", GlobalDeclaration.districtId, GlobalDeclaration.userID).
//                            observe(Objects.requireNonNull(getActivity()), new Observer<List<BloodGroups>>() {
//                                @Override
//                                public void onChanged(@Nullable List<BloodGroups> bloodGroupsList) {
//                                    if (bloodGroupsList != null) {
//                                        generateDataPie(bloodGroupsList);
//                                    }
//                                }
//                            });
//                } else {
//                    Snackbar snackbar = Snackbar
//                            .make(ll_lm, "No Internet Connection", Snackbar.LENGTH_LONG);
//                    snackbar.show();
//                }
//            }
//        });

        return binding.getRoot();
    }


    private void generateDataPie(List<BloodGroups> bloodGroupsList) {


        ArrayList<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i < bloodGroupsList.size(); i++) {
            entries.add(new PieEntry(Float.parseFloat(bloodGroupsList.get(i).getCount()), bloodGroupsList.get(i).getBloodGroup()));
        }

        binding.chartBlood.getDescription().setEnabled(false);
        binding.chartBlood.setNoDataText("No Data available");

        Legend l = binding.chartBlood.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);


        binding.chartBlood.setRotationEnabled(true);
        binding.chartBlood.setDrawCenterText(true);
        binding.chartBlood.setRotationEnabled(true);

        //.setUsePercentValues(true);
        binding.chartBlood.getDescription().setEnabled(false);
        binding.chartBlood.setDrawHoleEnabled(true);
        binding.chartBlood.setHoleColor(Color.WHITE);
        binding.chartBlood.setTransparentCircleColor(Color.WHITE);
        binding.chartBlood.setTransparentCircleAlpha(110);
        binding.chartBlood.setHoleRadius(58f);
        binding.chartBlood.setTransparentCircleRadius(61f);
        binding.chartBlood.setDrawCenterText(true);
        binding.chartBlood.setHighlightPerTapEnabled(true);
        binding.chartBlood.animateY(1400, Easing.EaseInOutQuad);


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
        binding.chartBlood.setData(pieData);
        binding.chartBlood.invalidate();
    }


}