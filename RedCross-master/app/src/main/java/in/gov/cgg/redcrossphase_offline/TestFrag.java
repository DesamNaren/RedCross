package in.gov.cgg.redcrossphase_offline;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class TestFrag extends Fragment {
    public Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
