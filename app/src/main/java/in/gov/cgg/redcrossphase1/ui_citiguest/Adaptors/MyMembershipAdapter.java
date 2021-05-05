package in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.Membership;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.DownloadCertificate;

import static android.content.Context.MODE_PRIVATE;


public class MyMembershipAdapter extends RecyclerView.Adapter<MyMembershipAdapter.DistrictViewHolder> {
    private final List<Membership> membershipList;
    Context mCtx;

    private int selectedThemeColor = -1;


    public MyMembershipAdapter(FragmentActivity activity, List<Membership> studentList) {


        this.mCtx = activity;
        this.membershipList = studentList;

    }

    @NonNull
    @Override
    public MyMembershipAdapter.DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.mymebrships_adapter, parent, false);
        return new MyMembershipAdapter.DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyMembershipAdapter.DistrictViewHolder holder, final int position) {

        if (membershipList != null) {
            holder.tv_dob.setText(membershipList.get(position).getDOB());
            holder.tv_date.setText(membershipList.get(position).getEnrollmentStartDate());
            holder.tv_type.setText(membershipList.get(position).getMemberShipType());
            holder.tv_id.setText(membershipList.get(position).getMemberID());
            holder.tv_name.setText(membershipList.get(position).getName());
            holder.tv_mobile.setText(membershipList.get(position).getMobile());

            holder.btn.setVisibility(View.VISIBLE);
        } else {
            holder.btn.setVisibility(View.GONE);
        }

        try {
            selectedThemeColor = mCtx.getSharedPreferences("THEMECOLOR_PREF",
                    MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                holder.htv_id.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
                holder.htv_name.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
                holder.htv_type.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
                holder.htv_dob.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
                holder.htv_edate.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
                holder.htv_mobile.setTextColor(mCtx.getResources().getColor(selectedThemeColor));
                holder.btn.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));
            }
        } catch (Exception e) {

        }

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalDeclaration.FARG_TAG = DownloadCertificate.class.getSimpleName();
                Fragment fragment = new DownloadCertificate();
                FragmentActivity activity = (FragmentActivity) v.getContext();
                Bundle args = new Bundle();
                args.putString("memId", membershipList.get(position).getMemberID().trim());
                args.putString("mdob", membershipList.get(position).getDOB().trim());
                fragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                        fragment).addToBackStack(null).commit();
            }
        });

    }


    @Override
    public int getItemCount() {
        return membershipList.size();
    }

    class DistrictViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_id, tv_type, tv_date, tv_dob, tv_mobile;
        TextView htv_name, htv_id, htv_type, htv_edate, htv_dob, htv_mobile;

        ImageView im_user;

        TextView btn;

        CardView cv_user;

        public DistrictViewHolder(View itemView) {
            super(itemView);


            htv_name = itemView.findViewById(R.id.tv_headername);
            htv_id = itemView.findViewById(R.id.tv_headermeberId);
            htv_type = itemView.findViewById(R.id.tv_headerMemberType);
            htv_edate = itemView.findViewById(R.id.tv_headerenrollstartrdate);
            htv_dob = itemView.findViewById(R.id.tv_headerdob);
            htv_dob = itemView.findViewById(R.id.tv_headerdob);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_date = itemView.findViewById(R.id.tv_enrollstartrdate);
            tv_dob = itemView.findViewById(R.id.tv_dob);
            tv_id = itemView.findViewById(R.id.tv_memberId);
            tv_mobile = itemView.findViewById(R.id.tv_mobilenumber);
            tv_type = itemView.findViewById(R.id.tv_MemberType);
            cv_user = itemView.findViewById(R.id.cv_user);
            im_user = itemView.findViewById(R.id.image_user);
            btn = itemView.findViewById(R.id.btn);


        }

    }
}
