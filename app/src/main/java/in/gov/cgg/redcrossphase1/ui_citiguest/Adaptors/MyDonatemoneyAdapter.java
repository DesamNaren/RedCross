package in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.Memberonlinedonation;

import static android.content.Context.MODE_PRIVATE;


public class MyDonatemoneyAdapter extends RecyclerView.Adapter<MyDonatemoneyAdapter.DistrictViewHolder> {
    private final List<Memberonlinedonation> monList;
    Context mCtx;

    private int selectedThemeColor = -1;


    public MyDonatemoneyAdapter(FragmentActivity activity, List<Memberonlinedonation> studentList) {


        this.mCtx = activity;
        this.monList = studentList;

    }

    @NonNull
    @Override
    public MyDonatemoneyAdapter.DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.moneydontion_adapter, parent, false);
        return new MyDonatemoneyAdapter.DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyDonatemoneyAdapter.DistrictViewHolder holder, final int position) {

        if (monList != null) {
            holder.tv_doatedamount.setText(monList.get(position).getDonationAmount());
            holder.tv_donddistrict.setText(monList.get(position).getDonatedDistrict());
            if (monList.get(position).getRemarks().length() > 0) {
                holder.tv_remarks.setText(monList.get(position).getRemarks());
            } else {
                holder.tv_remarks.setText("NA");
            }
        }

        try {
            selectedThemeColor = mCtx.getSharedPreferences("THEMECOLOR_PREF",
                    MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                holder.htv_donddistrict.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
                holder.htv_doatedamount.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
                holder.htv_rmarks.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
            }
        } catch (Exception e) {

        }


    }


    @Override
    public int getItemCount() {
        return monList.size();
    }

    class DistrictViewHolder extends RecyclerView.ViewHolder {

        TextView tv_doatedamount, tv_donddistrict, tv_remarks;
        TextView htv_doatedamount, htv_donddistrict, htv_rmarks;


        public DistrictViewHolder(View itemView) {
            super(itemView);


            tv_doatedamount = itemView.findViewById(R.id.tv_donatedamount);
            tv_donddistrict = itemView.findViewById(R.id.tv_donateddistrict);
            tv_remarks = itemView.findViewById(R.id.tv_remarks);


            htv_doatedamount = itemView.findViewById(R.id.tv_headerdonatedamount);
            htv_donddistrict = itemView.findViewById(R.id.tv_hdonateddistrict);
            htv_rmarks = itemView.findViewById(R.id.tv_hrmark);


        }

    }
}
