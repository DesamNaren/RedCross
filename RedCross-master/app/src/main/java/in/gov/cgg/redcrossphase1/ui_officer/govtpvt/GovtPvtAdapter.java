package in.gov.cgg.redcrossphase1.ui_officer.govtpvt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.gov.cgg.redcrossphase1.R;

public class GovtPvtAdapter extends RecyclerView.Adapter<GovtPvtAdapter.DistrictViewHolder> {
    Context mCtx;
    List<Last10day> dayWiseReportCountResponses;
    int selectedThemeColor = -1;

    public GovtPvtAdapter(Context mCtx, List<Last10day> dayWiseReportCountResponses) {
        this.mCtx = mCtx;
        this.dayWiseReportCountResponses = dayWiseReportCountResponses;
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


    }

    @Override
    public int getItemCount() {
        return dayWiseReportCountResponses.size();
    }

    class DistrictViewHolder extends RecyclerView.ViewHolder {

        TextView tvgovname, tvpvtname, tvgovcount, tvpvtcount, tv_alldate;

        public DistrictViewHolder(View itemView) {
            super(itemView);

            tvgovcount = itemView.findViewById(R.id.tv_govcount);
            tvpvtcount = itemView.findViewById(R.id.tv_pvtcount);
            tvgovname = itemView.findViewById(R.id.tv_govname);
            tvpvtname = itemView.findViewById(R.id.tv_pvtname);
            tv_alldate = itemView.findViewById(R.id.tv_alldate);

        }
    }
}
