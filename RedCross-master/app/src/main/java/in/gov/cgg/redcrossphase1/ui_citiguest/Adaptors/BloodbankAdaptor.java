package in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.BloodBankdetails_Bean;

public class BloodbankAdaptor extends RecyclerView.Adapter<BloodbankAdaptor.myViewHolder> {

    ArrayList<BloodBankdetails_Bean> arrayList;
    Context context;
    int selectedThemeColor = -1;


    public BloodbankAdaptor(ArrayList<BloodBankdetails_Bean> arrayList, Context context, int selectedThemeColor) {
        this.arrayList = arrayList;
        this.context = context;
        this.selectedThemeColor = selectedThemeColor;
    }

    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvlist_item, parent, false);
        return new myViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        holder.districtNmae.setText(arrayList.get(position).getDistictName());
        holder.officerName.setText(arrayList.get(position).getOfficerName());
        holder.ofcAddress.setText(arrayList.get(position).getOfcaddress());
        holder.contactNumber.setText(arrayList.get(position).getContactNumber());

        holder.tv_distHeading.setTextColor(context.getResources().getColor(selectedThemeColor));
        holder.tv_placeHeading.setTextColor(context.getResources().getColor(selectedThemeColor));
        holder.tv_contactHeading.setTextColor(context.getResources().getColor(selectedThemeColor));
        holder.tv_medicalofcerHeading.setTextColor(context.getResources().getColor(selectedThemeColor));
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

        TextView tv_distHeading;
        TextView tv_placeHeading;
        TextView tv_contactHeading;
        TextView tv_medicalofcerHeading;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            districtNmae = itemView.findViewById(R.id.tv_district);
            officerName = itemView.findViewById(R.id.tv_ofcrName);
            ofcAddress = itemView.findViewById(R.id.tv_adress);
            contactNumber = itemView.findViewById(R.id.tv_contactNumber);


            tv_distHeading = itemView.findViewById(R.id.tv_distHeading);
            tv_placeHeading = itemView.findViewById(R.id.tv_placeHeading);
            tv_contactHeading = itemView.findViewById(R.id.tv_contactHeading);
            tv_medicalofcerHeading = itemView.findViewById(R.id.tv_medicalofcerHeading);

        }
    }
}
