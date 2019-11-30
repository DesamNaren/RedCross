package in.gov.cgg.redcrossphase1.ui_officer.drilldown;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.gov.cgg.redcrossphase1.R;

public class DrillDownAdapter extends RecyclerView.Adapter<DrillDownAdapter.DistrictViewHolder> {
    private final List<StudentListBean> studentList;
    Context mCtx;
    List<String> headStringList;
    int selectedThemeColor = -1;

    private ArrayList<StudentListBean> data_dashbord;


    public DrillDownAdapter(FragmentActivity activity, List<String> headersList, List<StudentListBean> studentList, int selectedThemeColor) {
        this.mCtx = activity;
        this.headStringList = headersList;
        this.studentList = studentList;
        this.data_dashbord = new ArrayList<StudentListBean>();
        this.data_dashbord.addAll(studentList);
        this.selectedThemeColor = selectedThemeColor;

    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.drilldown_adapter, parent, false);
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DistrictViewHolder holder, final int position) {
        //  holder.htv_district.setText(headStringList.get(0)); //district
        holder.htv_mandal.setText(headStringList.get(0));
        holder.htv_village.setText(headStringList.get(1));
        holder.htv_merId.setText(headStringList.get(2));
        holder.htv_name.setText(headStringList.get(3));
        holder.htv_gender.setText(headStringList.get(4));
        holder.htv_dob.setText(headStringList.get(5));
        holder.htv_mobile.setText(headStringList.get(6));
        holder.htv_bloodgroup.setText(headStringList.get(7));
        holder.htv_email.setText(headStringList.get(8));
        holder.htv_class.setText(headStringList.get(9));
        holder.htv_instname.setText(headStringList.get(10));
        holder.htv_insttpe.setText(headStringList.get(11));  //Setting headers names
        holder.htv_enddate.setText(headStringList.get(12));  //Setting headers names


        //  holder.tv_district.setText(data_dashbord.get(position).getDisrtict());
        holder.tv_mandal.setText(data_dashbord.get(position).getMandal());
        holder.tv_village.setText(data_dashbord.get(position).getVillage());
        holder.tv_name.setText(data_dashbord.get(position).getName());
        holder.tv_merId.setText(data_dashbord.get(position).getMemberId());
        holder.tv_gender.setText(data_dashbord.get(position).getGender());
        holder.tv_dob.setText(data_dashbord.get(position).getDob());
        holder.tv_mobile.setText(data_dashbord.get(position).getPhone());
        holder.tv_bloodgroup.setText(data_dashbord.get(position).getBloodgp());
        holder.tv_email.setText(data_dashbord.get(position).getEmail());
        holder.tv_class.setText(data_dashbord.get(position).getClassName());
        holder.tv_instname.setText(data_dashbord.get(position).getSchoolname());
        holder.tv_insttpe.setText(data_dashbord.get(position).getSchooltype());
        holder.tv_enddate.setText(data_dashbord.get(position).getEndDate());

        //Themes
        if (selectedThemeColor != -1) {
            holder.htv_district.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
            holder.htv_mandal.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
            holder.htv_village.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
            holder.htv_district.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
            holder.htv_merId.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
            holder.htv_name.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
            holder.htv_gender.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
            holder.htv_dob.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
            holder.htv_mobile.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
            holder.htv_bloodgroup.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
            holder.htv_email.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
            holder.htv_class.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
            holder.htv_instname.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
            holder.htv_insttpe.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
            holder.htv_enddate.setTextColor(mCtx.getResources().getColor(selectedThemeColor));

        }

    }

    private void loadThemes() {
    }

    @Override
    public int getItemCount() {
        return data_dashbord.size();
    }

    public void filter(String newText) {

        android.util.Log.e("Searchletter", newText);
        newText = newText.toLowerCase(Locale.getDefault());
        data_dashbord.clear();
        if (newText.length() == 0) {
            data_dashbord.addAll(studentList);
        } else {
            for (StudentListBean wp : studentList) {
//
                if (wp.getDisrtict().toLowerCase(Locale.getDefault()).contains(newText.toLowerCase()) ||
                        wp.getMandal().toLowerCase(Locale.getDefault()).contains(newText.toLowerCase()) ||
                        wp.getVillage().toLowerCase(Locale.getDefault()).contains(newText.toLowerCase()) ||
                        wp.getName().toLowerCase(Locale.getDefault()).contains(newText.toLowerCase()) ||
                        wp.getGender().toLowerCase(Locale.getDefault()).contains(newText.toLowerCase()) ||
                        wp.getBloodgp().toLowerCase(Locale.getDefault()).contains(newText.toLowerCase()) ||
                        wp.getDob().toLowerCase(Locale.getDefault()).contains(newText.toLowerCase()) ||
                        wp.getPhone().toLowerCase(Locale.getDefault()).contains(newText.toLowerCase()) ||
                        wp.getEmail().toLowerCase(Locale.getDefault()).contains(newText.toLowerCase()) ||

                        wp.getClassName().toLowerCase(Locale.getDefault()).contains(newText.toLowerCase()) ||
                        wp.getSchoolname().toLowerCase(Locale.getDefault()).contains(newText.toLowerCase()) ||
                        wp.getMemberId().toLowerCase(Locale.getDefault()).contains(newText.toLowerCase()) ||
                        wp.getEndDate().toLowerCase(Locale.getDefault()).contains(newText.toLowerCase()) ||
                        wp.getSchooltype().toLowerCase(Locale.getDefault()).contains(newText.toLowerCase())
                ) {
                    data_dashbord.add(wp);
                }
            }

        }
        notifyDataSetChanged();
    }

    class DistrictViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_gender, tv_dob, tv_mobile, tv_bloodgroup, tv_email, tv_class, tv_instname,
                tv_insttpe, tv_district, tv_mandal, tv_village;
        TextView htv_name, htv_gender, htv_dob, htv_mobile, htv_bloodgroup, htv_email, htv_class, htv_instname, htv_insttpe,
                htv_district, htv_mandal, htv_village, tv_merId, htv_merId, htv_enddate, tv_enddate;

        public DistrictViewHolder(View itemView) {
            super(itemView);


            htv_name = itemView.findViewById(R.id.tv_headername);
            htv_enddate = itemView.findViewById(R.id.tv_headerenddate);
            htv_merId = itemView.findViewById(R.id.tv_headermeberId);
            htv_gender = itemView.findViewById(R.id.tv_headergender);
            htv_dob = itemView.findViewById(R.id.tv_headerdob);
            htv_mobile = itemView.findViewById(R.id.tv_headermobile);
            htv_bloodgroup = itemView.findViewById(R.id.tv_headerbloodgroup);
            htv_email = itemView.findViewById(R.id.tv_headeremail);
            htv_class = itemView.findViewById(R.id.tv_headerclass);
            htv_instname = itemView.findViewById(R.id.tv_headerinstutename);
            htv_insttpe = itemView.findViewById(R.id.tv_headerinstutetype);
            htv_district = itemView.findViewById(R.id.tv_headerdistrcit);
            htv_mandal = itemView.findViewById(R.id.tv_headermandal);
            htv_village = itemView.findViewById(R.id.tv_headervillage);


            tv_name = itemView.findViewById(R.id.tv_name);
            tv_merId = itemView.findViewById(R.id.tv_memberId);
            tv_enddate = itemView.findViewById(R.id.tv_enddate);
            tv_gender = itemView.findViewById(R.id.tv_gender);
            tv_dob = itemView.findViewById(R.id.tv_do);
            tv_mobile = itemView.findViewById(R.id.tv_mobilenumber);
            tv_bloodgroup = itemView.findViewById(R.id.tv_bloodgroup);
            tv_email = itemView.findViewById(R.id.tv_email);
            tv_class = itemView.findViewById(R.id.tv_class);
            tv_instname = itemView.findViewById(R.id.tv_instaname);
            tv_insttpe = itemView.findViewById(R.id.tv_instatype);
            tv_district = itemView.findViewById(R.id.tv_district);
            tv_mandal = itemView.findViewById(R.id.tv_mandal);
            tv_village = itemView.findViewById(R.id.tv_village);


        }
    }
}
