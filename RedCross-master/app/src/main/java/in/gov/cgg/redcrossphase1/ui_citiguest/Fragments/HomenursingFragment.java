package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_citiguest.HomeNursingActivity;

public class HomenursingFragment extends Fragment {
    Button btn_homeNursingRegProceed;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.homenursing_fragment_layout, container, false);
        //binding = DataBindingUtil.inflate(inflater, R.layout.homenursing_fragment_layout, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Home Nursing");
        btn_homeNursingRegProceed = root.findViewById(R.id.btn_homeNursingRegProceed);

        btn_homeNursingRegProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeNursingActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}
