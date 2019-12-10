package in.gov.cgg.redcrossphase1.ui_officer.drilldown;

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
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentDrilldownBinding;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport.AllVillageFragment;
import in.gov.cgg.redcrossphase1.ui_officer_new.NewOfficerMainActivity;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class GetDrilldownFragment extends Fragment {

    int selectedThemeColor = -1;
    //ProgressDialog pd;
    private List<String> headersList = new ArrayList<>();
    private List<List<String>> studentList = new ArrayList<>();
    NewDrillDownAdapter adapter1;
    int mid, did, vid;
    private List<StudentListBean> studentListBeanList = new ArrayList<>();
    private androidx.appcompat.widget.SearchView searchView;
    private androidx.appcompat.widget.SearchView.OnQueryTextListener queryTextListener;
    private CustomProgressDialog pd;
    FragmentDrilldownBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_drilldown, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Enrollments List");
        GlobalDeclaration.home = false;

        pd = new CustomProgressDialog(getActivity());

        if (getArguments() != null) {
            mid = getArguments().getInt("mid");
            did = getArguments().getInt("did");
            vid = getArguments().getInt("vid");

        } else {
            mid = GlobalDeclaration.localMid;
            did = GlobalDeclaration.localDid;
            vid = GlobalDeclaration.localVid;
        }


        if (GlobalDeclaration.leveMName != null) {
            binding.cvName.setVisibility(View.VISIBLE);
            binding.tvlevelname.setText("Dist: " + GlobalDeclaration.leveDName);
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

        Spinner spn_type, spn_gender, spn_bg;

        spn_type = view.findViewById(R.id.spn_type);
        spn_gender = view.findViewById(R.id.spn_gender);
        spn_bg = view.findViewById(R.id.spn_bg);


        final AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setView(view);
        final AlertDialog alert11 = builder1.create();
        alert11.show();


    }

    private void loaTheam() {
        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
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
                    binding.llDrilldown.setBackground(getResources().getDrawable(R.drawable.redcross_splashscreen_bg));
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            binding.llDrilldown.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
        }
    }

    private void loadDrilldown() {

        pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DrillDownResponse> call = apiInterface.getFullDrillDownDataWs("", "3", did, mid, vid);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<DrillDownResponse>() {
            @Override
            public void onResponse(Call<DrillDownResponse> call, Response<DrillDownResponse> response) {

                pd.dismiss();

                if (response.body() != null) {

                    headersList = response.body().getHeaders();
                    studentList = response.body().getStudentsList();
                    // studentListBeanList.clear();

                    for (int position = 0; position < studentList.size(); position++) {


                        StudentListBean studentListBean = new StudentListBean();
//                        if (studentList.get(position).get(0) == null) {
//                            studentListBean.setDisrtict("");
//                        } else {
//                            studentListBean.setDisrtict(studentList.get(position).get(0));
//                        }


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


                        if (studentList.get(position).get(12) == null) {
                            studentListBean.setEndDate("");
                        } else {
                            studentListBean.setEndDate(studentList.get(position).get(12));
                        }


                        if (studentList.get(position).get(13) == null) {
                            studentListBean.setEnrollmentType("");
                        } else {
                            studentListBean.setEnrollmentType(studentList.get(position).get(13));
                        }


                        studentListBeanList.add(studentListBean);
                    }
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

            @Override
            public void onFailure(Call<DrillDownResponse> call, Throwable t) {
                pd.dismiss();
                t.printStackTrace();
            }
        });


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

    /*    @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if (adapter1 != null) {
                adapter1.filter(newText);
            }
            return true;
        }*/
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

}
