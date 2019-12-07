package in.gov.cgg.redcrossphase1.ui_officer.daywisereportcont;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
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
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentDaywiseBinding;
import in.gov.cgg.redcrossphase1.ui_officer.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.home_distrcit.CustomDistricClass;
import in.gov.cgg.redcrossphase1.ui_officer.home_distrcit.LineXYMarkerView;
import in.gov.cgg.redcrossphase1.ui_officer_new.NewOfficerMainActivity;

import static android.content.Context.MODE_PRIVATE;

public class DaywiseFragment extends Fragment {

    int selectedThemeColor = -1;
    private DaywiseViewModel daywiseViewModel;
    private FragmentDaywiseBinding binding;
    String spn_year, spn_month;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_daywise, container, false);

        binding.easyFlipView.setFlipDuration(1000);
        binding.easyFlipView.setFlipEnabled(true);

        GlobalDeclaration.home = false;
        daywiseViewModel =
                ViewModelProviders.of(this, new CustomDistricClass(getActivity(), "day")).get(DaywiseViewModel.class);
        Objects.requireNonNull(getActivity()).setTitle("Day wise");
        binding.cvDay.setVisibility(View.VISIBLE);
        if (GlobalDeclaration.counts != null) {
            setCountsForDashboard(GlobalDeclaration.counts);
        }
        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                    binding.btnFlip.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                    binding.btnFlip.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                    binding.btnFlip.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                    binding.btnFlip.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                    binding.btnFlip.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                    binding.btnFlip.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                    binding.btnFlip.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                    binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                    binding.btnFlip.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));

                }
                binding.customCount.tvJrcnme.setTextColor(getResources().getColor(selectedThemeColor));
                binding.customCount.tvJrccount.setTextColor(getResources().getColor(selectedThemeColor));
                binding.customCount.tvYrccount.setTextColor(getResources().getColor(selectedThemeColor));
                binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
                binding.customCount.tvLmcount.setTextColor(getResources().getColor(selectedThemeColor));
                binding.customCount.tvLmname.setTextColor(getResources().getColor(selectedThemeColor));
                binding.customCount.tvAllcount.setTextColor(getResources().getColor(selectedThemeColor));
                binding.customCount.tvAllname.setTextColor(getResources().getColor(selectedThemeColor));
                binding.btnFlip.setTextColor(getResources().getColor(selectedThemeColor));

            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }


        binding.spnFinancialyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (binding.spnFinancialyear.getSelectedItem().toString().contains("2019")) {
                    spn_year = "3";
                    if (binding.spnMonth.getSelectedItem().toString().contains("Select")) {
                        Toast.makeText(getActivity(), "Please select Month", Toast.LENGTH_SHORT).show();
                        binding.rvDaywiselist.setVisibility(View.GONE);

                    } else {
                        daywiseViewModel.getDaysCount(spn_year, GlobalDeclaration.districtId, spn_month).
                                observe(getActivity(), new Observer<List<DayWiseReportCountResponse>>() {
                                    @Override
                                    public void onChanged(@Nullable List<DayWiseReportCountResponse> alldaywisecounts) {
                                        if (alldaywisecounts != null) {
                                            setDataforRV(alldaywisecounts);
                                        }
                                    }
                                });
                    }

                } else if (binding.spnFinancialyear.getSelectedItem().toString().contains("Select")) {
                    spn_year = "";
                    if (binding.spnMonth.getSelectedItem().toString().contains("Select")) {
                        Toast.makeText(getActivity(), "Please select Month", Toast.LENGTH_SHORT).show();
                    } else {
                        daywiseViewModel.getDaysCount(spn_year, GlobalDeclaration.districtId, spn_month).
                                observe(getActivity(), new Observer<List<DayWiseReportCountResponse>>() {
                                    @Override
                                    public void onChanged(@Nullable List<DayWiseReportCountResponse> alldaywisecounts) {
                                        if (alldaywisecounts != null) {
                                            setDataforRV(alldaywisecounts);
                                        }
                                    }
                                });
                    }
                } else {
                    Toast.makeText(getActivity(), "Please select Financial year", Toast.LENGTH_SHORT).show();
                    binding.rvDaywiselist.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "Please select Financial year", Toast.LENGTH_SHORT).show();
            }
        });

        binding.spnMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (binding.spnMonth.getSelectedItem().toString().contains("Novem")) {
                    spn_month = "11";
                    if (!spn_year.equalsIgnoreCase("")) {
                        daywiseViewModel.getDaysCount(spn_year, GlobalDeclaration.districtId, spn_month).
                                observe(getActivity(), new Observer<List<DayWiseReportCountResponse>>() {
                                    @Override
                                    public void onChanged(@Nullable List<DayWiseReportCountResponse> alldaywisecounts) {
                                        if (alldaywisecounts != null) {
                                            setDataforRV(alldaywisecounts);
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(getActivity(), "Please select Financial year", Toast.LENGTH_SHORT).show();
                        binding.rvDaywiselist.setVisibility(View.GONE);

                    }
                } else if (binding.spnMonth.getSelectedItem().toString().contains("Decem")) {
                    spn_month = "12";
                    if (!spn_year.equalsIgnoreCase("")) {
                        daywiseViewModel.getDaysCount(spn_year, GlobalDeclaration.districtId, spn_month).
                                observe(getActivity(), new Observer<List<DayWiseReportCountResponse>>() {
                                    @Override
                                    public void onChanged(@Nullable List<DayWiseReportCountResponse> alldaywisecounts) {
                                        if (alldaywisecounts != null) {
                                            setDataforRV(alldaywisecounts);

                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(getActivity(), "Please select Financial year", Toast.LENGTH_SHORT).show();
                        binding.rvDaywiselist.setVisibility(View.GONE);

                    }
                } else {
                    Toast.makeText(getActivity(), "Please select Month", Toast.LENGTH_SHORT).show();
                    binding.rvDaywiselist.setVisibility(View.GONE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//
//        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                binding.refreshLayout.setRefreshing(false);
//                // code on swipe refresh
//                daywiseViewModel.getDaysCount(spn_year, GlobalDeclaration.districtId, spn_month).
//                        observe(getActivity(), new Observer<List<DayWiseReportCountResponse>>() {
//                            @Override
//                            public void onChanged(@Nullable List<DayWiseReportCountResponse> alldaywisecounts) {
//                                if (alldaywisecounts != null) {
//                                    setDataforRV(alldaywisecounts);
//                                }
//                            }
//                        });
//            }
//        });
//        binding.refreshLayout.setColorSchemeColors(Color.RED);

        binding.btnFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.easyFlipView.flipTheView();
                binding.btnFlip.setText("View");
            }
        });

        return binding.getRoot();

    }

    private void generateGraph(List<DayWiseReportCountResponse> alldaywisecounts) {
        ArrayList<Entry> entriesJRC = new ArrayList<>();
        ArrayList<Entry> entriesYRC = new ArrayList<>();
        ArrayList<Entry> entriesMembership = new ArrayList<>();

        for (int i = 0; i < alldaywisecounts.size(); i++) {
            entriesJRC.add(new Entry(((i)), alldaywisecounts.get(i).getJRC()));
        }

        for (int i = 0; i < alldaywisecounts.size(); i++) {
            entriesYRC.add(new Entry(((i)), alldaywisecounts.get(i).getYRC()));
        }

        for (int i = 0; i < alldaywisecounts.size(); i++) {
            entriesMembership.add(new Entry(((i)), alldaywisecounts.get(i).getMembership()));
        }

        LineDataSet lineDataSetJRC = new LineDataSet(entriesJRC, "JRC Enrollments");
        lineDataSetJRC.setLineWidth(2.5f);
        lineDataSetJRC.setCircleRadius(4.5f);
        lineDataSetJRC.setDrawValues(false);

        LineDataSet lineDataSetYRC = new LineDataSet(entriesYRC, "YRC Enrollments");
        lineDataSetYRC.setLineWidth(2.5f);
        lineDataSetYRC.setCircleRadius(4.5f);
        lineDataSetYRC.setDrawValues(false);

        LineDataSet lineDataSetMembership = new LineDataSet(entriesMembership, "Membership Enrollments");
        lineDataSetMembership.setLineWidth(2.5f);
        lineDataSetMembership.setCircleRadius(4.5f);
        lineDataSetMembership.setDrawValues(false);


        lineDataSetJRC.setColor(ContextCompat.getColor(getActivity(), R.color.pdlg_color_blue));
        lineDataSetJRC.setValueTextColor(ContextCompat.getColor(getActivity(), R.color.black));

        lineDataSetYRC.setColor(ContextCompat.getColor(getActivity(), R.color.green));
        lineDataSetYRC.setValueTextColor(ContextCompat.getColor(getActivity(), R.color.black));

        lineDataSetMembership.setColor(ContextCompat.getColor(getActivity(), R.color.redcroosbg_2));
        lineDataSetMembership.setValueTextColor(ContextCompat.getColor(getActivity(), R.color.black));


        XAxis xAxis = binding.chartDaywise.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(9f);
        final List<String> stringList = new ArrayList<>();

        for (int i = 0; i < alldaywisecounts.size(); i++) {
            stringList.add(alldaywisecounts.get(i).getDate());
        }
        //final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr","March","test"};

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return stringList.get((int) value);
            }

        };

        try {
            LineXYMarkerView mv = new LineXYMarkerView(getActivity(), formatter);
            mv.setChartView(binding.chartDaywise);
            binding.chartDaywise.setMarker(mv);

            xAxis.setGranularity(1f);
            xAxis.setValueFormatter(formatter);

            YAxis yAxisRight = binding.chartDaywise.getAxisRight();
            yAxisRight.setEnabled(false);

            YAxis yAxisLeft = binding.chartDaywise.getAxisLeft();
            yAxisLeft.setGranularity(1f);

            // get the legend (only possible after setting data)
            Legend l = binding.chartDaywise.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);
            l.setXEntrySpace(7f);
            l.setYEntrySpace(0f);
            l.setYOffset(0f);

//            binding.chartDaywise.setBackgroundColor(Color.WHITE);
//            binding.chartDaywise.setGridBackgroundColor(fillColor);
//            binding.chartDaywise.setDrawGridBackground(true);
//
            // set the filled area
            lineDataSetJRC.setDrawFilled(true);
            lineDataSetJRC.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return binding.chartDaywise.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            // drawables only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(getActivity(), R.color.colorPrimary);
            lineDataSetJRC.setFillDrawable(drawable);

            binding.chartDaywise.setDrawBorders(true);

            LineData data = new LineData(lineDataSetJRC, lineDataSetYRC, lineDataSetMembership);

            // lineDataSetYRC.setFillFormatter(new MyFillFormatter(lineDataSetJRC));
            // binding.chartDaywise.setRenderer(new MyLineLegendRenderer(binding.chartDaywise, binding.chartDaywise.getAnimator(), binding.chartDaywise.getViewPortHandler()));

            lineDataSetJRC.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            lineDataSetYRC.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            lineDataSetMembership.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

            binding.chartDaywise.setData(data);
            binding.chartDaywise.getDescription().setEnabled(false);
            binding.chartDaywise.animateX(1500);
            binding.chartDaywise.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void setCountsForDashboard(DashboardCountResponse dashboardCountResponse) {
        if (dashboardCountResponse != null) {
            int total = dashboardCountResponse.getJrc() + dashboardCountResponse.getYrc() + dashboardCountResponse.getMs();
            binding.customCount.tvJrccount.setText(String.valueOf(dashboardCountResponse.getJrc()));
            binding.customCount.tvYrccount.setText(String.valueOf(dashboardCountResponse.getYrc()));
            binding.customCount.tvLmcount.setText(String.valueOf(dashboardCountResponse.getMs()));
            binding.customCount.tvAllcount.setText(String.valueOf(total));
        }
        //tv_lmcount.setText(String.valueOf(dashboardCountResponse.getTotal()));
    }

    private void setDataforRV(List<DayWiseReportCountResponse> daywisecount) {

        //int r= sortDatesHere();
        if (daywisecount.size() > 0) {
            binding.btnFlip.setVisibility(View.VISIBLE);
            Collections.reverse(daywisecount);
            binding.rvDaywiselist.setHasFixedSize(true);
            binding.rvDaywiselist.setLayoutManager(new LinearLayoutManager(getActivity()));

            Collections.reverse(daywisecount);
            DaywiseAdapter adapter1 = new DaywiseAdapter(getActivity(), daywisecount, selectedThemeColor);
            binding.rvDaywiselist.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();

            generateGraph(daywisecount);

        } else {
            binding.btnFlip.setVisibility(View.GONE);
            binding.rvDaywiselist.setVisibility(View.GONE);
            binding.tvNodata.setVisibility(View.VISIBLE);

        }


    }

    private int sortDatesHere() {
        List<String> values = new ArrayList<String>();
        values.add("30-03-2012");
        values.add("28-03-2013");
        values.add("31-03-2012");
        Collections.sort(values, new Comparator<String>() {

            @Override
            public int compare(String arg0, String arg1) {
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                int compareResult = 0;
                try {
                    Date arg0Date = format.parse(arg0);
                    Date arg1Date = format.parse(arg1);
                    compareResult = arg0Date.compareTo(arg1Date);
                } catch (ParseException e) {
                    e.printStackTrace();
                    compareResult = arg0.compareTo(arg1);
                    Log.e("sorted", String.valueOf(compareResult));
                }
                return compareResult;
            }
        });
        return 0;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear(); // Remove all existing items from the menu, leaving it empty as if it had just been created.
        inflater.inflate(R.menu.activity_backpress, menu);
        MenuItem logout = menu.findItem(R.id.logout);
        logout.setIcon(R.drawable.ic_home_white_48dp);

        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getActivity(), NewOfficerMainActivity.class));
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
