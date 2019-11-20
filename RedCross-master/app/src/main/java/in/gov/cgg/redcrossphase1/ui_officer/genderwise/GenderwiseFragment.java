package in.gov.cgg.redcrossphase1.ui_officer.genderwise;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_officer.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.bloodwise.BloodGroups;

import static in.gov.cgg.redcrossphase1.R.color.colorPrimary;
import static in.gov.cgg.redcrossphase1.R.color.white;

public class GenderwiseFragment extends Fragment {

    List<BloodGroups> bloodGroupsList = new ArrayList<>();
    PieChart piechart;
    TextView tv_jrcocunt, tv_yrccount, tv_lmcount, tv_yrcname, tv_jrcname;
    LinearLayout l_datepicker, ll_jrc, ll_yrc, ll_lm;

    GenderwiseViewModel genderwiseViewModel;
    // FragmentGenderwiseBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_genderwise, container, false);

        // binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_genderwise);

        genderwiseViewModel =
                ViewModelProviders.of(this).get(GenderwiseViewModel.class);
        getActivity().setTitle("Gender wise");


        ll_jrc = root.findViewById(R.id.ll_jrc);
        ll_yrc = root.findViewById(R.id.ll_yrc);
        ll_lm = root.findViewById(R.id.ll_lm);
        tv_jrcocunt = root.findViewById(R.id.tv_jrccount);
        tv_yrccount = root.findViewById(R.id.tv_yrccount);
        tv_lmcount = root.findViewById(R.id.tv_lmcount);
        tv_jrcname = root.findViewById(R.id.tv_jrcnme);
        tv_yrcname = root.findViewById(R.id.tv_yrcnme);
        setCountsForDashboard(GlobalDeclaration.counts);

        genderwiseViewModel.getGender("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                observe(getActivity(), new Observer<List<Genders>>() {
                    @Override
                    public void onChanged(@Nullable List<Genders> gendersList) {
                        if (gendersList != null) {
                            generateDataPie(gendersList);
                        }
                    }
                });
        ll_jrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ll_jrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
                tv_jrcocunt.setTextColor(getResources().getColor(white));
                tv_jrcname.setTextColor(getResources().getColor(white));
//
                ll_yrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                tv_yrcname.setTextColor(getResources().getColor(colorPrimary));
                tv_yrccount.setTextColor(getResources().getColor(colorPrimary));

                genderwiseViewModel.getGender("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                        observe(getActivity(), new Observer<List<Genders>>() {
                            @Override
                            public void onChanged(@Nullable List<Genders> gendersList) {
                                if (gendersList != null) {
                                    generateDataPie(gendersList);
                                }
                            }
                        });
            }
        });

        ll_yrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ll_jrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                tv_jrcocunt.setTextColor(getResources().getColor(colorPrimary));
                tv_jrcname.setTextColor(getResources().getColor(colorPrimary));
//
                ll_yrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
                tv_yrccount.setTextColor(getResources().getColor(white));
                tv_yrcname.setTextColor(getResources().getColor(white));

                genderwiseViewModel.getGender("YRC", GlobalDeclaration.districtId, GlobalDeclaration.userID)
                        .observe(getActivity(), new Observer<List<Genders>>() {
                            @Override
                            public void onChanged(@Nullable List<Genders> gendersList) {
                                if (gendersList != null) {
                                    generateDataPie(gendersList);
                                }
                            }
                        });
            }
        });

        piechart = root.findViewById(R.id.chart_gender);

        //  loadGender();

        //  generateDataPie(gendersList);

        return root;
    }

    private void setCountsForDashboard(DashboardCountResponse dashboardCountResponse) {

        if (dashboardCountResponse != null) {
            tv_jrcocunt.setText(String.valueOf(dashboardCountResponse.getJrc()));
            tv_yrccount.setText(String.valueOf(dashboardCountResponse.getYrc()));
        }
        //tv_lmcount.setText(String.valueOf(dashboardCountResponse.getTotal()));
    }

    private void generateDataPie(List<Genders> gendersList) {


        ArrayList<PieEntry> entries = new ArrayList<>();

//        ArrayList<String> groups = new ArrayList<>();
//
//        groups.add(0, "Male");
//        groups.add(1, "Female");


//        for (int i = 0; i < 2; i++) {
//            entries.add(new PieEntry((float) ((Math.random() * 70) + 30), groups.get(i)));
//        }
        piechart.getDescription().setEnabled(false);

        for (int i = 0; i < gendersList.size(); i++) {
            entries.add(new PieEntry(Float.parseFloat(gendersList.get(i).getCount()), gendersList.get(i).getGender()));
        }

        // entries.add(new PieEntry(Float.parseFloat(genderResponse.getCount()), genderResponse.getGender()));


        PieDataSet d = new PieDataSet(entries, "Count");
        d.setSliceSpace(2f);
        d.setValueLinePart1OffsetPercentage(80.f);
        d.setValueLinePart1Length(0.2f);
        d.setValueLinePart2Length(0.4f);
        //dataSet.setUsingSliceColorAsValueLineColor(true);

        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        d.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        d.setColors(ColorTemplate.JOYFUL_COLORS);
//        d.setSliceSpace(2f);
//        d.setValueTextColor(Color.WHITE);
//        d.setValueTextSize(10f);
//        d.setSliceSpace(5f);
        PieData pieData = new PieData(d);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.BLACK);
        piechart.setData(pieData);

        piechart.invalidate();
    }


}