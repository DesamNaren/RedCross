package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Objects;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.ServeAdapter;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.ServeBean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.ServeBeanMainBean;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServicesFragment extends Fragment {

    ServeAdapter serveAdapter;
    RecyclerView rv;
    private CustomProgressDialog progressDialog;
    private ArrayList<ServeBean> mainList;
    private TextView noDataTV;
    private Spinner spinner_bg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_services, container, false);
        Objects.requireNonNull(getActivity()).setTitle("SERV");
        progressDialog = new CustomProgressDialog(getActivity());
        rv = root.findViewById(R.id.bb_rv);
        noDataTV = root.findViewById(R.id.noDataTV);
        spinner_bg = root.findViewById(R.id.spinner_bg);
        // sortData(mainList);
        calladditionsCentersService("SERV Volunteer");

        spinner_bg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {
                    String selVal = spinner_bg.getSelectedItem().toString();
                    if (serveAdapter != null) {
                        if (progressDialog != null && !progressDialog.isShowing()) {
                            progressDialog.show();
                        }
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }

                        }, 2000);


                        serveAdapter.getFilter().filter(selVal);
                        serveAdapter.notifyDataSetChanged();
                        rv.smoothScrollToPosition(0);

                    }
                } else {
                    if (serveAdapter != null) {
                        serveAdapter = new ServeAdapter(getActivity(), mainList);
                        rv.setAdapter(serveAdapter);
                        serveAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return root;
    }


    private void calladditionsCentersService(String type) {


        ApiInterface apiServiceSession = ApiClient.getClient().create(ApiInterface.class);


        Call<ServeBeanMainBean> call = apiServiceSession.getadditionsCentersService(type);
        Log.e("apiServiceSession_url: ", "" + call.request().url());
        call.enqueue(new Callback<ServeBeanMainBean>() {
            @Override
            public void onResponse(Call<ServeBeanMainBean> call, Response<ServeBeanMainBean> response) {
                // hideProgressDialog(progressDiaLogss);
                try {
                    ServeBeanMainBean body = response.body();

                    if (body != null) {
                        mainList = (ArrayList<ServeBean>) body.getAdditionscenters();
                        ArrayList<ServeBean> servList = new ArrayList<>();
                        for (int x = 0; x < mainList.size(); x++) {
                            if (mainList.get(x).getType().contains("SERV")) {
                                servList.add(mainList.get(x));
                            }
                        }

                        ArrayList<String> arrayList = new ArrayList<>();
                        for (int x = 0; x < mainList.size(); x++) {
                            arrayList.add(mainList.get(x).getDistirctid());
                        }


                        LinkedHashSet<String> hashSet = new LinkedHashSet<String>(arrayList);
                        ArrayList<String> listWithoutDuplicates = new ArrayList<>(hashSet);

                        if (arrayList.size() > 0) {

                            listWithoutDuplicates.add(0, "Select District");
                            ArrayAdapter dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listWithoutDuplicates);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_bg.setAdapter(dataAdapter);
                        }

                        if (servList.size() > 0) {

                            //filterIV.setVisibility(View.VISIBLE);
                            rv.setVisibility(View.VISIBLE);
                            noDataTV.setVisibility(View.GONE);
                            final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            rv.setLayoutManager(layoutManager);

                            serveAdapter = new ServeAdapter(getActivity(), servList);
                            rv.setAdapter(serveAdapter);
                            serveAdapter.notifyDataSetChanged();


                        } else {
                            rv.setVisibility(View.GONE);
                            noDataTV.setVisibility(View.VISIBLE);
                        }
                    } else {
                        //  showResponseAlert();
                    }


                } catch (Exception e) {
                    Toast.makeText(getContext(), "Something went wrong" + e.toString(), Toast.LENGTH_SHORT).show();
                    Log.e("Error", e.toString());
                }
            }

            @Override
            public void onFailure(Call<ServeBeanMainBean> call, Throwable t) {
                //hideProgressDialog(progressDiaLogss);

                Log.e("Exp", t.toString());

            }
        });

    }
}


