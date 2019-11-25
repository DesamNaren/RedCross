package in.gov.cgg.redcrossphase1.ui_officer.drilldown;

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

public class DrillDownAdapter extends RecyclerView.Adapter<DrillDownAdapter.DistrictViewHolder> {
    Context mCtx;
    List<String> headStringList;
    List<List<String>> studentList;


    public DrillDownAdapter(FragmentActivity activity, List<String> headersList, List<List<String>> studentList) {
        this.mCtx = activity;
        this.headStringList = headersList;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.drilldown_adapter, parent, false);
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DistrictViewHolder holder, final int position) {

        holder.htv_name.setText(headStringList.get(0));
        holder.htv_gender.setText(headStringList.get(1));
        holder.htv_dob.setText(headStringList.get(2));
        holder.htv_mobile.setText(headStringList.get(3));
        holder.htv_bloodgroup.setText(headStringList.get(4));
        holder.htv_email.setText(headStringList.get(5));
        holder.htv_class.setText(headStringList.get(6));
        holder.htv_instname.setText(headStringList.get(7));
        holder.htv_insttpe.setText(headStringList.get(8));  //Setting headers names

        holder.tv_name.setText(studentList.get(position).get(0));
        holder.tv_gender.setText(studentList.get(0).get(1));
        holder.tv_dob.setText(studentList.get(0).get(2));
        holder.tv_mobile.setText(studentList.get(0).get(3));
        holder.tv_bloodgroup.setText(studentList.get(0).get(4));
        holder.tv_email.setText(studentList.get(0).get(5));
        holder.tv_class.setText(studentList.get(0).get(6));
        holder.tv_instname.setText(studentList.get(0).get(7));
        holder.tv_insttpe.setText(studentList.get(0).get(8));

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class DistrictViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_gender, tv_dob, tv_mobile, tv_bloodgroup, tv_email, tv_class, tv_instname, tv_insttpe;
        TextView htv_name, htv_gender, htv_dob, htv_mobile, htv_bloodgroup, htv_email, htv_class, htv_instname, htv_insttpe;

        public DistrictViewHolder(View itemView) {
            super(itemView);


            htv_name = itemView.findViewById(R.id.tv_headername);
            htv_gender = itemView.findViewById(R.id.tv_headergender);
            htv_dob = itemView.findViewById(R.id.tv_headerdob);
            htv_mobile = itemView.findViewById(R.id.tv_headermobile);
            htv_bloodgroup = itemView.findViewById(R.id.tv_headerbloodgroup);
            htv_email = itemView.findViewById(R.id.tv_headeremail);
            htv_class = itemView.findViewById(R.id.tv_headerclass);
            htv_instname = itemView.findViewById(R.id.tv_headerinstutename);
            htv_insttpe = itemView.findViewById(R.id.tv_headerinstutetype);


            tv_name = itemView.findViewById(R.id.tv_name);
            tv_gender = itemView.findViewById(R.id.tv_gender);
            tv_dob = itemView.findViewById(R.id.tv_do);
            tv_mobile = itemView.findViewById(R.id.tv_mobilenumber);
            tv_bloodgroup = itemView.findViewById(R.id.tv_bloodgroup);
            tv_email = itemView.findViewById(R.id.tv_email);
            tv_class = itemView.findViewById(R.id.tv_class);
            tv_instname = itemView.findViewById(R.id.tv_instaname);
            tv_insttpe = itemView.findViewById(R.id.tv_instatype);


        }
    }
}
