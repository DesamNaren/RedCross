package in.gov.cgg.redcrossphase1.ui_officer.drilldown;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.ui_officer.OfficerMainActivity;
import in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport.AllVillageFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetDrilldownFragment extends Fragment {


    RecyclerView rv_rilldown;
    ProgressDialog pd;
    private List<String> headersList = new ArrayList<>();
    private List<List<String>> studentList = new ArrayList<>();
    DrillDownAdapter adapter1;
    String mid = "", did = "", vid = "";
    private List<StudentListBean> studentListBeanList = new ArrayList<>();
    private androidx.appcompat.widget.SearchView searchView;
    private androidx.appcompat.widget.SearchView.OnQueryTextListener queryTextListener;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_drilldown, container, false);

        Objects.requireNonNull(getActivity()).setTitle("Enrollments List");
        GlobalDeclaration.home = false;
        rv_rilldown = root.findViewById(R.id.rv_drilldown);
        searchView = root.findViewById(R.id.searchView);

        if (getArguments() != null) {
            mid = getArguments().getString("mid");
            did = getArguments().getString("did");
            vid = getArguments().getString("vid");
        } else {
            if (!GlobalDeclaration.role.contains("D")) {
                did = String.valueOf(GlobalDeclaration.localDid);
                mid = String.valueOf(GlobalDeclaration.localMid);
                vid = String.valueOf(GlobalDeclaration.localVid);
            } else {
                did = GlobalDeclaration.districtId;
            }

        }

   /*     searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search ");*/

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading ,Please wait");
        pd.show();
//
//        drilldownViewmodel =
//                ViewModelProviders.of(this).get(DrilldownViewmodel.class);
//        Objects.requireNonNull(getActivity()).setTitle("Drilldown Report");
//

//        drilldownViewmodel.getHeaders().
//                observe(getActivity(), new Observer<List<String>>() {
//                    @Override
//                    public void onChanged(@Nullable List<String> heaStringList) {
//                        if (heaStringList != null) {
//
//                        }
//                    }
//                });
//        drilldownViewmodel.getStudentList().
//                observe(getActivity(), new Observer<List<List<String>>>() {
//                    @Override
//                    public void onChanged(@Nullable List<List<String>> stuListList) {
//                        if (stuListList != null) {
//
//                        }
//                    }
//                });

        loadDrilldown();

        return root;
    }

    private void loadDrilldown() {


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DrillDownResponse> call = apiInterface.getFullDrillDownDataWs(GlobalDeclaration.type, "3", did, mid, vid);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<DrillDownResponse>() {
            @Override
            public void onResponse(Call<DrillDownResponse> call, Response<DrillDownResponse> response) {

                if (response.body() != null) {

                    headersList = response.body().getHeaders();
                    studentList = response.body().getStudentsList();
                    studentListBeanList.clear();

                    for (int position = 0; position < studentList.size(); position++) {


                        StudentListBean studentListBean = new StudentListBean();
                        if (studentList.get(position).get(0) == null) {
                            studentListBean.setDisrtict("");
                        } else {
                            studentListBean.setDisrtict(studentList.get(position).get(0));
                        }

                        if (studentList.get(position).get(1) == null) {
                            studentListBean.setMandal("");
                        } else {
                            studentListBean.setMandal(studentList.get(position).get(1));
                        }
                        if (studentList.get(position).get(2) == null) {
                            studentListBean.setVillage("");
                        } else {
                            studentListBean.setVillage(studentList.get(position).get(2));
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
                            studentListBean.setBloodgp("");
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

                        studentListBeanList.add(studentListBean);
                    }

                    rv_rilldown.setHasFixedSize(true);
                    rv_rilldown.setLayoutManager(new LinearLayoutManager(getActivity()));
                    adapter1 = new DrillDownAdapter(getActivity(), headersList, studentListBeanList);
                    rv_rilldown.setAdapter(adapter1);
                    adapter1.notifyDataSetChanged();
                    pd.dismiss();


                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DrillDownResponse> call, Throwable t) {

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
                startActivity(new Intent(getActivity(), OfficerMainActivity.class));
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
