package in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembershipDetails_Bean;

public class MembershipDetailsAdaptor extends RecyclerView.Adapter<MembershipDetailsAdaptor.myViewHolder> {

    List<MembershipDetails_Bean> MembershipTypeList;
    Context context;
    int selectedThemeColor = -1;


    public MembershipDetailsAdaptor(List<MembershipDetails_Bean> arrayList, Context context, int selectedThemeColor) {
        this.MembershipTypeList = arrayList;
        this.context = context;
        this.selectedThemeColor = selectedThemeColor;

    }

    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_type_list_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        //holder.districtNmae.setText(contactarrayList.get(position).getDistarictName());
        //holder.address.setText(contactarrayList.get(position).getAdress());
        // holder.districtNmae.setTextColor(context.getResources().getColor(selectedThemeColor));

        holder.ROS.setText("Rs. " + MembershipTypeList.get(position).getFees());
        holder.TOM.setText(MembershipTypeList.get(position).getMembershipType());


    }

    @Override
    public int getItemCount() {

        return MembershipTypeList.size();
    }

    public void setFilter(ArrayList<MembershipDetails_Bean> newList) {
        MembershipTypeList = new ArrayList<>();
        MembershipTypeList.addAll(newList);
        notifyDataSetChanged();

    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView TOM;
        TextView ROS;
        //CardView header;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            TOM = itemView.findViewById(R.id.tv_TOM);
            ROS = itemView.findViewById(R.id.tv_ROS);
            //header = itemView.findViewById(R.id.header);


        }
    }
}
