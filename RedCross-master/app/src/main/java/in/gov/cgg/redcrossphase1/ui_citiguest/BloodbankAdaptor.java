package in.gov.cgg.redcrossphase1.ui_citiguest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.gov.cgg.redcrossphase1.R;

public class BloodbankAdaptor extends RecyclerView.Adapter<BloodbankAdaptor.myViewHolder> {

    ArrayList<BloodBankdetails_Bean> arrayList;
    Context context;

    BloodbankAdaptor(ArrayList<BloodBankdetails_Bean> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvlist_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        holder.districtNmae.setText(arrayList.get(position).getDistictName());
        holder.officerName.setText(arrayList.get(position).getOfficerName());
        holder.ofcAddress.setText(arrayList.get(position).getOfcaddress());
        holder.contactNumber.setText(arrayList.get(position).getContactNumber());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setFilter(ArrayList<BloodBankdetails_Bean> newList) {
        arrayList = new ArrayList<>();
        arrayList.addAll(newList);
        notifyDataSetChanged();

    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView districtNmae;
        TextView officerName;
        TextView ofcAddress;
        TextView contactNumber;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            districtNmae = itemView.findViewById(R.id.tv_district);
            officerName = itemView.findViewById(R.id.tv_ofcrName);
            ofcAddress = itemView.findViewById(R.id.tv_adress);
            contactNumber = itemView.findViewById(R.id.tv_contactNumber);

        }
    }
}
