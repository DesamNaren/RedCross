package in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.gov.cgg.redcrossphase1.R;

public class AllDistrictAdapter extends RecyclerView.Adapter<AllDistrictAdapter.DistrictViewHolder> {
    Context mCtx;
    List<AllDistrict> allDistricts;


    public AllDistrictAdapter(Context mCtx, List<AllDistrict> allDistricts) {
        this.mCtx = mCtx;
        this.allDistricts = allDistricts;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.all_district_adapter, parent, false);
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DistrictViewHolder holder, final int position) {


        holder.tv_alldname.setText(allDistricts.get(position).getDistrictName());
        holder.tv_jrcount.setText(allDistricts.get(position).getJRC().toString());
        holder.tv_yrccount.setText(String.valueOf(allDistricts.get(position).getYRC()));
        holder.tv_lmcunt.setText(String.valueOf(allDistricts.get(position).getMembership()));
        holder.tv_totalcount.setText(String.valueOf(allDistricts.get(position).getTotal()));

    }

    @Override
    public int getItemCount() {
        return allDistricts.size();
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
