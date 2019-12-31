package in.gov.cgg.redcrossphase_offline.ui_citiguest.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.gov.cgg.redcrossphase_offline.R;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans.ContactusDetails_Bean;

import static android.content.Context.MODE_PRIVATE;

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
        try {
            selectedThemeColor = context.getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);

            if (selectedThemeColor != -1) {

                holder.districtNmae.setBackgroundColor(context.getResources().getColor(selectedThemeColor));
                // holder.ll_alldlist.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));


                if (selectedThemeColor == R.color.redcroosbg_1) {
                    holder.districtNmae.setBackground(context.getResources().getDrawable(R.drawable.lltheme1_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    holder.districtNmae.setBackground(context.getResources().getDrawable(R.drawable.lltheme2_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    holder.districtNmae.setBackground(context.getResources().getDrawable(R.drawable.lltheme3_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    holder.districtNmae.setBackground(context.getResources().getDrawable(R.drawable.lltheme4_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    holder.districtNmae.setBackground(context.getResources().getDrawable(R.drawable.lltheme5_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    holder.districtNmae.setBackground(context.getResources().getDrawable(R.drawable.lltheme6_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    holder.districtNmae.setBackground(context.getResources().getDrawable(R.drawable.lltheme7_seleetedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    holder.districtNmae.setBackground(context.getResources().getDrawable(R.drawable.lltheme8_seleetedbg));

                } else {
                    holder.districtNmae.setBackground(context.getResources().getDrawable(R.drawable.lltheme7_seleetedbg));

                }
            } else {
                holder.districtNmae.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

            }
        } catch (Exception e) {
            e.printStackTrace();
            holder.districtNmae.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

        }
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