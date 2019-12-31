package in.gov.cgg.redcrossphase_offline.ui_citiguest.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import in.gov.cgg.redcrossphase_offline.R;
import in.gov.cgg.redcrossphase_offline.databinding.PoiCertificateFragBinding;

public class POICertificateFragment extends Fragment {

    private PoiCertificateFragBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
                inflater, R.layout.poi_certificate_frag, container, false);


        return binding.getRoot();
    }


}







