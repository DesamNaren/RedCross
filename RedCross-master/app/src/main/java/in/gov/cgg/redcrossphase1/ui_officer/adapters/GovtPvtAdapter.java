package in.gov.cgg.redcrossphase1.ui_officer.adapters;

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
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.Last10day;

import static android.content.Context.MODE_PRIVATE;
import static in.gov.cgg.redcrossphase1.R.color.colorPrimary;

public class GovtPvtAdapter extends RecyclerView.Adapter<GovtPvtAdapter.DistrictViewHolder> {
    Context mCtx;
    List<Last10day> dayWiseReportCountResponses;
    int selectedThemeColor = -1;

    public GovtPvtAdapter(Context mCtx, List<Last10day> dayWiseReportCountResponses, int selectedThemeColor) {
        this.mCtx = mCtx;
        this.dayWiseReportCountResponses = dayWiseReportCountResponses;
        this.selectedThemeColor = selectedThemeColor;
    }

    @NonNull
    @Override
    public GovtPvtAdapter.DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.govtpvt_adapter, parent, false);
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GovtPvtAdapter.DistrictViewHolder holder, final int position) {


        holder.tvgovcount.setText(String.valueOf(dayWiseReportCountResponses.get(position).getGov()));
        holder.tvpvtcount.setText(String.valueOf(dayWiseReportCountResponses.get(position).getPvt()));
        holder.tv_alldate.setText(String.valueOf(dayWiseReportCountResponses.get(position).getDate()));

        try {
            selectedThemeColor = mCtx.getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color",
                    -1);
            if (selectedThemeColor != -1) {

                holder.tvgovname.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
                holder.tvpvtname.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
                holder.ll_alldname.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));
                holder.ll_alldist.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));

            } else {
                holder.tvgovname.setTextColor(mCtx.getResources().getColor(colorPrimary));
                holder.tvpvtname.setTextColor(mCtx.getResources().getColor(colorPrimary));
                holder.ll_alldname.setBackgroundColor(mCtx.getResources().getColor(colorPrimary));
                holder.ll_alldist.setBackgroundColor(mCtx.getResources().getColor(colorPrimary));
            }

        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return dayWiseReportCountResponses.size();
    }

    class DistrictViewHolder extends RecyclerView.ViewHolder {

        TextView tvgovname, tvpvtname, tvgovcount, tvpvtcount, tv_alldate;
        LinearLayout ll_alldname, ll_alldist;

        public DistrictViewHolder(View itemView) {
            super(itemView);

            tvgovcount = itemView.findViewById(R.id.tv_govcount);
            tvpvtcount = itemView.findViewById(R.id.tv_pvtcount);
            tvgovname = itemView.findViewById(R.id.tv_govname);
            tvpvtname = itemView.findViewById(R.id.tv_pvtname);
            tv_alldate = itemView.findViewById(R.id.tv_alldate);
            ll_alldname = itemView.findViewById(R.id.ll_alldname);
            ll_alldist = itemView.findViewById(R.id.ll_alldlist);

        }
    }
}
