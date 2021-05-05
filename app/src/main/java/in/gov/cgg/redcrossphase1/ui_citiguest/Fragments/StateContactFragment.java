package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentContactsBinding;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.NewContactusAdapter;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.ContactBean;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.ui_officer.custom_officer.CustomDistricClass;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import in.gov.cgg.redcrossphase1.viewmodels.AllDistrictsViewModel;

import static android.content.Context.MODE_PRIVATE;

public class StateContactFragment extends Fragment {
    FragmentContactsBinding binding;
    AllDistrictsViewModel contactModel;
    int selectedThemeColor = -1;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));


        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_contacts, container, false);


        contactModel = ViewModelProviders.of(this, new CustomDistricClass(getActivity(), "d"))
                .get(AllDistrictsViewModel.class);
        try {
            selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF),
                    MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);

        } catch (Exception e) {
            e.printStackTrace();

        }
        if (CheckInternet.isOnline(getActivity())) {

            contactModel.getContacts("sc").
                    observe(getActivity(), new Observer<ContactBean>() {
                        @Override
                        public void onChanged(@Nullable ContactBean contactBean) {
                            if (contactBean != null) {
                                binding.tvNodata.setVisibility(View.GONE);

                                setDataforRV(contactBean);

                            } else {
                                binding.tvNodata.setVisibility(View.VISIBLE);
                                binding.tvNodata.setText("No data available");
                            }
                        }
                    });
        } else {
            Snackbar snackbar = Snackbar
                    .make(binding.rvContactUs, "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        return binding.getRoot();

    }


    private void setDataforRV(ContactBean contactBean) {
        binding.tvNodata.setVisibility(View.GONE);

        //int r= sortDatesHere();
        if (contactBean.getAdditionscenters().size() > 0) {
            binding.rvContactUs.setVisibility(View.VISIBLE);
            binding.tvNodata.setVisibility(View.GONE);
            binding.rvContactUs.setHasFixedSize(true);
            binding.rvContactUs.setLayoutManager(new LinearLayoutManager(getActivity()));


            NewContactusAdapter adapter1 = new NewContactusAdapter(getActivity(), contactBean.getAdditionscenters(), selectedThemeColor);
            binding.rvContactUs.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();


        } else {
            binding.rvContactUs.setVisibility(View.GONE);
            binding.tvNodata.setVisibility(View.VISIBLE);

        }
    }

}
