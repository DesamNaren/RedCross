package in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.ContactusDetails_Bean;

public class Contactus_adaptor extends RecyclerView.Adapter<Contactus_adaptor.myViewHolder> {

    ArrayList<ContactusDetails_Bean> contactarrayList;
    Context context;
    int selectedThemeColor = -1;


    public Contactus_adaptor(ArrayList<ContactusDetails_Bean> arrayList, Context context, int selectedThemeColor) {
        this.contactarrayList = arrayList;
        this.context = context;
        this.selectedThemeColor = selectedThemeColor;

    }

    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvcontactus_item, parent, false);

       /* if (GlobalDeclaration.cordinatorType.equalsIgnoreCase("s")) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_statecordinators, parent, false);

        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvcontactus_item, parent, false);
        }*/
        return new myViewHolder(view);

        //   return new DistrictViewHolder(view);


        //   View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvcontactus_item, parent, false);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        holder.districtNmae.setText(contactarrayList.get(position).getDistarictName());
        holder.address.setText(contactarrayList.get(position).getAdress());

       /* if (GlobalDeclaration.cordinatorType.equalsIgnoreCase("s")) {
            holder.address.setText(contactarrayList.get(position).getAdress());
        } else {
            holder.districtNmae.setText(contactarrayList.get(position).getDistarictName());
            holder.address.setText(contactarrayList.get(position).getAdress());
        }*/
        //   holder.districtNmae.setTextColor(context.getResources().getColor(selectedThemeColor));


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