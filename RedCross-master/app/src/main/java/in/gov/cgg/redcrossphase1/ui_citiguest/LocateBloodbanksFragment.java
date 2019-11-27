package in.gov.cgg.redcrossphase1.ui_citiguest;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;

public class LocateBloodbanksFragment extends Fragment implements SearchView.OnQueryTextListener {
    RecyclerView recyclerView;
    BloodbankAdaptor adapter;
    ArrayList<BloodBankdetails_Bean> detailsarrayList;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle("Bloodbanks Details");
        final FragmentActivity c = getActivity();
        setHasOptionsMenu(true);//Make sure you have this line of code.
        detailsarrayList = new ArrayList<>();
        View rootView = inflater.inflate(R.layout.fragment_ourbloodbanks, container, false);

        recyclerView = rootView.findViewById(R.id.rv_bloodBankDetails);
        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        BloodBankdetails_Bean details = new BloodBankdetails_Bean("Bhadrachalam", "9849172902",
                "Red Cross Blood Bank\nD.No.9-1-18/14\nIRCS Flood Shelter Building\nMedical Colony Kunavaram Road Bhadrachalam Bhadradrikothagudem.", "Dr.S.N.Kantha Rao");
        detailsarrayList.add(details);

        BloodBankdetails_Bean details1 = new BloodBankdetails_Bean("Hyderabad", "Ph.No.(040)27633087\n(040) 27627973 (F)", "H.No.1-9-310 Near Spencers Super Market Vidya Nagar\nHyderabad-500044.", "Dr.K.Pitchi Reddy");
        detailsarrayList.add(details1);

        BloodBankdetails_Bean details2 = new BloodBankdetails_Bean("Karimnagar", " 9849518963", "Red Cross Blood Bank\nH.No.4-1-22 Opp to Andhra Bank Main Branch Osmanpura\nKarimnagar.", "Dr.M.L.N. Reddy");
        detailsarrayList.add(details2);

        BloodBankdetails_Bean details3 = new BloodBankdetails_Bean("Mahaboob Nagar", "08542-246225",
                "Red Cross Blood Bank\n H.NO.1-6-65/5/A\nModern School Chourastha Mahaboob Nagar", "Dr.P.M.Janardhan Reddy");
        detailsarrayList.add(details3);

        BloodBankdetails_Bean details4 = new BloodBankdetails_Bean("Mancherial", "08736-259259\nCell: 9492557337", "Indian Red Cross Society Blood Bank\nGround Floor Area Hospital Mancherial", "Dr.D.Vishnu Murthy");
        detailsarrayList.add(details4);

        BloodBankdetails_Bean details5 = new BloodBankdetails_Bean("Nalgonda", "(08682)245790\n Cell:9010401091", "Indian Red Cross Society Blood Bank\nD.No.5-6-300 Red Cross Bhavan\nNear:RTCBusstnad\n Nalgonda-508001.", "Dr.D.Santosh Reddy");
        detailsarrayList.add(details5);

        BloodBankdetails_Bean details6 = new BloodBankdetails_Bean("Nizamabad", "08462-222002\n Cell: 9849459065", "Indian Red Cross Society Blood Bank,Khaleelwadi Beside Joint Collector Residence\n Nizamabad",
                "Dr.T.Nageshwara Reddy");
        detailsarrayList.add(details6);

        BloodBankdetails_Bean details7 = new BloodBankdetails_Bean("Warangal", "(0870) 2456765(Ph)\nCell: 9441413175", "Indian Red Cross Society Blood Bank, Red Cross Building Opp District Collectorate\nSubedari Hanamkonda Warangal District.", "Dr.G.Prabhakar Rao");
        detailsarrayList.add(details7);


        adapter = new BloodbankAdaptor(detailsarrayList, c);
        recyclerView.setAdapter(adapter);


        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_item, menu);
        MenuItem menuItem = menu.findItem(R.id.action_seach);
        // SearchView searchView =(SearchView) MenuItemCompat.getActionView(menuItem);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();

        ArrayList<BloodBankdetails_Bean> newList = new ArrayList<>();

        for (BloodBankdetails_Bean districtName : detailsarrayList) {
            String name = districtName.getDistictName().toLowerCase();
            if (name.contains(newText)) {
                newList.add(districtName);
            }
        }

        adapter.setFilter(newList);
        return true;
    }


}
