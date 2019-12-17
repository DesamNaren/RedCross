package in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.AbstractMemberbean;

import static android.content.Context.MODE_PRIVATE;

public class AbstractMemberAdapter extends RecyclerView.Adapter<AbstractMemberAdapter.BBViewHolder> implements Filterable {
    int selectedThemeColor = -1;
    private Context mCtx;
    private ArrayList<AbstractMemberbean> serveBeans;
    private ArrayList<AbstractMemberbean> mFilteredList;

    public AbstractMemberAdapter(Context mCtx, ArrayList<AbstractMemberbean> serveBeans, int selectedThemeColor) {
        this.mCtx = mCtx;
        this.serveBeans = serveBeans;
        mFilteredList = serveBeans;
        this.selectedThemeColor = selectedThemeColor;

    }

    public AbstractMemberAdapter(FragmentActivity activity, ArrayList<AbstractMemberbean> servList) {

    }

    @NonNull
    @Override
    public AbstractMemberAdapter.BBViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.bstractmemadapter, parent, false);
        return new AbstractMemberAdapter.BBViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AbstractMemberAdapter.BBViewHolder holder, final int position) {
        final AbstractMemberbean serveBean = mFilteredList.get(position);

        // float distance = Float.valueOf(String.format("%.2f", serveBean.getDistance()));

        // holder.tv_distance.setText(distance + " KMs");
        // holder.tv_type.setText(serveBean.getType());

        holder.tv_name.setText(serveBean.getName());
        holder.tv_count.setText(serveBean.getCount());
       /* holder.tv_avail.setText(serveBean.getAvailableWithQty());
        holder.tv_updated_time.setText("Last updated: " + eRaktkoshResponseBean.getLastUpdate());*/


        try {
            selectedThemeColor = mCtx.getSharedPreferences("THEMECOLOR_PREF", MODE_PRIVATE).getInt("theme_color", -1);

            if (selectedThemeColor != -1) {

                holder.v1.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));
                // holder.ll_alldlist.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));


                if (selectedThemeColor == R.color.redcroosbg_1) {
                    holder.v1.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme1_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    holder.v1.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme2_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    holder.v1.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme3_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    holder.v1.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme4_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    holder.v1.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme5_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    holder.v1.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme6_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    holder.v1.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme7_seleetedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    holder.v1.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme8_seleetedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    holder.v1.setBackground(mCtx.getResources().getDrawable(R.drawable.lltheme7_seleetedbg));

                }
            } else {
                holder.v1.setBackgroundColor(mCtx.getResources().getColor(R.color.colorPrimary));

            }
        } catch (Exception e) {
            e.printStackTrace();
            holder.v1.setBackgroundColor(mCtx.getResources().getColor(R.color.colorPrimary));

        }


    }


    @Override
    public int getItemCount() {
        return mFilteredList != null && mFilteredList.size() > 0 ? mFilteredList.size() : 0;
    }

    public ArrayList<AbstractMemberbean> getFilteredData() {
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
                        ArrayList<AbstractMemberbean> filteredList = new ArrayList<>();
                        for (AbstractMemberbean serveBean : serveBeans) {
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
                mFilteredList = (ArrayList<AbstractMemberbean>) filterResults.values;
                notifyDataSetChanged();
//                Snackbar.make(mCtx, "Filterd list"+mFilteredList.size(),Snackbar.LENGTH_SHORT).show();
                getFilteredData();
            }
        };
    }


    class BBViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_count;

        View v1;

        BBViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_data1);
            tv_count = itemView.findViewById(R.id.tv_count1);
            v1 = itemView.findViewById(R.id.view1);
        }
    }


}
