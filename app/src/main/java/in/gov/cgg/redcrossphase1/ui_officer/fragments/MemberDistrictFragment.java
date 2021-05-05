package in.gov.cgg.redcrossphase1.ui_officer.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.TabLoginActivity;
import in.gov.cgg.redcrossphase1.databinding.FragmentMemberdistrictBinding;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.ui_officer.adapters.MemberCountAdapter;
import in.gov.cgg.redcrossphase1.ui_officer.custom_officer.CustomDistricClass;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.Val;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import in.gov.cgg.redcrossphase1.viewmodels.AllDistrictsViewModel;

import static android.content.Context.MODE_PRIVATE;

public class MemberDistrictFragment extends Fragment {

    FragmentMemberdistrictBinding binding;
    private int selectedThemeColor = -1;
    private MemberCountAdapter adapter1;
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_memberdistrict, container, false);

        GlobalDeclaration.home = false;
        (getActivity()).setTitle("Dashboard");

        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF",
                    MODE_PRIVATE).getInt("theme_color", -1);


        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        AllDistrictsViewModel allDistrictsViewModel = ViewModelProviders.of(getActivity(), new CustomDistricClass(getActivity(),
                "d")).get(AllDistrictsViewModel.class);

        if (CheckInternet.isOnline(getActivity())) {
            allDistrictsViewModel.getMemberCountsForDashboardMobile("1").
                    observe(getViewLifecycleOwner(), new Observer<List<Val>>() {
                        @Override
                        public void onChanged(@Nullable List<Val> allDistrictList) {
                            if (allDistrictList != null) {
                                if (allDistrictList.size() > 0) {
                                    setDataforRVDistrict(allDistrictList);
                                } else {
                                    binding.tvNodata.setText("No data available");

                                }
                            }
                        }
                    });
            //}
        }

        return binding.getRoot();

    }


    private void setDataforRVDistrict(List<Val> allDistrictList) {

        if (allDistrictList.size() > 0) {

            List<Val> newlist = new ArrayList<>();
            newlist.addAll(allDistrictList);

            Collections.sort(newlist, new Comparator<Val>() {
                @Override
                public int compare(Val lhs, Val rhs) {
                    return lhs.getTotal().compareTo(rhs.getTotal());
                }
            });
            binding.tvNodata.setVisibility(View.GONE);

            Collections.reverse(newlist);
            binding.rvAlldistrictwise.setHasFixedSize(true);
            binding.rvAlldistrictwise.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter1 = new MemberCountAdapter(getActivity(), newlist, "d", selectedThemeColor);
            binding.rvAlldistrictwise.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();

        } else {
            Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
            binding.tvNodata.setText("No data available");

        }
    }


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

        //menu.findItem(R.id.logout_search).setIcon(R.drawable.ic_home_white_48dp);
        menu.findItem(R.id.logout_search).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                /*final PrettyDialog dialog = new PrettyDialog(getActivity());
                dialog
                        .setTitle("Red cross")
                        .setMessage("Do you want to Logout?")
                        .setIcon(R.drawable.pdlg_icon_info, R.color.pdlg_color_blue, null)
                        .addButton(getResources().getString(R.string.ok), R.color.pdlg_color_white, R.color.pdlg_color_green, new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                dialog.dismiss();
                                startActivity(new Intent(getActivity(), TabLoginActivity.class));
                                getActivity().finish();
                            }
                        })
                        .addButton("Cancel", R.color.pdlg_color_white, R.color.pdlg_color_red, new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                dialog.dismiss();
                                // Toast.makeText(OfficerMainActivity.this, "Cancel selected", Toast.LENGTH_SHORT).show();
                            }
                        });

                dialog.show();*/
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage("Do you want to Logout?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(getActivity(), TabLoginActivity.class));
                                getActivity().finish();
                            }
                        });

                builder1.setNegativeButton(
                        getResources().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
//                                                   Intent i = new Intent(MainActivity.this, MainActivity.class);
//                                startActivity(i);

                                dialog.dismiss();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                return true;
            }
        });

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
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
