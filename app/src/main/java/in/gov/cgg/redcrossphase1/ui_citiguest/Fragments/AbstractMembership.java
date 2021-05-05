package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.AbstractMemberAdapter;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.AbstractMainBean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.AbstractMemberbean;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
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

    TextView tv_register, tv_download;
    TextView btn_htbm;
    int selectedThemeColor = -1;
    RelativeLayout MainLayout;
    TextView data1, count1;
    AbstractMemberAdapter abstractMemberAdapter;
    int total;
    int c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12;
    RecyclerView rv;
    ArrayList<AbstractMemberbean> servList;
    TextView tv_datanot;
    private CustomProgressDialog progressDialog;
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
        View v = inflater.inflate(R.layout.fragment_abstract_membership, container, false);
        Objects.requireNonNull(getActivity()).setTitle(getResources().getString(R.string.membership));
        rv = v.findViewById(R.id.bb_rv);
        rv.setVisibility(View.VISIBLE);
        tv_register = v.findViewById(R.id.tv_register);
        tv_download = v.findViewById(R.id.tv_download);
        tv_datanot = v.findViewById(R.id.tv_datanot);
        btn_htbm = v.findViewById(R.id.btn_htbm);
        MainLayout = v.findViewById(R.id.MainLayout);
        count1 = v.findViewById(R.id.tv_count1);
        data1 = v.findViewById(R.id.tv_data1);
        progressDialog = new CustomProgressDialog(getActivity());
        //  noDataTV.setVisibility(View.GONE);

        if (CheckInternet.isOnline(getActivity())) {

            if (GlobalDeclaration.abstractList == null) {
                callabstractlist();
            } else {
                if (GlobalDeclaration.abstractList.size() > 0) {
                    count1.setText(GlobalDeclaration.totallist);
                    final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rv.setLayoutManager(layoutManager);
                    abstractMemberAdapter = new AbstractMemberAdapter(getActivity(), GlobalDeclaration.abstractList, selectedThemeColor);
                    rv.setAdapter(abstractMemberAdapter);
                    abstractMemberAdapter.notifyDataSetChanged();
                    tv_datanot.setVisibility(View.GONE);
                }
            }

        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }


        try {
            selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF), MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);
            if (selectedThemeColor != -1) {
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    MainLayout.setBackgroundResource(R.drawable.redcross1_bg);
                    data1.setTextColor(getResources().getColor(R.color.redcroosbg_1));
                    count1.setTextColor(getResources().getColor(R.color.redcroosbg_1));
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    MainLayout.setBackgroundResource(R.drawable.redcross2_bg);
                    data1.setTextColor(getResources().getColor(R.color.redcroosbg_2));
                    count1.setTextColor(getResources().getColor(R.color.redcroosbg_2));
                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    MainLayout.setBackgroundResource(R.drawable.redcross3_bg);
                    data1.setTextColor(getResources().getColor(R.color.redcroosbg_3));
                    count1.setTextColor(getResources().getColor(R.color.redcroosbg_3));
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    MainLayout.setBackgroundResource(R.drawable.redcross4_bg);
                    data1.setTextColor(getResources().getColor(R.color.redcroosbg_4));
                    count1.setTextColor(getResources().getColor(R.color.redcroosbg_4));
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    MainLayout.setBackgroundResource(R.drawable.redcross5_bg);
                    data1.setTextColor(getResources().getColor(R.color.redcroosbg_5));
                    count1.setTextColor(getResources().getColor(R.color.redcroosbg_5));
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    MainLayout.setBackgroundResource(R.drawable.redcross6_bg);
                    data1.setTextColor(getResources().getColor(R.color.redcroosbg_6));
                    count1.setTextColor(getResources().getColor(R.color.redcroosbg_6));
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    MainLayout.setBackgroundResource(R.drawable.redcross7_bg);
                    data1.setTextColor(getResources().getColor(R.color.redcroosbg_7));
                    count1.setTextColor(getResources().getColor(R.color.redcroosbg_7));
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    MainLayout.setBackgroundResource(R.drawable.redcross8_bg);
                    data1.setTextColor(getResources().getColor(R.color.redcroosbg_8));
                    count1.setTextColor(getResources().getColor(R.color.redcroosbg_8));
                } else {
                    MainLayout.setBackgroundResource(R.drawable.redcross6_bg);
                    data1.setTextColor(getResources().getColor(R.color.redcroosbg_6));
                    count1.setTextColor(getResources().getColor(R.color.redcroosbg_6));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            MainLayout.setBackgroundResource(R.drawable.redcross6_bg);
            data1.setTextColor(getResources().getColor(R.color.redcroosbg_6));
            count1.setTextColor(getResources().getColor(R.color.redcroosbg_6));
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


        return v;
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

    private void callabstractlist() {

        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        ApiInterface apiServiceSession = ApiClient.getClient().create(ApiInterface.class);

        Call<AbstractMainBean> call = apiServiceSession.getAAbstractdata();
        Log.e("apiServiceSession_url: ", "" + call.request().url());
        call.enqueue(new Callback<AbstractMainBean>() {
            @Override
            public void onResponse(Call<AbstractMainBean> call, Response<AbstractMainBean> response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                try {
                    AbstractMainBean body = response.body();

                    if (body != null) {

                        servList = (ArrayList<AbstractMemberbean>) response.body().getVals();
                        count1.setText("" + response.body().getTotal());
                        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        rv.setLayoutManager(layoutManager);

                        GlobalDeclaration.abstractList = servList;
                        GlobalDeclaration.totallist = response.body().getTotal() + "";
                        abstractMemberAdapter = new AbstractMemberAdapter(getActivity(), servList, selectedThemeColor);
                        rv.setAdapter(abstractMemberAdapter);
                        abstractMemberAdapter.notifyDataSetChanged();
                        tv_datanot.setVisibility(View.GONE);


                    } else {
                        tv_datanot.setVisibility(View.VISIBLE);
                        //  showResponseAlert();
                    }


                } catch (Exception e) {
                    Toast.makeText(getContext(), getResources().getString(R.string.somethingwentwrong) + e.toString(), Toast.LENGTH_SHORT).show();
                    Log.e("Error", e.toString());
                }
            }

            @Override
            public void onFailure(Call<AbstractMainBean> call, Throwable t) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("Exp", t.toString());

            }
        });

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
