package in.gov.cgg.redcrossphase1.ui_officer.fragments;

import android.content.Intent;
import android.net.Uri;
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
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Objects;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.TestFrag;
import in.gov.cgg.redcrossphase1.databinding.FragmentMembereditBinding;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.ui_officer.activities.NewOfficerMainActivity;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.StudentListBean;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class EnrolledMemberEditFragment extends TestFrag {


    private static final int PICK_IMAGE_REQUEST = 101;
    FragmentMembereditBinding binding;
    private StudentListBean data_dashbord;
    private List<String> headStringList;
    private int selectedThemeColor = -1;
    private Uri mProfilePicUri;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_memberedit, container, false);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));


        if (GlobalDeclaration.role != null) {
            if (GlobalDeclaration.role.contains("D")) {
                binding.editDetails.setVisibility(View.VISIBLE);
            } else {
                binding.editDetails.setVisibility(View.GONE);

            }
        }

        try {
            selectedThemeColor = Objects.requireNonNull(getActivity()).getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF),
                    MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);

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
        if (10 < headStringList.size())
            binding.tvHeaderinstutename.setText(headStringList.get(10));

        if (11 < headStringList.size())
            binding.tvHeaderinstutetype.setText(headStringList.get(11));

        if (12 < headStringList.size())
            binding.tvHeaderenddate.setText(headStringList.get(12));

        if (13 < headStringList.size())
            binding.tvHeaderMemberType.setText(headStringList.get(13));

//        if (data_dashbord.getPhoto() != null) {
//            if (!data_dashbord.getPhoto().isEmpty()) {
//
//                Glide.with(context).load(data_dashbord.getPhoto()).into(binding.imgUser);
//            } else {
//                Glide.with(context).load(R.drawable.edituser2).into(binding.imgUser);
//
//            }
//        } else {
//            Glide.with(context).load(R.drawable.edituser2).into(binding.imgUser);
//
//        }
        try {
            if (data_dashbord.getPhoto() != null) {
                if (!data_dashbord.getPhoto().isEmpty()) {

                    Glide.with(context)
//                            .load(data_dashbord.getPhoto())
                            .load(Uri.parse(CheckInternet.getRightAngleImage(data_dashbord.getPhoto())))
                            .placeholder(R.drawable.loader_black1)
                            .error(getResources().getDrawable(R.drawable.edituser2))
                            .into(binding.imgUser);
                } else {
                    binding.imgUser.setImageDrawable(getResources().getDrawable(R.drawable.edituser2));

                }
            } else {
                binding.imgUser.setImageDrawable(getResources().getDrawable(R.drawable.edituser2));

            }
        } catch (Exception e) {
            binding.imgUser.setImageDrawable(getResources().getDrawable(R.drawable.edituser2));

        }

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

//        binding.imgUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openFileChooser();
//            }
//        });
        binding.editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewEditProfileFramnet.class));
//                FragmentActivity activity = (FragmentActivity) v.getContext();
//                Fragment frag = new EditProfileFramnet();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
//                        frag).addToBackStack(null).commit();
                GlobalDeclaration.clickeddetails = data_dashbord;
            }
        });


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
        MenuItem logout = menu.findItem(R.id.home);
        logout.setIcon(R.drawable.ic_home_white_48dp);

        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getActivity(), NewOfficerMainActivity.class));
                return true;
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

        if (GlobalDeclaration.flag) {
            Fragment frag = new GetDrilldownFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.nav_host_fragment_officer, frag, GetDrilldownFragment.class.getSimpleName());
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        } else {

        }

        Objects.requireNonNull(getView()).setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    FragmentActivity activity = (FragmentActivity) v.getContext();

                    if (GlobalDeclaration.Selection_type.equalsIgnoreCase("Membership")) {
                        Fragment frag = new GetMembershipDrilldown();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                                frag).addToBackStack(null).commit();

                    } else {
                        Fragment frag = new GetDrilldownFragment();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                                frag).addToBackStack(null).commit();

                    }


                    //getActivity().finishFromChild(getActivity());
                    return true;

                }
                return false;
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mProfilePicUri = data.getData();
            Glide.with(getActivity())
                    .load(mProfilePicUri)
                    .into(binding.imgUser);
        }
    }

}