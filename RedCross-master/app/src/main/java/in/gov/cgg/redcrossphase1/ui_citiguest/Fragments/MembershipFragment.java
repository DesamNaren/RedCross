package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.MembershipDetailsAdaptor;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembershipDetails_Bean;

import static android.content.Context.MODE_PRIVATE;

public class MembershipFragment extends Fragment {
    RecyclerView recyclerView;
    MembershipDetailsAdaptor adapter;
    ArrayList<MembershipDetails_Bean> MembershipTypearrayList;
    LinearLayout ll_lifetime_membership;
    int selectedThemeColor = -1;
    LinearLayout Parent_layout, ll_LTM_types;
    Button Proceed;
    Spinner TypeSpinner;
    private FragmentActivity c;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_membership, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Membership");

        c = getActivity();
        findViews(root);
        Parent_layout = root.findViewById(R.id.Parent_layout);
        ll_LTM_types = root.findViewById(R.id.ll_LTM_types);
        Proceed = root.findViewById(R.id.Proceed_bt);
        Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



               /* Intent i= new Intent(c, MembershipRegFormActivity.class);
                startActivity(i);*/
            }
        });


        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    ll_lifetime_membership.setBackgroundResource(R.drawable.redcross1_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    ll_lifetime_membership.setBackgroundResource(R.drawable.redcross2_bg);

                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    ll_lifetime_membership.setBackgroundResource(R.drawable.redcross3_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    ll_lifetime_membership.setBackgroundResource(R.drawable.redcross4_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    ll_lifetime_membership.setBackgroundResource(R.drawable.redcross5_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    ll_lifetime_membership.setBackgroundResource(R.drawable.redcross6_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    ll_lifetime_membership.setBackgroundResource(R.drawable.redcross7_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    ll_lifetime_membership.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        MembershipTypearrayList = new ArrayList<>();
        recyclerView = root.findViewById(R.id.rv_Membership_Types);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        MembershipDetails_Bean type1 = new MembershipDetails_Bean("Vice Patron", "Rs. 2");
        MembershipTypearrayList.add(type1);

        MembershipDetails_Bean type2 = new MembershipDetails_Bean("Life Member", "Rs. 2");
        MembershipTypearrayList.add(type2);

        MembershipDetails_Bean type3 = new MembershipDetails_Bean("Patron", "Rs. 2");
        MembershipTypearrayList.add(type3);

        MembershipDetails_Bean type4 = new MembershipDetails_Bean("Life Associate", "Rs. 2");
        MembershipTypearrayList.add(type4);

        MembershipDetails_Bean type5 = new MembershipDetails_Bean("Annual Member", "RS. 1 per annum");
        MembershipTypearrayList.add(type5);

        MembershipDetails_Bean type6 = new MembershipDetails_Bean("Institutional Member", "RS. 1 per annum");
        MembershipTypearrayList.add(type6);

        MembershipDetails_Bean type7 = new MembershipDetails_Bean("Annual Associate", "RS. 1 per annum");
        MembershipTypearrayList.add(type7);

        adapter = new MembershipDetailsAdaptor(MembershipTypearrayList, c, selectedThemeColor);
        recyclerView.setAdapter(adapter);

        return root;
    }

    private void findViews(View root) {
        ll_lifetime_membership = root.findViewById(R.id.ll_lifetime_membership);
    }


}







