package in.gov.cgg.redcrossphase1.ui_officer_new;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport.AllMandalsFragment;
import in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport.AllVillageFragment;
import in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport.StatelevelDistrictViewCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.drilldown.GetDrilldownFragment;

public class DistrictLevelAdapter extends RecyclerView.Adapter<DistrictLevelAdapter.DistrictViewHolder> {
    private final ArrayList<StatelevelDistrictViewCountResponse> data_dashbord;
    Context mCtx;
    List<StatelevelDistrictViewCountResponse> allDistricts;
    String type;
    int total;
//    int selectedThemeColor = -1;

    public DistrictLevelAdapter(Context mCtx, List<StatelevelDistrictViewCountResponse> allDistricts, String type, int selectedThemeColor) {
        this.mCtx = mCtx;
        this.type = type;

        this.allDistricts = allDistricts;
        this.data_dashbord = new ArrayList<StatelevelDistrictViewCountResponse>();
        this.data_dashbord.addAll(allDistricts);
//        this.selectedThemeColor = selectedThemeColor;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.districtlevel_adapter, parent, false);
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DistrictViewHolder holder, final int position) {

        holder.mainLayout.setBackgroundColor(mCtx.getResources().getColor(R.color.redcroosbg_6));

        total = allDistricts.get(position).getJRC() + allDistricts.get(position).getYRC() + allDistricts.get(position).getMembership();
        //total = allDistricts.get(position).getTotalCounts();

        //  holder.tv_count.setText(String.valueOf(total));
        holder.tv_dname.setText(String.valueOf(allDistricts.get(position).getName()));
        holder.tv_jrccount.setText(String.valueOf(allDistricts.get(position).getJRC()));
        holder.tv_yrccount.setText(String.valueOf(allDistricts.get(position).getYRC()));
        holder.tv_lmcount.setText(String.valueOf(allDistricts.get(position).getMembership()));
        holder.tv_dname.setText(String.valueOf(allDistricts.get(position).getName()));
        holder.tv_dname.setText(String.valueOf(allDistricts.get(position).getName()));
        holder.tv_allcount.setText(String.valueOf(total));

//        try {
//            selectedThemeColor = mCtx.getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);
//            if (selectedThemeColor != -1) {
////            if (selectedThemeColor == R.color.redcroosbg_1) {
////                holder.ll_line.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme1_bg));
////                holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme1_bg));
////                holder.ll_lm.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme1_bg));
////            } else if (selectedThemeColor == R.color.redcroosbg_2) {
////                holder.ll_line.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme2_bg));
////                holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme2_bg));
////                holder.ll_lm.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme2_bg));
////            } else if (selectedThemeColor == R.color.redcroosbg_3) {
////                holder.ll_line.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme3_bg));
////                holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme3_bg));
////                holder.ll_lm.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme3_bg));
////            } else if (selectedThemeColor == R.color.redcroosbg_4) {
////                holder.ll_line.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme4_bg));
////                holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme4_bg));
////                holder.ll_lm.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme4_bg));
////            } else if (selectedThemeColor == R.color.redcroosbg_5) {
////                holder.ll_line.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme5_bg));
////                holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme5_bg));
////                holder.ll_lm.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme5_bg));
////            } else if (selectedThemeColor == R.color.redcroosbg_6) {
////                holder.ll_line.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme6_bg));
////                holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme6_bg));
////                holder.ll_lm.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme6_bg));
////            } else if (selectedThemeColor == R.color.redcroosbg_7) {
////                holder.ll_line.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme7_bg));
////                holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme7_bg));
////                holder.ll_lm.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme7_bg));
////            } else if (selectedThemeColor == R.color.redcroosbg_8) {
////                holder.ll_line.setBackground(mCtx.getResources().getDrawable(R.drawable.tab_background_unselected));
////                holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.tab_background_unselected));
////                holder.ll_lm.setBackground(mCtx.getResources().getDrawable(R.drawable.tab_background_unselected));
////            }
//                holder.ll_line.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));
//                holder.ll_line1.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));
//                holder.mainLayout.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equalsIgnoreCase("d")) {
                    FragmentActivity activity = (FragmentActivity) v.getContext();

                    GlobalDeclaration.FARG_TAG = AllMandalsFragment.class.getSimpleName();
                    Fragment frag = new AllMandalsFragment();
//                String backStateName = frag.getClass().getName();
                    Bundle args = new Bundle();
                    args.putString("did", String.valueOf(data_dashbord.get(position).getId()));
                    frag.setArguments(args);
                    GlobalDeclaration.localDid = data_dashbord.get(position).getId();
                    GlobalDeclaration.type = "";
                    GlobalDeclaration.leveDName = data_dashbord.get(position).getName();

                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();
                } else if (type.equalsIgnoreCase("m")) {

                    FragmentActivity activity = (FragmentActivity) v.getContext();
                    GlobalDeclaration.FARG_TAG = AllVillageFragment.class.getSimpleName();
                    Fragment frag = new AllVillageFragment();
//                String backStateName = frag.getClass().getName();
                    Bundle args = new Bundle();
                    args.putString("mid", String.valueOf(data_dashbord.get(position).getId()));
                    frag.setArguments(args);
                    GlobalDeclaration.localMid = data_dashbord.get(position).getId();
                    GlobalDeclaration.type = "";
                    GlobalDeclaration.leveMName = data_dashbord.get(position).getName();

                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();

                } else if (type.equalsIgnoreCase("v")) {

                    FragmentActivity activity = (FragmentActivity) v.getContext();
                    GlobalDeclaration.FARG_TAG = GetDrilldownFragment.class.getSimpleName();
                    Fragment frag = new GetDrilldownFragment();
//                String backStateName = frag.getClass().getName();
                    Bundle args = new Bundle();
                    args.putString("vid", String.valueOf(allDistricts.get(position).getId()));
                    frag.setArguments(args);
                    GlobalDeclaration.localVid = data_dashbord.get(position).getId();
                    GlobalDeclaration.type = "";
                    GlobalDeclaration.leveVName = data_dashbord.get(position).getName();

                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();

                }
            }
        });

        holder.img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return data_dashbord.size();
    }

    public void filter(String newText) {
        android.util.Log.e("Searchletter", newText);
        newText = newText.toLowerCase(Locale.getDefault());
        data_dashbord.clear();
        if (newText.length() == 0) {
            data_dashbord.addAll(allDistricts);
        } else {
            for (StatelevelDistrictViewCountResponse wp : allDistricts) {

                if (wp.getName().toLowerCase(Locale.getDefault()).contains(newText.toLowerCase())) {
                    data_dashbord.add(wp);
                }
            }

        }
        notifyDataSetChanged();
    }

    class DistrictViewHolder extends RecyclerView.ViewHolder {


        TextView tv_dname, tv_count, ll_line, ll_line0, ll_all, ll_line1, tv_jrccount, tv_yrccount, tv_lmcount, tvallname, tv_allcount;
        RelativeLayout mainLayout;
        ImageView img_share;

        public DistrictViewHolder(View itemView) {
            super(itemView);
            tv_dname = itemView.findViewById(R.id.tv_dname);
            tv_allcount = itemView.findViewById(R.id.tv_allcount);
            tvallname = itemView.findViewById(R.id.tv_alldname);
            tv_jrccount = itemView.findViewById(R.id.tv_jrccount);
            tv_yrccount = itemView.findViewById(R.id.tv_yrccount);
            tv_lmcount = itemView.findViewById(R.id.tv_lmcount);
            tv_count = itemView.findViewById(R.id.tv_count);
            ll_line = itemView.findViewById(R.id.ll_line);
            ll_line1 = itemView.findViewById(R.id.ll_line1);
            ll_line0 = itemView.findViewById(R.id.ll_line0);
//                ll_all = itemView.findViewById(R.id.ll_all);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            img_share = itemView.findViewById(R.id.img_share);

        }
    }

/*
    private void shareImage() {

        // binding.llShare.setVisibility(View.GONE);
        CustomProgressDialog dialog = new CustomProgressDialog(mCtx);
        // dialog.setMessage("Loading...");
        dialog.show();

//        Bitmap bitmap1 = getBitmapFromView(binding.rvAlldistrictwise, binding.rvAlldistrictwise.getChildAt(0).getHeight(),
//                binding.rvAlldistrictwise.getChildAt(0).getWidth());

        Bitmap bitmap1 = getScreenshotFromRecyclerView(binding.rvAlldistrictwise);

        try {
            File cachePath = new File(getActivity().getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png");
            // overwrites this image every time
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
            dialog.dismiss();

        } catch (IOException e) {
            e.printStackTrace();
        }
        File imagePath = new File(getActivity().getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        Uri contentUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".fileprovider",
                newFile);

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, getActivity().getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.setType("image/png");
            getActivity().startActivity(Intent.createChooser(shareIntent, "Share via"));
        }
    }
*/



}
