package in.gov.cgg.redcrossphase_offline.ui_officer.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase_offline.R;
import in.gov.cgg.redcrossphase_offline.TestFrag;
import in.gov.cgg.redcrossphase_offline.databinding.FragmentDaywiseBinding;
import in.gov.cgg.redcrossphase_offline.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase_offline.ui_officer.activities.NewOfficerMainActivity;
import in.gov.cgg.redcrossphase_offline.ui_officer.adapters.DaywiseAdapter;
import in.gov.cgg.redcrossphase_offline.ui_officer.custom_officer.CustomDistricClass;
import in.gov.cgg.redcrossphase_offline.ui_officer.custom_officer.LineXYMarkerView;
import in.gov.cgg.redcrossphase_offline.ui_officer.modelbeans.DashboardCountResponse;
import in.gov.cgg.redcrossphase_offline.ui_officer.modelbeans.DayWiseReportCountResponse;
import in.gov.cgg.redcrossphase_offline.ui_officer.viewmodels.DaywiseViewModel;

import static android.content.Context.MODE_PRIVATE;
import static in.gov.cgg.redcrossphase_offline.R.color.colorPrimary;

public class DaywiseFragment extends TestFrag {

    int selectedThemeColor = -1;
    private DaywiseViewModel daywiseViewModel;
    private FragmentDaywiseBinding binding;
    String spn_year;
    int spn_month;
    String newspn_month;
    int i = 0;
    int selectionPosition;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_daywise, container, false);

        binding.easyFlipView.setFlipDuration(1000);
        binding.easyFlipView.setFlipEnabled(true);

        GlobalDeclaration.home = false;
        try {
            selectedThemeColor = context.getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            callThemesChanges();

        } catch (Exception e) {
            e.printStackTrace();

        }
        daywiseViewModel =
                ViewModelProviders.of(this, new CustomDistricClass(getActivity(), "day")).get(DaywiseViewModel.class);
        Objects.requireNonNull(getActivity()).setTitle("Day wise");
        binding.cvDay.setVisibility(View.VISIBLE);
        if (GlobalDeclaration.counts != null) {
            setCountsForDashboard(GlobalDeclaration.counts);
        }

        Calendar c = Calendar.getInstance();
        String[] monthName = {"January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};
        String month = monthName[c.get(Calendar.MONTH)];
        Log.e("fjsdjdf............", "Month name:" + month);


        ArrayAdapter dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, monthName);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnMonth.setAdapter(dataAdapter);

        selectionPosition = dataAdapter.getPosition(month);
        binding.spnMonth.setSelection(selectionPosition);

        binding.spnFinancialyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (binding.spnFinancialyear.getSelectedItem().toString().contains("2019")) {
                    spn_year = "3";
                    if (binding.spnMonth.getSelectedItem().toString().contains("Select")) {
                        Toast.makeText(getActivity(), "Please select Month", Toast.LENGTH_SHORT).show();
                        binding.rvDaywiselist.setVisibility(View.GONE);

                    } else {
                        daywiseViewModel.getDaysCount(spn_year, GlobalDeclaration.districtId, newspn_month).
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
                        daywiseViewModel.getDaysCount(spn_year, GlobalDeclaration.districtId, newspn_month).
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
//                if (binding.spnMonth.getSelectedItem().toString().contains("Novem")) {
//                    spn_month = "11";
//                    if (!spn_year.equalsIgnoreCase("")) {
//                        daywiseViewModel.getDaysCount(spn_year, GlobalDeclaration.districtId, spn_month).
//                                observe(getActivity(), new Observer<List<DayWiseReportCountResponse>>() {
//                                    @Override
//                                    public void onChanged(@Nullable List<DayWiseReportCountResponse> alldaywisecounts) {
//                                        if (alldaywisecounts != null) {
//                                            setDataforRV(alldaywisecounts);
//                                        }
//                                    }
//                                });
//                    } else {
//                        Toast.makeText(getActivity(), "Please select Financial year", Toast.LENGTH_SHORT).show();
//                        binding.rvDaywiselist.setVisibility(View.GONE);
//
//                    }
//                } else if (binding.spnMonth.getSelectedItem().toString().contains("Decem")) {
//                    spn_month = "12";
//                    if (!spn_year.equalsIgnoreCase("")) {
//                        daywiseViewModel.getDaysCount(spn_year, GlobalDeclaration.districtId, spn_month).
//                                observe(getActivity(), new Observer<List<DayWiseReportCountResponse>>() {
//                                    @Override
//                                    public void onChanged(@Nullable List<DayWiseReportCountResponse> alldaywisecounts) {
//                                        if (alldaywisecounts != null) {
//                                            setDataforRV(alldaywisecounts);
//
//                                        }
//                                    }
//                                });
//                    } else {
//                        Toast.makeText(getActivity(), "Please select Financial year", Toast.LENGTH_SHORT).show();
//                        binding.rvDaywiselist.setVisibility(View.GONE);
//
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "Please select Month", Toast.LENGTH_SHORT).show();
//                    binding.rvDaywiselist.setVisibility(View.GONE);
//
//                }
                spn_month = (binding.spnMonth.getSelectedItemPosition() + 1);
                newspn_month = String.format("%02d", spn_month);
                Log.e("position............", "Month Id:" + newspn_month);


                if (!spn_year.equalsIgnoreCase("")) {

                    daywiseViewModel.getDaysCount(spn_year, GlobalDeclaration.districtId, newspn_month).
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

        if (getActivity() != null) {
            lineDataSetJRC.setColor(ContextCompat.getColor(getActivity(), R.color.pdlg_color_blue));
            lineDataSetJRC.setValueTextColor(ContextCompat.getColor(getActivity(), R.color.black));

            lineDataSetYRC.setColor(ContextCompat.getColor(getActivity(), R.color.green));
            lineDataSetYRC.setValueTextColor(ContextCompat.getColor(getActivity(), R.color.black));

            lineDataSetMembership.setColor(ContextCompat.getColor(getActivity(), R.color.redcroosbg_2));
            lineDataSetMembership.setValueTextColor(ContextCompat.getColor(getActivity(), R.color.black));
        } else {
            lineDataSetJRC.setColor(ContextCompat.getColor(context, R.color.pdlg_color_blue));
            lineDataSetJRC.setValueTextColor(ContextCompat.getColor(context, R.color.black));

            lineDataSetYRC.setColor(ContextCompat.getColor(context, R.color.green));
            lineDataSetYRC.setValueTextColor(ContextCompat.getColor(context, R.color.black));

            lineDataSetMembership.setColor(ContextCompat.getColor(context, R.color.redcroosbg_2));
            lineDataSetMembership.setValueTextColor(ContextCompat.getColor(context, R.color.black));
        }


        XAxis xAxis = binding.chartDaywise.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(9f);
        final List<String> stringList = new ArrayList<>();

        for (int i = 0; i < alldaywisecounts.size(); i++) {
            stringList.add(alldaywisecounts.get(i).getDate().substring(0, 5));
        }
        //final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr","March","test"};

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return stringList.get((int) value);
            }

        };

        try {
            if (getActivity() != null) {
                LineXYMarkerView mv = new LineXYMarkerView(getActivity(), formatter);
                mv.setChartView(binding.chartDaywise);
                binding.chartDaywise.setMarker(mv);
            } else {
                LineXYMarkerView mv = new LineXYMarkerView(context, formatter);
                mv.setChartView(binding.chartDaywise);
                binding.chartDaywise.setMarker(mv);
            }

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
            lineDataSetJRC.setDrawFilled(false);
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
            binding.chartDaywise.animateY(1500);
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
        } else {
            binding.customCount.tvJrccount.setText("");
            binding.customCount.tvYrccount.setText("");
            binding.customCount.tvLmcount.setText("");
            binding.customCount.tvAllcount.setText("");
        }
        //tv_lmcount.setText(String.valueOf(dashboardCountResponse.getTotal()));
    }

    private void setDataforRV(List<DayWiseReportCountResponse> daywisecount) {

        //int r= sortDatesHere();
        if (daywisecount.size() > 1) {
            binding.chartDaywise.setVisibility(View.VISIBLE);
            binding.btnFlip.setVisibility(View.VISIBLE);
            binding.tvNodata.setVisibility(View.GONE);
            Collections.reverse(daywisecount);
            binding.rvDaywiselist.setHasFixedSize(true);
            binding.rvDaywiselist.setLayoutManager(new LinearLayoutManager(getActivity()));

            DaywiseAdapter adapter1 = new DaywiseAdapter(getActivity(), daywisecount, selectedThemeColor);
            binding.rvDaywiselist.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();

            generateGraph(daywisecount);

        } else {
            binding.btnFlip.setVisibility(View.GONE);
            binding.chartDaywise.setVisibility(View.GONE);
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
        MenuItem logout = menu.findItem(R.id.home);
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    //Pressed return button - returns to the results menu
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getView()).setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    FragmentActivity activity = (FragmentActivity) v.getContext();
                        Fragment frag = new NewOfficerHomeFragment();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                                frag).addToBackStack(null).commit();
                        return true;
                }
                return false;
            }
        });
    }

    private void callThemesChanges() {
        if (selectedThemeColor != -1) {

            binding.customCount.tvAllcount.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvAllname.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvJrccount.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvJrcnme.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvYrccount.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvLmcount.setTextColor(getResources().getColor(selectedThemeColor));
            binding.customCount.tvLmname.setTextColor(getResources().getColor(selectedThemeColor));

            if (selectedThemeColor == R.color.redcroosbg_1) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
            } else if (selectedThemeColor == R.color.redcroosbg_2) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
            } else if (selectedThemeColor == R.color.redcroosbg_3) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
            } else if (selectedThemeColor == R.color.redcroosbg_4) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
            } else if (selectedThemeColor == R.color.redcroosbg_5) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
            } else if (selectedThemeColor == R.color.redcroosbg_6) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
            } else if (selectedThemeColor == R.color.redcroosbg_7) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
            } else if (selectedThemeColor == R.color.redcroosbg_8) {
                binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
            }
        } else {
            binding.customCount.tvAllcount.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvAllname.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvJrccount.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvJrcnme.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvYrccount.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvYrcnme.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvLmcount.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.tvLmname.setTextColor(getResources().getColor(colorPrimary));
            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
            binding.customCount.llAll.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
        }
    }

}
