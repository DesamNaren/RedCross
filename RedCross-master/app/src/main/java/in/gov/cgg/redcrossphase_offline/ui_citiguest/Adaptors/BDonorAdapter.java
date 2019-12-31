package in.gov.cgg.redcrossphase_offline.ui_citiguest.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.gov.cgg.redcrossphase_offline.R;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.Beans.BloodDonorResponse;
import in.gov.cgg.redcrossphase_offline.ui_citiguest.DonorsMapsActivity;

import static android.content.Context.MODE_PRIVATE;

public class BDonorAdapter extends RecyclerView.Adapter<BDonorAdapter.BBViewHolder> implements Filterable {
    private Context mCtx;
    private ArrayList<BloodDonorResponse> bloodDonorResponses;
    private ArrayList<BloodDonorResponse> mFilteredList;
    int selectedThemeColor = -1;

    public BDonorAdapter(Context mCtx, ArrayList<BloodDonorResponse> bloodDonorResponses) {
        this.mCtx = mCtx;
        this.bloodDonorResponses = bloodDonorResponses;
        mFilteredList = bloodDonorResponses;
    }

    public BDonorAdapter() {

    }

    @NonNull
    @Override
    public BBViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.b_donor_adapter, parent, false);
        return new BBViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BBViewHolder holder, final int position) {
        final BloodDonorResponse bloodDonorResponses = mFilteredList.get(position);

        float distance = Float.valueOf(String.format("%.2f", bloodDonorResponses.getDistance()));
        try {
            selectedThemeColor = mCtx.getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);

            if (selectedThemeColor != -1) {

                holder.rl_header.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));
                // holder.ll_alldlist.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));


                if (selectedThemeColor == R.color.redcroosbg_1) {
                    holder.rl_header.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme1_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    holder.rl_header.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme2_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    holder.rl_header.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme3_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    holder.rl_header.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme4_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    holder.rl_header.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme5_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    holder.rl_header.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme6_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    holder.rl_header.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme7_seleetedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    holder.rl_header.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme8_seleetedbg));

                } else {
                    holder.rl_header.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme7_seleetedbg));

                }
            } else {
                holder.rl_header.setBackgroundColor(mCtx.getResources().getColor(R.color.colorPrimary));

            }
        } catch (Exception e) {
            e.printStackTrace();
            holder.rl_header.setBackgroundColor(mCtx.getResources().getColor(R.color.colorPrimary));

        }
        try {
            selectedThemeColor = mCtx.getSharedPreferences("THEMECOLOR_PREF",
                    MODE_PRIVATE).getInt("theme_color", -1);

            if (selectedThemeColor != -1) {


                holder.ll_alldlist.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));


                if (selectedThemeColor == R.color.redcroosbg_1) {
                    holder.ll_alldlist.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme1_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    holder.ll_alldlist.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme2_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    holder.ll_alldlist.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme3_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    holder.ll_alldlist.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme4_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    holder.ll_alldlist.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme5_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    holder.ll_alldlist.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme6_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    holder.ll_alldlist.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme7_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    holder.ll_alldlist.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme8_bg));
                } else {
                    holder.ll_alldlist.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme7_bg));
                }
            } else {
                holder.ll_alldlist.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme7_bg));
            }
        } catch (Exception e) {
            e.printStackTrace();
            holder.ll_alldlist.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme7_bg));

        }
        holder.tv_distance.setText(distance + " KMs");

        holder.tv_blood_type.setText(bloodDonorResponses.getBloodGroup());
        holder.tv_address.setText(bloodDonorResponses.getAddress() + " ," +
                bloodDonorResponses.getDistrict() + " ," +
                bloodDonorResponses.getMandal() + " ," +
                bloodDonorResponses.getVillage());

        holder.tv_name.setText(bloodDonorResponses.getName());
        holder.tv_contact.setText(bloodDonorResponses.getPhoneNo());
        holder.tv_email.setText(bloodDonorResponses.getEmail());

        if (bloodDonorResponses.getPhoneNo() != null) {
            holder.ll_contact.setVisibility(View.VISIBLE);
        } else {
            holder.ll_contact.setVisibility(View.GONE);
        }


        if (bloodDonorResponses.getEmail().contains("@")) {
            holder.ll_email.setVisibility(View.VISIBLE);
            holder.tv_email.setText(bloodDonorResponses.getEmail());
        } else {
            holder.ll_email.setVisibility(View.GONE);
        }

        holder.ll_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bloodDonorResponses.getPhoneNo() != null) {
                    ShowContactAlert(bloodDonorResponses.getPhoneNo());
                }
            }
        });

        holder.ll_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callEmail(bloodDonorResponses.getEmail());
            }
        });

        holder.iv_map_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtx, DonorsMapsActivity.class);
                Bundle bundle = new Bundle();
                ArrayList<BloodDonorResponse> list = new ArrayList<>();
                list.add(bloodDonorResponses);
                bundle.putParcelableArrayList("DONORS_DATA", list);
                bundle.putString("FROM_CLASS", "SINGLE");
                intent.putExtras(bundle);
                mCtx.startActivity(intent);

            }
        });
    }

    private void callEmail(String email) {
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:" + email));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Reg: Request for Blood, raised from Red Cross mobile app");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "");
            mCtx.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (Exception e) {
            Toast.makeText(mCtx, "Sorry...You don't have any mail app", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void ShowContactAlert(String mobNo) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL,
                    Uri.fromParts("tel", mobNo, null));
            mCtx.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mFilteredList != null && mFilteredList.size() > 0 ? mFilteredList.size() : 0;
    }

    public ArrayList<BloodDonorResponse> getFilteredData() {
        return mFilteredList;
    }


    public ArrayList<BloodDonorResponse> getBDonorFilter(String charString) {
        ArrayList<BloodDonorResponse> filteredList = new ArrayList<>();
        try {
            String[] str = charString.split("_");
            for (BloodDonorResponse bloodDonorResponses : bloodDonorResponses) {
                if (!str[0].contains("Select") && !str[1].contains("Select")) {

                    if (!TextUtils.isEmpty(bloodDonorResponses.getBloodGroup())
                            && bloodDonorResponses.getBloodGroup().
                            toLowerCase().replace(" ", "")
                            .equalsIgnoreCase(str[0].trim().toLowerCase())

                            &&

                            !TextUtils.isEmpty(bloodDonorResponses.getDistrict())
                            && bloodDonorResponses.getDistrict().
                            toLowerCase().trim().equalsIgnoreCase(str[1].trim().toLowerCase())) {
                        filteredList.add(bloodDonorResponses);
                    }
                } else if (!str[0].contains("Select") && str[1].contains("Select")) {
                    if (!TextUtils.isEmpty(bloodDonorResponses.getBloodGroup())
                            && bloodDonorResponses.getBloodGroup().
                            toLowerCase().replace(" ", "")
                            .equalsIgnoreCase(str[0].trim().toLowerCase())) {
                        filteredList.add(bloodDonorResponses);
                    }
                } else if (!str[1].contains("Select") && str[0].contains("Select")) {
                    if (!TextUtils.isEmpty(bloodDonorResponses.getBloodGroup())
                            && bloodDonorResponses.getDistrict().
                            toLowerCase()
                            .equalsIgnoreCase(str[1].trim().toLowerCase())) {
                        filteredList.add(bloodDonorResponses);
                    }

                }
            }
            mFilteredList = filteredList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mFilteredList;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = null;
                try {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        mFilteredList = bloodDonorResponses;
                    } else {
                        try {
                            ArrayList<BloodDonorResponse> filteredList = new ArrayList<>();
                            for (BloodDonorResponse bloodDonorResponse : bloodDonorResponses) {
                                if (!TextUtils.isEmpty(bloodDonorResponse.getName())
                                        && bloodDonorResponse.getName().toLowerCase().trim().contains(charString.toLowerCase().trim())) {
                                    filteredList.add(bloodDonorResponse);
                                }
                            }
                            mFilteredList = filteredList;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    filterResults = new FilterResults();
                    filterResults.values = mFilteredList;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<BloodDonorResponse>) filterResults.values;
                notifyDataSetChanged();
                getFilteredData();
            }
        };
    }

    class BBViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_blood_type, tv_address, tv_contact, tv_email, tv_distance;
        LinearLayout ll_contact, ll_email, ll_alldlist;
        RelativeLayout rl_header;
        ImageView iv_map_loc;

        BBViewHolder(View itemView) {
            super(itemView);

            tv_distance = itemView.findViewById(R.id.tv_distance);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_blood_type = itemView.findViewById(R.id.tv_type);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_contact = itemView.findViewById(R.id.tv_contact);
            tv_email = itemView.findViewById(R.id.tv_email);
            ll_contact = itemView.findViewById(R.id.ll_contact);
            ll_email = itemView.findViewById(R.id.ll_email);
            ll_alldlist = itemView.findViewById(R.id.ll_alldlist);
            rl_header = itemView.findViewById(R.id.rl_header);
            iv_map_loc = itemView.findViewById(R.id.iv_map_loc);
        }
    }

}
