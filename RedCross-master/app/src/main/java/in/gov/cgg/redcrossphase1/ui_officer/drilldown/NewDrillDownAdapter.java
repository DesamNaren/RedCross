package in.gov.cgg.redcrossphase1.ui_officer.drilldown;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;

public class NewDrillDownAdapter extends RecyclerView.Adapter<NewDrillDownAdapter.DistrictViewHolder> {
    private final List<StudentListBean> studentList;
    Context mCtx;
    List<String> headStringList;
    int selectedThemeColor = -1;

    private ArrayList<StudentListBean> data_dashbord;


    public NewDrillDownAdapter(FragmentActivity activity, List<String> headersList, List<StudentListBean> studentList,
                               int selectedThemeColor) {


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
        View view = LayoutInflater.from(mCtx).inflate(R.layout.drilldown_adapter_new, parent, false);
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DistrictViewHolder holder, final int position) {

//        holder.htv_id.setText(headStringList.get(2));
//        holder.htv_name.setText(headStringList.get(3));
//        holder.htv_gender.setText(headStringList.get(4));
//        holder.htv_type.setText(headStringList.get(13));

        holder.tv_name.setText(studentList.get(position).getName());//Here paramters are changed run and see result
        holder.tv_id.setText(studentList.get(position).getMemberId());
        holder.tv_gender.setText(studentList.get(position).getGender());
        if (!studentList.get(position).getBloodgp().equalsIgnoreCase("")) {
            holder.tv_type.setText(studentList.get(position).getBloodgp());
        } else {
            holder.tv_type.setText("-");
        }

        holder.cv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity) mCtx;
                Fragment frag = new EnrolledMemberEditFragment();
                GlobalDeclaration.drilldownResponse = studentList.get(position);
                GlobalDeclaration.headStringList = headStringList;
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                        frag).addToBackStack(null).commit();
            }
        });


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
                if (
                        wp.getName().toLowerCase(Locale.getDefault()).contains(newText.toLowerCase()) ||
                                wp.getGender().toLowerCase(Locale.getDefault()).contains(newText.toLowerCase()) ||
                                wp.getMemberId().toLowerCase(Locale.getDefault()).contains(newText.toLowerCase()) ||
                                wp.getBloodgp().toLowerCase(Locale.getDefault()).contains(newText.toLowerCase())

                ) {
                    data_dashbord.add(wp);
                }
            }

        }
        notifyDataSetChanged();
    }

    class DistrictViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_id, tv_gender, tv_type;
        TextView htv_name, htv_id, htv_gender, htv_type;


        CardView cv_user;

        public DistrictViewHolder(View itemView) {
            super(itemView);


            htv_name = itemView.findViewById(R.id.tv_headername);
            htv_id = itemView.findViewById(R.id.tv_headermeberId);
            htv_gender = itemView.findViewById(R.id.tv_headergender);
            htv_type = itemView.findViewById(R.id.tv_headertype);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_id = itemView.findViewById(R.id.tv_memberId);
            tv_gender = itemView.findViewById(R.id.tv_gender);
            tv_type = itemView.findViewById(R.id.tv_type);
            cv_user = itemView.findViewById(R.id.cv_user);


        }
    }
}
