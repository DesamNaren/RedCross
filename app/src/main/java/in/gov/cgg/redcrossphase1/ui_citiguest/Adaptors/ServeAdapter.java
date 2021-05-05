package in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import java.util.Arrays;
import java.util.List;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.ServeBean;

import static android.content.Context.MODE_PRIVATE;

public class ServeAdapter extends RecyclerView.Adapter<ServeAdapter.BBViewHolder> implements Filterable {
    int selectedThemeColor = -1;
    private Context mCtx;
    private ArrayList<ServeBean> serveBeans;
    private ArrayList<ServeBean> mFilteredList;

    public ServeAdapter(Context mCtx, ArrayList<ServeBean> serveBeans, int selectedThemeColor) {
        this.mCtx = mCtx;
        this.serveBeans = serveBeans;
        mFilteredList = serveBeans;
        this.selectedThemeColor = selectedThemeColor;

    }

    public ServeAdapter() {

    }

    @NonNull
    @Override
    public ServeAdapter.BBViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.serveadpter, parent, false);
        return new ServeAdapter.BBViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServeAdapter.BBViewHolder holder, final int position) {
        final ServeBean serveBean = mFilteredList.get(position);

        // float distance = Float.valueOf(String.format("%.2f", serveBean.getDistance()));

        // holder.tv_distance.setText(distance + " KMs");
        // holder.tv_type.setText(serveBean.getType());

        holder.tv_name.setText(serveBean.getName());
        holder.tv_address.setText(serveBean.getAddress() + ", " + serveBean.getDistirctid());
        holder.tv_contact.setText(serveBean.getMobileno());
       /* holder.tv_avail.setText(serveBean.getAvailableWithQty());
        holder.tv_updated_time.setText("Last updated: " + eRaktkoshResponseBean.getLastUpdate());*/

        holder.ll_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serveBean.getMobileno() != null && serveBean.getMobileno().contains(",")) {
                    String[] str = serveBean.getMobileno().split(",");
                    List<String> asList = Arrays.asList(str);
                    ShowContactAlert(asList.get(0));
                } else {
                    ShowContactAlert(serveBean.getMobileno());
                }
            }
        });

        if (serveBean.getMobileno() != null) {
            if (serveBean.getMobileno().equals("NA")) {
                holder.ll_contact.setVisibility(View.GONE);
            } else {
                holder.ll_contact.setVisibility(View.VISIBLE);
            }
        }

       /* if (serveBean.get().contains("@")) {
            holder.ll_email.setVisibility(View.VISIBLE);
            holder.tv_email.setText(eRaktkoshResponseBean.getEmail());
        } else {
            holder.ll_email.setVisibility(View.GONE);
        }*/

       /* if (!TextUtils.isEmpty(holder.tv_avail.getText().toString().trim())) {
            holder.ll_avail.setVisibility(View.VISIBLE);
            holder.rl_header.setBackgroundColor(mCtx.getResources().getColor(R.color.green));

        } else {
            holder.ll_avail.setVisibility(View.GONE);
            holder.rl_header.setBackgroundColor(mCtx.getResources().getColor(R.color.li_gray));
        }*/

       /* holder.ll_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callEmail(eRaktkoshResponseBean.getEmail());
            }
        });
*/
      /*  holder.iv_map_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtx, MapsActivity.class);
                Bundle bundle = new Bundle();
                ArrayList<eRaktkoshResponseBean> list = new ArrayList<>();
                list.add(eRaktkoshResponseBean);
                bundle.putParcelableArrayList("E_RAKTAKOSH_DATA", list);
                bundle.putString("FROM_CLASS", "SINGLE");
                intent.putExtras(bundle);
                mCtx.startActivity(intent);

            }
        });*/

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
                    holder.rl_header.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme6_selectedbg));

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
                holder.ll_alldlist.setBackgroundColor(mCtx.getResources().getColor(R.color.colorPrimary));
            }
        } catch (Exception e) {
            e.printStackTrace();
            holder.ll_alldlist.setBackgroundColor(mCtx.getResources().getColor(R.color.colorPrimary));

        }


    }

    private void callEmail(String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:" + email));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Reg: Request for Blood, raised from Red Cross mobile app");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        try {
            mCtx.startActivity(Intent.createChooser(emailIntent, "Send mail..."));

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mCtx, "There is no email client installed.", Toast.LENGTH_SHORT).show();
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

    public ArrayList<ServeBean> getFilteredData() {
        return mFilteredList;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredList = serveBeans;
                } else {
                    try {
                        ArrayList<ServeBean> filteredList = new ArrayList<>();
                        for (ServeBean serveBean : serveBeans) {
                            if (!TextUtils.isEmpty(serveBean.getName())
                                    && serveBean.getName().trim().toLowerCase().contains(charString.trim().toLowerCase())) {
                                filteredList.add(serveBean);
                            }
                        }
                        mFilteredList = filteredList;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<ServeBean>) filterResults.values;
                notifyDataSetChanged();
//                Snackbar.make(mCtx, "Filterd list"+mFilteredList.size(),Snackbar.LENGTH_SHORT).show();
                getFilteredData();
            }
        };
    }

    public ArrayList<ServeBean> getBBFilter(String charString) {

        ArrayList<ServeBean> filteredList = new ArrayList<>();
        for (ServeBean eRaktkoshResponseBean : serveBeans) {
            if (!TextUtils.isEmpty(eRaktkoshResponseBean.getDistirctid())) {
                String[] available = eRaktkoshResponseBean.getDistirctid().split(",");
                List<String> availableList = Arrays.asList(available);

                for (int z = 0; z < availableList.size(); z++) {
                    if (availableList.get(z).trim().equalsIgnoreCase(charString.trim())) {
                        filteredList.add(eRaktkoshResponseBean);
                    }
                }
            }
        }
        mFilteredList = filteredList;
        return mFilteredList;
    }


    class BBViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_address, tv_contact, tv_email, tv_distance, tv_type, tv_avail, tv_updated_time;
        ImageView iv_map_loc;
        LinearLayout ll_contact, ll_email, ll_avail, ll_alldlist;
        RelativeLayout rl_header;

        BBViewHolder(View itemView) {
            super(itemView);

            tv_distance = itemView.findViewById(R.id.tv_distance);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_contact = itemView.findViewById(R.id.tv_contact);
            tv_email = itemView.findViewById(R.id.tv_email);
            tv_avail = itemView.findViewById(R.id.tv_avail);
            iv_map_loc = itemView.findViewById(R.id.iv_map_loc);
            ll_contact = itemView.findViewById(R.id.ll_contact);
            ll_email = itemView.findViewById(R.id.ll_email);
            ll_avail = itemView.findViewById(R.id.ll_avail);
            tv_updated_time = itemView.findViewById(R.id.tv_updated_time);
            ll_alldlist = itemView.findViewById(R.id.ll_alldlist);
            rl_header = itemView.findViewById(R.id.rl_header);
        }
    }


}
