package in.gov.cgg.redcrossphase_offline.ui_officer.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase_offline.R;
import in.gov.cgg.redcrossphase_offline.TabLoginActivity;
import in.gov.cgg.redcrossphase_offline.TestFrag;
import in.gov.cgg.redcrossphase_offline.databinding.FragmentMembereditBinding;
import in.gov.cgg.redcrossphase_offline.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase_offline.ui_officer.modelbeans.StudentListBean;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

import static android.content.Context.MODE_PRIVATE;

public class EnrolledMemberEditFragment extends TestFrag {


    FragmentMembereditBinding binding;
    private StudentListBean data_dashbord;
    private List<String> headStringList;
    private int selectedThemeColor = -1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_memberedit, container, false);

        try {
            selectedThemeColor = Objects.requireNonNull(getActivity()).getSharedPreferences("THEMECOLOR_PREF",
                    MODE_PRIVATE).getInt("theme_color", -1);

            if (selectedThemeColor != -1) {
                binding.scroll.setBackgroundColor(getResources().getColor(selectedThemeColor));
                binding.tvHeadermandal.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvHeaderMemberType.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvHeaderenddate.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvHeaderinstutetype.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvHeaderclass.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvHeaderinstutename.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvHeaderemail.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvHeaderbloodgroup.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvHeadervillage.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvHeadername.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvHeadermeberId.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvHeaderdob.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvHeadergender.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvHeadermobile.setTextColor(getResources().getColor(selectedThemeColor));
                binding.tvHeaderenddate.setTextColor(getResources().getColor(selectedThemeColor));
                binding.adapter.setBackgroundColor(getResources().getColor(selectedThemeColor));

                binding.btnSubmit.setTextColor(getResources().getColor(selectedThemeColor));
                binding.btnCancel.setTextColor(getResources().getColor(selectedThemeColor));

            } else {
                binding.scroll.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.tvHeadermandal.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvHeaderMemberType.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvHeaderenddate.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvHeaderinstutetype.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvHeaderclass.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvHeaderinstutename.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvHeaderemail.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvHeaderbloodgroup.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvHeadervillage.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvHeadername.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvHeadermeberId.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvHeaderdob.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvHeadergender.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvHeaderenddate.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.tvHeadermobile.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.adapter.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


                binding.btnSubmit.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.btnCancel.setTextColor(getResources().getColor(R.color.colorPrimary));

            }

        } catch (Exception e) {
            e.printStackTrace();

        }


        data_dashbord = GlobalDeclaration.drilldownResponse;
        headStringList = GlobalDeclaration.headStringList;
//header
        binding.tvHeadermandal.setText(headStringList.get(0));
        binding.tvHeadervillage.setText(headStringList.get(1));
        binding.tvHeadername.setText(headStringList.get(3));
        binding.tvHeadermeberId.setText(headStringList.get(2));
        binding.tvHeadergender.setText(headStringList.get(4));
        binding.tvHeaderdob.setText(headStringList.get(5));
        binding.tvHeadermobile.setText(headStringList.get(6));
        binding.tvHeaderbloodgroup.setText(headStringList.get(7));
        binding.tvHeaderemail.setText(headStringList.get(8));
        binding.tvHeaderclass.setText(headStringList.get(9));
        binding.tvHeaderinstutename.setText(headStringList.get(10));
        binding.tvHeaderinstutetype.setText(headStringList.get(11));
        binding.tvHeaderenddate.setText(headStringList.get(12));
        binding.tvHeaderMemberType.setText(headStringList.get(13));

        //values
        binding.tvMandal.setText(data_dashbord.getMandal());
        binding.tvVillage.setText(data_dashbord.getVillage());
        binding.tvName.setText(data_dashbord.getName());
        binding.tvMemberId.setText(data_dashbord.getMemberId());
        binding.tvGender.setText(data_dashbord.getGender());
        binding.tvDo.setText(data_dashbord.getDob());
        binding.tvMobilenumber.setText(data_dashbord.getPhone());
        binding.tvBloodgroup.setText(data_dashbord.getBloodgp());
        binding.tvEmail.setText(data_dashbord.getEmail());
        binding.tvClass.setText(data_dashbord.getClassName());
        binding.tvInstaname.setText(data_dashbord.getSchoolname());
        binding.tvInstatype.setText(data_dashbord.getSchooltype());
        binding.tvEnddate.setText(data_dashbord.getEndDate());
        binding.tvMembertype.setText(data_dashbord.getEnrollmentType());


        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear(); // Remove all existing items from the menu, leaving it empty as if it had just been created.
        inflater.inflate(R.menu.activity_backpress, menu);
        MenuItem logout = menu.findItem(R.id.logout);

        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //startActivity(new Intent(getActivity(), NewOfficerMainActivity.class));
                final PrettyDialog dialog = new PrettyDialog(getActivity());
                dialog
                        .setTitle("Red cross")
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
                return false;
            }
        });

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
        return super.onOptionsItemSelected(item);
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
                    Fragment frag = new GetDrilldownFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();
                    return true;

                }
                return false;
            }
        });
    }


}