package in.gov.cgg.redcrossphase1.ui_officer_new;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport.StatelevelDistrictViewCountResponse;

public class DistrictLevelAdapter extends RecyclerView.Adapter<DistrictLevelAdapter.DistrictViewHolder> {
    private final ArrayList<StatelevelDistrictViewCountResponse> data_dashbord;
    Context mCtx;
    List<StatelevelDistrictViewCountResponse> allDistricts;
    String type;
    int total;
    int selectedThemeColor = -1;

    public DistrictLevelAdapter(Context mCtx, List<StatelevelDistrictViewCountResponse> allDistricts, String type, int selectedThemeColor) {
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
        View view = LayoutInflater.from(mCtx).inflate(R.layout.districtlevel_adapter, parent, false);
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DistrictViewHolder holder, final int position) {


        total = allDistricts.get(position).getJRC() + allDistricts.get(position).getYRC() + allDistricts.get(position).getMembership();
        //total = allDistricts.get(position).getTotalCounts();

        holder.tv_count.setText(String.valueOf(total));
        holder.tv_dname.setText(String.valueOf(allDistricts.get(position).getName()));


     /*   if (selectedThemeColor != -1) {
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
        }*/

//        holder.cd_district.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                if (type.equalsIgnoreCase("d")) {
//                    FragmentActivity activity = (FragmentActivity) v.getContext();
//
//                    GlobalDeclaration.FARG_TAG = AllMandalsFragment.class.getSimpleName();
//                    Fragment frag = new AllMandalsFragment();
////                String backStateName = frag.getClass().getName();
//                    Bundle args = new Bundle();
//                    args.putString("did", String.valueOf(data_dashbord.get(position).getId()));
//                    frag.setArguments(args);
//                    GlobalDeclaration.localDid = data_dashbord.get(position).getId();
//                    GlobalDeclaration.type = "";
//                    GlobalDeclaration.leveDName = data_dashbord.get(position).getName();
//
//                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
//                            frag).addToBackStack(null).commit();
//                }
//            }
//        });
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


        TextView tv_dname, tv_count;

        public DistrictViewHolder(View itemView) {
            super(itemView);
            tv_dname = itemView.findViewById(R.id.tv_dname);
            tv_count = itemView.findViewById(R.id.tv_count);

        }
    }


}
