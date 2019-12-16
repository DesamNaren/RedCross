package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.AbstractMemberbean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AbstractMembership.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AbstractMembership#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AbstractMembership extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView tv_d1;
    TextView tv_d2;
    TextView tv_d3;
    TextView tv_d4;
    TextView tv_d5;
    TextView tv_d6;
    TextView tv_d7;
    TextView tv_d8;
    TextView tv_d9;
    TextView tv_d10;
    TextView tv_d11;
    TextView tv_d12;
    TextView tv_c1;
    TextView tv_c2;
    TextView tv_c3;
    TextView tv_c4;
    TextView tv_c5;
    TextView tv_c6;
    TextView tv_c7;
    TextView tv_c8;
    TextView tv_c9;
    TextView tv_c10;
    TextView tv_total;
    TextView tv_c11;
    TextView tv_c12;
    View view1, view2, view3, view4, view5, view6, view7, view8, view9, view10;
    TextView tv_register, tv_download;
    TextView btn_htbm;
    int selectedThemeColor = -1;
    LinearLayout MainLayout;

    int total;
    int c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AbstractMembership() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AbstractMembership.
     */
    // TODO: Rename and change types and number of parameters
    public static AbstractMembership newInstance(String param1, String param2) {
        AbstractMembership fragment = new AbstractMembership();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.bstractmemadapter, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Membership");
        tv_c1 = v.findViewById(R.id.tv_count1);
        tv_c2 = v.findViewById(R.id.tv_count2);
        tv_c3 = v.findViewById(R.id.tv_count3);
        tv_c4 = v.findViewById(R.id.tv_count4);
        tv_c5 = v.findViewById(R.id.tv_count5);
        tv_c6 = v.findViewById(R.id.tv_count6);
        tv_c7 = v.findViewById(R.id.tv_count7);
        tv_c8 = v.findViewById(R.id.tv_count8);
        tv_c9 = v.findViewById(R.id.tv_count9);
        tv_c10 = v.findViewById(R.id.tv_count10);
        tv_total = v.findViewById(R.id.tv_totalcount);
        tv_register = v.findViewById(R.id.tv_register);
        tv_download = v.findViewById(R.id.tv_download);
        btn_htbm = v.findViewById(R.id.btn_htbm);
        MainLayout = v.findViewById(R.id.MainLayout);
        view1 = v.findViewById(R.id.view1);
        view2 = v.findViewById(R.id.view2);
        view3 = v.findViewById(R.id.view3);
        view4 = v.findViewById(R.id.view4);
        view5 = v.findViewById(R.id.view5);
        view6 = v.findViewById(R.id.view6);
        view7 = v.findViewById(R.id.view7);
        view8 = v.findViewById(R.id.view8);
        view9 = v.findViewById(R.id.view9);
        view10 = v.findViewById(R.id.view10);

        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    MainLayout.setBackgroundResource(R.drawable.redcross1_bg);
                    view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view4.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view5.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view6.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view7.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view8.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view9.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view10.setBackgroundColor(getResources().getColor(selectedThemeColor));


                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    MainLayout.setBackgroundResource(R.drawable.redcross2_bg);
                    view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view4.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view5.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view6.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view7.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view8.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view9.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view10.setBackgroundColor(getResources().getColor(selectedThemeColor));


                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    MainLayout.setBackgroundResource(R.drawable.redcross3_bg);
                    view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view4.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view5.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view6.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view7.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view8.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view9.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view10.setBackgroundColor(getResources().getColor(selectedThemeColor));


                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    MainLayout.setBackgroundResource(R.drawable.redcross4_bg);
                    view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view4.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view5.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view6.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view7.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view8.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view9.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view10.setBackgroundColor(getResources().getColor(selectedThemeColor));


                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    MainLayout.setBackgroundResource(R.drawable.redcross5_bg);
                    view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view4.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view5.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view6.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view7.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view8.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view9.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view10.setBackgroundColor(getResources().getColor(selectedThemeColor));

                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    MainLayout.setBackgroundResource(R.drawable.redcross6_bg);
                    view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view4.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view5.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view6.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view7.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view8.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view9.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view10.setBackgroundColor(getResources().getColor(selectedThemeColor));


                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    MainLayout.setBackgroundResource(R.drawable.redcross7_bg);
                    view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view4.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view5.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view6.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view7.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view8.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view9.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view10.setBackgroundColor(getResources().getColor(selectedThemeColor));


                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    MainLayout.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
                    view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view4.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view5.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view6.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view7.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view8.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view9.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view10.setBackgroundColor(getResources().getColor(selectedThemeColor));

                } else {
                    MainLayout.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
                    view1.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view2.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view3.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view4.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view5.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view6.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view7.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view8.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view9.setBackgroundColor(getResources().getColor(selectedThemeColor));
                    view10.setBackgroundColor(getResources().getColor(selectedThemeColor));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalDeclaration.FARG_TAG = MembershipFragment.class.getSimpleName();
                Fragment fragment = new MembershipFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment, MembershipFragment.class.getSimpleName());
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        });
        tv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalDeclaration.FARG_TAG = DownloadCertificate.class.getSimpleName();
                Fragment fragment = new DownloadCertificate();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment, DownloadCertificate.class.getSimpleName());
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        });

        btn_htbm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GlobalDeclaration.FARG_TAG = MemberFragment.class.getSimpleName();
                Fragment fragment = new MemberFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment, MemberFragment.class.getSimpleName());
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        });

        tv_d1 = v.findViewById(R.id.tv_data1);
        tv_d2 = v.findViewById(R.id.tv_data2);
        tv_d3 = v.findViewById(R.id.tv_data3);
        tv_d4 = v.findViewById(R.id.tv_data4);
        tv_d5 = v.findViewById(R.id.tv_data5);
        tv_d6 = v.findViewById(R.id.tv_data6);
        tv_d7 = v.findViewById(R.id.tv_data7);
        tv_d8 = v.findViewById(R.id.tv_data8);
        tv_d9 = v.findViewById(R.id.tv_data9);
        tv_d10 = v.findViewById(R.id.tv_data10);

        calladditionsCentersService();

        return v;
    }

    private void calladditionsCentersService() {


        ApiInterface apiServiceSession = ApiClient.getClient().create(ApiInterface.class);


        Call<AbstractMemberbean> call = apiServiceSession.getAAbstractdata();
        Log.e("apiServiceSession_url: ", "" + call.request().url());
        call.enqueue(new Callback<AbstractMemberbean>() {
            @Override
            public void onResponse(Call<AbstractMemberbean> call, Response<AbstractMemberbean> response) {
                // hideProgressDialog(progressDiaLogss);
                try {
                    AbstractMemberbean body = response.body();
                    if (body != null) {

                        tv_c1.setText(response.body().getPatron());
                        tv_c2.setText(response.body().getAnnualAssociate());
                        tv_c3.setText(response.body().getAnnualMember());
                        tv_c4.setText(response.body().getInstitutionalMember());
                        tv_c5.setText(response.body().getJrc());
                        tv_c6.setText(response.body().getLifeAssociate());
                        tv_c7.setText(response.body().getLifeMember());
                        tv_c8.setText(response.body().getMs());
                        tv_c9.setText(response.body().getVicePatron());
                        tv_c10.setText(response.body().getYrc());


                        c1 = Integer.parseInt(tv_c1.getText().toString());
                        c2 = Integer.parseInt(tv_c2.getText().toString());
                        c3 = Integer.parseInt(tv_c3.getText().toString());
                        c4 = Integer.parseInt(tv_c4.getText().toString());
                        c5 = Integer.parseInt(tv_c5.getText().toString());
                        c6 = Integer.parseInt(tv_c6.getText().toString());
                        c7 = Integer.parseInt(tv_c7.getText().toString());
                        c8 = Integer.parseInt(tv_c8.getText().toString());
                        c9 = Integer.parseInt(tv_c9.getText().toString());
                        c10 = Integer.parseInt(tv_c10.getText().toString());

                        total = c1 + c2 + c3 + c4 + c5 + c6 + c7 + c8 + c9 + c10;
                        tv_total.setText(String.valueOf(total));

                    } else {
                        //  showResponseAlert();
                    }


                } catch (Exception e) {
                    Toast.makeText(getContext(), "Something went wrong" + e.toString(), Toast.LENGTH_SHORT).show();
                    Log.e("Error", e.toString());
                }
            }

            @Override
            public void onFailure(Call<AbstractMemberbean> call, Throwable t) {
                //hideProgressDialog(progressDiaLogss);

                Log.e("Exp", t.toString());

            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
