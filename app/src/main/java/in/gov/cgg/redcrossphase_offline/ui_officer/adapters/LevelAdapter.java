package in.gov.cgg.redcrossphase_offline.ui_officer.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.gov.cgg.redcrossphase_offline.BuildConfig;
import in.gov.cgg.redcrossphase_offline.R;
import in.gov.cgg.redcrossphase_offline.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase_offline.ui_officer.fragments.AllMandalsFragment;
import in.gov.cgg.redcrossphase_offline.ui_officer.fragments.AllVillageFragment;
import in.gov.cgg.redcrossphase_offline.ui_officer.fragments.GetDrilldownFragment;
import in.gov.cgg.redcrossphase_offline.ui_officer.modelbeans.StatelevelDistrictViewCountResponse;
import in.gov.cgg.redcrossphase_offline.utils.CustomProgressDialog;

import static android.content.Context.MODE_PRIVATE;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.DistrictViewHolder> {
    private final ArrayList<StatelevelDistrictViewCountResponse> data_dashbord;
    Context mCtx;
    List<StatelevelDistrictViewCountResponse> allDistricts;
    String type;
    int total;
    CardView card_share;
    ImageView img_info;

    int selectedThemeColor = -1;

    public LevelAdapter(Context mCtx, List<StatelevelDistrictViewCountResponse> allDistricts, String type, int selectedThemeColor) {
        this.mCtx = mCtx;
        this.type = type;

        this.allDistricts = allDistricts;
        this.data_dashbord = new ArrayList<StatelevelDistrictViewCountResponse>();
        this.data_dashbord.addAll(allDistricts);
        this.selectedThemeColor = selectedThemeColor;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        if (type.equalsIgnoreCase("d")) {

            view = LayoutInflater.from(mCtx).inflate(R.layout.info_district_adapter, parent, false);

        } else {
            view = LayoutInflater.from(mCtx).inflate(R.layout.all_district_adapter, parent, false);
        }
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DistrictViewHolder holder, final int position) {


        total = data_dashbord.get(position).getJRC() + data_dashbord.get(position).getYRC() +
                data_dashbord.get(position).getMembership();

        holder.tv_alldname.setText(data_dashbord.get(position).getName());
        holder.tv_jrcount.setText(String.valueOf(data_dashbord.get(position).getJRC()));
        holder.tv_yrccount.setText(String.valueOf(data_dashbord.get(position).getYRC()));
        holder.tv_lmcunt.setText(String.valueOf(data_dashbord.get(position).getMembership()));
        holder.tv_totalcount.setText(String.valueOf(total));

        try {
            selectedThemeColor = mCtx.getSharedPreferences("THEMECOLOR_PREF",
                    MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                holder.tv_alldname.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));
                holder.ll_alldlist.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));
                holder.tv_totaltext.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
                holder.share.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));

                if (selectedThemeColor == R.color.redcroosbg_1) {
                    holder.ll_jrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme1_bg));
                    holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme1_bg));
                    holder.ll_lm.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme1_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    holder.ll_jrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme2_bg));
                    holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme2_bg));
                    holder.ll_lm.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme2_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    holder.ll_jrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme3_bg));
                    holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme3_bg));
                    holder.ll_lm.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme3_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    holder.ll_jrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme4_bg));
                    holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme4_bg));
                    holder.ll_lm.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme4_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    holder.ll_jrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme5_bg));
                    holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme5_bg));
                    holder.ll_lm.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme5_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    holder.ll_jrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme6_bg));
                    holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme6_bg));
                    holder.ll_lm.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme6_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    holder.ll_jrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme7_bg));
                    holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme7_bg));
                    holder.ll_lm.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme7_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    holder.ll_jrc.setBackground(mCtx.getResources().getDrawable(R.drawable.tab_background_unselected));
                    holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.tab_background_unselected));
                    holder.ll_lm.setBackground(mCtx.getResources().getDrawable(R.drawable.tab_background_unselected));
                }
            } else {
                holder.ll_jrc.setBackground(mCtx.getResources().getDrawable(R.drawable.tab_background_unselected));
                holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.tab_background_unselected));
                holder.ll_lm.setBackground(mCtx.getResources().getDrawable(R.drawable.tab_background_unselected));

                holder.tv_alldname.setBackgroundColor(mCtx.getResources().getColor(R.color.colorPrimary));
                holder.ll_alldlist.setBackgroundColor(mCtx.getResources().getColor(R.color.colorPrimary));
                holder.tv_totaltext.setTextColor(mCtx.getResources().getColor(R.color.colorPrimary));
                holder.share.setBackgroundColor(mCtx.getResources().getColor(R.color.colorPrimary));
            }
        } catch (Exception e) {

        }

        holder.cd_district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (type.equalsIgnoreCase("d")) {
                    //holder.img_info.setVisibility(View.VISIBLE);
                    FragmentActivity activity = (FragmentActivity) v.getContext();

                    GlobalDeclaration.FARG_TAG = AllMandalsFragment.class.getSimpleName();
                    Fragment frag = new AllMandalsFragment();
//                String backStateName = frag.getClass().getName();
                    Bundle args = new Bundle();
                    frag.setArguments(args);
                    if (GlobalDeclaration.role.contains("D")) {
                        GlobalDeclaration.localDid = Integer.valueOf(GlobalDeclaration.districtId);
                        args.putInt("did", GlobalDeclaration.localDid);

                    } else {
                        GlobalDeclaration.localDid = data_dashbord.get(position).getId();
                        args.putInt("did", GlobalDeclaration.localDid);

                    }
                    GlobalDeclaration.type = "";
                    GlobalDeclaration.leveDName = data_dashbord.get(position).getName();

                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();
                } else if (type.equalsIgnoreCase("m")) {

                    //holder.img_info.setVisibility(View.GONE);
                    FragmentActivity activity = (FragmentActivity) v.getContext();
                    GlobalDeclaration.FARG_TAG = AllVillageFragment.class.getSimpleName();
                    Fragment frag = new AllVillageFragment();
//                String backStateName = frag.getClass().getName();
                    Bundle args = new Bundle();
                    args.putInt("mid", data_dashbord.get(position).getId());
//                    if (!GlobalDeclaration.role.contains("D")) {
////                        args.putInt("did", GlobalDeclaration.localDid);
////                    } else {
////                        args.putInt("did", Integer.parseInt(GlobalDeclaration.districtId));
////                    }
                    if (GlobalDeclaration.role.contains("D")) {
                        GlobalDeclaration.localDid = Integer.valueOf(GlobalDeclaration.districtId);
                        args.putInt("did", GlobalDeclaration.localDid);
                    } else {

                        args.putInt("did", GlobalDeclaration.localDid);


                    }
                    frag.setArguments(args);
                    GlobalDeclaration.localMid = data_dashbord.get(position).getId();
                    GlobalDeclaration.type = "";
                    GlobalDeclaration.leveMName = data_dashbord.get(position).getName();

                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();

                } else if (type.equalsIgnoreCase("v")) {
                    // holder.img_info.setVisibility(View.GONE);
                    FragmentActivity activity = (FragmentActivity) v.getContext();
                    GlobalDeclaration.FARG_TAG = GetDrilldownFragment.class.getSimpleName();
                    Fragment frag = new GetDrilldownFragment();
//                String backStateName = frag.getClass().getName();
                    Bundle args = new Bundle();
                    args.putInt("vid", data_dashbord.get(position).getId());
                    args.putInt("mid", GlobalDeclaration.localMid);
                    if (GlobalDeclaration.role.contains("D")) {
                        GlobalDeclaration.localDid = Integer.valueOf(GlobalDeclaration.districtId);
                        args.putInt("did", GlobalDeclaration.localDid);
                    } else {
                        args.putInt("did", GlobalDeclaration.localDid);

                    }
                    frag.setArguments(args);
                    GlobalDeclaration.localVid = data_dashbord.get(position).getId();
                    GlobalDeclaration.type = "";
                    GlobalDeclaration.leveVName = data_dashbord.get(position).getName();


                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();

                }

            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareImage();
            }
        });

        holder.ll_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View bview = LayoutInflater.from(mCtx).inflate(R.layout.btm_sheet_contact, null);

                final BottomSheetDialog dialog = new BottomSheetDialog(mCtx);

                dialog.setContentView(bview);

                TextView tv_cname, tv_mobilenumber, tv_email, designation;
                ImageView mobile, message, close;
                LinearLayout llemail, llphone;
                TextView email;
                tv_cname = bview.findViewById(R.id.memberName);
                tv_mobilenumber = bview.findViewById(R.id.tv_mobilenumber);
                tv_email = bview.findViewById(R.id.tv_email);
                designation = bview.findViewById(R.id.designation);
                mobile = bview.findViewById(R.id.img_call);
                message = bview.findViewById(R.id.img_message);
                email = bview.findViewById(R.id.img_email);
                close = bview.findViewById(R.id.closeBottomSheet);
                llemail = bview.findViewById(R.id.llemail);
                llphone = bview.findViewById(R.id.llmobile);

                if (allDistricts.get(position).getDistrictChairManName() != null) {
                    if (allDistricts.get(position).getDistrictChairManName().length() > 0) {
                        dialog.show();
                    } else {
                        Toast.makeText(mCtx, "No contact details available", Toast.LENGTH_SHORT).show();
                    }
                }
                if (allDistricts.get(position).getPhoneNo() != null) {
                    if (allDistricts.get(position).getPhoneNo().length() > 0) {
                        llphone.setVisibility(View.VISIBLE);
                    } else {
                        llphone.setVisibility(View.GONE);
                    }
                } else {
                    llphone.setVisibility(View.GONE);

                }
                if (allDistricts.get(position).getEmail() != null) {
                    if (allDistricts.get(position).getEmail().length() > 0) {
                        llemail.setVisibility(View.VISIBLE);

                    } else {
                        llemail.setVisibility(View.GONE);
                    }
                } else {
                    llemail.setVisibility(View.GONE);

                }

                tv_cname.setText(allDistricts.get(position).getDistrictChairManName());
                tv_mobilenumber.setText(allDistricts.get(position).getPhoneNo());
                tv_email.setText(allDistricts.get(position).getEmail());

                designation.setText("District Chairman, " + allDistricts.get(position).getName());


                mobile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_DIAL,
                                    Uri.fromParts("tel", allDistricts.get(position).getPhoneNo(), null));
                            mCtx.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            mCtx.startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.fromParts("sms", allDistricts.get(position).getPhoneNo().trim(),
                                            null)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + allDistricts.get(position)
                                    .getEmail()));
                            mCtx.startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(mCtx, "Sorry...You don't have any mail app", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


            }
        });
    }

    private void shareImage() {

        img_info.setVisibility(View.INVISIBLE);
        CustomProgressDialog dialog = new CustomProgressDialog(mCtx);
        dialog.show();

        Bitmap bitmap1 = getBitmapFromView(card_share, card_share.getChildAt(0).getHeight(),
                card_share.getChildAt(0).getWidth());

        //Bitmap bitmap1 = getScreenshotFromRecyclerView(binding.rvAlldistrictwise);

        try {
            File cachePath = new File(mCtx.getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png");
            // overwrites this image every time
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
            dialog.dismiss();

        } catch (IOException e) {
            e.printStackTrace();
        }
        File imagePath = new File(mCtx.getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        Uri contentUri = FileProvider.getUriForFile(mCtx, BuildConfig.APPLICATION_ID + ".fileprovider",
                newFile);

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, mCtx.getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.setType("image/png");
            mCtx.startActivity(Intent.createChooser(shareIntent, "Share via"));
        }
    }

    //create bitmap from the view
    private Bitmap getBitmapFromView(View view, int height, int width) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
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

        TextView tv_alldname, tv_jrcname, tv_yrcname, tv_lmname, tv_jrcount, tv_yrccount, tv_lmcunt, tv_totalcount, tv_totaltext;
        LinearLayout cd_district;
        LinearLayout ll_jrc, ll_yrc, ll_total, ll_lm, ll_alldlist;
        ImageView share;

        public DistrictViewHolder(View itemView) {
            super(itemView);

            tv_alldname = itemView.findViewById(R.id.tv_alldname);
            tv_jrcount = itemView.findViewById(R.id.tv_jrccount);
            tv_yrccount = itemView.findViewById(R.id.tv_yrccount);
            tv_lmcunt = itemView.findViewById(R.id.tv_lmcount);
            tv_jrcname = itemView.findViewById(R.id.tv_jrcnme);
            tv_yrcname = itemView.findViewById(R.id.tv_yrcnme);
            tv_lmname = itemView.findViewById(R.id.tv_lmname);
            tv_totalcount = itemView.findViewById(R.id.tv_totalcount);
            cd_district = itemView.findViewById(R.id.cv_district);
            ll_jrc = itemView.findViewById(R.id.ll_jrc);
            ll_yrc = itemView.findViewById(R.id.ll_yrc);
            ll_total = itemView.findViewById(R.id.ll_total);
            tv_totaltext = itemView.findViewById(R.id.tv_totaltext);
            ll_lm = itemView.findViewById(R.id.ll_lm);
            ll_alldlist = itemView.findViewById(R.id.ll_alldlist);
            img_info = itemView.findViewById(R.id.img_info);
            card_share = itemView.findViewById(R.id.shareCard);
            share = itemView.findViewById(R.id.ic_share);

        }
    }


}
