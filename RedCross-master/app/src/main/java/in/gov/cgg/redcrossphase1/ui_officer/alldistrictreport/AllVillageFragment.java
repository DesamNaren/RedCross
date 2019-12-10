package in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentAldistrictBinding;
import in.gov.cgg.redcrossphase1.ui_officer.home_distrcit.CustomDistricClass;
import in.gov.cgg.redcrossphase1.ui_officer_new.NewOfficerMainActivity;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;

public class AllVillageFragment extends Fragment {


    CustomProgressDialog pd;
    int value;
    private AllDistrictsViewModel allDistrictsViewModel;
    private FragmentAldistrictBinding binding;
    private LevelAdapter adapter1;
    int selectedThemeColor = -1;
    private androidx.appcompat.widget.SearchView searchView;
    private androidx.appcompat.widget.SearchView.OnQueryTextListener queryTextListener;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_aldistrict, container, false);


        GlobalDeclaration.home = false;

        if (GlobalDeclaration.leveMName != null) {
            binding.cvName.setVisibility(View.VISIBLE);
            binding.tvlevelname.setText("Dist: " + GlobalDeclaration.leveDName);
            binding.tvmname.setText("Mndl: " + GlobalDeclaration.leveMName);
        } else {
            binding.cvName.setVisibility(View.GONE);
        }
       /* binding.searchView.setIconifiedByDefault(false);
        binding.searchView.setOnQueryTextListener(this);
        binding.searchView.setSubmitButtonEnabled(true);
        binding.searchView.setQueryHint("Search By Name");*/

//        binding.customCount.llPicker.setVisibility(View.GONE);
//        binding.customCount.cvName.setVisibility(View.VISIBLE);
//        try {
//            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
//            if (selectedThemeColor != -1) {
//                if (selectedThemeColor == R.color.redcroosbg_1) {
//                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//                    binding.customCount.llName.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
//
//                } else if (selectedThemeColor == R.color.redcroosbg_2) {
//                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//                    binding.customCount.llName.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
//                } else if (selectedThemeColor == R.color.redcroosbg_3) {
//                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                    binding.customCount.llName.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
//                } else if (selectedThemeColor == R.color.redcroosbg_4) {
//                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                    binding.customCount.llName.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
//                } else if (selectedThemeColor == R.color.redcroosbg_5) {
//                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                    binding.customCount.llName.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
//                } else if (selectedThemeColor == R.color.redcroosbg_6) {
//                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                    binding.customCount.llName.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
//                } else if (selectedThemeColor == R.color.redcroosbg_7) {
//                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                    binding.customCount.llName.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
//                } else if (selectedThemeColor == R.color.redcroosbg_8) {
//                    binding.customCount.llJrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                    binding.customCount.llYrc.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                    binding.customCount.llLm.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));
//                }
//                binding.customCount.tvJrcnme.setTextColor(getResources().getColor(selectedThemeColor));
//                binding.customCount.tvJrccount.setTextColor(getResources().getColor(selectedThemeColor));
//                binding.customCount.tvYrccount.setTextColor(getResources().getColor(selectedThemeColor));
//                binding.customCount.tvYrcnme.setTextColor(getResources().getColor(selectedThemeColor));
//                binding.customCount.tvLmcount.setTextColor(getResources().getColor(selectedThemeColor));
//                binding.customCount.tvLmname.setTextColor(getResources().getColor(selectedThemeColor));
//                binding.customCount.tvTotal.setTextColor(getResources().getColor(selectedThemeColor));
//                binding.customCount.tvlevelname.setTextColor(getResources().getColor(selectedThemeColor));
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
        pd = new CustomProgressDialog(getActivity());
        pd.show();
        allDistrictsViewModel =
                ViewModelProviders.of(this, new CustomDistricClass(getActivity(), "alldistrict")).
                        get(AllDistrictsViewModel.class);
        Objects.requireNonNull(getActivity()).setTitle("Village wise");

        if (getArguments() != null) {
            value = getArguments().getInt("mid");
        } else {
            value = GlobalDeclaration.localMid;
        }

        allDistrictsViewModel.getAllVillages("VillageWise", "3", value).
                observe(getActivity(), new Observer<List<StatelevelDistrictViewCountResponse>>() {
                    @Override
                    public void onChanged(@Nullable List<StatelevelDistrictViewCountResponse> allDistrictList) {
                        if (allDistrictList != null) {
                            setDataforRV(allDistrictList);
                            //setCountsForDashboard(allDistrictList);
                            pd.dismiss();
                        }
                    }
                });

//        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                binding.refreshLayout.setRefreshing(false);
//                // code on swipe refresh
//                allDistrictsViewModel.getAllVillages("VillageWise", "3", value).
//                        observe(getActivity(), new Observer<List<StatelevelDistrictViewCountResponse>>() {
//                            @Override
//                            public void onChanged(@Nullable List<StatelevelDistrictViewCountResponse> allDistrictList) {
//                                if (allDistrictList != null) {
//                                    setDataforRV(allDistrictList);
//                                    pd.dismiss();
//                                }
//                            }
//                        });
//            }
//        });
//        binding.refreshLayout.setColorSchemeColors(Color.RED);


        return binding.getRoot();

    }


    private void setDataforRV(List<StatelevelDistrictViewCountResponse> allDistrictList) {
        if (allDistrictList.size() > 0) {
            for (int i = 0; i < allDistrictList.size(); i++) {
                allDistrictList.get(i).setTotalCounts((allDistrictList.get(i).getJRC() +
                        allDistrictList.get(i).getYRC() +
                        allDistrictList.get(i).getMembership()));
            }
            List<StatelevelDistrictViewCountResponse> newlist = new ArrayList<>();
            newlist.addAll(allDistrictList);

            Collections.sort(newlist, new Comparator<StatelevelDistrictViewCountResponse>() {
                @Override
                public int compare(StatelevelDistrictViewCountResponse lhs, StatelevelDistrictViewCountResponse rhs) {
                    return lhs.getTotalCounts().compareTo(rhs.getTotalCounts());
                }
            });

            Collections.reverse(newlist);
            binding.rvAlldistrictwise.setHasFixedSize(true);
            binding.rvAlldistrictwise.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter1 = new LevelAdapter(getActivity(), allDistrictList, "v", selectedThemeColor);
            binding.rvAlldistrictwise.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    //Pressed return button - returns to the results menu
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getView()).setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    FragmentActivity activity = (FragmentActivity) v.getContext();
                    Fragment frag = new AllMandalsFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();
                    return true;

                }
                return false;
            }
        });
    }

/*    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (adapter1 != null) {
            adapter1.filter(newText);
        }
        return true;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear(); // Remove all existing items from the menu, leaving it empty as if it had just been created.
        inflater.inflate(R.menu.activity_searchmenu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        menu.findItem(R.id.logout_search).setIcon(R.drawable.ic_home_white_48dp);
        menu.findItem(R.id.logout_search).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getActivity(), NewOfficerMainActivity.class));
                return true;
            }
        });

        if (searchItem != null) {
            searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);
                    if (adapter1 != null) {
                        adapter1.filter(newText);
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

}
