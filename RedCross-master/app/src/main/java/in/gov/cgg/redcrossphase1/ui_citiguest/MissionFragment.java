package in.gov.cgg.redcrossphase1.ui_citiguest;

import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;


public class MissionFragment extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tc, container, false);

        Objects.requireNonNull(getActivity()).setTitle("Mission");

        TextView tv_first = root.findViewById(R.id.text1);

        String longDescription = "The Mission of the Indian Red Cross is to inspire, encourage and initiate at all times all forms of humanitarain activities so that human suffering can be minimized and even prevented and thus contribute to creating more congenial climate for peace.";

        String[] arr = longDescription.split("\n");

        int bulletGap = (int) dp(10);

        SpannableStringBuilder ssb = new SpannableStringBuilder();
        for (int i = 0; i < arr.length; i++) {
            String line = arr[i];
            SpannableString ss = new SpannableString(line);
            //ss.setSpan(new BulletSpan(bulletGap), 0, line.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.append(ss);

            //avoid last "\n"
            if (i + 1 < arr.length)
                ssb.append("\n");

        }

        tv_first.setText(ssb);
        return root;

    }

    private float dp(int dp) {
        return getResources().getDisplayMetrics().density * dp;
    }

}