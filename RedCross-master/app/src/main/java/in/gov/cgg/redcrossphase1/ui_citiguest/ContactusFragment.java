package in.gov.cgg.redcrossphase1.ui_citiguest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.TabLoginActivity;

import static android.content.Context.MODE_PRIVATE;


public class ContactusFragment extends Fragment implements SearchView.OnQueryTextListener {
    RecyclerView recyclerView;
    Contactus_adaptor adapter;
    ArrayList<ContactusDetails_Bean> contactUsarrayList;
    private FragmentActivity c;
    LinearLayout ll_contactUs;
    int selectedThemeColor = -1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle("Contact Us");

        View root = inflater.inflate(R.layout.fragment_contactus, container, false);
        c = getActivity();
        setHasOptionsMenu(true);//Make sure you have this line of code.

        findViews(root);


        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                if (selectedThemeColor == R.color.redcroosbg_1) {
                    ll_contactUs.setBackgroundResource(R.drawable.redcross1_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    ll_contactUs.setBackgroundResource(R.drawable.redcross2_bg);

                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    ll_contactUs.setBackgroundResource(R.drawable.redcross3_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    ll_contactUs.setBackgroundResource(R.drawable.redcross4_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    ll_contactUs.setBackgroundResource(R.drawable.redcross5_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    ll_contactUs.setBackgroundResource(R.drawable.redcross6_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    ll_contactUs.setBackgroundResource(R.drawable.redcross7_bg);
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    ll_contactUs.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        contactUsarrayList = new ArrayList<>();
        recyclerView = root.findViewById(R.id.rv_contactUs);
        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        ContactusDetails_Bean details = new ContactusDetails_Bean("ADILABAD DISTRICT", "Indian Red Cross Society,\n" +
                "Adilabad District Branch,\n" +
                "Adilabad 504001");
        contactUsarrayList.add(details);

        ContactusDetails_Bean details1 = new ContactusDetails_Bean("BHADRADRI KOTTAGUDEM DISTRICT", "Indian Red Cross Society,\n" +
                "Old LIC Office Road,\n" +
                "Bhadrachalam, Bhadradri -Kothagudem-507111");
        contactUsarrayList.add(details1);


        ContactusDetails_Bean details2 = new ContactusDetails_Bean("HYDERABAD DISTRICT", "Indian Red Cross Society,\n" +
                "District Collectorate Office,\n" +
                "Nampally Station Road, Abids,\n" +
                "HYDERABAD -500001.");
        contactUsarrayList.add(details2);

        ContactusDetails_Bean details3 = new ContactusDetails_Bean("JAGTIAL DISTRICT", "H.No.1-4-181, Balaji Nagar,\n" +
                "Jagityal Dist-505327");
        contactUsarrayList.add(details3);

        ContactusDetails_Bean details4 = new ContactusDetails_Bean("KUMARAM BHEEM ASIFABAD DISTRICT", "H.No.1-16-273/6,\n" +
                "Opp: Indian Oil Petrol Pump, Industrial Area,\n" +
                "Sirpur Khagaz Nagar - Komaram Bheem Asifabad Dist-504299");
        contactUsarrayList.add(details4);

        ContactusDetails_Bean details5 = new ContactusDetails_Bean("MAHABUBABAD DISTRICT", "D.No.4-4-10/1,U.Town,\n" +
                "Mahaboobabad District.-506101");
        contactUsarrayList.add(details5);

        ContactusDetails_Bean details6 = new ContactusDetails_Bean("MAHABUBNAGAR DISTRICT", "H.No.6-1-92/C, Ganesh Nagar,\n" +
                "Near Rto Office, Mahabubnagar-509001");
        contactUsarrayList.add(details6);

        ContactusDetails_Bean details7 = new ContactusDetails_Bean("MANCHERIAL DISTRICT", "Blood Bank,I B Chowrasta\n" +
                "Mancherial Dist.-504208");
        contactUsarrayList.add(details7);

        ContactusDetails_Bean details8 = new ContactusDetails_Bean("MEDAK DISTRICT", "H.No:- 16-83, Reddy Colony,\n" +
                "Ramayanpet,\n" +
                "Medak District-502101");
        contactUsarrayList.add(details8);

        ContactusDetails_Bean details9 = new ContactusDetails_Bean("MEDCHAL MALKAJGIRI DISTRICT", "C/o. Misrilal Mangilal Maternity & Children Hospital Premises, Bowenpally, Sec-bad");
        contactUsarrayList.add(details9);

        ContactusDetails_Bean details10 = new ContactusDetails_Bean("MULUGU DISTRICT", "H.No.5-38,\n" +
                "Govindaraopet,(Village & Mandal),\n" +
                "Mulugu District -506344");
        contactUsarrayList.add(details10);

        ContactusDetails_Bean details11 = new ContactusDetails_Bean("NAGAR KURNOOL DISTRICT", "DM&HO and Chairman,\n" +
                "Indian Red Cross Society,\n" +
                "DM&HO, Office\n" +
                "Nagarkurnool District-509209");
        contactUsarrayList.add(details11);

        ContactusDetails_Bean details12 = new ContactusDetails_Bean("NARAYANPET DISTRICT", "H.No.1-7-48/4,\n" +
                "Teachers Colony,\n" +
                "Narayanapet -509210");
        contactUsarrayList.add(details12);

        ContactusDetails_Bean details13 = new ContactusDetails_Bean("NALGONDA DISTRICT", "H.No.5-6-300,\n" +
                "Red Cross Bhavan,\n" +
                "Near Bus Stand,\n" +
                "Nalgonda-508001");
        contactUsarrayList.add(details13);

        ContactusDetails_Bean details14 = new ContactusDetails_Bean("NIRMAL DISTRICT", "Plot No.26,\n" +
                "52 G N R Colony,\n" +
                "Nirmal District - 504106");
        contactUsarrayList.add(details14);


        ContactusDetails_Bean details15 = new ContactusDetails_Bean("NIZAMABAD DISTRICT", "Behind: Tahasildar Office,\n" +
                "KhaleelWadi,\n" +
                "Nizamabad-503001");
        contactUsarrayList.add(details15);

        ContactusDetails_Bean details16 = new ContactusDetails_Bean("PEDDAPALLY DISTRICT", "Office of D.M&H.O,\n" +
                "Railway Station Road,\n" +
                "Peddapally District -505172");
        contactUsarrayList.add(details16);

        ContactusDetails_Bean details17 = new ContactusDetails_Bean("RAJANNA SIRICILLA DISTRICT", "H.No.6-7-34, Vidya Nagar,\n" +
                "Siricilla, Rajanna siricilla District - 505301");
        contactUsarrayList.add(details17);

        ContactusDetails_Bean details18 = new ContactusDetails_Bean("RANGA REDDY DISTRICT", "Collectorate Campus,\n" +
                "lakdikapool, Hyd");
        contactUsarrayList.add(details18);

        ContactusDetails_Bean details19 = new ContactusDetails_Bean("SANGA REDDY DISTRICT", "Opp:Shree Gayathri School,\n" +
                "Behind: I.B.Sanga Reddy,\n" +
                "SANGA REDDY District.-502001");
        contactUsarrayList.add(details19);

        ContactusDetails_Bean details20 = new ContactusDetails_Bean("SURYAPET DISTRICT", "H.No.1-9-37/B,\n" +
                "Main Road,\n" +
                "C/o.Bharat Gas Office,\n" +
                "Gopalpuram,\n" +
                "Suryapet District-508213");
        contactUsarrayList.add(details20);

        ContactusDetails_Bean details21 = new ContactusDetails_Bean("WANAPARTHY DISTRICT", "R.No:-124, Dist Hospital,\n" +
                "wanaparthy district-509103");
        contactUsarrayList.add(details21);

        ContactusDetails_Bean details22 = new ContactusDetails_Bean("WARANGAL (RURAL) DISTRICT", "DM&HO Office,\n" +
                "Warangal (Rural)-506164");
        contactUsarrayList.add(details22);

        ContactusDetails_Bean details23 = new ContactusDetails_Bean("WARANGAL (URBAN) DISTRICT", "Opp: District Collect rate,\n" +
                "Subedari, Hanamkonda - 506001");
        contactUsarrayList.add(details23);

        ContactusDetails_Bean details24 = new ContactusDetails_Bean("SIDDIPET DISTRICT", "Collector Office");
        contactUsarrayList.add(details24);

        ContactusDetails_Bean details25 = new ContactusDetails_Bean("VIKARABAD DISTRICT", "Collector Office");
        contactUsarrayList.add(details25);


        adapter = new Contactus_adaptor(contactUsarrayList, c, selectedThemeColor);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();

        inflater.inflate(R.menu.menu_item, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_seach);
        // SearchView searchView =(SearchView) MenuItemCompat.getActionView(menuItem);
        //  SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setOnQueryTextListener(this);

       /* MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Set styles for expanded state here
                if (((AppCompatActivity)getActivity()).getSupportActionBar() != null) {
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                }
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Set styles for collapsed state here
                if (((AppCompatActivity)getActivity()).getSupportActionBar() != null) {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLUE));
                }
                return true;
            }
        });*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_exit:
                // search action
                onClickExit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickExit() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        final AlertDialog alertDialog1 = alertDialog.create();


        alertDialog.setMessage("Do you want to exit ?");
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //alertDialog1.dismiss();\

                alertDialog1.dismiss();
            }
        });
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                startActivity(new Intent(c, TabLoginActivity.class));
                getActivity().finish();
            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();

        ArrayList<ContactusDetails_Bean> newList = new ArrayList<>();

        for (ContactusDetails_Bean districtName : contactUsarrayList) {
            String name = districtName.getDistarictName().toLowerCase();
            if (name.contains(newText)) {
                newList.add(districtName);
            }
        }

        adapter.setFilter(newList);
        return true;
    }

    private void findViews(View root) {
        ll_contactUs = root.findViewById(R.id.ll_contactUs);
    }
}


