package in.gov.cgg.redcrossphase1.ui_officer.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.snackbar.Snackbar;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.TestFrag;
import in.gov.cgg.redcrossphase1.databinding.FragmentGenderwiseBinding;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.ui_officer.adapters.AgeAdapter;
import in.gov.cgg.redcrossphase1.ui_officer.custom_officer.CustomDistricClass;
import in.gov.cgg.redcrossphase1.ui_officer.custom_officer.XYMarkerView;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.Age;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.Genders;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import in.gov.cgg.redcrossphase1.viewmodels.AllDistrictsViewModel;

import static android.content.Context.MODE_PRIVATE;

public class GenderwiseFragment extends TestFrag {

    FragmentGenderwiseBinding binding;
    int i = 0, selectedThemeColor = -1;
    private AllDistrictsViewModel genderwiseViewModel;
    private AllDistrictsViewModel agewiseViewModel;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_genderwise, container, false);
        genderwiseViewModel =
                ViewModelProviders.of(Objects.requireNonNull(getActivity()), new CustomDistricClass(getActivity(), "d")).get(AllDistrictsViewModel.class);

        agewiseViewModel =
                ViewModelProviders.of(getActivity(), new CustomDistricClass(getActivity(), "d")).get(AllDistrictsViewModel.class);

        binding.tvNodata.setVisibility(View.VISIBLE);
        binding.tvNodata.setText("Loading...");

        try {
            selectedThemeColor = context.getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF),
                    MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);


        } catch (Exception e) {
            e.printStackTrace();

        }

        GlobalDeclaration.home = false;
        if (CheckInternet.isOnline(getActivity())) {
            agewiseViewModel.getAges(GlobalDeclaration.Selection_type, GlobalDeclaration.districtId, GlobalDeclaration.userID).
                    observe(getViewLifecycleOwner(), new Observer<List<Age>>() {
                        @Override
                        public void onChanged(@Nullable List<Age> ageList) {
                            if (ageList != null) {
                                binding.tvNodata.setVisibility(View.GONE);
                                setDataforRV(ageList);

                                //   generateDataBar(ageList);

                            } else {
                                binding.tvNodata.setVisibility(View.VISIBLE);
                                binding.tvNodata.setText("No data available");
                            }
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "Please check internet internet", Toast.LENGTH_SHORT).show();

        }

        if (CheckInternet.isOnline(getActivity())) {
            genderwiseViewModel.getGender(GlobalDeclaration.Selection_type, GlobalDeclaration.districtId, GlobalDeclaration.userID).
                    observe(getViewLifecycleOwner(), new Observer<List<Genders>>() {
                        @Override
                        public void onChanged(@Nullable List<Genders> gendersList) {
                            if (gendersList != null) {

                                generateDataPie(gendersList);

                            } else {
                                binding.tvNodata.setVisibility(View.VISIBLE);
                            }
                        }
                    });


        } else {
            Snackbar snackbar = Snackbar
                    .make(binding.chartGender, "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

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
                    binding.ll.setVisibility(View.GONE);
                    binding.easyFlipView.flipTheView();
                    binding.btnFlip.setText("View Age Data");
                } else {
                    binding.ll.setVisibility(View.VISIBLE);
                    binding.easyFlipView.flipTheView();
                    binding.btnFlip.setText("View Age Chart");
                }
            }
        });


        return binding.getRoot();
    }

    private void setDataforRV(List<Age> ageList) {

        binding.tvNodata.setVisibility(View.GONE);

        if (ageList.size() > 0) {
            binding.chartAge.setVisibility(View.VISIBLE);
            binding.btnFlip.setVisibility(View.VISIBLE);
            binding.tvNodata.setVisibility(View.GONE);
            binding.rvBloodList.setHasFixedSize(true);

            binding.rvBloodList.setLayoutManager(new LinearLayoutManager(getActivity()));
            Collections.sort(ageList, new Comparator<Age>() {
                @Override
                public int compare(Age lhs, Age rhs) {
                    return lhs.getAge().compareTo(rhs.getAge());
                }
            });

            AgeAdapter adapter1 = new AgeAdapter(getActivity(), ageList, selectedThemeColor);
            binding.rvBloodList.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();
            generateDataBar(ageList);


        } else {
            binding.btnFlip.setVisibility(View.GONE);
            binding.chartAge.setVisibility(View.GONE);
            binding.rvBloodList.setVisibility(View.GONE);
            binding.tvNodata.setVisibility(View.VISIBLE);
            binding.tvNodata.setText("No data available");

        }

    }


    private void generateDataPie(List<Genders> gendersList) {


        if (gendersList.size() > 0) {
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

            binding.chartGender.setExtraOffsets(5f, 0f, 10f, -100f);

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
        }
    }

    private void generateDataBar(List<Age> ageList) {

        if (ageList.size() > 0) {
            binding.chartAge.setVisibility(View.VISIBLE);

            ArrayList<BarEntry> entries = new ArrayList<>();

            for (int i = 0; i < ageList.size(); i++) {
                entries.add(new BarEntry(Float.parseFloat(ageList.get(i).getAge()), Float.parseFloat(ageList.get(i).getCount())));
            }

            BarDataSet d = new BarDataSet(entries, "");
            d.setColors(ColorTemplate.JOYFUL_COLORS);
            d.setHighLightAlpha(255);

            BarData cd = new BarData(d);
            cd.setBarWidth(0.9f);

//        Legend l = binding.chartAge.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);

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
            if (getActivity() != null) {
                XYMarkerView mv = new XYMarkerView(getActivity(), xAxisFormatter);
                mv.setChartView(binding.chartAge); // For bounds control

                binding.chartAge.setMarker(mv);
            } else {
                XYMarkerView mv = new XYMarkerView(context, xAxisFormatter);
                mv.setChartView(binding.chartAge); // For bounds control

                binding.chartAge.setMarker(mv);
            }

            binding.chartAge.setData(cd);

        } else {
            binding.chartAge.setVisibility(View.GONE);
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}