package in.gov.cgg.redcrossphase1.ui_officer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.UserTypesList;

import static android.content.Context.MODE_PRIVATE;

public class InstitutionAdapter extends RecyclerView.Adapter<InstitutionAdapter.DistrictViewHolder> {
    private final ArrayList<UserTypesList> data_dashbord;
    Context mCtx;
    int selectedThemeColor = -1;
    List<UserTypesList> dayWiseReportCountResponses;
    String type;
    int total;
    CardView card_share;
    ImageView img_info;

    public InstitutionAdapter(Context mCtx, List<UserTypesList> dayWiseReportCountResponses, int selectedThemeColor) {
        this.mCtx = mCtx;
        this.dayWiseReportCountResponses = dayWiseReportCountResponses;
        this.selectedThemeColor = selectedThemeColor;


        this.data_dashbord = new ArrayList<UserTypesList>();
        this.data_dashbord.addAll(dayWiseReportCountResponses);
        this.selectedThemeColor = selectedThemeColor;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.all_institution_adapter, parent, false);
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DistrictViewHolder holder, final int position) {

        if (String.valueOf(data_dashbord.get(position).getJRC()).equalsIgnoreCase("0")) {
            holder.ll_jrc.setVisibility(View.GONE);
            holder.v1.setVisibility(View.GONE);
            holder.ll_yrc.setVisibility(View.VISIBLE);
            holder.v2.setVisibility(View.VISIBLE);
        } else if (String.valueOf(data_dashbord.get(position).getYRC()).equalsIgnoreCase("0")) {
            holder.ll_yrc.setVisibility(View.GONE);
            holder.v2.setVisibility(View.GONE);
            holder.ll_jrc.setVisibility(View.VISIBLE);
            holder.v1.setVisibility(View.VISIBLE);
        }

        int total = (data_dashbord.get(position).getJRC()) + (data_dashbord.get(position).
                getYRC());

        holder.tv_alldname.setText(data_dashbord.get(position).getIname());
        holder.tv_jrcount.setText(String.valueOf(data_dashbord.get(position).getJRC()));
        holder.tv_yrccount.setText(String.valueOf(data_dashbord.get(position).getYRC()));
        holder.tv_totalcount.setText(String.valueOf(total));

        try {
            selectedThemeColor = mCtx.getSharedPreferences("THEMECOLOR_PREF",
                    MODE_PRIVATE).getInt("theme_color", -1);

            if (selectedThemeColor != -1) {

                holder.tv_alldname.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));
                holder.ll_alldlist.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));
                holder.ll_alldname.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));
                holder.tv_totaltext.setTextColor(mCtx.getResources().getColor(selectedThemeColor));

                if (selectedThemeColor == R.color.redcroosbg_1) {
                    holder.ll_jrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme1_bg));
                    holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme1_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    holder.ll_jrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme2_bg));
                    holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme2_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    holder.ll_jrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme3_bg));
                    holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme3_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    holder.ll_jrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme4_bg));
                    holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme4_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    holder.ll_jrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme5_bg));
                    holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme5_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    holder.ll_jrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme6_bg));
                    holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme6_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    holder.ll_jrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme7_bg));
                    holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme7_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    holder.ll_jrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme8_bg));
                    holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme8_bg));
                } else {
                    holder.ll_jrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme6_bg));
                    holder.ll_yrc.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme6_bg));
                }
            } else {
                holder.tv_alldname.setBackgroundColor(mCtx.getResources().getColor(R.color.colorPrimary));
                holder.ll_alldlist.setBackgroundColor(mCtx.getResources().getColor(R.color.colorPrimary));
                holder.ll_alldname.setBackgroundColor(mCtx.getResources().getColor(R.color.colorPrimary));
                holder.tv_totaltext.setTextColor(mCtx.getResources().getColor(R.color.colorPrimary));
            }
        } catch (Exception e) {
            e.printStackTrace();
            holder.tv_alldname.setBackgroundColor(mCtx.getResources().getColor(R.color.colorPrimary));
            holder.ll_alldlist.setBackgroundColor(mCtx.getResources().getColor(R.color.colorPrimary));
            holder.ll_alldname.setBackgroundColor(mCtx.getResources().getColor(R.color.colorPrimary));
            holder.tv_totaltext.setTextColor(mCtx.getResources().getColor(R.color.colorPrimary));

        }

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
            data_dashbord.addAll(dayWiseReportCountResponses);
        } else {
            for (UserTypesList wp : dayWiseReportCountResponses) {

                if (wp.getIname().toLowerCase(Locale.getDefault()).contains(newText.toLowerCase())) {
                    data_dashbord.add(wp);
                }
            }

        }
        notifyDataSetChanged();
    }

    class DistrictViewHolder extends RecyclerView.ViewHolder {

        TextView tv_alldname, tv_jrcname, tv_yrcname, tv_jrcount, tv_yrccount, tv_totalcount, tv_totaltext;
        LinearLayout ll_alldlist, ll_jrc, ll_yrc, ll_lm, ll_alldname;
        ImageView ic_share;
        View v1, v2;

        public DistrictViewHolder(View itemView) {
            super(itemView);

            tv_alldname = itemView.findViewById(R.id.tv_alldname);
            tv_jrcount = itemView.findViewById(R.id.tv_jrccount);
            tv_yrccount = itemView.findViewById(R.id.tv_yrccount);
            tv_jrcname = itemView.findViewById(R.id.tv_jrcnme);
            tv_yrcname = itemView.findViewById(R.id.tv_yrcnme);
            tv_totalcount = itemView.findViewById(R.id.tv_totalcount);
            tv_totaltext = itemView.findViewById(R.id.tv_totaltext);
            ll_alldlist = itemView.findViewById(R.id.ll_alldlist);
            ll_jrc = itemView.findViewById(R.id.ll_jrc);
            ll_yrc = itemView.findViewById(R.id.ll_yrc);
            ll_lm = itemView.findViewById(R.id.ll_lm);
            ll_alldname = itemView.findViewById(R.id.ll_alldname);
            ic_share = itemView.findViewById(R.id.ic_share);
            v1 = itemView.findViewById(R.id.v1);
            v2 = itemView.findViewById(R.id.v2);


        }
    }

}
