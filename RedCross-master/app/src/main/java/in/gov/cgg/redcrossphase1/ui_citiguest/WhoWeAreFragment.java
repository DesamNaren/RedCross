package in.gov.cgg.redcrossphase1.ui_citiguest;

import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;

import static android.content.Context.MODE_PRIVATE;


public class WhoWeAreFragment extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    int selectedThemeColor = -1;
    LinearLayout ll_whoweAre;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_tc, container, false);
        ll_whoweAre = root.findViewById(R.id.ll_whoweAre);

        Objects.requireNonNull(getActivity()).setTitle("Who we are");
        TextView tv_first = root.findViewById(R.id.text1);
        String longDescription = "Indian Red Cross Society was incorporated under Parliament Act XV of 1920. In the princely state of Hyderabad, under the rule of the Nizams, the Red Cross Society was established in 1920 in a small building in St. Mary's road, Secunderabad as a child welfare center.\r\n\n The British Resident at Hyderabad was its President at that time. Indian Red Cross Society, Telangana State Branch has come into existence in the year 2014. Hon'ble Governor of Telangana is the President of Telangana State Branch. Indian Red Cross Society, Telangana State Branch is having 10 District Branches.\r\n\n  District Collector is the President for a District Branch and Mandal Revenue Officer/Tahsildar is President for a District Sub-Branch. Red Cross has been in the fore front, alleviating peoples suffering caused either by natural disasters or by calamities like earthquakes, cyclones, heavy rains and floods, tsunamis etc,. The coastal areas of the State experience many cyclones, which occur every year. \r\n\n   Indian Red Cross Society, Telangana Branch is well prepared to play a significant role in evacuating inhabitants from vulnerable areas to safer places well before an impending disaster and also in organizing rescue, relief and rehabilitation operations when a calamity strikes.\r\n\n The important aspect of such operations is to minimize the effect of calamity in terms of loss of life and suffering of the victims. Apart from this, Indian Red Cross Society, Telangana Branch is active in promoting Health Education, Health Care, free Medical Aid and Medicines to the poor people, Blood Donations, Junior Red Cross and Youth Red Cross associations. In addition the Society also conducts vocational training programmes, runs orphanages and Senior Citizen Resort for elders.";
        tv_first.setText(longDescription);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tv_first.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }

        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    ll_whoweAre.setBackgroundResource(R.drawable.redcross1_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    ll_whoweAre.setBackgroundResource(R.drawable.redcross2_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    ll_whoweAre.setBackgroundResource(R.drawable.redcross3_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    ll_whoweAre.setBackgroundResource(R.drawable.redcross4_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    ll_whoweAre.setBackgroundResource(R.drawable.redcross5_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    ll_whoweAre.setBackgroundResource(R.drawable.redcross6_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    ll_whoweAre.setBackgroundResource(R.drawable.redcross7_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    ll_whoweAre.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
                }

            } else {
                ll_whoweAre.setBackgroundResource(R.drawable.redcross_splashscreen_bg);

            }
        } catch (Exception e) {
            e.printStackTrace();

        }


        return root;

    }

    private float dp(int dp) {
        return getResources().getDisplayMetrics().density * dp;
    }

}