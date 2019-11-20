package in.gov.cgg.redcrossphase1.ui_officer.govtpvt;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;

public class GovtPvtFragment extends Fragment {

    LineChart lineChart;

    GovtPvtViewModel govtPvtViewModel;
    // FragmentGenderwiseBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_govtpvt, container, false);

        // binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_genderwise);

        govtPvtViewModel =
                ViewModelProviders.of(this).get(GovtPvtViewModel.class);

        getActivity().setTitle("Gov Vs Pvt Graph");


        govtPvtViewModel.getGender("JRC", GlobalDeclaration.districtId, GlobalDeclaration.userID).
                observe(getActivity(), new Observer<List<GovType>>() {
                    @Override
                    public void onChanged(@Nullable List<GovType> govTypeList) {
                        if (govTypeList != null) {
                            generateLine(govTypeList);
                        }
                    }
                });

        lineChart = root.findViewById(R.id.chart_govtpvt);


        return root;
    }


    private void generateLine(List<GovType> govTypeList) {
        int cnt = 50;
        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            values1.add(new Entry(i, (int) (Math.random() * 65) + 40));
        }

        LineDataSet d1 = new LineDataSet(values1, "Govt" + cnt + ", (1)");
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);

        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            values2.add(new Entry(i, values1.get(i).getY() - 30));
        }

        LineDataSet d2 = new LineDataSet(values2, "Pvt" + cnt + ", (2)");
        d2.setLineWidth(2.5f);
        d2.setCircleRadius(4.5f);
        d2.setHighLightColor(Color.rgb(244, 117, 117));
        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setDrawValues(false);

        LineData data = new LineData(d1, d2);
        lineChart.setData(data);
        lineChart.animateX(1500);
        lineChart.invalidate();
    }


}