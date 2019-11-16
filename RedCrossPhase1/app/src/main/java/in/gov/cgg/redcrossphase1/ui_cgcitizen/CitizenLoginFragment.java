package in.gov.cgg.redcrossphase1.ui_cgcitizen;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentCitizenBinding;

public class CitizenLoginFragment extends Fragment {

    FragmentCitizenBinding binding;

    public CitizenLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_citizen, container, false);

        // binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_citizen);


        return v;
    }

}
