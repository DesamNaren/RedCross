package in.gov.cgg.redcrossphase1.ui_officer.home_distrcit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.gov.cgg.redcrossphase1.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.R;

public class TBDistrictAdapter extends RecyclerView.Adapter<TBDistrictAdapter.DistrictViewHolder> {
    Context mCtx;
    List<Top5> districtResponses;

    boolean colorchek, click;


    public TBDistrictAdapter(Context mCtx, List<Top5> districtResponses, boolean b) {
        this.mCtx = mCtx;
        this.districtResponses = districtResponses;
        this.colorchek = b;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recycler_view_layout, parent, false);
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DistrictViewHolder holder, final int position) {

        if (colorchek) {
            holder.tv_rank.setBackgroundColor(mCtx.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.tv_rank.setBackground(mCtx.getResources().getDrawable(R.drawable.tv_top));
        }

        holder.textViewname.setText(districtResponses.get(position).getName());
        holder.tv_enrol.setText(districtResponses.get(position).getCount());
        holder.tv_rank.setText(districtResponses.get(position).getRank());
        if (GlobalDeclaration.role != null) {
            if (!GlobalDeclaration.role.contains("D")) {
                if (districtResponses.get(position).getChairmanName().length() > 1 &&
                        districtResponses.get(position).getChairmanPhoneNo().length() > 1) {
                    holder.ll_chairman.setVisibility(View.VISIBLE);
                    holder.tv_chairmename.setText(districtResponses.get(position).getChairmanName());
                    holder.tv_number.setText(String.format("%s", districtResponses.get(position).getChairmanPhoneNo()));
                    if (districtResponses.get(position).getChairmanEmailId().length() > 1) {
                        holder.ll_email.setVisibility(View.VISIBLE);
                        holder.tv_email.setText(String.format("%s", districtResponses.get(position).getChairmanEmailId()));
                    } else {
                        holder.ll_email.setVisibility(View.GONE);
                    }

                } else {
                    holder.ll_chairman.setVisibility(View.GONE);
                }

            }
        }
        holder.ll_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click) {
                    if (!GlobalDeclaration.role.contains("D")) {
                        click = false;
                        holder.ll_content.setVisibility(View.GONE);
                        if (!(districtResponses.get(position).getChairmanName().length() > 1) ||
                                !(districtResponses.get(position).getChairmanPhoneNo().length() > 1)) {
                            Toast.makeText(mCtx, "No Contact Details", Toast.LENGTH_SHORT).show();
                        }
                    }


                } else {
                    if (!GlobalDeclaration.role.contains("D")) {
                        click = true;
                        holder.ll_content.setVisibility(View.VISIBLE);
                        if (!(districtResponses.get(position).getChairmanName().length() > 1) ||
                                !(districtResponses.get(position).getChairmanPhoneNo().length() > 1)) {
                            Toast.makeText(mCtx, "No Contact Details", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        holder.img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalDeclaration.role != null) {
                    if (!GlobalDeclaration.role.contains("D")) {
                        if (!districtResponses.get(position).getChairmanPhoneNo().trim().equals("")) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_DIAL,
                                        Uri.fromParts("tel", districtResponses.get(position).getChairmanPhoneNo(), null));
                                mCtx.startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mCtx, "Mobile number is empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        holder.img_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalDeclaration.role != null) {
                    if (!GlobalDeclaration.role.contains("D")) {
                        if (!districtResponses.get(position).getChairmanPhoneNo().trim().equals("")) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + districtResponses.get(position)
                                        .getChairmanEmailId()));
                                mCtx.startActivity(intent);
                            } catch (Exception e) {
                                Toast.makeText(mCtx, "Sorry...You don't have any mail app", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mCtx, "Mobile number is empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        holder.img_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalDeclaration.role != null) {
                    if (!GlobalDeclaration.role.contains("D")) {
                        if (!districtResponses.get(position).getChairmanPhoneNo().trim().equals("")) {
                            try {
                                mCtx.startActivity(new Intent(Intent.ACTION_VIEW,
                                        Uri.fromParts("sms", districtResponses.get(position).getChairmanPhoneNo().trim(),
                                                null)));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mCtx, "Mobile number is empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return districtResponses.size();
    }

    class DistrictViewHolder extends RecyclerView.ViewHolder {

        TextView textViewname, tv_enrol, tv_rank, tv_chairmename, tv_number, tv_email;
        ImageView img_call, img_message, img_email;
        LinearLayout ll_chairman, ll_click, ll_email, ll_content;

        public DistrictViewHolder(View itemView) {
            super(itemView);

            textViewname = itemView.findViewById(R.id.tv_topdname);
            tv_enrol = itemView.findViewById(R.id.tv_topenrolment);
            tv_rank = itemView.findViewById(R.id.tv_torank);
            tv_email = itemView.findViewById(R.id.tv_email);
            tv_chairmename = itemView.findViewById(R.id.tv_card_dname);
            tv_number = itemView.findViewById(R.id.tv_mobilenumber);
//            info = itemView.findViewById(R.id.info);
            img_call = itemView.findViewById(R.id.card_call);
            img_message = itemView.findViewById(R.id.card_message);
            img_email = itemView.findViewById(R.id.card_email);
            ll_chairman = itemView.findViewById(R.id.ll_chirman);
            ll_click = itemView.findViewById(R.id.ll_click);
            ll_email = itemView.findViewById(R.id.ll_email);
            ll_content = itemView.findViewById(R.id.ll_content);
        }
    }
}
