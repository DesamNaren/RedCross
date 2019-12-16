package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import in.gov.cgg.redcrossphase1.R;

import static android.content.Context.MODE_PRIVATE;


public class MissionFragment extends Fragment {
    int selectedThemeColor = -1;
    LinearLayout ll_mission;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mission, container, false);

        // Objects.requireNonNull(getActivity()).setTitle("Mission");

        TextView tv_first = root.findViewById(R.id.text1);

        String longDescription = "The Mission of the Indian Red Cross is to inspire, encourage and initiate at all times all forms of humanitarain activities so that human suffering can be minimized and even prevented and thus contribute to creating more congenial climate for peace.";

        String[] arr = longDescription.split("\n");
        findViews(root);
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

        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    ll_mission.setBackgroundResource(R.drawable.redcross1_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    ll_mission.setBackgroundResource(R.drawable.redcross2_bg);

                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    ll_mission.setBackgroundResource(R.drawable.redcross3_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    ll_mission.setBackgroundResource(R.drawable.redcross4_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    ll_mission.setBackgroundResource(R.drawable.redcross5_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    ll_mission.setBackgroundResource(R.drawable.redcross6_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    ll_mission.setBackgroundResource(R.drawable.redcross7_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    ll_mission.setBackgroundResource(R.drawable.redcross8_bg);
                } else {
                    ll_mission.setBackgroundResource(R.drawable.redcross7_bg);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return root;
    }


    private void findViews(View root) {
        ll_mission = root.findViewById(R.id.ll_mission);
    }


    private float dp(int dp) {
        return getResources().getDisplayMetrics().density * dp;
    }

    public interface OnFragmentInteractionListener {
    }
}