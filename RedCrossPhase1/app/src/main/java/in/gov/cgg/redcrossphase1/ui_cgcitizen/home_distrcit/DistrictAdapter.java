package in.gov.cgg.redcrossphase1.ui_cgcitizen.home_distrcit;

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
    List<DistrictResponse> districtResponses;

    public DistrictAdapter(Context mCtx, List<DistrictResponse> districtResponses) {
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
        DistrictResponse hero = districtResponses.get(position);


        holder.textView.setText(hero.getDistrictName1());
    }

    @Override
    public int getItemCount() {
        return districtResponses.size();
    }

    class DistrictViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public DistrictViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_topdname);
        }
    }
}
