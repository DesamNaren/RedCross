package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;

import static android.content.Context.MODE_PRIVATE;


public class WhoWeAreFragment extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    int selectedThemeColor = -1;
    RelativeLayout ll_whoweAre;
    HomeViewPagerAdapter adaptor;
    ViewPager viewpager;
    int position;
    private String pages;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_tc, container, false);
        ll_whoweAre = root.findViewById(R.id.ll_whoweAre);

        Objects.requireNonNull(getActivity()).setTitle("About Us");
        TextView tv_first = root.findViewById(R.id.text1);
        viewpager = root.findViewById(R.id.viewpager_aboutus);
        final TabLayout tabsLayout = root.findViewById(R.id.tabs_aboutusCitizen);

        setupViewPager(viewpager);
        tabsLayout.setupWithViewPager(viewpager);


        if (GlobalDeclaration.tabposition == 1) {
            viewpager.setCurrentItem(0);
        } else if (GlobalDeclaration.tabposition == 2) {
            viewpager.setCurrentItem(1);
        } else if (GlobalDeclaration.tabposition == 3) {
            viewpager.setCurrentItem(2);
        } else if (GlobalDeclaration.tabposition == 4) {
            viewpager.setCurrentItem(3);
        }

//        Objects.requireNonNull(
//
//                getActivity()).
//
//                setTitle("Dashboard");

//        GlobalDeclaration.home = true;
//        GlobalDeclaration.Selection_type = "JRC";
        reload();

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tv_first.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }*/


        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    ll_whoweAre.setBackgroundResource(R.drawable.redcross1_bg);
                    tabsLayout.setSelectedTabIndicatorColor(R.color.redcroosbg_1);
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    ll_whoweAre.setBackgroundResource(R.drawable.redcross2_bg);
                    tabsLayout.setSelectedTabIndicatorColor(R.color.redcroosbg_2);
                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    ll_whoweAre.setBackgroundResource(R.drawable.redcross3_bg);
                    tabsLayout.setSelectedTabIndicatorColor(R.color.redcroosbg_3);
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    ll_whoweAre.setBackgroundResource(R.drawable.redcross4_bg);
                    tabsLayout.setSelectedTabIndicatorColor(R.color.redcroosbg_4);
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    ll_whoweAre.setBackgroundResource(R.drawable.redcross5_bg);
                    tabsLayout.setSelectedTabIndicatorColor(R.color.redcroosbg_5);
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    ll_whoweAre.setBackgroundResource(R.drawable.redcross6_bg);
                    tabsLayout.setSelectedTabIndicatorColor(R.color.redcroosbg_6);
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    ll_whoweAre.setBackgroundResource(R.drawable.redcross7_bg);
                    tabsLayout.setSelectedTabIndicatorColor(R.color.redcroosbg_7);
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    ll_whoweAre.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
                    tabsLayout.setSelectedTabIndicatorColor(R.color.redcroosbg_8);

                } else {
                    ll_whoweAre.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
                    tabsLayout.setSelectedTabIndicatorColor(R.drawable.redcross_splashscreen_bg);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }


        return root;

    }

    private void reload() {

        adaptor.notifyDataSetChanged();
        viewpager.invalidate();

        //pager.setCurrentItem(0);
    }

    private void setupViewPager(ViewPager viewpager) {
        adaptor = new HomeViewPagerAdapter(getFragmentManager());
        adaptor.addFragment(new About_usFragment(), "About us");
        adaptor.addFragment(new HistoryFragment(), "History");
        adaptor.addFragment(new VisionFragment(), "Vision");
        adaptor.addFragment(new MissionFragment(), "Mission");
        adaptor.addFragment(new SevenFundamentalFragment(), "Principles");
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


