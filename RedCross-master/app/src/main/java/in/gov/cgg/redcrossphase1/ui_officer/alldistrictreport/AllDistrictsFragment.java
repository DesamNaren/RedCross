package in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;

public class AllDistrictsFragment extends Fragment {


    ProgressDialog pd;
    private AllDistrictsViewModel allDistrictsViewModel;
    private RecyclerView rv_district;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_aldistrict, container, false);

        rv_district = root.findViewById(R.id.rv_alldistrictwise);
        GlobalDeclaration.home = false;

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading ,Please wait");
        pd.show();
        allDistrictsViewModel =
                ViewModelProviders.of(this).get(AllDistrictsViewModel.class);
        Objects.requireNonNull(getActivity()).setTitle("District wise");


        allDistrictsViewModel.getAllDistrcts("STATE").
                observe(getActivity(), new Observer<List<AllDistrict>>() {
                    @Override
                    public void onChanged(@Nullable List<AllDistrict> allDistrictList) {
                        if (allDistrictList != null) {
                            setDataforRV(allDistrictList);
                            pd.dismiss();

                        }
                    }
                });


        return root;

    }

    private void setDataforRV(List<AllDistrict> allDistrictList) {
        rv_district.setHasFixedSize(true);
        rv_district.setLayoutManager(new LinearLayoutManager(getActivity()));
        AllDistrictAdapter adapter1 = new AllDistrictAdapter(getActivity(), allDistrictList);
        rv_district.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();


    }
}
