package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentNewcontactusBinding;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.ContactusDetails_Bean;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;

import static android.content.Context.MODE_PRIVATE;


public class NewContactusFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<ContactusDetails_Bean> contactUsarrayList;
    LinearLayout ll_contactUs;
    Button btn_sateCordinators, btn_districtCordinators;
    CustomProgressDialog progressDialog;
    SearchView searchView;
    FragmentNewcontactusBinding binding;
    int selectedThemeColor = -1;
    ViewPagerAdapter adapter;
    private FragmentActivity c;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));

        Objects.requireNonNull(getActivity()).setTitle(R.string.contact_us);


        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_newcontactus, container, false);
        binding.tabsHome.setupWithViewPager(binding.viewpagerHome);
        setupViewPager(binding.viewpagerHome);

        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF",
                    MODE_PRIVATE).getInt("theme_color", -1);

            if (selectedThemeColor != -1) {
                binding.tabsHome.setSelectedTabIndicatorColor(getResources().getColor(selectedThemeColor));
            }

        } catch (Exception e) {
            e.printStackTrace();

        }


        if (GlobalDeclaration.contactposition == 0) {
            binding.viewpagerHome.setCurrentItem(0);
        } else if (GlobalDeclaration.contactposition == 1) {
            binding.viewpagerHome.setCurrentItem(1);
        }


        return binding.getRoot();

    }

    private void setupViewPager(ViewPager viewpager) {
        adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.addFrag(new StateContactFragment(), "State Coordinators");
        adapter.addFrag(new DistrictContactFragment(), "District Coordinators");
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


