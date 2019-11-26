package in.gov.cgg.redcrossphase1.ui_officer.daywisereportcont;


import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_officer.drilldown.DrillDownAdapter;
import in.gov.cgg.redcrossphase1.ui_officer.drilldown.DrillDownResponse;
import in.gov.cgg.redcrossphase1.ui_officer.drilldown.DrilldownViewmodel;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;

import static in.gov.cgg.redcrossphase1.R.color.colorPrimary;
import static in.gov.cgg.redcrossphase1.R.color.white;

public class DaywiseFragment extends Fragment {

    private DrilldownViewmodel drilldownViewmodel;
    private LinearLayout ll_jrc, ll_datepicker;
    private LinearLayout ll_yrc;
    private LinearLayout ll_lm;
    private TextView tv_jrcocunt, tv_yrccount, tv_lmcount, tv_yrcname, tv_jrcname, tv_lmname;
    private RecyclerView recyclerView_daywise;
    private TextView txtDate;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String selectedDate = "", date = "";


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        DaywiseViewModel daywiseViewModel = ViewModelProviders.of(this).get(DaywiseViewModel.class);
        View root = inflater.inflate(R.layout.fragment_daywise, container, false);
        drilldownViewmodel =
                ViewModelProviders.of(this).get(DrilldownViewmodel.class);
        Objects.requireNonNull(getActivity()).setTitle("District wise");

        GlobalDeclaration.home = false;
        findAllVIEWS(root);

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        selectedDate = new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
        txtDate.setText(selectedDate);

        Objects.requireNonNull(getActivity()).setTitle("Day wise");

        if (CheckInternet.isOnline(getActivity())) {
            daywiseViewModel.getDaysCount("3", GlobalDeclaration.districtId, "11").
                    observe(getActivity(), new Observer<List<DayWiseReportCountResponse>>() {
                        @Override
                        public void onChanged(@Nullable List<DayWiseReportCountResponse> dayWiseReportCountResponse) {
                            if (dayWiseReportCountResponse != null) {
                                setCountsForDashboard(dayWiseReportCountResponse);
                            }
                        }
                    });
            drilldownViewmodel.getDrillDownList("JRC", "3", selectedDate, GlobalDeclaration.districtId).
                    observe(getActivity(), new Observer<DrillDownResponse>() {
                        @Override
                        public void onChanged(@Nullable DrillDownResponse allDistrictList) {
                            if (allDistrictList != null) {
                                setDataforRV(allDistrictList);
                            }
                        }
                    });
        } else {
            Snackbar snackbar = Snackbar
                    .make(ll_jrc, "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        ll_datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                // txtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
//                                txtDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()));
                                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                                try {
                                    selectedDate = df.format(df.parse(date));
                                    txtDate.setText(selectedDate);
                                    Toast.makeText(getActivity(), "Selected Enrollment type", Toast.LENGTH_SHORT).show();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
//                                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
//                                try {
//                                    Date date = fmt.parse(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                                    selectedDate=date.toString();
//                                    Log.e("Datehere",selectedDate);
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        ll_jrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckInternet.isOnline(getActivity())) {
                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
                    tv_jrcocunt.setTextColor(getResources().getColor(white));
                    tv_jrcname.setTextColor(getResources().getColor(white));
//
                    ll_yrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                    tv_yrcname.setTextColor(getResources().getColor(colorPrimary));
                    tv_yrccount.setTextColor(getResources().getColor(colorPrimary));


                    ll_lm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                    tv_lmcount.setTextColor(getResources().getColor(colorPrimary));
                    tv_lmname.setTextColor(getResources().getColor(colorPrimary));


                    drilldownViewmodel.getDrillDownList("JRC", "3", selectedDate, GlobalDeclaration.districtId).
                            observe(getActivity(), new Observer<DrillDownResponse>() {
                                @Override
                                public void onChanged(@Nullable DrillDownResponse allDistrictList) {
                                    if (allDistrictList != null) {
                                        setDataforRV(allDistrictList);
                                    }
                                }
                            });

                } else {
                    Snackbar snackbar = Snackbar
                            .make(ll_jrc, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });
        ll_yrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckInternet.isOnline(getActivity())) {
                    ll_yrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
                    tv_yrccount.setTextColor(getResources().getColor(white));
                    tv_yrcname.setTextColor(getResources().getColor(white));
//
                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                    tv_jrcocunt.setTextColor(getResources().getColor(colorPrimary));
                    tv_jrcname.setTextColor(getResources().getColor(colorPrimary));


                    ll_lm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                    tv_lmcount.setTextColor(getResources().getColor(colorPrimary));
                    tv_lmname.setTextColor(getResources().getColor(colorPrimary));

                    drilldownViewmodel.getDrillDownList("YRC", "3", selectedDate, GlobalDeclaration.districtId).
                            observe(getActivity(), new Observer<DrillDownResponse>() {
                                @Override
                                public void onChanged(@Nullable DrillDownResponse allDistrictList) {
                                    if (allDistrictList != null) {
                                        setDataforRV(allDistrictList);
                                    }
                                }
                            });


                } else {
                    Snackbar snackbar = Snackbar
                            .make(ll_jrc, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });
        ll_lm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckInternet.isOnline(getActivity())) {
                    ll_jrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                    tv_jrcocunt.setTextColor(getResources().getColor(colorPrimary));
                    tv_jrcname.setTextColor(getResources().getColor(colorPrimary));
//
                    ll_yrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
                    tv_yrcname.setTextColor(getResources().getColor(colorPrimary));
                    tv_yrccount.setTextColor(getResources().getColor(colorPrimary));


                    ll_lm.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));
                    tv_lmcount.setTextColor(getResources().getColor(white));
                    tv_lmname.setTextColor(getResources().getColor(white));


                    drilldownViewmodel.getDrillDownList("Membership", "3", selectedDate, GlobalDeclaration.districtId).
                            observe(getActivity(), new Observer<DrillDownResponse>() {
                                @Override
                                public void onChanged(@Nullable DrillDownResponse allDistrictList) {
                                    if (allDistrictList != null) {
                                        setDataforRV(allDistrictList);
                                    }
                                }
                            });

                } else {
                    Snackbar snackbar = Snackbar
                            .make(ll_jrc, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });


        return root;
    }

    private void setDataforRV(DrillDownResponse drillDownResponse) {

        recyclerView_daywise.setHasFixedSize(true);
        recyclerView_daywise.setLayoutManager(new LinearLayoutManager(getActivity()));
        DrillDownAdapter adapter1 = new DrillDownAdapter(getActivity(), drillDownResponse.getHeaders(),
                drillDownResponse.getStudentsList());
        recyclerView_daywise.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
    }

    private void setCountsForDashboard(List<DayWiseReportCountResponse> dayWiseReportCountResponse) {
        if (dayWiseReportCountResponse != null) {
            int jrcCount = 0, yrcCount = 0, MembeshipCount = 0;


            for (int i = 0; i < dayWiseReportCountResponse.size(); i++) {
                jrcCount += dayWiseReportCountResponse.get(i).getJRC();
                yrcCount += dayWiseReportCountResponse.get(i).getYRC();
                MembeshipCount += dayWiseReportCountResponse.get(i).getMembership();
            }
            tv_jrcocunt.setText(String.valueOf(jrcCount));
            tv_yrccount.setText(String.valueOf(yrcCount));
            tv_lmcount.setText(String.valueOf(MembeshipCount));
        }
    }

    private void findAllVIEWS(View root) {
        ll_jrc = root.findViewById(R.id.ll_jrc);
        ll_yrc = root.findViewById(R.id.ll_yrc);
        ll_lm = root.findViewById(R.id.ll_lm);
        Spinner spinYear = root.findViewById(R.id.spn_financialyear);
        Spinner spinMonth = root.findViewById(R.id.spn_month);
        ll_datepicker = root.findViewById(R.id.ll_datepicker);
        txtDate = root.findViewById(R.id.text_date);
        tv_jrcocunt = root.findViewById(R.id.tv_jrccount);
        tv_yrccount = root.findViewById(R.id.tv_yrccount);
        tv_lmcount = root.findViewById(R.id.tv_lmcount);
        tv_lmname = root.findViewById(R.id.tv_lmname);
        tv_jrcname = root.findViewById(R.id.tv_jrcnme);
        tv_yrcname = root.findViewById(R.id.tv_yrcnme);

        recyclerView_daywise = root.findViewById(R.id.rv_daywiselist);

    }


}