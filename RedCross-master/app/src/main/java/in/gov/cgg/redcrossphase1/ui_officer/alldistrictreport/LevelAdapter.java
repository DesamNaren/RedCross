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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_officer.drilldown.GetDrilldownFragment;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.DistrictViewHolder> {
    private final ArrayList<StatelevelDistrictViewCountResponse> data_dashbord;
    Context mCtx;
    List<StatelevelDistrictViewCountResponse> allDistricts;
    String type;


    public LevelAdapter(Context mCtx, List<StatelevelDistrictViewCountResponse> allDistricts, String type) {
        this.mCtx = mCtx;
        this.type = type;

        this.allDistricts = allDistricts;
        this.data_dashbord = new ArrayList<StatelevelDistrictViewCountResponse>();
        this.data_dashbord.addAll(allDistricts);
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.all_district_adapter, parent, false);
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DistrictViewHolder holder, final int position) {


        holder.tv_alldname.setText(data_dashbord.get(position).getName());
        //  holder.tv_jrcount.setText(allDistricts.toString());
        //holder.tv_yrccount.setText(String.valueOf(allDistricts.getYRC()));
        // holder.tv_lmcunt.setText(String.valueOf(allDistricts.getMembership()));
        holder.tv_totalcount.setText(String.valueOf(data_dashbord.get(position).getCount()));

        holder.cd_district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equalsIgnoreCase("d")) {
                    FragmentActivity activity = (FragmentActivity) v.getContext();
                    Fragment frag = new AllMandalsFragment();
//                String backStateName = frag.getClass().getName();
                    Bundle args = new Bundle();
                    args.putString("did", String.valueOf(data_dashbord.get(position).getId()));
                    frag.setArguments(args);
                    GlobalDeclaration.localDid = data_dashbord.get(position).getId();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();
                } else if (type.equalsIgnoreCase("m")) {

                    FragmentActivity activity = (FragmentActivity) v.getContext();
                    Fragment frag = new AllVillageFragment();
//                String backStateName = frag.getClass().getName();
                    Bundle args = new Bundle();
                    args.putString("mid", String.valueOf(data_dashbord.get(position).getId()));
                    frag.setArguments(args);
                    GlobalDeclaration.localMid = data_dashbord.get(position).getId();

                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();

                } else if (type.equalsIgnoreCase("v")) {

                    FragmentActivity activity = (FragmentActivity) v.getContext();
                    Fragment frag = new GetDrilldownFragment();
//                String backStateName = frag.getClass().getName();
                    //   Bundle args = new Bundle();
                    // args.putString("mid", String.valueOf(allDistricts.get(position).getId()));
                    //frag.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();

                }

            }
        });

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
