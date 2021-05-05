package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentNewcontactusBinding;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;

import static android.content.Context.MODE_PRIVATE;


public class NewBBInfoFragment extends BBBaseFragment {

    CustomProgressDialog progressDialog;
    FragmentNewcontactusBinding binding;
    int selectedThemeColor = -1;
    ViewPagerAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));

        Objects.requireNonNull(getActivity()).setTitle("Blood Bank Info");


        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_newcontactus, container, false);
        binding.tabsHome.setupWithViewPager(binding.viewpagerHome);
        setupViewPager(binding.viewpagerHome);

        try {
            selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF),
                    MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);

            if (selectedThemeColor != -1) {
                binding.tabsHome.setSelectedTabIndicatorColor(getResources().getColor(selectedThemeColor));
            }

        } catch (Exception e) {
            e.printStackTrace();

        }


        if (GlobalDeclaration.bloodposition == 0) {
            binding.viewpagerHome.setCurrentItem(0);
        } else if (GlobalDeclaration.bloodposition == 1) {
            binding.viewpagerHome.setCurrentItem(1);
        }


        return binding.getRoot();

    }

    private void setupViewPager(ViewPager viewpager) {
        adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.addFrag(new NewBloodBankFragment(), "Blood Banks");
        adapter.addFrag(new NewBloodDonorFragment(), "Blood Donors");
        viewpager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.viewpagerHome.invalidate();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}