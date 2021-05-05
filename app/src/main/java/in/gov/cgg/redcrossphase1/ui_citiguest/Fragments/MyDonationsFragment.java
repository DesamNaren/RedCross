package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentMydonationsBinding;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.MyBloodDonateAdapter;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.MyDonatemoneyAdapter;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.Memberonlinedonation;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MyBlooddonor;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MyMembership;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MyOnline;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class MyDonationsFragment extends androidx.fragment.app.Fragment {

    CustomProgressDialog progressDialog;
    List<Memberonlinedonation> moneylist = new ArrayList<>();
    List<MyBlooddonor> myBlooddonors = new ArrayList<>();
    private FragmentMydonationsBinding binding;
    private int selectedThemeColor = -1;
    private boolean money, blood;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_mydonations, container, false);

        progressDialog = new CustomProgressDialog(getActivity());

        getActivity().setTitle("My Donations");


        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF",
                    MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                binding.ll.setBackgroundColor(getActivity().getResources().getColor(selectedThemeColor));
                binding.ll1.setBackgroundColor(getActivity().getResources().getColor(selectedThemeColor));
                binding.scroll.setBackgroundColor(getActivity().getResources().getColor(selectedThemeColor));
            }
        } catch (Exception e) {

        }

        if (!GlobalDeclaration.profilePic.isEmpty()) {
            Glide.with(getActivity()).load(GlobalDeclaration.profilePic).into(binding.imgUser);
        }

        if (GlobalDeclaration.citizenMobile != null) {
            if (!GlobalDeclaration.citizenMobile.isEmpty()) {
                callMoneyDonations(GlobalDeclaration.citizenMobile);
                callMyMemberships(GlobalDeclaration.citizenMobile);
//
//                if (moneylist != null && myBlooddonors != null) {
//                    if (moneylist.size() != 0 && myBlooddonors.size() != 0) {
//                        binding.nodata.setVisibility(View.GONE);
//                    } else {
//                        binding.nodata.setVisibility(View.VISIBLE);
//
//                    }
//                } else {
//                    binding.nodata.setVisibility(View.VISIBLE);
//
//                }
//
//                if (money && blood) {
//                    binding.nodata.setVisibility(View.VISIBLE);
//                    binding.rvBlooddonations.setVisibility(View.INVISIBLE);
//                    binding.rvMoneydonations.setVisibility(View.INVISIBLE);
//                } else {
//                    binding.nodata.setVisibility(View.GONE);
//                    binding.rvBlooddonations.setVisibility(View.VISIBLE);
//                    binding.rvMoneydonations.setVisibility(View.VISIBLE);
//                }

            }
        }


        return binding.getRoot();
    }

    private void callMoneyDonations(String citizenMobile) {


        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


        Call<MyOnline> call = apiInterface.getMemberOnlineDonations(citizenMobile);
        Log.d("url", "calldonationurl: " + call.request().url());
        call.enqueue(new Callback<MyOnline>() {
            @Override
            public void onResponse(Call<MyOnline> call, Response<MyOnline> response) {
                progressDialog.dismiss();

                moneylist.clear();
                if (response.body() != null) {
                    if (response.body().getMemberonlinedonations() != null) {
                        moneylist = response.body().getMemberonlinedonations();

                        if (moneylist.size() != 0) {
                            binding.ll.setVisibility(View.VISIBLE);
                            binding.nodata.setVisibility(View.GONE);

                            binding.rvMoneydonations.setHasFixedSize(true);
                            binding.rvMoneydonations.setLayoutManager(new LinearLayoutManager(getActivity()));

                            MyDonatemoneyAdapter adapter1 = new MyDonatemoneyAdapter(getActivity(), moneylist);
                            binding.rvMoneydonations.setAdapter(adapter1);
                            adapter1.notifyDataSetChanged();
                            money = true;
                        } else {
                            binding.ll.setVisibility(View.INVISIBLE);


                        }
                    } else {
                        binding.ll.setVisibility(View.INVISIBLE);
                    }

                } else {
                    binding.ll.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<MyOnline> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callMyMemberships(String citizenMobile) {


        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


        Call<MyMembership> call = apiInterface.getMemberships(citizenMobile);
        Log.d("Login", "callmymemebrshipurl: " + call.request().url());
        call.enqueue(new Callback<MyMembership>() {
            @Override
            public void onResponse(Call<MyMembership> call, Response<MyMembership> response) {
                progressDialog.dismiss();

                GlobalDeclaration.bloodonationdetails.clear();
                if (response.body() != null) {

                    if (response.body().getBlooddonor() != null) {
                        GlobalDeclaration.bloodonationdetails = response.body().getBlooddonor();
                        myBlooddonors = response.body().getBlooddonor();
                        if (GlobalDeclaration.bloodonationdetails.size() != 0) {
                            binding.ll1.setVisibility(View.VISIBLE);
                            binding.nodata.setVisibility(View.GONE);
                            binding.rvBlooddonations.setHasFixedSize(true);
                            binding.rvBlooddonations.setLayoutManager(new LinearLayoutManager(getActivity()));

                            MyBloodDonateAdapter adapter1 = new MyBloodDonateAdapter(getActivity(),
                                    GlobalDeclaration.bloodonationdetails);
                            binding.rvBlooddonations.setAdapter(adapter1);
                            adapter1.notifyDataSetChanged();
                            blood = true;
                        } else {
//                            binding.ll1.setVisibility(View.INVISIBLE);
                        }

                    } else {
//                        binding.ll1.setVisibility(View.INVISIBLE);
                    }
                } else {
//                    binding.ll1.setVisibility(View.INVISIBLE);

                }

            }

            @Override
            public void onFailure(Call<MyMembership> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
