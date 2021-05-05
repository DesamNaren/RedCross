package in.gov.cgg.redcrossphase_offline.ui_citiguest.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase_offline.R;

public class BloodbankInfoFragment extends Fragment {

    HomeViewPagerAdapter adaptor;
    ViewPager viewpager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_bloodbank_info, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Blood Bank Info");
        viewpager = root.findViewById(R.id.viewpager_aboutus);
        TabLayout tabsLayout = root.findViewById(R.id.tabs_aboutusCitizen);

        setupViewPager(viewpager);
        tabsLayout.setupWithViewPager(viewpager);
        return root;
    }

    private void reload() {

        adaptor.notifyDataSetChanged();
        viewpager.invalidate();

        //pager.setCurrentItem(0);
    }

    private void setupViewPager(ViewPager viewpager) {
        adaptor = new HomeViewPagerAdapter(getChildFragmentManager());
        adaptor.addFragment(new BlooddonorRegistrationFragment(), "Blood Donor Registration");
        adaptor.addFragment(new HowToBecomeDoner(), "Become A Doner");
        adaptor.addFragment(new UpdateBloodDonationDetails(), "Vision");
        adaptor.addFragment(new SearchBloodDonors(), "Mission");
        viewpager.setAdapter(adaptor);
        adaptor.notifyDataSetChanged();
        viewpager.invalidate();
    }

    class HomeViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private FragmentManager mFragmentManager;

        HomeViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public int getItemPosition(Object object) {
// POSITION_NONE makes it possible to reload the PagerAdapter
            return POSITION_NONE;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }


}
