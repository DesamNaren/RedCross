package in.gov.cgg.redcrossphase_offline.ui_citiguest.Fragments;

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

import in.gov.cgg.redcrossphase_offline.R;

import static android.content.Context.MODE_PRIVATE;


public class About_usFragment extends Fragment {
    LinearLayout ll_history;
    int selectedThemeColor = -1;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.aboutusfragment, container, false);
        //Objects.requireNonNull(getActivity()).setTitle("History");
        TextView tv_first = root.findViewById(R.id.text1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tv_first.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }
        String longDescription = "Red Cross is the largest humanitarian movement in the world with members of 190 countries.  Over the last 150 years, the work of Red Cross has grown from the first aim of caring for the war wounded and the prisoners of war to  medical care, blood donations, first aid and so on in furtherance of seven fundamental principles of Red Cross movement viz, Humanity, Impartiality, Neutrality, independence, Voluntary Service, Unity and Universality.\n" +
                "\n" +
                "Indian Red Cross Society has been formed under Parliament Act XV of 1920 as per the Geneva Convention 1864. The Telangana State Branch started in the year 1920  in the princely state of Nizam under the presidentship of the then British Resident. The erstwhile Andhra Pradesh State Branch of Indian Red Cross Society has been formed in the year 1958 and bifurcated into Indian Red Cross Society, Telangana State branch and Indian Red Cross Society, Andhra Pradesh State branch with effect from 11-11-2014 in terms of provisions of India Red Cross Society Act.\n" +
                "\n" +
                "Red Cross has been in the fore-front in alleviating suffering of people caused by either natural disaster or other calamities. The Telangana State branch of Indian Red Cross Society, under the president ship of Hon’ble Governor of Telangana rendering yeoman services to the society in providing relief to victims of natural calamities besides running several charitable institutions providing free medical aid to the poor, specially to those residing in slum areas. The Red Cross has always been a volunteer’s movement deriving its strength from material and support given philanthropists. \n" +
                "\n" +
                "The Indian Red Cross Society, Telangana State branch is conducting various community welfare activities through 33 district branches such as Blood Banks, Blood Transfusion Centers for Thallassemia Patients, Orphanages, Schools, Jeevandhara Generic Medical Stores, Home Nurse/Bed Side Assistant Services, Dementia Day Care Center, Yoga and Nature Cure Institute, AYUSH Clinics, Social and Emergency Response Volunteer (SERV) Programme, Family News Service (FNS), First Aid Trainings, Junior and Youth Red Cross, Urban Health Centers, Schools, Senior Citizen Homes etc,.\n" +
                "\n" +
                "The Indian Red Cross Society, Telangana State Branch going to celebrate the centenary year in 2020. In the Centenary Year, the State Branch targeting to reach one lakh life members and 10.00 lakh Junior and Youth Red Cross Volunteers under the Dynamic Leadership of           Dr. (Smt.) Tamilisai Soundararajan, Honorable Governor and President, Indian Red Cross Society, Telangana State Branch and State Red Cross Team.\n";
        tv_first.setText(longDescription);
        findViews(root);


        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    ll_history.setBackgroundResource(R.drawable.redcross1_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    ll_history.setBackgroundResource(R.drawable.redcross2_bg);

                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    ll_history.setBackgroundResource(R.drawable.redcross3_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    ll_history.setBackgroundResource(R.drawable.redcross4_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    ll_history.setBackgroundResource(R.drawable.redcross5_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    ll_history.setBackgroundResource(R.drawable.redcross6_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    ll_history.setBackgroundResource(R.drawable.redcross7_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    ll_history.setBackgroundResource(R.drawable.redcross8_bg);
                } else {
                    ll_history.setBackgroundResource(R.drawable.redcross7_bg);
                }

            } else {
                ll_history.setBackgroundResource(R.drawable.redcross7_bg);

            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return root;

    }

    private void findViews(View root) {
        ll_history = root.findViewById(R.id.ll_history);
    }

    private float dp(int dp) {
        return getResources().getDisplayMetrics().density * dp;
    }

    public interface OnFragmentInteractionListener {
    }
}