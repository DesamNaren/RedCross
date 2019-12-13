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
import java.util.Collections;
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
            Collections.reverse(last10days);
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


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

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
            stringList.add(ageList.get(i).getDate().substring(0, 5));
        }

//
//        for (int i = 0; i < ageList.size(); i++) {
//
//                String ip = ageList.get(i).getDate();
//                String op=ip.substring(0, 5);
//                Log.i("date", "generateDataLine: " + op);
//
//
//        }

        // String output = input.substring(0, input.lastIndexOf("/"));


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