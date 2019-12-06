package in.gov.cgg.redcrossphase1.ui_officer_new;

import android.graphics.RectF;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragemtHomeOfficerNewBinding;
import in.gov.cgg.redcrossphase1.ui_officer.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.agewise.AgewiseFragment;
import in.gov.cgg.redcrossphase1.ui_officer.agewise.AgewiseViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.bloodwise.BloodwiseFragment;
import in.gov.cgg.redcrossphase1.ui_officer.bloodwise.BloodwiseViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.daywisereportcont.DaywiseFragment;
import in.gov.cgg.redcrossphase1.ui_officer.genderwise.GenderwiseFragment;
import in.gov.cgg.redcrossphase1.ui_officer.genderwise.GenderwiseViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.govtpvt.GovtPvtViewModel;
import in.gov.cgg.redcrossphase1.ui_officer.home_distrcit.CustomDistricClass;
import in.gov.cgg.redcrossphase1.ui_officer.home_distrcit.DistrictViewModel;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;

import static in.gov.cgg.redcrossphase1.R.color.white;

public class NewOfficerHomeFragment extends Fragment {
    private final RectF onValueSelectedRectF = new RectF();

    int selectedThemeColor = -1;
    GovtPvtViewModel govtPvtViewModel;
    private DistrictViewModel districtViewModel;
    private AgewiseViewModel agewiseViewModel;
    private BloodwiseViewModel bloodwiseVm;
    private GenderwiseViewModel genderwiseViewModel;
    private String gender, bloodwise, age, top5, btm5;
    private Fragment fragment;
    private LineChart lineChart;
    private FragemtHomeOfficerNewBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragemt_home_officer_new, container, false);


        setupViewPager(binding.viewpagerHome);
        binding.tabsHome.setupWithViewPager(binding.viewpagerHome);

        districtViewModel =
                ViewModelProviders.of(this, new CustomDistricClass(getActivity(), "district")).get(DistrictViewModel.class);
        agewiseViewModel =
                ViewModelProviders.of(this, new CustomDistricClass(getActivity(), "age")).get(AgewiseViewModel.class);
        bloodwiseVm =
                ViewModelProviders.of(this, new CustomDistricClass(getActivity(), "blood")).get(BloodwiseViewModel.class);
        genderwiseViewModel =
                ViewModelProviders.of(this, new CustomDistricClass(getActivity(), "gender")).get(GenderwiseViewModel.class);

        govtPvtViewModel = ViewModelProviders.of(this, new CustomDistricClass(getActivity(), "gvtpvt")).get(GovtPvtViewModel.class);


        GlobalDeclaration.home = true;


        districtViewModel.getDashboardCounts("JRC", GlobalDeclaration.userID, GlobalDeclaration.districtId)
                .observe(Objects.requireNonNull(getActivity()), new Observer<DashboardCountResponse>() {

                    @Override
                    public void onChanged(@Nullable DashboardCountResponse dashboardCountResponse) {
                        if (dashboardCountResponse != null) {
                            GlobalDeclaration.counts = dashboardCountResponse;
                            setCountsForDashboard(dashboardCountResponse);
                        }
                    }
                });

        binding.fabDaywise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new DaywiseFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_officer, fragment);
                fragmentTransaction.commit();
            }
        });



        binding.customCount.llYrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isOnline(getActivity())) {

                    if (selectedThemeColor != -1) {

                        binding.customCount.tvYrcnme.setTextColor(getResources().getColor(white));
                        binding.customCount.tvYrccount.setTextColor(getResources().getColor(white));
                        binding.customCount.tvJrcnme.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvJrccount.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvLmcount.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvLmname.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvAllcount.setTextColor(getResources().getColor(selectedThemeColor));
                        binding.customCount.tvAllname.setTextColor(getResources().getColor(selectedThemeColor));

                        countColorSelection();
                        binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_selected));

                        if (selectedThemeColor == R.color.redcroosbg_1) {
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_selectedbg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));


                        } else if (selectedThemeColor == R.color.redcroosbg_2) {
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_selectedbg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_3) {
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_selectedbg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_4) {
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_selectedbg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_5) {
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_selectedbg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_6) {
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_selectedbg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));

                        } else if (selectedThemeColor == R.color.redcroosbg_7) {
                            binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_seleetedbg));
                            binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                            binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));

                        }

                    }


                } else {
                    Snackbar snackbar = Snackbar
                            .make(binding.customCount.llAll, "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

        });


        return binding.getRoot();

    }

    private void countColorSelection() {
        binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
        binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
        binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
    }

    private void setupViewPager(ViewPager viewPager_homer) {
        HomeViewPagerAdapter adapter = new HomeViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new NewAllDistrictsFragment(), "Districtwise");
        adapter.addFragment(new GenderwiseFragment(), "Genderwise");
        adapter.addFragment(new BloodwiseFragment(), "BloodGroupwise");
        adapter.addFragment(new AgewiseFragment(), "Agewise");

        viewPager_homer.setAdapter(adapter);
    }

    private void setCountsForDashboard(DashboardCountResponse dashboardCountResponse) {
        if (dashboardCountResponse != null) {

            int total = dashboardCountResponse.getJrc() + dashboardCountResponse.getYrc() + dashboardCountResponse.getMs();
            binding.customCount.tvAllcount.setText(String.valueOf(total));
            binding.customCount.tvJrccount.setText(String.valueOf(dashboardCountResponse.getJrc()));
            binding.customCount.tvYrccount.setText(String.valueOf(dashboardCountResponse.getYrc()));
            binding.customCount.tvLmcount.setText(String.valueOf(dashboardCountResponse.getMs()));

        }
    }


    class HomeViewPagerAdapter extends FragmentPagerAdapter {
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
