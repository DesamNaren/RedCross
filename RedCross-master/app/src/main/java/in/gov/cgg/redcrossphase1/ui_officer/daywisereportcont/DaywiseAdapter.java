package in.gov.cgg.redcrossphase1.ui_officer.daywisereportcont;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.gov.cgg.redcrossphase1.R;

public class DaywiseAdapter extends RecyclerView.Adapter<DaywiseAdapter.DistrictViewHolder> {
    Context mCtx;
    List<DayWiseReportCountResponse> dayWiseReportCountResponses;


    public DaywiseAdapter(Context mCtx, List<DayWiseReportCountResponse> dayWiseReportCountResponses) {
        this.mCtx = mCtx;
        this.dayWiseReportCountResponses = dayWiseReportCountResponses;
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

    }

    @Override
    public int getItemCount() {
        return dayWiseReportCountResponses.size();
    }

    class DistrictViewHolder extends RecyclerView.ViewHolder {

        TextView tv_alldname, tv_jrcname, tv_yrcname, tv_lmname, tv_jrcount, tv_yrccount, tv_lmcunt, tv_totalcount;

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


        }
    }
}