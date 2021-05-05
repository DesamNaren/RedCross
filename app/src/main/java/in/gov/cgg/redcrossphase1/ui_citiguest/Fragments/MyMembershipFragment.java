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
import in.gov.cgg.redcrossphase1.databinding.FragmentMyembrshipsBinding;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.MyMembershipAdapter;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.Membership;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MyMembership;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class MyMembershipFragment extends androidx.fragment.app.Fragment {

    CustomProgressDialog progressDialog;
    List<Membership> membrshipData = new ArrayList<>();
    private FragmentMyembrshipsBinding binding;
    private int selectedThemeColor = -1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_myembrships, container, false);

        progressDialog = new CustomProgressDialog(getActivity());

        getActivity().setTitle("My Memberships");

        try {
            selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF),
                    MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                binding.rlmain.setBackgroundColor(getActivity().getResources().getColor(selectedThemeColor));
                // binding.btn.setBackgroundColor(selectedThemeColor);
            }
        } catch (Exception e) {

        }

        if (!GlobalDeclaration.profilePic.isEmpty()) {
            Glide.with(getActivity()).load(GlobalDeclaration.profilePic).into(binding.imgUser);
        }

        if (GlobalDeclaration.citizenMobile != null) {
            if (!GlobalDeclaration.citizenMobile.isEmpty()) {
                callMyMemberships(GlobalDeclaration.citizenMobile);
            }
        }

        return binding.getRoot();
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

                membrshipData.clear();
                if (response.body() != null) {
                    if (response.body().getMemberships() != null) {
                        membrshipData = response.body().getMemberships();

                        if (membrshipData.size() != 0) {
                            binding.rvMembrships.setVisibility(View.VISIBLE);
                            binding.nodata1.setVisibility(View.GONE);

                            binding.rvMembrships.setHasFixedSize(true);
                            binding.rvMembrships.setLayoutManager(new LinearLayoutManager(getActivity()));

                            MyMembershipAdapter adapter1 = new MyMembershipAdapter(getActivity(), membrshipData);
                            binding.rvMembrships.setAdapter(adapter1);
                            adapter1.notifyDataSetChanged();
                        } else {
                            binding.rvMembrships.setVisibility(View.INVISIBLE);
                        }

                    } else {
                        binding.rvMembrships.setVisibility(View.INVISIBLE);
                    }

                } else {
                    binding.rvMembrships.setVisibility(View.INVISIBLE);
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
