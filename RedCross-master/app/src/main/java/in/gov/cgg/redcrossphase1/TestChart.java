package in.gov.cgg.redcrossphase1;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestChart extends AppCompatActivity {

    BarChart barChart;
    PieChart pieChart;
    LineChart lineChart;

    // Typeface tfLight;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        barChart = findViewById(R.id.bbar);
//        lineChart=findViewById(R.id.lchrt);
        pieChart = findViewById(R.id.pch);

        //  tfLight = Typeface.createFromAsset(getAssets(), "droid_serif.ttf");

        //generateDataPie();
        // generateDataBar(50);
        // sortDates();
        // TEST(50);


    }

    private void sortDates() {
//        List<String> values = new ArrayList<String>();
//        values.add("30-03-2012");
//        values.add("28-03-2013");
//        values.add("31-03-2012");
//        Collections.sort(values, new Comparator<String>() {
//
//            @Override
//            public int compare(String arg0, String arg1) {
//                SimpleDateFormat format = new SimpleDateFormat(
//                        "dd-MM-yyyy");
//                int compareResult = 0;
//                Date arg0Date = null,arg1Date = null;
//                try {
//                    arg0Date = format.parse(arg0);
//                    arg1Date = format.parse(arg1);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                    Toast.makeText(TestChart.this, "exception", Toast.LENGTH_SHORT).show();
//                }
//                compareResult = arg0Date.compareTo(arg1Date);
//                return compareResult;
//            }
//        });

        List<String> values = new ArrayList<String>();
        values.add("30-03-2012");
        values.add("28-03-2013");
        values.add("31-03-2012");
        class StringDateComparator implements Comparator<String> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            public int compare(String lhs, String rhs) {
                try {
                    return dateFormat.parse(lhs).compareTo(dateFormat.parse(rhs));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        }
        Collections.sort(values, new StringDateComparator());


    }

    private void generateDataLine(int cnt) {

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            values1.add(new Entry(i, (int) (Math.random() * 65) + 40));
        }

        LineDataSet d1 = new LineDataSet(values1, "New DataSet " + cnt + ", (1)");
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);

        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            values2.add(new Entry(i, values1.get(i).getY() - 30));
        }

        LineDataSet d2 = new LineDataSet(values2, "New DataSet " + cnt + ", (2)");
        d2.setLineWidth(2.5f);
        d2.setCircleRadius(4.5f);
        d2.setHighLightColor(Color.rgb(244, 117, 117));
        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setDrawValues(false);

//        ArrayList<ILineDataSet> sets = new ArrayList<>();
//        sets.add(d1);
//        sets.add(d2);

        LineData lineData1 = new LineData(d1);

        lineChart.setData(lineData1);

    }

    private void generateDataBar(int cnt) {

        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry(i, (int) (Math.random() * 70) + 30));
        }

        BarDataSet d = new BarDataSet(entries, "New DataSet " + cnt);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);

        BarData cd = new BarData(d);
        cd.setBarWidth(0.9f);

        Description des = new Description();
        des.setText("test chhart");


        // barChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);

        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        barChart.getAxisLeft().setDrawGridLines(false);


        // add a nice and smooth animation
        barChart.animateY(1500);

        // barChart.getLegend().setEnabled(false);

        barChart.setData(cd);
    }

    private void generateDataPie() {

        ArrayList<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            entries.add(new PieEntry((float) ((Math.random() * 70) + 30), "Quarter " + (i + 1)));
        }

        PieDataSet d = new PieDataSet(entries, "");

        Description des = new Description();
        des.setText("Test chart");

        pieChart.setDescription(des);
        pieChart.setNoDataText("No data available for chart");

        pieChart.setUsePercentValues(true);
//        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        // pieChart.setCenterTextTypeface(tfLight);
        pieChart.setCenterText(generateCenterSpannableText());

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
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);

        PieData pieData = new PieData(d);

        pieChart.setData(pieData);


    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }


}
