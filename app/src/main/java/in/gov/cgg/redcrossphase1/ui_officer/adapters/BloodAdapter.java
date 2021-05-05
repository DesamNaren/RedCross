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
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.BloodGroups;

import static android.content.Context.MODE_PRIVATE;

public class BloodAdapter extends RecyclerView.Adapter<BloodAdapter.DistrictViewHolder> {
    private final Context mCtx;
    private final List<BloodGroups> dayWiseReportCountResponses;
    private int selectedThemeColor;

    public BloodAdapter(Context mCtx, List<BloodGroups> dayWiseReportCountResponses, int selectedThemeColor) {
        this.mCtx = mCtx;
        this.dayWiseReportCountResponses = dayWiseReportCountResponses;
        this.selectedThemeColor = selectedThemeColor;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.blood_adapter, parent, false);
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DistrictViewHolder holder, final int position) {


        holder.tvbname.setText(dayWiseReportCountResponses.get(position).getBloodGroup());
        holder.tvbcount.setText(String.valueOf(dayWiseReportCountResponses.get(position).getCount()));
        try {
            selectedThemeColor = mCtx.getSharedPreferences("THEMECOLOR_PREF",
                    MODE_PRIVATE).getInt("theme_color", -1);

            if (selectedThemeColor != -1) {

                holder.tvbname.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
            }
        } catch (Exception ignored) {

        }

    }

    @Override
    public int getItemCount() {
        return dayWiseReportCountResponses.size();
    }

    class DistrictViewHolder extends RecyclerView.ViewHolder {

        TextView tvbname, tvbcount;
        LinearLayout ll_alldlist;

        DistrictViewHolder(View itemView) {
            super(itemView);

            tvbname = itemView.findViewById(R.id.tv_blooname);
            tvbcount = itemView.findViewById(R.id.tv_bloodcount);

            ll_alldlist = itemView.findViewById(R.id.ll_total);
        }
    }
}
