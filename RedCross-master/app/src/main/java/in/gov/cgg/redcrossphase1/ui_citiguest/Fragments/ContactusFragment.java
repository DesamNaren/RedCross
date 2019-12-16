package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.TabLoginActivity;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors.Contactus_adaptor;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.ContactusDetails_Bean;
import in.gov.cgg.redcrossphase1.ui_citiguest.CitiGuestMainActivity;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

import static android.content.Context.MODE_PRIVATE;


public class ContactusFragment extends Fragment implements SearchView.OnQueryTextListener {
    RecyclerView recyclerView;
    Contactus_adaptor adapter;
    ArrayList<ContactusDetails_Bean> contactUsarrayList;
    private FragmentActivity c;
    LinearLayout ll_contactUs;
    Button btn_sateCordinators, btn_districtCordinators;
    CustomProgressDialog progressDialog;


    int selectedThemeColor = -1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle("Contact Us");

        View root = inflater.inflate(R.layout.fragment_contactus, container, false);
        c = getActivity();
        setHasOptionsMenu(true);//Make sure you have this line of code.

        findViews(root);
        GlobalDeclaration.cordinatorType = "s";

        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
            setThemes();

        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
//            if (selectedThemeColor != -1) {
//                if (selectedThemeColor == R.color.redcroosbg_1) {
//                    ll_contactUs.setBackgroundResource(R.drawable.redcross1_bg);
//                    btn_sateCordinators.setBackgroundColor(getResources().getColor(selectedThemeColor));
//
//                } else if (selectedThemeColor == R.color.redcroosbg_2) {
//                    ll_contactUs.setBackgroundResource(R.drawable.redcross2_bg);
//                    btn_sateCordinators.setBackgroundColor(getResources().getColor(selectedThemeColor));
//
//                } else if (selectedThemeColor == R.color.redcroosbg_3) {
//                    ll_contactUs.setBackgroundResource(R.drawable.redcross3_bg);
//                    btn_sateCordinators.setBackgroundColor(getResources().getColor(selectedThemeColor));
//
//                } else if (selectedThemeColor == R.color.redcroosbg_4) {
//                    ll_contactUs.setBackgroundResource(R.drawable.redcross4_bg);
//                    btn_sateCordinators.setBackgroundColor(getResources().getColor(selectedThemeColor));
//
//                } else if (selectedThemeColor == R.color.redcroosbg_5) {
//                    ll_contactUs.setBackgroundResource(R.drawable.redcross5_bg);
//                    btn_sateCordinators.setBackgroundColor(getResources().getColor(selectedThemeColor));
//
//                } else if (selectedThemeColor == R.color.redcroosbg_6) {
//                    ll_contactUs.setBackgroundResource(R.drawable.redcross6_bg);
//                    btn_sateCordinators.setBackgroundColor(getResources().getColor(selectedThemeColor));
//
//                } else if (selectedThemeColor == R.color.redcroosbg_7) {
//                    ll_contactUs.setBackgroundResource(R.drawable.redcross7_bg);
//                    btn_sateCordinators.setBackgroundColor(getResources().getColor(selectedThemeColor));
//
//                } else if (selectedThemeColor == R.color.redcroosbg_8) {
//                    ll_contactUs.setBackgroundResource(R.drawable.redcross_splashscreen_bg);
//                    btn_sateCordinators.setBackgroundColor(getResources().getColor(selectedThemeColor));
//
//                } else {
//                    btn_sateCordinators.setBackgroundColor(getResources().getColor(selectedThemeColor));
//
//                }
//
//            } else {
//                btn_sateCordinators.setBackgroundColor(getResources().getColor(selectedThemeColor));
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }

        contactUsarrayList = new ArrayList<>();
        recyclerView = root.findViewById(R.id.rv_contactUs);
        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        prepareStateCordinatorsData();
        adapter = new Contactus_adaptor(contactUsarrayList, c, selectedThemeColor);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btn_districtCordinators.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallDistLogic();

            }
        });
        btn_sateCordinators.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_districtCordinators.setBackground(getResources().getDrawable(R.drawable.tab_background_unselected));

                CallStateLogic();
            }
        });


        String fromType;
        if (getArguments() != null) {
            fromType = getArguments().getString("FROM_TYPE");
            if (fromType != null && fromType.equalsIgnoreCase("FROM_STATE")) {
                CallStateLogic();
            } else {
                CallDistLogic();
            }
        } else {
            CallStateLogic();
        }

        return root;
    }

    private void setThemes() {
        if (selectedThemeColor != -1) { //
            if (selectedThemeColor == R.color.redcroosbg_1) {
                btn_sateCordinators.setBackgroundColor(getResources().getColor(R.color.redcroosbg_1));
                btn_sateCordinators.setTextColor(getResources().getColor(R.color.white));
                btn_districtCordinators.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                btn_districtCordinators.setTextColor(getResources().getColor(R.color.redcroosbg_1));
            } else if (selectedThemeColor == R.color.redcroosbg_2) {
                btn_sateCordinators.setBackgroundColor(getResources().getColor(R.color.redcroosbg_2));
                btn_sateCordinators.setTextColor(getResources().getColor(R.color.white));
                btn_districtCordinators.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                btn_districtCordinators.setTextColor(getResources().getColor(R.color.redcroosbg_2));

            } else if (selectedThemeColor == R.color.redcroosbg_3) {
                btn_sateCordinators.setBackgroundColor(getResources().getColor(R.color.redcroosbg_3));
                btn_sateCordinators.setTextColor(getResources().getColor(R.color.white));
                btn_districtCordinators.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                btn_districtCordinators.setTextColor(getResources().getColor(R.color.redcroosbg_3));

            } else if (selectedThemeColor == R.color.redcroosbg_4) {
                btn_sateCordinators.setBackgroundColor(getResources().getColor(R.color.redcroosbg_4));
                btn_sateCordinators.setTextColor(getResources().getColor(R.color.white));
                btn_districtCordinators.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                btn_districtCordinators.setTextColor(getResources().getColor(R.color.redcroosbg_4));
            } else if (selectedThemeColor == R.color.redcroosbg_5) {
                btn_sateCordinators.setBackgroundColor(getResources().getColor(R.color.redcroosbg_5));
                btn_sateCordinators.setTextColor(getResources().getColor(R.color.white));
                btn_districtCordinators.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                btn_districtCordinators.setTextColor(getResources().getColor(R.color.redcroosbg_5));
            } else if (selectedThemeColor == R.color.redcroosbg_6) {
                btn_sateCordinators.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));
                btn_sateCordinators.setTextColor(getResources().getColor(R.color.white));
                btn_districtCordinators.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                btn_districtCordinators.setTextColor(getResources().getColor(R.color.redcroosbg_6));
            } else if (selectedThemeColor == R.color.redcroosbg_7) {
                btn_sateCordinators.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));
                btn_sateCordinators.setTextColor(getResources().getColor(R.color.white));
                btn_districtCordinators.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                btn_districtCordinators.setTextColor(getResources().getColor(R.color.redcroosbg_7));

            } else if (selectedThemeColor == R.color.redcroosbg_8) {
                btn_sateCordinators.setBackgroundColor(getResources().getColor(R.color.redcroosbg_8));
                btn_sateCordinators.setTextColor(getResources().getColor(R.color.white));
                btn_districtCordinators.setBackground(getResources().getDrawable(R.drawable.lltheme8_bg));
                btn_districtCordinators.setTextColor(getResources().getColor(R.color.redcroosbg_8));
            } else {
                btn_sateCordinators.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));
                btn_sateCordinators.setTextColor(getResources().getColor(R.color.white));
                btn_districtCordinators.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                btn_districtCordinators.setTextColor(getResources().getColor(R.color.black));

            }

        } else {
            btn_sateCordinators.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));
            btn_sateCordinators.setTextColor(getResources().getColor(R.color.white));
            btn_districtCordinators.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
            btn_districtCordinators.setTextColor(getResources().getColor(R.color.black));

        }
    }

    private void setThemes_1() {
        if (selectedThemeColor != -1) { //
            if (selectedThemeColor == R.color.redcroosbg_1) {
                btn_districtCordinators.setBackgroundColor(getResources().getColor(R.color.redcroosbg_1));
                btn_districtCordinators.setTextColor(getResources().getColor(R.color.white));
                btn_sateCordinators.setBackground(getResources().getDrawable(R.drawable.lltheme1_bg));
                btn_sateCordinators.setTextColor(getResources().getColor(R.color.redcroosbg_1));
            } else if (selectedThemeColor == R.color.redcroosbg_2) {
                btn_districtCordinators.setBackgroundColor(getResources().getColor(R.color.redcroosbg_2));
                btn_districtCordinators.setTextColor(getResources().getColor(R.color.white));
                btn_sateCordinators.setBackground(getResources().getDrawable(R.drawable.lltheme2_bg));
                btn_sateCordinators.setTextColor(getResources().getColor(R.color.redcroosbg_2));

            } else if (selectedThemeColor == R.color.redcroosbg_3) {
                btn_districtCordinators.setBackgroundColor(getResources().getColor(R.color.redcroosbg_3));
                btn_districtCordinators.setTextColor(getResources().getColor(R.color.white));
                btn_sateCordinators.setBackground(getResources().getDrawable(R.drawable.lltheme3_bg));
                btn_sateCordinators.setTextColor(getResources().getColor(R.color.redcroosbg_3));

            } else if (selectedThemeColor == R.color.redcroosbg_4) {
                btn_districtCordinators.setBackgroundColor(getResources().getColor(R.color.redcroosbg_4));
                btn_districtCordinators.setTextColor(getResources().getColor(R.color.white));
                btn_sateCordinators.setBackground(getResources().getDrawable(R.drawable.lltheme4_bg));
                btn_sateCordinators.setTextColor(getResources().getColor(R.color.redcroosbg_4));
            } else if (selectedThemeColor == R.color.redcroosbg_5) {
                btn_districtCordinators.setBackgroundColor(getResources().getColor(R.color.redcroosbg_5));
                btn_districtCordinators.setTextColor(getResources().getColor(R.color.white));
                btn_sateCordinators.setBackground(getResources().getDrawable(R.drawable.lltheme5_bg));
                btn_sateCordinators.setTextColor(getResources().getColor(R.color.redcroosbg_5));
            } else if (selectedThemeColor == R.color.redcroosbg_6) {
                btn_districtCordinators.setBackgroundColor(getResources().getColor(R.color.redcroosbg_6));
                btn_districtCordinators.setTextColor(getResources().getColor(R.color.white));
                btn_sateCordinators.setBackground(getResources().getDrawable(R.drawable.lltheme6_bg));
                btn_sateCordinators.setTextColor(getResources().getColor(R.color.redcroosbg_6));
            } else if (selectedThemeColor == R.color.redcroosbg_7) {
                btn_districtCordinators.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));
                btn_districtCordinators.setTextColor(getResources().getColor(R.color.white));
                btn_sateCordinators.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                btn_sateCordinators.setTextColor(getResources().getColor(R.color.redcroosbg_7));

            } else if (selectedThemeColor == R.color.redcroosbg_8) {
                btn_districtCordinators.setBackgroundColor(getResources().getColor(R.color.redcroosbg_8));
                btn_districtCordinators.setTextColor(getResources().getColor(R.color.white));
                btn_sateCordinators.setBackground(getResources().getDrawable(R.drawable.red_tabunselected));
                btn_sateCordinators.setTextColor(getResources().getColor(R.color.redcroosbg_8));
            } else {
                btn_districtCordinators.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));
                btn_districtCordinators.setTextColor(getResources().getColor(R.color.white));
                btn_sateCordinators.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
                btn_sateCordinators.setTextColor(getResources().getColor(R.color.black));

            }

        } else {
            btn_districtCordinators.setBackgroundColor(getResources().getColor(R.color.redcroosbg_7));
            btn_districtCordinators.setTextColor(getResources().getColor(R.color.white));
            btn_sateCordinators.setBackground(getResources().getDrawable(R.drawable.lltheme7_bg));
            btn_sateCordinators.setTextColor(getResources().getColor(R.color.black));


        }
    }

    private void CallDistLogic() {
        GlobalDeclaration.cordinatorType = "";

        // 2. set layoutManger
        contactUsarrayList.clear();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        prepareDistrictCordinatorsData();
        adapter = new Contactus_adaptor(contactUsarrayList, c, selectedThemeColor);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btn_districtCordinators.setBackgroundResource(R.color.redcroosbg_6);
        btn_districtCordinators.setTextColor(getResources().getColor(R.color.white));
        btn_sateCordinators.setBackgroundResource(R.color.white);
        btn_sateCordinators.setTextColor(getResources().getColor(R.color.black));
        setThemes_1();
    }

    private void CallStateLogic() {
        GlobalDeclaration.cordinatorType = "s";


        // 2. set layoutManger
        contactUsarrayList.clear();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        prepareStateCordinatorsData();
        adapter = new Contactus_adaptor(contactUsarrayList, c, selectedThemeColor);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btn_districtCordinators.setBackgroundResource(R.color.white);
        btn_districtCordinators.setTextColor(getResources().getColor(R.color.black));
        btn_sateCordinators.setBackgroundResource(R.color.redcroosbg_6);
        btn_sateCordinators.setTextColor(getResources().getColor(R.color.white));
        setThemes();
    }


    private void prepareStateCordinatorsData() {
        ContactusDetails_Bean detail26 = new ContactusDetails_Bean("P. Vijaya Kumar Babu",
                "State Coordinator, Disaster ManagementManager, RCSMH,\n" +
                        "Focal Person, Family News Service & ICRC Cooperation Activities, \n" + "In-charge, Resource Mobilization\n" + "Indian Red Cross Society,\n" + "Telangana State Branch,\n" + "Mobile: 9849555393,\n" + "E-Mail: vijay_redcross@yahoo.co.in");
        contactUsarrayList.add(detail26);
        ContactusDetails_Bean detail27 = new ContactusDetails_Bean("Ramana",
                " First Aid & JRC/YRC,\n" +
                        "Indian Red Cross Society, \n" + "Mobile: 9948633398\n" + "E-Mail:ramanagmsw@gmail.com");
        contactUsarrayList.add(detail27);
    }

    private void prepareDistrictCordinatorsData() {
        ContactusDetails_Bean details = new ContactusDetails_Bean("Adilabad ", "Indian Red Cross Society,\n" +
                "Adilabad District Branch,\n" +
                "Adilabad 504001");
        contactUsarrayList.add(details);

        ContactusDetails_Bean details1 = new ContactusDetails_Bean("Bhadradri kottagudem", "Indian Red Cross Society,\n" +
                "Old LIC Office Road,\n" +
                "Bhadrachalam, Bhadradri -Kothagudem-507111");
        contactUsarrayList.add(details1);


        ContactusDetails_Bean details2 = new ContactusDetails_Bean("Hyderabad ", "Indian Red Cross Society,\n" +
                "District Collectorate Office,\n" +
                "Nampally Station Road, Abids,\n" +
                "HYDERABAD -500001.");
        contactUsarrayList.add(details2);

        ContactusDetails_Bean details3 = new ContactusDetails_Bean("Jagital ", "H.No.1-4-181, Balaji Nagar,\n" +
                "Jagityal Dist-505327");
        contactUsarrayList.add(details3);
        ContactusDetails_Bean details4 = new ContactusDetails_Bean("Kumaram bheem asifabad ", "H.No.1-16-273/6,\n" +
                "Opp: Indian Oil Petrol Pump, Industrial Area,\n" +
                "Sirpur Khagaz Nagar - Komaram Bheem Asifabad Dist-504299");
        contactUsarrayList.add(details4);

        ContactusDetails_Bean details5 = new ContactusDetails_Bean("Mahabubabad ", "D.No.4-4-10/1,U.Town,\n" +
                "Mahaboobabad District.-506101");
        contactUsarrayList.add(details5);

        ContactusDetails_Bean details6 = new ContactusDetails_Bean("Mahabubnagar", "H.No.6-1-92/C, Ganesh Nagar,\n" +
                "Near Rto Office, Mahabubnagar-509001");
        contactUsarrayList.add(details6);

        ContactusDetails_Bean details7 = new ContactusDetails_Bean("Mancherial ", "Blood Bank,I B Chowrasta\n" +
                "Mancherial Dist.-504208");
        contactUsarrayList.add(details7);

        ContactusDetails_Bean details8 = new ContactusDetails_Bean("Medak", "H.No:- 16-83, Reddy Colony,\n" +
                "Ramayanpet,\n" +
                "Medak District-502101");
        contactUsarrayList.add(details8);

        ContactusDetails_Bean details9 = new ContactusDetails_Bean("Medchal malkajgiri", "C/o. Misrilal Mangilal Maternity & Children Hospital Premises, Bowenpally, Sec-bad");
        contactUsarrayList.add(details9);

        ContactusDetails_Bean details10 = new ContactusDetails_Bean("Mulugu", "H.No.5-38,\n" +
                "Govindaraopet,(Village & Mandal),\n" +
                "Mulugu District -506344");
        contactUsarrayList.add(details10);

        ContactusDetails_Bean details11 = new ContactusDetails_Bean("Nagar kurnool", "DM&HO and Chairman,\n" +
                "Indian Red Cross Society,\n" +
                "DM&HO, Office\n" +
                "Nagarkurnool District-509209");
        contactUsarrayList.add(details11);

        ContactusDetails_Bean details12 = new ContactusDetails_Bean("Narayanpet", "H.No.1-7-48/4,\n" +
                "Teachers Colony,\n" +
                "Narayanapet -509210");
        contactUsarrayList.add(details12);

        ContactusDetails_Bean details13 = new ContactusDetails_Bean("Nalgonda ", "H.No.5-6-300,\n" +
                "Red Cross Bhavan,\n" +
                "Near Bus Stand,\n" +
                "Nalgonda-508001");
        contactUsarrayList.add(details13);

        ContactusDetails_Bean details14 = new ContactusDetails_Bean("Nirmal ", "Plot No.26,\n" +
                "52 G N R Colony,\n" +
                "Nirmal District - 504106");
        contactUsarrayList.add(details14);


        ContactusDetails_Bean details15 = new ContactusDetails_Bean("Nizamabad", "Behind: Tahasildar Office,\n" +
                "KhaleelWadi,\n" +
                "Nizamabad-503001");
        contactUsarrayList.add(details15);

        ContactusDetails_Bean details16 = new ContactusDetails_Bean("Peddapally ", "Office of D.M&H.O,\n" +
                "Railway Station Road,\n" +
                "Peddapally District -505172");
        contactUsarrayList.add(details16);

        ContactusDetails_Bean details17 = new ContactusDetails_Bean("Rajanna sirisilla", "H.No.6-7-34, Vidya Nagar,\n" +
                "Siricilla, Rajanna siricilla District - 505301");
        contactUsarrayList.add(details17);

        ContactusDetails_Bean details18 = new ContactusDetails_Bean("Ranga reddy", "Collectorate Campus,\n" +
                "lakdikapool, Hyd");
        contactUsarrayList.add(details18);

        ContactusDetails_Bean details19 = new ContactusDetails_Bean("Sanga reddy", "Opp:Shree Gayathri School,\n" +
                "Behind: I.B.Sanga Reddy,\n" +
                "SANGA REDDY District.-502001");
        contactUsarrayList.add(details19);

        ContactusDetails_Bean details20 = new ContactusDetails_Bean("Suryapet", "H.No.1-9-37/B,\n" +
                "Main Road,\n" +
                "C/o.Bharat Gas Office,\n" +
                "Gopalpuram,\n" +
                "Suryapet District-508213");
        contactUsarrayList.add(details20);

        ContactusDetails_Bean details21 = new ContactusDetails_Bean("Wanaparthy ", "R.No:-124, Dist Hospital,\n" +
                "wanaparthy district-509103");
        contactUsarrayList.add(details21);

        ContactusDetails_Bean details22 = new ContactusDetails_Bean("Warangal (RURAL)", "DM&HO Office,\n" +
                "Warangal (Rural)-506164");
        contactUsarrayList.add(details22);

        ContactusDetails_Bean details23 = new ContactusDetails_Bean("Warangal (URBAN)", "Opp: District Collect rate,\n" +
                "Subedari, Hanamkonda - 506001");
        contactUsarrayList.add(details23);

        ContactusDetails_Bean details24 = new ContactusDetails_Bean("Siddipet ", "Collector Office");
        contactUsarrayList.add(details24);

        ContactusDetails_Bean details25 = new ContactusDetails_Bean("Vikarabad", "Collector Office");
        contactUsarrayList.add(details25);


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
                startActivity(new Intent(getActivity(), CitiGuestMainActivity.class));
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickExit() {

        final PrettyDialog dialog = new PrettyDialog(getActivity());
        dialog
                .setTitle("")
                .setMessage("Do you want to Logout?")
                .setIcon(R.drawable.pdlg_icon_info, R.color.pdlg_color_blue, null)
                .addButton("OK", R.color.pdlg_color_white, R.color.pdlg_color_green, new PrettyDialogCallback() {
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

        dialog.show();
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
        btn_sateCordinators = root.findViewById(R.id.btn_sateCordinators);
        btn_districtCordinators = root.findViewById(R.id.btn_districtCordinators);
    }

    public interface OnFragmentInteractionListener {
    }
}


