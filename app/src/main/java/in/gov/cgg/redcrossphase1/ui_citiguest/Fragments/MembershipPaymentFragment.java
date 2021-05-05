package in.gov.cgg.redcrossphase1.ui_citiguest.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import in.gov.cgg.redcrossphase1.BuildConfig;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.CitiGuestMainActivity;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;

public class MembershipPaymentFragment extends AppCompatActivity {

    CustomProgressDialog progressDialog;
    LinearLayout mainLayoutPayment;
    WebView mWebView;
    String FinalURL;
    private FragmentActivity c;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_webview);

        mainLayoutPayment = findViewById(R.id.mainLayoutPayment);
        mWebView = findViewById(R.id.webviewid);
        progressDialog = new CustomProgressDialog(MembershipPaymentFragment.this);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //setProgressBarVisibility(View.VISIBLE);
                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                FinalURL = url;
                Log.e("TAG", "==== " + url);
                if (url.contains(BuildConfig.SERVER_PAYMENT_URL.concat("0300/Success/"))) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            paymentSuccess();
                        }
                    }, 2000);

                } else if (url.contains(BuildConfig.SERVER_PAYMENT_URL.concat("0399"))
                        || url.contains(BuildConfig.SERVER_PAYMENT_URL.concat("NA"))
                        || url.contains(BuildConfig.SERVER_PAYMENT_URL.concat("0002"))
                        || url.contains(BuildConfig.SERVER_PAYMENT_URL.concat("0001"))
                        || url.contains(BuildConfig.SERVER_PAYMENT_URL.concat("na"))) {
                    paymentFailed();

                }
                progressDialog.dismiss();

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

            }
        });

        GlobalDeclaration.encrpyt = GlobalDeclaration.encrpyt.replace("|", "!");

        String value = GlobalDeclaration.encrpyt;
        String url = GlobalDeclaration.Paymenturl + "?msg=" + value;
        Log.e("TAG", "==== " + url);
        // http://uat2.cgg.gov.in:8081/redcrosspayment/proceedMob?msg=IRCSTS!7507!NA!1!NA!NA!NA!INR!NA!R!ircsts!NA!NA!F!9554949495!fmmhfhtddhy!11-12-2019!11-12-2019!AMR20197507!NA!NA!http://uat2.cgg.gov.in:8081/redcross/getPaymentResponse
        mWebView.loadUrl(url);

        WebSettings webSettings1 = mWebView.getSettings();
        webSettings1.setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int progress) {

                mWebView.setVisibility(View.VISIBLE);

            }
        });
    }


    private void paymentSuccess() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(MembershipPaymentFragment.this);
        builder1.setMessage("Success, Click OK to download ID and certificate.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        Fragment fragment = new DownloadCertificate();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.nav_host_fragment, fragment, DownloadCertificate.class.getSimpleName());
                        transaction.addToBackStack(null);
                        transaction.commitAllowingStateLoss();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void paymentFailed() {

       /* final PrettyDialog dialog = new PrettyDialog(getActivity());
        dialog
                .setTitle("RedCross")
                .setMessage("Failed, Please try again.")

                .setIcon(R.drawable.pdlg_icon_close, R.color.pdlg_color_red, null)

                .addButton(getResources().getString(R.string.ok), R.color.pdlg_color_white, R.color.pdlg_color_red, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        dialog.dismiss();
                        Intent i = new Intent(getActivity(), CitiGuestMainActivity.class);
                        startActivity(i);
                        getActivity().finish();

                    }
                }).setCancelable(false);

        dialog.show();*/
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MembershipPaymentFragment.this);
        builder1.setMessage("Failed, Please try again.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        Intent i = new Intent(MembershipPaymentFragment.this, CitiGuestMainActivity.class);
                        startActivity(i);
                        finish();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
