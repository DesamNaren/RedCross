package in.gov.cgg.redcrossphase1.ui_citiguest.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.gov.cgg.redcrossphase1.BuildConfig;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.ContactAdditionscenter;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;

import static android.content.Context.MODE_PRIVATE;

public class NewContactusAdapter extends RecyclerView.Adapter<NewContactusAdapter.myViewHolder> {

    List<ContactAdditionscenter> contactarrayList;
    Context context;
    int selectedThemeColor = -1;
    MaterialCardView cardView;

    public NewContactusAdapter(FragmentActivity activity, List<ContactAdditionscenter> additionscenters, int selectedThemeColor) {
        this.contactarrayList = additionscenters;
        this.context = activity;
        this.selectedThemeColor = selectedThemeColor;
    }

    @Override
    public NewContactusAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvcontactus_item, parent, false);

        return new NewContactusAdapter.myViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, int position) {

        //     holder.share.setVisibility(View.VISIBLE);

        try {
            selectedThemeColor = context.getSharedPreferences("THEMECOLOR_PREF",
                    MODE_PRIVATE).getInt("theme_color", -1);

            if (selectedThemeColor != -1) {

                holder.ll_alldname.setBackgroundColor(context.getResources().getColor(selectedThemeColor));
                // holder.ll_alldlist.setBackgroundColor(mCtx.getResources().getColor(selectedThemeColor));


                if (selectedThemeColor == R.color.redcroosbg_1) {
                    holder.ll_alldname.setBackground(context.getResources().getDrawable(R.drawable.lltheme1_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    holder.ll_alldname.setBackground(context.getResources().getDrawable(R.drawable.lltheme2_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    holder.ll_alldname.setBackground(context.getResources().getDrawable(R.drawable.lltheme3_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    holder.ll_alldname.setBackground(context.getResources().getDrawable(R.drawable.lltheme4_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    holder.ll_alldname.setBackground(context.getResources().getDrawable(R.drawable.lltheme5_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    holder.ll_alldname.setBackground(context.getResources().getDrawable(R.drawable.lltheme6_selectedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    holder.ll_alldname.setBackground(context.getResources().getDrawable(R.drawable.lltheme7_seleetedbg));

                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    holder.ll_alldname.setBackground(context.getResources().getDrawable(R.drawable.lltheme8_seleetedbg));

                } else {
                    holder.ll_alldname.setBackground(context.getResources().getDrawable(R.drawable.lltheme6_selectedbg));

                }
            } else {
                holder.ll_alldname.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

            }
        } catch (Exception e) {
            e.printStackTrace();
            holder.ll_alldname.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

        }
        holder.districtNmae.setText(contactarrayList.get(position).getDistirctid());
        holder.address.setText(String.format("%s\n%s", contactarrayList.get(position).getName(),
                contactarrayList.get(position).getAddress()));


        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // holder.share.setVisibility(View.INVISIBLE);

                shareImage();
            }
        });
    }

    private void shareImage() {

        CustomProgressDialog dialog = new CustomProgressDialog(context);
        dialog.show();

        Bitmap bitmap1 = getBitmapFromView(cardView, cardView.getChildAt(0).getHeight(),
                cardView.getChildAt(0).getWidth());

        //Bitmap bitmap1 = getScreenshotFromRecyclerView(binding.rvAlldistrictwise);

        try {
            File cachePath = new File(context.getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png");
            // overwrites this image every time
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
            dialog.dismiss();

        } catch (IOException e) {
            e.printStackTrace();
        }
        File imagePath = new File(context.getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider",
                newFile);

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, context.getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.setType("image/png");
            context.startActivity(Intent.createChooser(shareIntent, "Share via"));
        }
    }

    //create bitmap from the view
    private Bitmap getBitmapFromView(View view, int height, int width) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }


    @Override
    public int getItemCount() {

        return contactarrayList.size();
    }

    public void setFilter(ArrayList<ContactAdditionscenter> newList) {
        contactarrayList = new ArrayList<>();
        contactarrayList.addAll(newList);
        notifyDataSetChanged();

    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView districtNmae;
        TextView address;
        ImageView share;
        LinearLayout ll_alldname;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            districtNmae = itemView.findViewById(R.id.tv_districtName);
            address = itemView.findViewById(R.id.tv_adress);
            share = itemView.findViewById(R.id.ic_share);
            cardView = itemView.findViewById(R.id.card);
            ll_alldname = itemView.findViewById(R.id.ll_alldname);


        }
    }
}
