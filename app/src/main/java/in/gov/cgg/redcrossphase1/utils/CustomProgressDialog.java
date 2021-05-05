package in.gov.cgg.redcrossphase1.utils;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import in.gov.cgg.redcrossphase1.R;

public class CustomProgressDialog extends Dialog {
    private CustomProgressDialog customProgressDialog;

    public CustomProgressDialog(Context context) {
        super(context);
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            View view = LayoutInflater.from(context).inflate(R.layout.custom_progress_layout, null);
            ImageView imageprogress = view.findViewById(R.id.imageprogress);
            GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageprogress);
            Glide.with(context).load(R.drawable.loader_black1).into(imageViewTarget);
            //  customProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            setContentView(view);
            if (getWindow() != null)
                this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            setCancelable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    private Dialog dialog;
//    private static final CustomProgressDialog ourInstance = new CustomProgressDialog();
//
//    public static CustomProgressDialog getInstance() {
//        return ourInstance;
//    }
//
//    private CustomProgressDialog() {
//    }
//
//    public void show(Context context) {
//        if (dialog != null && dialog.isShowing()) {
//            return;
//        }
//        dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        dialog.setContentView(R.layout.custom_progress_layout);
//        ImageView imageprogress = dialog.findViewById(R.id.imageprogress);
//        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageprogress);
//        Glide.with(context).load(R.drawable.loader).into(imageViewTarget);
//        dialog.setCancelable(true);
//        dialog.show();
//    }
//
//    public void dismiss() {
//        if (dialog != null && dialog.isShowing()) {
//            dialog.dismiss();
//        }
//    }
}
