package in.gov.cgg.redcrossphase1.ui_officer.alldistrictreport;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.gov.cgg.redcrossphase1.R;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.DistrictViewHolder> {
    Context mCtx;
    List<StatelevelDistrictViewCountResponse> allDistricts;
    String type;


    public LevelAdapter(Context mCtx, List<StatelevelDistrictViewCountResponse> allDistricts, String type) {
        this.mCtx = mCtx;
        this.allDistricts = allDistricts;
        this.type = type;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.all_district_adapter, parent, false);
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DistrictViewHolder holder, final int position) {


        holder.tv_alldname.setText(allDistricts.get(position).getName());
        //  holder.tv_jrcount.setText(allDistricts.toString());
        //holder.tv_yrccount.setText(String.valueOf(allDistricts.getYRC()));
        // holder.tv_lmcunt.setText(String.valueOf(allDistricts.getMembership()));
        holder.tv_totalcount.setText(String.valueOf(allDistricts.get(position).getCount()));

        holder.cd_district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equalsIgnoreCase("d")) {
                    FragmentActivity activity = (FragmentActivity) v.getContext();
                    Fragment frag = new AllMandalsFragment();
//                String backStateName = frag.getClass().getName();
                    Bundle args = new Bundle();
                    args.putString("did", String.valueOf(allDistricts.get(position).getId()));
                    frag.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();
                } else if (type.equalsIgnoreCase("m")) {

                    FragmentActivity activity = (FragmentActivity) v.getContext();
                    Fragment frag = new AllVillageFragment();
//                String backStateName = frag.getClass().getName();
                    Bundle args = new Bundle();
                    args.putString("mid", String.valueOf(allDistricts.get(position).getId()));
                    frag.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();

                } else {

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return allDistricts.size();
    }

    class DistrictViewHolder extends RecyclerView.ViewHolder {

        TextView tv_alldname, tv_jrcname, tv_yrcname, tv_lmname, tv_jrcount, tv_yrccount, tv_lmcunt, tv_totalcount;
        CardView cd_district;

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


        }
    }
}
