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

public class Contactus_adaptor extends RecyclerView.Adapter<Contactus_adaptor.myViewHolder> {

    ArrayList<ContactusDetails_Bean> contactarrayList;
    Context context;

    Contactus_adaptor(ArrayList<ContactusDetails_Bean> arrayList, Context context) {
        this.contactarrayList = arrayList;
        this.context = context;
    }

    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvcontactus_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        holder.districtNmae.setText(contactarrayList.get(position).getDistarictName());
        holder.address.setText(contactarrayList.get(position).getAdress());

    }

    @Override
    public int getItemCount() {

        return contactarrayList.size();
    }

    public void setFilter(ArrayList<ContactusDetails_Bean> newList) {
        contactarrayList = new ArrayList<>();
        contactarrayList.addAll(newList);
        notifyDataSetChanged();

    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView districtNmae;
        TextView address;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            districtNmae = itemView.findViewById(R.id.tv_districtName);
            address = itemView.findViewById(R.id.tv_adress);


        }
    }

}