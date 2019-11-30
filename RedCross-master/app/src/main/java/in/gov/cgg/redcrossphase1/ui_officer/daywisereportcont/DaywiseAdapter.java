package in.gov.cgg.redcrossphase1.ui_officer.daywisereportcont;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.gov.cgg.redcrossphase1.R;

public class DaywiseAdapter extends RecyclerView.Adapter<DaywiseAdapter.DistrictViewHolder> {
    Context mCtx;
    List<DayWiseReportCountResponse> dayWiseReportCountResponses;
    int selectedThemeColor = -1;

    public DaywiseAdapter(Context mCtx, List<DayWiseReportCountResponse> dayWiseReportCountResponses, int selectedThemeColor) {
        this.mCtx = mCtx;
        this.dayWiseReportCountResponses = dayWiseReportCountResponses;
        this.selectedThemeColor = selectedThemeColor;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.all_district_adapter, parent, false);
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DistrictViewHolder holder, final int position) {


        holder.tv_alldname.setText(dayWiseReportCountResponses.get(position).getDate());
        holder.tv_jrcount.setText(String.valueOf(dayWiseReportCountResponses.get(position).getJRC()));
        holder.tv_yrccount.setText(String.valueOf(dayWiseReportCountResponses.get(position).getYRC()));
        holder.tv_lmcunt.setText(String.valueOf(dayWiseReportCountResponses.get(position).getMembership()));
        holder.tv_totalcount.setText(String.valueOf(dayWiseReportCountResponses.get(position).getTotal()));
        holder.tv_alldname.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));
        holder.ll_alldlist.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));
        holder.tv_totaltext.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
        if (selectedThemeColor != -1) {
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
        }


    }

    @Override
    public int getItemCount() {
        return dayWiseReportCountResponses.size();
    }

    class DistrictViewHolder extends RecyclerView.ViewHolder {

        TextView tv_alldname, tv_jrcname, tv_yrcname, tv_lmname, tv_jrcount, tv_yrccount, tv_lmcunt, tv_totalcount, tv_totaltext;
        LinearLayout ll_alldlist, ll_jrc, ll_yrc, ll_lm;

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
            tv_totaltext = itemView.findViewById(R.id.tv_totaltext);
            ll_alldlist = itemView.findViewById(R.id.ll_alldlist);
            ll_jrc = itemView.findViewById(R.id.ll_jrc);
            ll_yrc = itemView.findViewById(R.id.ll_yrc);
            ll_lm = itemView.findViewById(R.id.ll_lm);


        }
    }
}
