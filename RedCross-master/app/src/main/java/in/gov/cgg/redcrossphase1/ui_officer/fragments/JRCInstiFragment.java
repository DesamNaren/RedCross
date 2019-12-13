package in.gov.cgg.redcrossphase1.ui_officer.fragments;

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
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentJrcinstiBinding;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_officer.activities.NewOfficerMainActivity;
import in.gov.cgg.redcrossphase1.ui_officer.adapters.InstitutionAdapter;
import in.gov.cgg.redcrossphase1.ui_officer.custom_officer.CustomDistricClass;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.UserTypesList;
import in.gov.cgg.redcrossphase1.ui_officer.viewmodels.AllDistrictsViewModel;

public class JRCInstiFragment extends Fragment {
    int selectedThemeColor = -1;

    FragmentJrcinstiBinding binding;
    private InstitutionAdapter adapter1;
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_jrcinsti, container, false);


        binding.tvNodata.setVisibility(View.VISIBLE);
        GlobalDeclaration.home = false;

        AllDistrictsViewModel model = ViewModelProviders.of(this, new CustomDistricClass(getActivity(),
                "alldistrict")).get(AllDistrictsViewModel.class);


        (getActivity()).setTitle("Institution wise");


        model.getInstitutionCounts().observe(getActivity(), new Observer<List<UserTypesList>>() {
            @Override
            public void onChanged(@Nullable List<UserTypesList> alldaywisecounts) {
                if (alldaywisecounts != null) {
                    setDataforRV(alldaywisecounts);
                }
            }
        });

        return binding.getRoot();

    }


    private void setDataforRV(List<UserTypesList> daywisecount) {

        //int r= sortDatesHere();
        int total;
        if (daywisecount.size() > 1) {

            binding.tvNodata.setVisibility(View.GONE);

            binding.rvDaywiselist.setVisibility(View.VISIBLE);
            binding.tvNodata.setVisibility(View.GONE);
            binding.rvDaywiselist.setHasFixedSize(true);
            binding.rvDaywiselist.setLayoutManager(new LinearLayoutManager(getActivity()));

            adapter1 = new InstitutionAdapter(getActivity(), daywisecount, selectedThemeColor);
            binding.rvDaywiselist.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();


        } else {
            binding.tvNodata.setText("No data available");

            binding.rvDaywiselist.setVisibility(View.GONE);
            binding.tvNodata.setVisibility(View.VISIBLE);
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
                    Fragment frag = new NewOfficerHomeFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear(); // Remove all existing items from the menu, leaving it empty as if it had just been created.
        inflater.inflate(R.menu.bb_search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        menu.findItem(R.id.action_logout).setIcon(R.drawable.ic_home_white_48dp);
        menu.findItem(R.id.action_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getActivity(), NewOfficerMainActivity.class));
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
