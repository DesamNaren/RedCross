package in.gov.cgg.redcrossphase1.ui_officer.home_distrcit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.gov.cgg.redcrossphase1.R;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.DistrictViewHolder> {
    Context mCtx;
    List<Top5> districtResponses;

    public DistrictAdapter(Context mCtx, List<Top5> districtResponses) {
        this.mCtx = mCtx;
        this.districtResponses = districtResponses;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recycler_view_layout, parent, false);
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictViewHolder holder, int position) {


        holder.textViewname.setText(districtResponses.get(position).getName());
        holder.tv_enrol.setText(districtResponses.get(position).getCount());
        holder.tv_rank.setText(districtResponses.get(position).getRank());
    }

    @Override
    public int getItemCount() {
        return districtResponses.size();
    }

    class DistrictViewHolder extends RecyclerView.ViewHolder {

        TextView textViewname, tv_enrol, tv_rank;

        public DistrictViewHolder(View itemView) {
            super(itemView);

            textViewname = itemView.findViewById(R.id.tv_topdname);
            tv_enrol = itemView.findViewById(R.id.tv_topenrolment);
            tv_rank = itemView.findViewById(R.id.tv_torank);
        }
    }
}
