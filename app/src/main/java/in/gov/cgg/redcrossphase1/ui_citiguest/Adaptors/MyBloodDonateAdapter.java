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
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MyBlooddonor;

import static android.content.Context.MODE_PRIVATE;

public class MyBloodDonateAdapter extends RecyclerView.Adapter<MyBloodDonateAdapter.DistrictViewHolder> {
    private final List<MyBlooddonor> monList;
    Context mCtx;

    private int selectedThemeColor = -1;


    public MyBloodDonateAdapter(FragmentActivity activity, List<MyBlooddonor> studentList) {


        this.mCtx = activity;
        this.monList = studentList;

    }

    @NonNull
    @Override
    public MyBloodDonateAdapter.DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.myblooddonateadapter, parent, false);
        return new MyBloodDonateAdapter.DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyBloodDonateAdapter.DistrictViewHolder holder, final int position) {

        if (monList != null) {
            if (monList.get(position).getPrevDonationDate().length() > 10) {
                holder.tv_ltdate.setText(monList.get(position).getPrevDonationDate().substring(0, 10));
            }
            if (monList.get(position).getDonateType().length() > 10) {
                holder.tv_dtpe.setText(monList.get(position).getDonateType());
            } else {
                holder.tv_dtpe.setText("NA");
            }
        }

        try {
            selectedThemeColor = mCtx.getSharedPreferences("THEMECOLOR_PREF",
                    MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                holder.tv_hdtype.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
                holder.tv_hlastdate.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
            }
        } catch (Exception e) {

        }


    }


    @Override
    public int getItemCount() {
        return monList.size();
    }

    class DistrictViewHolder extends RecyclerView.ViewHolder {

        TextView tv_hlastdate, tv_hdtype;
        TextView tv_ltdate, tv_dtpe;


        public DistrictViewHolder(View itemView) {
            super(itemView);


            tv_hlastdate = itemView.findViewById(R.id.tv_hlastdate);
            tv_hdtype = itemView.findViewById(R.id.tv_htype);


            tv_ltdate = itemView.findViewById(R.id.tv_lastdate);
            tv_dtpe = itemView.findViewById(R.id.tv_doantiontype);


        }

    }
}


