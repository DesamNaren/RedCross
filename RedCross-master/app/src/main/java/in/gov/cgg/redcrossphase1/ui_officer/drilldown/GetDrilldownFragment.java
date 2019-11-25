package in.gov.cgg.redcrossphase1.ui_officer.drilldown;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetDrilldownFragment extends Fragment {


    RecyclerView rv_rilldown;
    ProgressDialog pd;
    private DrilldownViewmodel drilldownViewmodel;
    private List<String> headersList = new ArrayList<>();
    private List<List<String>> studentList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_drilldown, container, false);

        Objects.requireNonNull(getActivity()).setTitle("Enrollments List");
        GlobalDeclaration.home = false;
        rv_rilldown = root.findViewById(R.id.rv_drilldown);

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
        Call<DrillDownResponse> call = apiInterface.getFullDrillDownDataWs();
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<DrillDownResponse>() {
            @Override
            public void onResponse(Call<DrillDownResponse> call, Response<DrillDownResponse> response) {

                if (response.body() != null) {

                    headersList = response.body().getHeaders();
                    studentList = response.body().getStudentsList();

                    rv_rilldown.setHasFixedSize(true);
                    rv_rilldown.setLayoutManager(new LinearLayoutManager(getActivity()));
                    DrillDownAdapter adapter1 = new DrillDownAdapter(getActivity(), headersList, studentList);
                    rv_rilldown.setAdapter(adapter1);
                    adapter1.notifyDataSetChanged();
                    pd.dismiss();


//                    HashMap<String, String> map = new HashMap<>();
//
//                    for (int i = 0; i < studentList.size(); i++) {
//
//                        map.put(headersList.get(i), studentList.get(i).get(i));
//                    }
//                    //map.put(headersList.get(0),studentList.get(0).get(0));
//
//                    Set set = map.entrySet();
//                    Iterator iterator = set.iterator();
//                    while(iterator.hasNext()) {
//                        Map.Entry mentry = (Map.Entry)iterator.next();
//                        System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
//                        System.out.println(mentry.getValue());
//                    }


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

}
