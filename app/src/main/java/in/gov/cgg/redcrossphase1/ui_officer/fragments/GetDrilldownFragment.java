package in.gov.cgg.redcrossphase1.ui_officer.fragments;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.TestFrag;
import in.gov.cgg.redcrossphase1.databinding.FragmentDrilldownBinding;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.ui_officer.activities.NewOfficerMainActivity;
import in.gov.cgg.redcrossphase1.ui_officer.adapters.NewDrillDownAdapter;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.DrillDownResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.StudentListBean;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class GetDrilldownFragment extends TestFrag {

    private final List<StudentListBean> studentListBeanList = new ArrayList<>();
    int selectedThemeColor = -1;
    NewDrillDownAdapter adapter1;
    //FilterDrillDownAdapter adapter2;
    int mid, did, vid;
    FragmentDrilldownBinding binding;
    String etype, gender2, bGroup;
    int bgPos, genPo, typePos;
    //ProgressDialog pd;
    private List<String> headersList = new ArrayList<>();
    private List<List<String>> studentList = new ArrayList<>();
    private androidx.appcompat.widget.SearchView searchView;
    private androidx.appcompat.widget.SearchView.OnQueryTextListener queryTextListener;
    private CustomProgressDialog pd;
    private boolean ok1;
    private boolean ok2;
    private boolean ok3;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_drilldown, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Enrollments List");
        GlobalDeclaration.home = false;
        GlobalDeclaration.flag = false;
        pd = new CustomProgressDialog(getActivity());


        loaTheam();

        try {
            if (getArguments() != null) {
                mid = getArguments().getInt("mid");
                did = getArguments().getInt("did");
                vid = getArguments().getInt("vid");

            } else {
                mid = GlobalDeclaration.localMid;
                did = GlobalDeclaration.localDid;
                vid = GlobalDeclaration.localVid;
            }
        } catch (Exception e) {

        }


        if (GlobalDeclaration.leveMName != null) {
            binding.cvName.setVisibility(View.VISIBLE);
            if (!GlobalDeclaration.role.contains("D")) {
                binding.tvlevelname.setText("Dist: " + GlobalDeclaration.leveDName);
            } else {
                binding.tvlevelname.setText("");

            }
            binding.tvmname.setText("Mndl: " + GlobalDeclaration.leveMName);
            binding.tvvname.setText("Vil: " + GlobalDeclaration.leveVName);
        } else {
            binding.cvName.setVisibility(View.GONE);
        }

        loadDrilldown();
        //loaTheam();

        binding.fabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterDilaog();
            }
        });


        return binding.getRoot();
    }

    private void showFilterDilaog() {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View view = factory.inflate(R.layout.dialog_filter, null);

        final Spinner spn_type, spn_gender, spn_bg;
        TextView btn_ok, btn_cancel;

        spn_type = view.findViewById(R.id.spn_type);
        spn_gender = view.findViewById(R.id.spn_gender);
        spn_bg = view.findViewById(R.id.spn_bg);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_ok = view.findViewById(R.id.btn_submit);

        try {
            selectedThemeColor = context.getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF),
                    MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);

            if (selectedThemeColor != -1) {
                btn_cancel.setBackgroundColor(getResources().getColor(selectedThemeColor));
                btn_ok.setBackgroundColor(getResources().getColor(selectedThemeColor));
            } else {
                btn_cancel.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btn_ok.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        } catch (Exception e) {
            btn_cancel.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            btn_ok.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setView(view);
        final AlertDialog alert11 = builder1.create();

        spn_bg.setSelection(GlobalDeclaration.bgPos);
        spn_gender.setSelection(GlobalDeclaration.genPos);
        spn_type.setSelection(GlobalDeclaration.typePos);

        spn_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_type.getSelectedItem().toString().equalsIgnoreCase("all")) {
                    // Toast.makeText(getActivity(), "Please Select Enrollment Type", Toast.LENGTH_SHORT).show();
                    etype = "";
                } else {
                    ok1 = true;
                    etype = spn_type.getSelectedItem().toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spn_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_gender.getSelectedItem().toString().equalsIgnoreCase("all")) {
                    //Toast.makeText(getActivity(), "Please Select Gender", Toast.LENGTH_SHORT).show();
                    gender2 = "";

                } else {
                    ok2 = true;
                    gender2 = spn_gender.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_bg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_bg.getSelectedItem().toString().equalsIgnoreCase("all")) {
                    // Toast.makeText(getActivity(), "Please Select Bloodgroup", Toast.LENGTH_SHORT).show();
                    bGroup = "";

                } else {
                    ok3 = true;
                    bGroup = spn_bg.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckInternet.isOnline(getActivity())) {
                    loadFilterDrillDown(etype, gender2, bGroup);
                } else {
                    Toast.makeText(getActivity(), "Please check internet internet", Toast.LENGTH_SHORT).show();

                }

                GlobalDeclaration.bgPos = spn_bg.getSelectedItemPosition();
                GlobalDeclaration.genPos = spn_gender.getSelectedItemPosition();
                GlobalDeclaration.typePos = spn_type.getSelectedItemPosition();


                alert11.dismiss();

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spn_gender.setSelection(0);
                spn_bg.setSelection(0);
                spn_type.setSelection(0);
                loadDrilldown();
                GlobalDeclaration.bgPos = 0;
                GlobalDeclaration.genPos = 0;
                GlobalDeclaration.typePos = 0;
                loadDrilldown();
                Toast.makeText(getActivity(), "All filters cleared", Toast.LENGTH_SHORT).show();
                alert11.dismiss();
            }
        });


        alert11.show();


    }

    private void loadFilterDrillDown(String etype, String gender, String bloodGroup) {
        pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DrillDownResponse> call = apiInterface.getFullDrillDownDataWsFilter(etype,
                GlobalDeclaration.spn_year, did, mid, vid, gender, bloodGroup);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<DrillDownResponse>() {
            @Override
            public void onResponse(Call<DrillDownResponse> call, Response<DrillDownResponse> response) {

                pd.dismiss();

                if (response.body() != null) {
                    headersList.clear();
                    studentList.clear();
                    studentListBeanList.clear();
                    headersList = response.body().getHeaders();
                    studentList = response.body().getStudentsList();
                    // studentListBeanList.clear();

                    for (int position = 0; position < studentList.size(); position++) {


                        StudentListBean studentListBean = new StudentListBean();

                        if (studentList.get(position).get(0) == null) {
                            studentListBean.setMandal("");
                        } else {
                            studentListBean.setMandal(studentList.get(position).get(0));
                        }


                        if (studentList.get(position).get(1) == null) {
                            studentListBean.setVillage("");
                        } else {
                            studentListBean.setVillage(studentList.get(position).get(1));
                        }


                        if (studentList.get(position).get(2) == null) {
                            studentListBean.setMemberId("");
                        } else {
                            studentListBean.setMemberId(studentList.get(position).get(2));
                        }


                        if (studentList.get(position).get(3) == null) {
                            studentListBean.setName("");
                        } else {
                            studentListBean.setName(studentList.get(position).get(3));
                        }


                        if (studentList.get(position).get(4) == null) {
                            studentListBean.setGender("");
                        } else {
                            studentListBean.setGender(studentList.get(position).get(4));
                        }

                        if (studentList.get(position).get(5) == null) {
                            studentListBean.setDob("");
                        } else {
                            studentListBean.setDob(studentList.get(position).get(5));
                        }


                        if (studentList.get(position).get(6) == null) {
                            studentListBean.setPhone("");
                        } else {
                            studentListBean.setPhone(studentList.get(position).get(6));
                        }


                        if (studentList.get(position).get(7) == null) {
                            studentListBean.setBloodgp("-");
                        } else {
                            studentListBean.setBloodgp(studentList.get(position).get(7));
                        }


                        if (studentList.get(position).get(8) == null) {
                            studentListBean.setEmail("");
                        } else {
                            studentListBean.setEmail(studentList.get(position).get(8));
                        }


                        if (studentList.get(position).get(9) == null) {
                            studentListBean.setClassName("");

                        } else {
                            studentListBean.setClassName(studentList.get(position).get(9));
                        }


                        if (studentList.get(position).get(10) == null) {
                            studentListBean.setSchoolname("");

                        } else {
                            studentListBean.setSchoolname(studentList.get(position).get(10));
                        }


                        if (studentList.get(position).get(11) == null) {
                            studentListBean.setSchooltype("");
                        } else {
                            studentListBean.setSchooltype(studentList.get(position).get(11));
                        }

                        if (12 < headersList.size()) {

                            if (studentList.get(position).get(12) == null) {
                                studentListBean.setEndDate("");
                            } else {
                                studentListBean.setEndDate(studentList.get(position).get(12));
                            }
                        }
                        if (13 < headersList.size()) {

                            if (studentList.get(position).get(13) == null) {
                                studentListBean.setEnrollmentType("");
                            } else {
                                studentListBean.setEnrollmentType(studentList.get(position).get(13));
                            }
                        }

                        if (studentList.get(position).get(14) == null) {
                            studentListBean.setPhoto("");
                        } else {
                            studentListBean.setPhoto(studentList.get(position).get(14));
                        }

                        studentListBeanList.add(studentListBean);
                    }

                    List<StudentListBean> newlist = new ArrayList<>();
                    newlist.addAll(studentListBeanList);

                    Collections.sort(newlist, new Comparator<StudentListBean>() {
                        public int compare(StudentListBean lhs, StudentListBean rhs) {
                            return (lhs.getPhoto().compareTo(rhs.getPhoto()));
                        }
                    });

                    Collections.reverse(newlist);

                    if (studentListBeanList.size() > 0) {
                        binding.tvNodata.setVisibility(View.GONE);
                        binding.rvDrilldown.setVisibility(View.VISIBLE);
                        binding.rvDrilldown.setHasFixedSize(true);
                        binding.rvDrilldown.setLayoutManager(new LinearLayoutManager(getActivity()));
                        adapter1 = new NewDrillDownAdapter(getActivity(), headersList, studentListBeanList, selectedThemeColor);
                        binding.rvDrilldown.setAdapter(adapter1);
                        adapter1.notifyDataSetChanged();
                    } else {
                        binding.tvNodata.setVisibility(View.VISIBLE);
                        binding.rvDrilldown.setVisibility(View.GONE);

                    }

                }

            }

            @Override
            public void onFailure(Call<DrillDownResponse> call, Throwable t) {
                pd.dismiss();
                t.printStackTrace();
            }
        });

    }

    private void loaTheam() {
        try {
            selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF),
                    MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);

            if (selectedThemeColor != -1) {

                binding.tvvname.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvlevelname.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvmname.setTextColor(getResources().getColor(selectedThemeColor));
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    binding.llDrilldown.setBackground(getResources().getDrawable(R.drawable.redcross1_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    binding.llDrilldown.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    binding.llDrilldown.setBackground(getResources().getDrawable(R.drawable.redcross3_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    binding.llDrilldown.setBackground(getResources().getDrawable(R.drawable.redcross4_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    binding.llDrilldown.setBackground(getResources().getDrawable(R.drawable.redcross5_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    binding.llDrilldown.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    binding.llDrilldown.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    binding.llDrilldown.setBackground(getResources().getDrawable(R.drawable.redcross8_bg));
                } else {
                    binding.llDrilldown.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            binding.llDrilldown.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
        }
    }

    private void loadDrilldown() {

        if (CheckInternet.isOnline(getActivity())) {
            pd.show();
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<DrillDownResponse> call = apiInterface.getFullDrillDownDataWs("", GlobalDeclaration.spn_year,
                    "", did, mid, vid);
            Log.e("  url", call.request().url().toString());

            call.enqueue(new Callback<DrillDownResponse>() {
                @Override
                public void onResponse(Call<DrillDownResponse> call, Response<DrillDownResponse> response) {

                    pd.dismiss();

                    if (response.body() != null) {

                        headersList = response.body().getHeaders();
                        studentList = response.body().getStudentsList();

                        for (int position = 0; position < studentList.size(); position++) {


                            StudentListBean studentListBean = new StudentListBean();

                            if (studentList.get(position).get(0) == null) {
                                studentListBean.setMandal("");
                            } else {
                                studentListBean.setMandal(studentList.get(position).get(0));
                            }


                            if (studentList.get(position).get(1) == null) {
                                studentListBean.setVillage("");
                            } else {
                                studentListBean.setVillage(studentList.get(position).get(1));
                            }


                            if (studentList.get(position).get(2) == null) {
                                studentListBean.setMemberId("");
                            } else {
                                studentListBean.setMemberId(studentList.get(position).get(2));
                            }


                            if (studentList.get(position).get(3) == null) {
                                studentListBean.setName("");
                            } else {
                                studentListBean.setName(studentList.get(position).get(3));
                            }


                            if (studentList.get(position).get(4) == null) {
                                studentListBean.setGender("");
                            } else {
                                studentListBean.setGender(studentList.get(position).get(4));
                            }

                            if (studentList.get(position).get(5) == null) {
                                studentListBean.setDob("");
                            } else {
                                studentListBean.setDob(studentList.get(position).get(5));
                            }


                            if (studentList.get(position).get(6) == null) {
                                studentListBean.setPhone("");
                            } else {
                                studentListBean.setPhone(studentList.get(position).get(6));
                            }


                            if (studentList.get(position).get(7) == null) {
                                studentListBean.setBloodgp("-");
                            } else {
                                studentListBean.setBloodgp(studentList.get(position).get(7));
                            }


                            if (studentList.get(position).get(8) == null) {
                                studentListBean.setEmail("");
                            } else {
                                studentListBean.setEmail(studentList.get(position).get(8));
                            }


                            if (studentList.get(position).get(9) == null) {
                                studentListBean.setClassName("");

                            } else {
                                studentListBean.setClassName(studentList.get(position).get(9));
                            }


                            if (studentList.get(position).get(10) == null) {
                                studentListBean.setSchoolname("");

                            } else {
                                studentListBean.setSchoolname(studentList.get(position).get(10));
                            }


                            if (studentList.get(position).get(11) == null) {
                                studentListBean.setSchooltype("");
                            } else {
                                studentListBean.setSchooltype(studentList.get(position).get(11));
                            }

                            if (12 < headersList.size()) {

                                if (studentList.get(position).get(12) == null) {
                                    studentListBean.setEndDate("");
                                } else {
                                    studentListBean.setEndDate(studentList.get(position).get(12));
                                }
                            }
                            if (13 < headersList.size()) {
                                if (studentList.get(position).get(13) == null) {
                                    studentListBean.setEnrollmentType("");
                                } else {
                                    studentListBean.setEnrollmentType(studentList.get(position).get(13));
                                }
                            }

                            if (studentList.get(position).get(14) == null) {
                                studentListBean.setPhoto("");
                            } else {
                                studentListBean.setPhoto(studentList.get(position).get(14));
                            }

                            studentListBeanList.add(studentListBean);
                        }

                        List<StudentListBean> newlist = new ArrayList<>();
                        newlist.addAll(studentListBeanList);

                        Collections.sort(newlist, new Comparator<StudentListBean>() {
                            public int compare(StudentListBean lhs, StudentListBean rhs) {
                                return (lhs.getPhoto().compareTo(rhs.getPhoto()));
                            }
                        });

                        Collections.reverse(newlist);

                        if (studentListBeanList.size() > 0) {
                            binding.tvNodata.setVisibility(View.GONE);
                            binding.rvDrilldown.setVisibility(View.VISIBLE);
                            binding.rvDrilldown.setHasFixedSize(true);
                            binding.rvDrilldown.setLayoutManager(new LinearLayoutManager(getActivity()));

                            adapter1 = new NewDrillDownAdapter(getActivity(), headersList, newlist, selectedThemeColor);
                            binding.rvDrilldown.setAdapter(adapter1);
                            adapter1.notifyDataSetChanged();
                        } else {
                            binding.tvNodata.setVisibility(View.VISIBLE);
                            binding.rvDrilldown.setVisibility(View.GONE);
                            binding.tvNodata.setText("No data available");


                        }
                    }

                }

                @Override
                public void onFailure(Call<DrillDownResponse> call, Throwable t) {
                    pd.dismiss();
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please check internet internet", Toast.LENGTH_SHORT).show();

        }

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
                    Fragment frag = new AllVillageFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();
                    return true;

                }
                return false;
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear(); // Remove all existing items from the menu, leaving it empty as if it had just been created.
        inflater.inflate(R.menu.activity_searchmenu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        menu.findItem(R.id.logout_search).setIcon(R.drawable.ic_home_white_48dp);
        menu.findItem(R.id.logout_search).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getActivity(), NewOfficerMainActivity.class));
                return true;
            }
        });

        if (searchItem != null) {
            searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);
                    if (adapter1 != null) {
                        adapter1.filter(newText);
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
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
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
