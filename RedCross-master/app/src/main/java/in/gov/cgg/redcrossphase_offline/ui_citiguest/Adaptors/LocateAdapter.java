package in.gov.cgg.redcrossphase_offline.ui_citiguest.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import java.util.List;

import in.gov.cgg.redcrossphase_offline.R;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.LocateActivity;


public class LocateAdapter extends RecyclerView.Adapter<LocateAdapter.MyHolder> {

    Context context;
    List<String> locateResponses;
    private JsonObject gsonObject;

    public LocateAdapter(Context context, List<String> locateResponses) {
        this.context = context;
        this.locateResponses = locateResponses;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.locate_row, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.tv_title.setText(locateResponses.get(position));

//        Picasso.with(context).load(list.get(position).getIURL()).into(holder.iv_icon);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LocateActivity) context).onClickCalled(locateResponses.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return locateResponses != null && locateResponses.size() > 0 ? locateResponses.size() : 0;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView iv_icon;
        TextView tv_title;
        LinearLayout layout;

        public MyHolder(View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_title = itemView.findViewById(R.id.tv_title);
            layout = itemView.findViewById(R.id.layout);

        }
    }

}