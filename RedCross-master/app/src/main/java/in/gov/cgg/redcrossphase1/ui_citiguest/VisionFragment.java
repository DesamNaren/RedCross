package in.gov.cgg.redcrossphase1.ui_citiguest;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;


public class VisionFragment extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle("Vision");


        return inflater.inflate(R.layout.fragment_vision, container, false);
    }

}