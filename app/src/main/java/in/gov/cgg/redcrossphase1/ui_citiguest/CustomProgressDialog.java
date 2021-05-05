package in.gov.cgg.redcrossphase1.ui_citiguest;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import in.gov.cgg.redcrossphase1.R;

public class CustomProgressDialog {
    public static CustomProgressDialog mCShowProgress;
    public Dialog mDialog;

    public CustomProgressDialog() {
    }

    public static CustomProgressDialog getInstance() {
        if (mCShowProgress == null) {
            mCShowProgress = new CustomProgressDialog();
        }
        return mCShowProgress;
    }

    public void showProgress(final Context mContext) {
        mDialog = new Dialog(mContext);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.app_progress_dialog);
        mDialog.findViewById(R.id.progress_bar);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setCancelable(false);
        mDialog.show();

    }

    public void hideProgress() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
