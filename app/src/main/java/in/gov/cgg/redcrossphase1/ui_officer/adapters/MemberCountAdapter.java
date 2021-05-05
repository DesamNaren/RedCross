package in.gov.cgg.redcrossphase1.ui_officer.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_officer.fragments.GetMembershipDrilldown;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.Val;

import static android.content.Context.MODE_PRIVATE;

public class MemberCountAdapter extends RecyclerView.Adapter<MemberCountAdapter.DistrictViewHolder> {
    private final ArrayList<Val> data_dashbord;

    Context mCtx;


    List<Val> allDistricts;
    String type;
    CardView card_share;
    ImageView img_info;

    int selectedThemeColor = -1;

    public MemberCountAdapter(Context mCtx, List<Val> allDistricts, String type, int selectedThemeColor) {
        this.mCtx = mCtx;
        this.type = type;

        this.allDistricts = allDistricts;
        this.data_dashbord = new ArrayList<Val>();
        this.data_dashbord.addAll(allDistricts);
        this.selectedThemeColor = selectedThemeColor;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;

        view = LayoutInflater.from(mCtx).inflate(R.layout.member_district_adapter, parent, false);


        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DistrictViewHolder holder, final int position) {


        try {
            selectedThemeColor = mCtx.getSharedPreferences("THEMECOLOR_PREF",
                    MODE_PRIVATE).getInt("theme_color", -1);
            if (selectedThemeColor != -1) {
                holder.tv_alldname.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));
                holder.tv_totalcount.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));


            } else {
                selectedThemeColor = mCtx.getResources().getColor(R.color.colorPrimary);
                holder.tv_alldname.setBackgroundColor(mCtx.getResources().getColor(R.color.colorPrimary));
                holder.tv_totalcount.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));


            }
        } catch (Exception e) {
            selectedThemeColor = mCtx.getResources().getColor(R.color.colorPrimary);
            holder.tv_alldname.setBackgroundColor(mCtx.getResources().getColor(R.color.colorPrimary));
            holder.tv_totalcount.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));

        }


        if (String.valueOf(data_dashbord.get(position).getTotal()).equalsIgnoreCase("0")) {
            holder.ll_alldlist.setVisibility(View.GONE);
        } else {
            holder.ll_alldlist.setVisibility(View.VISIBLE);
        }

        holder.tv_totalcount.setText(String.valueOf(data_dashbord.get(position).getTotal()));
        holder.tv_alldname.setText(data_dashbord.get(position).getName());
        holder.tv_lifemembecount.setText(data_dashbord.get(position).getLifemember());
        holder.tv_Patroncount.setText(data_dashbord.get(position).getPatron());
        holder.tv_Vice_Patroncount.setText(data_dashbord.get(position).getVicepatron());
        holder.tv_LifeAssociatecount.setText(data_dashbord.get(position).getLifeassociate());
        holder.tv_AnnualAssociatecount.setText(data_dashbord.get(position).getAnnualassociate());
        holder.tv_AnnualMembercount.setText(data_dashbord.get(position).getAnnualmember());
        holder.tv_institutionalmembercount.setText(data_dashbord.get(position).getInstitutionalmember());

        holder.ll_lifemember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equalsIgnoreCase("d")) {
                    FragmentActivity activity = (FragmentActivity) v.getContext();
                    GlobalDeclaration.FARG_TAG = GetMembershipDrilldown.class.getSimpleName();
                    Fragment frag = new GetMembershipDrilldown();
                    Bundle args = new Bundle();
                    frag.setArguments(args);

                    GlobalDeclaration.localDid = Integer.valueOf(data_dashbord.get(position).getDistrictid());
                    args.putInt("did", GlobalDeclaration.localDid);
                    Log.e("diddd", GlobalDeclaration.localDid.toString());

                    GlobalDeclaration.localmeberId = data_dashbord.get(position).getLifememberId();
                    GlobalDeclaration.localmemType = holder.tv_lifemembename.getText().toString();

                    GlobalDeclaration.leveDName = data_dashbord.get(position).getName();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();
                }

            }
        });
        holder.ll_patron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equalsIgnoreCase("d")) {
                    FragmentActivity activity = (FragmentActivity) v.getContext();
                    GlobalDeclaration.FARG_TAG = GetMembershipDrilldown.class.getSimpleName();
                    Fragment frag = new GetMembershipDrilldown();
                    Bundle args = new Bundle();
                    frag.setArguments(args);
                    if (GlobalDeclaration.role.contains("D")) {
                        GlobalDeclaration.localDid = Integer.valueOf(GlobalDeclaration.districtId);
                        args.putInt("did", GlobalDeclaration.localDid);
                    } else {
                        GlobalDeclaration.localDid = Integer.valueOf(data_dashbord.get(position).getDistrictid());
                        args.putInt("did", GlobalDeclaration.localDid);
                    }
                    GlobalDeclaration.localmeberId = data_dashbord.get(position).getPatronId();
                    GlobalDeclaration.localmemType = holder.tv_Patron_nme.getText().toString();

                    GlobalDeclaration.leveDName = data_dashbord.get(position).getName();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();
                }

            }
        });
        holder.ll_vicepatron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equalsIgnoreCase("d")) {
                    FragmentActivity activity = (FragmentActivity) v.getContext();
                    GlobalDeclaration.FARG_TAG = GetMembershipDrilldown.class.getSimpleName();
                    Fragment frag = new GetMembershipDrilldown();
                    Bundle args = new Bundle();
                    frag.setArguments(args);
                    if (GlobalDeclaration.role.contains("D")) {
                        GlobalDeclaration.localDid = Integer.valueOf(GlobalDeclaration.districtId);
                        args.putInt("did", GlobalDeclaration.localDid);
                    } else {
                        GlobalDeclaration.localDid = Integer.valueOf(data_dashbord.get(position).getDistrictid());
                        args.putInt("did", GlobalDeclaration.localDid);
                    }
                    GlobalDeclaration.localmeberId = data_dashbord.get(position).getVicepatronId();
                    GlobalDeclaration.localmemType = holder.tv_Vice_Patronname.getText().toString();

                    GlobalDeclaration.leveDName = data_dashbord.get(position).getName();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();
                }

            }
        });
        holder.ll_lifeassociate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equalsIgnoreCase("d")) {
                    FragmentActivity activity = (FragmentActivity) v.getContext();
                    GlobalDeclaration.FARG_TAG = GetMembershipDrilldown.class.getSimpleName();
                    Fragment frag = new GetMembershipDrilldown();
                    Bundle args = new Bundle();
                    frag.setArguments(args);
                    if (GlobalDeclaration.role.contains("D")) {
                        GlobalDeclaration.localDid = Integer.valueOf(GlobalDeclaration.districtId);
                        args.putInt("did", GlobalDeclaration.localDid);
                    } else {
                        GlobalDeclaration.localDid = Integer.valueOf(data_dashbord.get(position).getDistrictid());
                        args.putInt("did", GlobalDeclaration.localDid);
                    }
                    GlobalDeclaration.localmeberId = data_dashbord.get(position).getLifeassociateId();
                    GlobalDeclaration.localmemType = holder.tv_LifeAssociatenme.getText().toString();

                    GlobalDeclaration.leveDName = data_dashbord.get(position).getName();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();
                }

            }
        });
        holder.ll_annualassosciate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equalsIgnoreCase("d")) {
                    FragmentActivity activity = (FragmentActivity) v.getContext();
                    GlobalDeclaration.FARG_TAG = GetMembershipDrilldown.class.getSimpleName();
                    Fragment frag = new GetMembershipDrilldown();
                    Bundle args = new Bundle();
                    frag.setArguments(args);
                    if (GlobalDeclaration.role.contains("D")) {
                        GlobalDeclaration.localDid = Integer.valueOf(GlobalDeclaration.districtId);
                        args.putInt("did", GlobalDeclaration.localDid);
                    } else {
                        GlobalDeclaration.localDid = Integer.valueOf(data_dashbord.get(position).getDistrictid());
                        args.putInt("did", GlobalDeclaration.localDid);
                    }
                    GlobalDeclaration.localmeberId = data_dashbord.get(position).getAnnualassociateId();
                    GlobalDeclaration.localmemType = holder.tv_AnnualAssociatenme.getText().toString();

                    GlobalDeclaration.leveDName = data_dashbord.get(position).getName();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();
                }

            }
        });
        holder.ll_annualmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equalsIgnoreCase("d")) {
                    FragmentActivity activity = (FragmentActivity) v.getContext();
                    GlobalDeclaration.FARG_TAG = GetMembershipDrilldown.class.getSimpleName();
                    Fragment frag = new GetMembershipDrilldown();
                    Bundle args = new Bundle();
                    frag.setArguments(args);
                    if (GlobalDeclaration.role.contains("D")) {
                        GlobalDeclaration.localDid = Integer.valueOf(GlobalDeclaration.districtId);
                        args.putInt("did", GlobalDeclaration.localDid);
                    } else {
                        GlobalDeclaration.localDid = Integer.valueOf(data_dashbord.get(position).getDistrictid());
                        args.putInt("did", GlobalDeclaration.localDid);
                    }
                    GlobalDeclaration.localmeberId = data_dashbord.get(position).getAnnualmemberId();
                    GlobalDeclaration.localmemType = holder.tv_AnnualMembername.getText().toString();

                    GlobalDeclaration.leveDName = data_dashbord.get(position).getName();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();
                }

            }
        });
        holder.ll_instmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equalsIgnoreCase("d")) {
                    FragmentActivity activity = (FragmentActivity) v.getContext();
                    GlobalDeclaration.FARG_TAG = GetMembershipDrilldown.class.getSimpleName();
                    Fragment frag = new GetMembershipDrilldown();
                    Bundle args = new Bundle();
                    frag.setArguments(args);
                    if (GlobalDeclaration.role.contains("D")) {
                        GlobalDeclaration.localDid = Integer.valueOf(GlobalDeclaration.districtId);
                        args.putInt("did", GlobalDeclaration.localDid);
                    } else {
                        GlobalDeclaration.localDid = Integer.valueOf(data_dashbord.get(position).getDistrictid());
                        args.putInt("did", GlobalDeclaration.localDid);
                    }
                    GlobalDeclaration.localmeberId = data_dashbord.get(position).getInstitutionalmemberId();
                    GlobalDeclaration.localmemType = holder.tv_institutionalmembername.getText().toString();

                    GlobalDeclaration.leveDName = data_dashbord.get(position).getName();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                            frag).addToBackStack(null).commit();
                }

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
            data_dashbord.addAll(allDistricts);
        } else {
            for (Val wp : allDistricts) {

                if (wp.getName().toLowerCase(Locale.getDefault()).contains(newText.toLowerCase())) {
                    data_dashbord.add(wp);
                }
            }

        }
        notifyDataSetChanged();
    }

    class DistrictViewHolder extends RecyclerView.ViewHolder {

        TextView tv_alldname, tv_totaltext, tv_Patroncount, tv_Vice_Patroncount, tv_AnnualMembercount,
                tv_AnnualAssociatecount, tv_lifemembecount, tv_LifeAssociatecount, tv_Patron_nme,
                tv_Vice_Patronname, tv_AnnualMembername, tv_AnnualAssociatenme, tv_lifemembename,
                tv_LifeAssociatenme, tv_institutionalmembercount, tv_institutionalmembername, tv_totalcount;

        LinearLayout cd_district;
        LinearLayout ll_lifemember, ll_patron, ll_vicepatron, ll_lifeassociate, ll_annualassosciate, ll_annualmember, ll_instmember;
        LinearLayout ll_alldlist;
        ImageView share;

        public DistrictViewHolder(View itemView) {
            super(itemView);

            tv_alldname = itemView.findViewById(R.id.tv_alldname);

            tv_Vice_Patroncount = itemView.findViewById(R.id.tv_Vice_Patroncount);
            tv_Patroncount = itemView.findViewById(R.id.tv_Patroncount);
            tv_AnnualMembercount = itemView.findViewById(R.id.tv_AnnualMembercount);
            tv_AnnualAssociatecount = itemView.findViewById(R.id.tv_AnnualAssociatecount);
            tv_lifemembecount = itemView.findViewById(R.id.tv_lifemembecount);
            tv_LifeAssociatecount = itemView.findViewById(R.id.tv_LifeAssociatecount);
            tv_institutionalmembercount = itemView.findViewById(R.id.tv_institutionalmembercount);

            tv_Vice_Patronname = itemView.findViewById(R.id.tv_Vice_Patronname);
            tv_Patron_nme = itemView.findViewById(R.id.tv_Patron_nme);
            tv_AnnualMembername = itemView.findViewById(R.id.tv_AnnualMembername);
            tv_AnnualAssociatenme = itemView.findViewById(R.id.tv_AnnualAssociatenme);
            tv_lifemembename = itemView.findViewById(R.id.tv_lifemembername);
            tv_LifeAssociatenme = itemView.findViewById(R.id.tv_LifeAssociatenme);
            tv_institutionalmembername = itemView.findViewById(R.id.tv_institutionalmembername);


            cd_district = itemView.findViewById(R.id.cv_district);

            tv_totaltext = itemView.findViewById(R.id.tv_totaltext);
            ll_alldlist = itemView.findViewById(R.id.ll_alldlist);
            img_info = itemView.findViewById(R.id.img_info);
            card_share = itemView.findViewById(R.id.shareCard);
            share = itemView.findViewById(R.id.ic_share);

            tv_totalcount = itemView.findViewById(R.id.tv_totalcount);


            ll_lifemember = itemView.findViewById(R.id.ll_lifemember);
            ll_patron = itemView.findViewById(R.id.ll_Patron);
            ll_vicepatron = itemView.findViewById(R.id.ll_Vice_Patron);
            ll_lifeassociate = itemView.findViewById(R.id.ll_LifeAssociate);
            ll_annualassosciate = itemView.findViewById(R.id.ll_AnnualAssociate);
            ll_annualmember = itemView.findViewById(R.id.ll_AnnualMember);
            ll_instmember = itemView.findViewById(R.id.ll_institutionalmember);

        }
    }


}
